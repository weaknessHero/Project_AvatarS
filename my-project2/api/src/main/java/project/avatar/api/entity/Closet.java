package project.avatar.api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@Document(collection = "closet")
public class Closet {

    @Id
    private String id; // 아이템의 고유 식별자

    private String username; // 사용자 이름

    private List<String> productIds; // 저장된 상품 ID들

    public Closet(String username, List<String> productIds) {
        this.username = username;
        this.productIds = productIds;
    }

}
