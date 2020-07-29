package com.iotdatamp.mpstreamapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
*
* This is basically the same class as SensorSummaryDTO
* The reason for the different name is that the user is not
* aware of the backend and its terminology but for him there
* is only a sensor. It is the backend's responsibility
* how to handle the Sensor data. In this case each sensor
* has its Kafka Topic.
*
* */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicSummaryDTO {
    private String topicName;
    private int topicSize;
}
