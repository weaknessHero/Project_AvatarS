package project.avatar.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private String id;
    private String name;
    private String category;
    private String brand;
    private int price; // 가격은 일반적으로 정수로 처리합니다.
    private String imageUrl;
    private String buyUrl;
    private List<String> tags; // 태그 정보는 리스트로 관리합니다.

    public ProductDTO(String id, String name, String brand,
                      String category, int price, String imageUrl, String buyUrl, List tags) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.buyUrl = buyUrl;
        if (tags != null && !tags.isEmpty()) {
            this.tags = tags; // 실제 태그 값
        } else {
            // 기본 태그 값
            this.tags = Arrays.asList("기본 태그1", "기본 태그2");
        }

    }
}
