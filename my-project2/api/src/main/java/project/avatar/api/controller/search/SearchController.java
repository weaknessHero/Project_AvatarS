package project.avatar.api.controller.search;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class SearchController {

    private final String clientId = "pyKxHNJQD2r5QL18dwff";
    private final String clientSecret = "Is26TZ2Tes";

    @GetMapping("/naverSearch")
    public Object search(@RequestParam String query){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Object apiResults = restTemplate.exchange(
                "https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=30",
                HttpMethod.GET,
                entity,
                Object.class
        ).getBody();

        return apiResults;
    }
}
