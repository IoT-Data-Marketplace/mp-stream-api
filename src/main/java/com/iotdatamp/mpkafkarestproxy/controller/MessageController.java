package com.iotdatamp.mpkafkarestproxy.controller;

import com.iotdatamp.mpkafkarestproxy.dto.NewMessagesDTO;
import com.iotdatamp.mpkafkarestproxy.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{topicName}")
    public ResponseEntity<?> createTopic(@PathVariable String topicName, @RequestBody NewMessagesDTO newMessages) {
        return messageService.sendMessages(topicName, newMessages);
    }

    @GetMapping("/{topicName}")
    public ResponseEntity<?> getMessages(@PathVariable String topicName,
                                         @RequestParam Integer offset,
                                         @RequestParam Integer count) {
        return messageService.getMessages(topicName, offset, count);
    }

}
