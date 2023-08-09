package project.avatar.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.avatar.api.dto.ProductDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleSearchService {
    private static final String URL_TEMPLATE = "https://www.googleapis.com/customsearch/v1?cx={cx}&key={key}&q={q}";

    @Value("${google.search.api.key}")
    private String apiKey;

    @Value("${google.search.engine.id}")
    private String searchEngineId;

    public List<ProductDTO> searchProducts(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String finalUrl = URL_TEMPLATE.replaceAll("\\{cx\\}", searchEngineId)
                .replaceAll("\\{key\\}", apiKey)
                .replaceAll("\\{q\\}", query);

        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

        List<ProductDTO> products = new ArrayList<>();
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode items = rootNode.path("items");

                for (JsonNode item : items) {
                    String title = item.path("title").textValue();
                    String imageUrl = item.path("image").path("link").textValue();
                    String price = ""; // 가격 정보가 API 응답에서 제공되지 않으므로, 다른 방법으로 가져와야 합니다.

                    ProductDTO product = new ProductDTO(title, imageUrl, price);
                    products.add(product);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 에러 처리
            System.err.println("Error fetching search results from Google API. Response status: " + response.getStatusCodeValue());
        }
        return products;
    }
}