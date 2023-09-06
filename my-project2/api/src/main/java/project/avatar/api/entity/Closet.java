package project.avatar.api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    @DBRef(lazy = true)
    private Users user; // 사용자 정보

    @DBRef(lazy = true)
    private List<Products> products; // 저장된 상품 정보들

    public Closet(Users user, List<Products> products) {
        this.user = user;
        this.products = products;
    }

}