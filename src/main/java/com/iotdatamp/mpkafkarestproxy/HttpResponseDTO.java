package com.iotdatamp.mpkafkarestproxy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpResponseDTO {
    private int statusCode;
    private String responseBody;
}
