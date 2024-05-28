package com.nighthawk.spring_portfolio.mvc.compile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class JudgeController {

    @Value("${judge0.api.url}")
    private String judge0ApiUrl;

    @Value("${judge0.api.key}")
    private String judge0ApiKey;

    @PostMapping("/compile")
    @CrossOrigin(origins = "*")  // This allows requests from any origin. Adjust as needed for security.
    public ResponseEntity<Map<String, Object>> compileAndRun(@RequestBody Map<String, String> request) {
        String sourceCode = request.get("code");
        String languageId = "62"; // Language ID for Java in Judge0

        RestTemplate restTemplate = new RestTemplate();

        // Prepare request payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("source_code", sourceCode);
        payload.put("language_id", languageId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + judge0ApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        // Send POST request to Judge0
        ResponseEntity<Map> response = restTemplate.postForEntity(judge0ApiUrl + "/submissions?base64_encoded=false&wait=true", entity, Map.class);

        Map<String, Object> responseBody = response.getBody();

        Map<String, Object> result = new HashMap<>();
        if (responseBody != null && responseBody.containsKey("stdout")) {
            result.put("output", responseBody.get("stdout"));
        } else if (responseBody != null && responseBody.containsKey("stderr")) {
            result.put("error", responseBody.get("stderr"));
        } else if (responseBody != null && responseBody.containsKey("compile_output")) {
            result.put("error", responseBody.get("compile_output"));
        }

        return ResponseEntity.ok(result);
    }
}
