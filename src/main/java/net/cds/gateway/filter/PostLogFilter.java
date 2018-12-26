package net.cds.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.util.IOUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Component
public class PostLogFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(PostLogFilter.class);

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
        return true; // 默认放行
    }

    //TODO 如果被流空了，则不需要做
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
            if (responseObj != null) {
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
