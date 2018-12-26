package net.cds.gateway.filter;


import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 限流
 */
@Component
public class RateLimiterFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);

    //每秒产生1000个令牌
    //TODO 怎么动态刷新配置参数
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1000);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // ACL 这里增加需要过滤的URL正则表达式
        if ("/product/api/v1/product/find".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        } else if ("/product/api/v1/product/save".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String url = request.getRequestURL().toString();

        if (!RATE_LIMITER.tryAcquire()) {
            //TODO 输出日志带有Request-id
            logger.warn(String.format("discard url: %s", url));
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }

        logger.info(String.format("passed url: %s", url));
        return null;
    }

}
