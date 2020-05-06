package com.iotdatamp.mpkafkarestproxy.controller;

import com.iotdatamp.mpkafkarestproxy.HttpResponseDTO;
import com.iotdatamp.mpkafkarestproxy.dto.CreateTopicDTO;
import com.iotdatamp.mpkafkarestproxy.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<?> createTopic(@RequestBody CreateTopicDTO createTopicDTO) {
        HttpResponseDTO response = topicService.createTopic(createTopicDTO);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode())).body(response.getResponseBody());
    }

}


