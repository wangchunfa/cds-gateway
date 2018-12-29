package net.cds.gateway.filter;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;


import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@RefreshScope
public class PreLogFilter extends ZuulFilter {
    private static final String REQUEST_ID = "request-id";
    private static final Logger logger = LoggerFactory.getLogger(PreLogFilter.class);

    @Value("${filter.PreLogFilter.filterOrder}")
    private int filterOrder = 20;
    @Value("${filter.PreLogFilter.pattern}")
    private String pattern = "";

    /**
     * 过滤器类型，前置过滤器
     *
     * @return pre
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，越小越先执行
     *
     * @return 20
     */
    @Override
    public int filterOrder() {
        return filterOrder;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return Pattern.matches(pattern, request.getRequestURI());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        setTraceId(requestContext);
        logRequestInfo(requestContext);
        return null;
    }

    /**
     * 记录请求信息
     *
     * @param requestContext 请求context
     */
    private void logRequestInfo(RequestContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        JSONObject requestObj = new JSONObject();
        requestObj.put("Method", request.getMethod());
        requestObj.put("URL", request.getRequestURL());
        this.formatReqParams(requestObj, request);
        this.formatReqBody(requestObj, request);
        this.formatReqHeader(requestObj, request);
        logger.info(requestObj.toJSONString());
    }

    private void formatReqParams(JSONObject requestObj, HttpServletRequest request) {
        try {
            Map map = request.getParameterMap();
            requestObj.put("Params", new JSONObject(map));
        } catch (Exception e) {
            logger.error("get request params error.", e);
        }
    }

    private void formatReqBody(JSONObject requestObj, HttpServletRequest request) {
        try {
            String bodyStr = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
            try {
                requestObj.put("Body", JSONObject.parseObject(bodyStr));
            } catch (JSONException ex1) {
                requestObj.put("Body", bodyStr);
            }
        } catch (IOException e) {
            logger.error("get request body error.", e);
        }

    }

    private void formatReqHeader(JSONObject requestObj, HttpServletRequest request) {
        Enumeration headerNames = request.getHeaderNames();
        JSONObject headerObj = new JSONObject();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            headerObj.put(key, request.getHeader(key));
        }
        requestObj.put("Header", headerObj);
    }


    /**
     * 设置链路跟踪ID
     *
     * @param requestContext 请求context
     */
    private void setTraceId(RequestContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        String requestId = request.getHeader(REQUEST_ID);
        if (StringUtils.isBlank(requestId)) {
            requestContext.addZuulRequestHeader(REQUEST_ID, UUID.randomUUID().toString().toUpperCase());
        }
    }

}
