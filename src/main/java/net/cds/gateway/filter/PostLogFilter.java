package net.cds.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.micrometer.core.instrument.util.IOUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Component
@RefreshScope
public class PostLogFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(PostLogFilter.class);

    @Value("${filter.PostLogFilter.filterOrder}")
    private int filterOrder = 31;
    @Value("${filter.PostLogFilter.pattern}")
    private String pattern = "";

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 31;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return Pattern.matches(pattern, request.getRequestURI());
    }

    //TODO 如果被流控了，则不需要处理
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        JSONObject responseObj = new JSONObject();
        Object zuulResponse = requestContext.get("zuulResponse");
        if (zuulResponse instanceof RibbonHttpResponse) {
            RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
            // 内容重新写入，不然在默认的过滤器SendResponseFilter中会报错
            requestContext.setResponseBody(this.formatRespBody(responseObj, resp));
            this.formatRespHeader(responseObj, resp);
            if (!responseObj.isEmpty()) {
                logger.info(responseObj.toJSONString());
            }
        } else if (zuulResponse != null) {
            logger.info(zuulResponse.toString());
        }

        return null;
    }

    private String formatRespBody(JSONObject responseObj, RibbonHttpResponse resp) {
        try {
            String bodyStr = IOUtils.toString(resp.getBody());
            if (StringUtils.isNotBlank(bodyStr)) {
                try {
                    JSONObject bodyObject = JSONObject.parseObject(bodyStr);
                    responseObj.put("Response", bodyObject);
                } catch (Exception e) {
                    responseObj.put("Response", bodyStr);
                }
            }
            return bodyStr;
        } catch (IOException e) {
            logger.error("get response body error.", e);
            return null;
        }
    }

    private void formatRespHeader(JSONObject responseObj, RibbonHttpResponse resp) {
        HttpHeaders headers = resp.getHeaders();
        JSONObject headerObj = new JSONObject((Map) headers);
        responseObj.put("Header", headerObj);
    }

}
