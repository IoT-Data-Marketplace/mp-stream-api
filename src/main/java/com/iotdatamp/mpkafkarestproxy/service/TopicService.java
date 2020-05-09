package com.iotdatamp.mpkafkarestproxy.service;

import com.iotdatamp.mpkafkarestproxy.config.PropertiesBean;
import com.iotdatamp.mpkafkarestproxy.dto.CreateTopicDTO;
import com.iotdatamp.mpkafkarestproxy.dto.TopicSummaryDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class TopicService {

    private final PropertiesBean properties;

    @SneakyThrows
    public ResponseEntity<?> createTopic(final CreateTopicDTO createTopicDTO) {
        OkHttpClient client = new OkHttpClient();
        String requestBody = getCreateTopicRequestBody(createTopicDTO.getTopicName());
        MediaType mediaType = MediaType.parse("application/vnd.api+json");
        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(properties.getKafkaRestUrl().concat("/v3/clusters/").concat(properties.getKafkaClusterId()).concat("/topics"))
                .post(body)
                .addHeader("Content-Type", "application/vnd.api+json")
                .addHeader("Accept", "application/vnd.api+json")
                .build();
        log.info("Creating a topic with name: " + createTopicDTO.getTopicName());
        Response response = client.newCall(request).execute();
        return ResponseEntity.status(HttpStatus.valueOf(response.code())).body(response.body().string());
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

    @SneakyThrows
    public ResponseEntity<?> getTopic(String topicName) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(properties.getKafkaRestUrl().concat("/topics/").concat(topicName).concat("/partitions/0/offsets"))
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        TopicSummaryDTO topicSummaryDTO = TopicSummaryDTO.builder()
                .topicName(topicName)
                .topicSize(jsonObject.getInt("end_offset"))
                .build();
        return ResponseEntity.status(HttpStatus.valueOf(response.code())).body(topicSummaryDTO);
    }
}
