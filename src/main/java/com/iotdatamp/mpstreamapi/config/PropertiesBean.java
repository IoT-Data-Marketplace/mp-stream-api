package com.iotdatamp.mpstreamapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PropertiesBean {

    @Value("${services.kafka.clusterId}")
    private String kafkaClusterId;

    @Value("${services.kafka.restapi.url}")
    private String kafkaRestUrl;

}
