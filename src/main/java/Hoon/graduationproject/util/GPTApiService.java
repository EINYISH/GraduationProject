package Hoon.graduationproject.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPTApiService {

    @Value("${openai.api-key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/completions";

    public List<String> generateInterviewQuestions(String resumeContent) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", "Generate interview questions based on this resume: " + resumeContent);
        requestBody.put("max_tokens", 200);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, Map.class);

        // 응답에서 질문 추출 및 String을 List<String>으로 변환
        List<?> choicesList = (List<?>) response.getBody().get("choices"); // List<?>로 중간 캐스팅
        Map<String, Object> choices = null;

        if (choicesList instanceof List && !((List<?>) choicesList).isEmpty()) {
            Object firstChoice = ((List<?>) choicesList).get(0);  // 첫 번째 선택지

            // firstChoice가 Map<String, Object>인지 체크하고 캐스팅
            if (firstChoice instanceof Map) {  // 모든 타입을 Map으로 처리
                @SuppressWarnings("unchecked") // 이 경고를 무시하도록 지정
                Map<String, Object> castedChoice = (Map<String, Object>) firstChoice; // 안전하게 캐스팅
                choices = castedChoice;
            }
        }

        String responseText = choices != null ? (String) choices.get("text") : "";

        // 응답 텍스트를 줄바꿈 또는 특정 구분자로 분리하여 리스트로 변환
        List<String> questions = new ArrayList<>();
        for (String question : responseText.split("\n")) { // 줄바꿈을 기준으로 분리
            if (!question.trim().isEmpty()) {
                questions.add(question.trim());
            }
        }

        return questions;
    }
}