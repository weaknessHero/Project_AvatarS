package project.avatar.api.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Id;
import java.util.Date;

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
    private Date createdTime;
    private Path imageUrl;

    public Products(String name, String category, String brandName,
                    Integer price, String updateDate, String style,
                    Path imageUrl){
        this.brandName=brandName;
        this.category=category;
        this.name=name;
        this.price=price;
        this.updateDate=updateDate;
        this.style=style;
        //this.createdTime=koreanDate;
        this.createdTime=new Date();
        this.imageUrl=imageUrl;
    }

    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
    //String koreanDate = LocalDateTime.now().format(formatter);

    public String getName(){
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

    public String toString() {
        return String.format("yyyy년 MM월 dd일 HH:mm:ss", createdTime);
    }

}
