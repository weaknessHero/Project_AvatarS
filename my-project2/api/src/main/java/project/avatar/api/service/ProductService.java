package project.avatar.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.avatar.api.dto.ProductDTO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;


@Service
public class ProductService {

    public List<ProductDTO> searchProducts(String query) {
        // 검색 결과와 웹 스크래핑을 위한 List 생성
        List<ProductDTO> products = new ArrayList<>();

        // 1. Google Custom Search API를 사용하여 검색 결과 가져오기
        List<String> searchResultUrls = callGoogleCustomSearchAPI(query);

        // 2. JSoup를 사용하여 웹 스크래핑 수행
        for (String url : searchResultUrls) {
            try {
                int timeout = (int) TimeUnit.SECONDS.toMillis(200); //타임아웃 값 20초로 연장
                // 페이지 접속 및 HTML 문서 파싱
                Document document = Jsoup.connect(url)
                        .timeout(timeout)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
                        .get();

                // 웹 페이지에서 상품 정보 추출하는 코드 작성 (예: CSS 셀렉터를 사용하여 제목, 이미지, 가격 등 추출)
                String title = extractTitle(document);
                String imageUrl = extractImageUrl(document);
                String price = extractPrice(document);

                // 3. 추출한 상품 정보를 ProductDTO 형식으로 변환
                ProductDTO product = new ProductDTO(title, imageUrl, price);

                // 결과 목록에 추가
                products.add(product);
            } catch (IOException e) {
                // 웹 페이지에 접근할 수 없는 경우에 대한 에러 처리
                System.err.println("Error fetching web page: " + url);
                e.printStackTrace();
            }
        }

        // 4. 상품 정보를 포함한 ProductDTO 목록 반환
        return products;
    }

    // Google Custom Search API를 호출하여 검색 결과 URL 목록 얻기
    private List<String> callGoogleCustomSearchAPI(String query) {
        List<String> searchResultUrls = new ArrayList<>();

        // API 키와 검색 엔진 ID 설정 (여기에 실제 값을 사용해야 함)
        String apiKey = "AIzaSyBG8PUN32srAPwFdhgyjYGyL6hsGZh9ISw";
        String searchEngineId = "f6162f1854edb462e";
        String urlTemplate = "https://www.googleapis.com/customsearch/v1?key={0}&cx={1}&q={2}";

        RestTemplate restTemplate = new RestTemplate();

        // URL 생성
        String finalUrl = UriComponentsBuilder.fromHttpUrl(urlTemplate)
                .buildAndExpand(apiKey, searchEngineId, query)
                .toString();

        ResponseEntity<CustomSearchResponse> response = restTemplate.getForEntity(finalUrl, CustomSearchResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            CustomSearchResponse searchResponse = response.getBody();
            if (searchResponse != null) {
                for (Items items : searchResponse.getItems()) {
                    searchResultUrls.add(items.getLink());
                }
            }
        } else {
            // 에러 처리
            System.err.println("Error fetching search results from Google API. Response status: " + response.getStatusCodeValue());
        }

        return searchResultUrls;
    }

    // 각 웹 페이지에서 상품 정보를 추출하는 메서드들
    // 웹 페이지의 구조에 따라 이 메서드들을 구현하여 제목, 이미지 URL 및 가격을 추출

    private String extractTitle(Document document) {
        Element titleElement = document.selectFirst(".product-title"); // 예: CSS 셀렉터를 사용하여 제목 추출
        return titleElement != null ? titleElement.text() : "";
    }

    private String extractImageUrl(Document document) {
        Element imageElement = document.selectFirst(".product-image"); // 예: CSS 셀렉터를 사용하여 이미지 URL 추출
        return imageElement != null ? imageElement.attr("src") : "";
    }

    private String extractPrice(Document document) {
        Element priceElement = document.selectFirst(".product-price"); // 예: CSS 셀렉터를 사용하여 가격 추출
        return priceElement != null ? priceElement.text() : "";
    }
}
