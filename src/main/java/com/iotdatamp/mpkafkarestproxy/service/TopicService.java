package com.iotdatamp.mpkafkarestproxy.service;

import com.iotdatamp.mpkafkarestproxy.HttpResponseDTO;
import com.iotdatamp.mpkafkarestproxy.config.PropertiesBean;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import okhttp3.*;
import com.iotdatamp.mpkafkarestproxy.dto.CreateTopicDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class TopicService {

    private final PropertiesBean properties;

    @SneakyThrows
    public HttpResponseDTO createTopic(final CreateTopicDTO createTopicDTO) {
        OkHttpClient client = new OkHttpClient();
        String requestBody = getCreateTopicRequestBody(createTopicDTO.getTopicName());
        MediaType mediaType = MediaType.parse("application/vnd.api+json");
        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(properties.getKafkaRestUrl()+ "/v3/clusters/" + properties.getKafkaClusterId() + "/topics")
                .post(body)
                .addHeader("Content-Type", "application/vnd.api+json")
                .addHeader("Accept", "application/vnd.api+json")
                .build();
        log.info("Creating a topic with name: " + createTopicDTO.getTopicName());
        Response response = client.newCall(request).execute();
        HttpResponseDTO responseDTO = HttpResponseDTO.builder().statusCode(response.code()).responseBody(response.body().string()).build();
        log.info("Response: " + responseDTO.getResponseBody());
        return responseDTO;
    }


    private String getCreateTopicRequestBody(String topicName) {
        JSONObject attributes = new JSONObject();
        attributes.put("topic_name", topicName);
        attributes.put("partitions_count", 1);
        attributes.put("replication_factor", 1);

        JSONArray configs = new JSONArray();
        JSONObject config = new JSONObject();
        config.put("name", "cleanup.policy");
        config.put("value", "compact");
        configs.put(config);
        attributes.put("configs", configs);

        JSONObject data = new JSONObject();
        data.put("attributes", attributes);

        JSONObject root = new JSONObject();
        root.put("data", data);
        return root.toString();
    }

}
