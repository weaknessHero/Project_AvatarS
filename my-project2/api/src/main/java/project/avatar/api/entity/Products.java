package project.avatar.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Data
@Document(collection = "products")
public class Products {
    public Products() {}

    @Id
    private String name;
    private String category;
    private Integer price;
    private String brandName;
    private String updateDate;
    private String style;

    public Products(String name, String category, String brandName,
                    Integer price, String updateDate, String style){
        this.brandName=brandName;
        this.category=category;
        this.name=name;
        this.price=price;
        this.updateDate=updateDate;
        this.style=style;
    }

    public String getName(Products products){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public String getBrandName(){
        return brandName;
    }

    public Integer getPrice(Products products) {
        return price;
    }
}
