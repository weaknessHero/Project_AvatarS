package project.avatar.api.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@Document(collection = "products") // This is a JPA annotation to specify the details of the table that will be used to create the table.
public class Products {

    @Id
    @GeneratedValue
    private Long id; // Changed from String to Long for better handling of ID.

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "brand")
    private String brand; // Renamed from 'brand' for consistency with ProductDTO.

    @Column(name = "price")
    private Integer price;

    @Column(name = "image_url")
    private String imageUrl; // Added back as string type as per ProductDTO.

    @Column(name = "buy_url")
    private String buyUrl;  // Added new field as per ProductDTO.

    @Column(name = "tags")
    @Field("tags")
    private List<String> tags;

    public Products(Long id, String name,
                    String category,
                    String brand,
                    Integer price,
                    String imageUrl,
                    String buyUrl,
                    List<String> tags){
        this.id=id;
        this.brand=brand;
        this.category=category;
        this.name=name;
        this.price=price;
        this.imageUrl=imageUrl;
        this.buyUrl=buyUrl;
        this.tags=tags;
    }
}
