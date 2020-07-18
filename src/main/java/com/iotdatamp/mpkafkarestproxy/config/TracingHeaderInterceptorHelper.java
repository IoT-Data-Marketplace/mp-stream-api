package com.iotdatamp.mpkafkarestproxy.config;

import lombok.extern.java.Log;
import okhttp3.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log
@Component
public class TracingHeaderInterceptorHelper {

    //    https://istio.io/latest/docs/tasks/observability/distributed-tracing/overview/#trace-context-propagation
    public Headers getTracingHeaders(Map<String, String> requestHeadersMap) {
        Map<String, String> headersMap = new HashMap<>();
        String[] tracingHeaders = new String[]{
                "x-request-id",
                "x-b3-traceid",
                "x-b3-spanid",
                "x-b3-parentspanid",
                "x-b3-sampled",
                "x-b3-flags",
                "x-ot-span-context",
        };
        for (String tracingHeader : tracingHeaders) {
            try {
                if (requestHeadersMap.get(tracingHeader) != null)
                    headersMap.put(tracingHeader, requestHeadersMap.get(tracingHeader));
            } catch (Exception e) {
                log.warning("Header".concat(tracingHeader).concat(" not found"));
            }
        }
        return Headers.of(headersMap);
    }


}
