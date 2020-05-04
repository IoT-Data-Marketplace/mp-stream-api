package com.iotdatamp.mpkafkarestproxy.controller;

import com.iotdatamp.mpkafkarestproxy.dto.CreateTopicDTO;
import com.iotdatamp.mpkafkarestproxy.service.TopicService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
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
    public HttpStatus createTopic(@RequestBody CreateTopicDTO createTopicDTO) {
        return topicService.createTopic(createTopicDTO);
    }

}


