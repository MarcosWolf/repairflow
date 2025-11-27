package com.marcoswolf.repairflow.ui.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpClientUtils {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static <T> T post(String url, T body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> entity = new HttpEntity<>(body, headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        return response.getBody();
    }
}
