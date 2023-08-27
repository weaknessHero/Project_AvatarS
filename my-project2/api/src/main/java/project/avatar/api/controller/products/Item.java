package project.avatar.api.controller.products;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
public class Item {
    private Long productId;
    private String image;
    private String title;
    private String category1;
    private String mallname;
    private int lprice;
    private String link;

    /* getters
    public Long getProductId() {
        return productId;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory1() {
        return category1;
    }

    public String getMallname() {
        return mallname;
    }

    public int getLprice() {
        return lprice;
    }

    public String getLink(){
        return link;
    }


    // setters
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory1(String category1){
        this.category1 = category1;
    }

    public void setMallname(String mallname){
        this.mallname = mallname;
    }

    public void setLprice(int lprice){
        this.lprice = lprice;
    }

    public void setLink(String link){
        this.link=link ;
    }*/
}

