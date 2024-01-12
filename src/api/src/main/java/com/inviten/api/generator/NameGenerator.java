package com.inviten.api.generator;
import org.springframework.web.client.RestTemplate;



public class NameGenerator {

    private final String apiUrl;

    public NameGenerator() {
        this.apiUrl = "https://random-word-api.herokuapp.com/word";
    }

    public String getRandomWord() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String[] result = restTemplate.getForObject(apiUrl, String[].class);

            if (result != null && result.length > 0) {
                return result[0];
            } else {
                return "Error: No word received from the API.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
