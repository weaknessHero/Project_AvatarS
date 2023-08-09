package project.avatar.api.dto;

public class ProductDTO {
    private String id;
    private String name;
    private String imageUrl;
    private String price; // Number 대신 String을 사용하셨다면 이 부분을 유지하세요.
    private float rating;

    public ProductDTO(String name, String imageUrl, String price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    // Getter, Setter 메서드들...


    // getter 메서드
    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    // setter 메서드
    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    // hashCode, equals, toString 등 추가적인 메서드가 필요하면 여기 구현
}
