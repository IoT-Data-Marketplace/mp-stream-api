package com.iotdatamp.mpkafkarestproxy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotdatamp.mpkafkarestproxy.config.PropertiesBean;
import com.iotdatamp.mpkafkarestproxy.dto.NewMessagesDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class MessageService {

    private final PropertiesBean properties;
    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public ResponseEntity<?> sendMessages(final String topicName, final NewMessagesDTO newMessagesDTO, Headers tracingHeaders) {
        MediaType mediaType = MediaType.parse("application/vnd.kafka.binary.v2+json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(newMessagesDTO));
        Request request = new Request.Builder()
                .url(properties.getKafkaRestUrl().concat("/topics/").concat(topicName))
                .post(body)
                .headers(tracingHeaders)
                .addHeader("Content-Type", "application/vnd.kafka.binary.v2+json")
                .addHeader("Accept", "application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json")
                .addHeader("cache-control", "no-cache")
                .build();
        Response response = client.newCall(request).execute();
        return ResponseEntity.status(HttpStatus.valueOf(response.code())).body(response.body().string());
    }

    @SneakyThrows
    public ResponseEntity<?> getMessages(final String topicName, final int offset, final int count, Headers tracingHeaders) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(properties.getKafkaRestUrl().concat("/topics/").concat(topicName)
                        .concat("/partitions/0/messages?offset=").concat(String.valueOf(offset)).concat("&count=").concat(String.valueOf(count)))
                .method("GET", null)
                .headers(tracingHeaders)
                .build();
        Response response = client.newCall(request).execute();
        return ResponseEntity.status(HttpStatus.valueOf(response.code())).body(response.body().string());
    }

}
