package com.iotdatamp.mpstreamapi.controller;

import com.iotdatamp.mpstreamapi.config.TracingHeaderInterceptorHelper;
import com.iotdatamp.mpstreamapi.dto.NewMessagesDTO;
import com.iotdatamp.mpstreamapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final TracingHeaderInterceptorHelper tracingHeaderInterceptorHelper;

    @PostMapping("/{topicName}")
    public ResponseEntity<?> publishMessage(@RequestHeader Map<String, String> headers,
                                            @PathVariable String topicName,
                                            @RequestBody NewMessagesDTO newMessages) {
        return messageService.sendMessages(topicName, newMessages, tracingHeaderInterceptorHelper.getTracingHeaders(headers));
    }

    @GetMapping("/{topicName}")
    public ResponseEntity<?> getMessages(@RequestHeader Map<String, String> headers,
                                         @PathVariable String topicName,
                                         @RequestParam Integer offset,
                                         @RequestParam Integer count) {
        return messageService.getMessages(topicName, offset, count, tracingHeaderInterceptorHelper.getTracingHeaders(headers));
    }

}
