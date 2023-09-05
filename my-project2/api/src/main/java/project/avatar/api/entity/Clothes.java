package project.avatar.api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@Document(collection = "clothes")
public class Clothes {

    @Id
    private String id; // 아이템의 고유 식별자

    @DBRef(lazy = true)
    private Users user; // 사용자 정보

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product; // 저장된 상품 정보

    public Clothes(Users user, Products product) {
        this.user = user;
        this.product = product;
    }

}
