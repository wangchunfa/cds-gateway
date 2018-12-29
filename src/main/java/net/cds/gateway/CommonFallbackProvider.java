package net.cds.gateway;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import net.cds.gateway.constant.CommonErrorCode;
import net.cds.gateway.filter.PostLogFilter;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

@Component
public class CommonFallbackProvider implements FallbackProvider {
    private static final Logger logger = LoggerFactory.getLogger(PostLogFilter.class);

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT, CommonErrorCode.SERVICE_UNAVAILABLE, cause.getMessage());
        } else if (cause instanceof HttpHostConnectException) {
            return response(HttpStatus.SERVICE_UNAVAILABLE, CommonErrorCode.SERVICE_UNAVAILABLE, cause.getMessage());
        } else if (cause instanceof SocketTimeoutException) {
            return response(HttpStatus.REQUEST_TIMEOUT, CommonErrorCode.SERVICE_UNAVAILABLE_READ_TIMEOUT, cause.getMessage());
        } else {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, CommonErrorCode.INTERNAL_ERROR, cause.getMessage());
        }
    }

    private ClientHttpResponse response(final HttpStatus status, final CommonErrorCode errorCode,
                                        final String detailMessage) {
        ClientHttpResponse clientHttpResponse = new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                String bodyText = String.format("{\"code\": \"%s\",\"msg\": \"%s\"}",
                        errorCode.getCode(), errorCode.getMsg() + ": " + detailMessage);
                logger.error(bodyText);
                return new ByteArrayInputStream(bodyText.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
        return clientHttpResponse;
    }

}
