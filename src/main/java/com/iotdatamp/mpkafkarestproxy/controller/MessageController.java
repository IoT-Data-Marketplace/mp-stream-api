package com.iotdatamp.mpkafkarestproxy.controller;

import com.iotdatamp.mpkafkarestproxy.dto.NewMessagesDTO;
import com.iotdatamp.mpkafkarestproxy.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @SneakyThrows
    @PostMapping("/{topicName}")
    public ResponseEntity<?> createTopic(@PathVariable String topicName, @RequestBody NewMessagesDTO newMessages) {
        return messageService.sendMessages(topicName, newMessages);
    }

}
