package com.iotdatamp.mpstreamapi.controller;

import com.iotdatamp.mpstreamapi.config.TracingHeaderInterceptorHelper;
import com.iotdatamp.mpstreamapi.dto.CreateTopicDTO;
import com.iotdatamp.mpstreamapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final TracingHeaderInterceptorHelper tracingHeaderInterceptorHelper;

    @GetMapping("/{topicName}")
    public ResponseEntity<?> getTopic(@RequestHeader Map<String, String> headers,
                                      @PathVariable String topicName) {
        return topicService.getTopic(topicName, tracingHeaderInterceptorHelper.getTracingHeaders(headers));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<?> createTopic(@RequestHeader Map<String, String> headers,
                                         @RequestBody CreateTopicDTO createTopicDTO) {
        return topicService.createTopic(createTopicDTO, tracingHeaderInterceptorHelper.getTracingHeaders(headers));
    }

}


