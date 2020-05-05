package com.iotdatamp.mpkafkarestproxy.controller;

import com.iotdatamp.mpkafkarestproxy.dto.CreateTopicDTO;
import com.iotdatamp.mpkafkarestproxy.service.TopicService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity createTopic(@RequestBody CreateTopicDTO createTopicDTO) {
        return new ResponseEntity(topicService.createTopic(createTopicDTO));
    }

}


