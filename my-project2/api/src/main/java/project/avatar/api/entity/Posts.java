package project.avatar.api.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Data
@NoArgsConstructor
@Document(collection = "posts")
public class Posts {
    
    @Id
    private String id; // 아이템의 고유 식별자
    private String username; // 사용자 이름
    private String title; // 제목
    @Field(name="imagePaths")
    private List<String>  imagePaths; //이미지 리스트
    private String gender; // 성별
    private String style; //스타일
    private String body;
    //private List<String> postIds; // 저장된 상품 ID들

    public Posts(String username, String body, List<String> imagePaths, String title, String gender, String style, List<String> postIds) {
        this.username = username;
        this.title = title;
        this.imagePaths = imagePaths;
        this.gender = gender;
        this.style = style;
        //this.postIds = postIds;
        this.body = body;
    }

    public void addImagePath(String imagePath) {
        if (this.imagePaths == null) {
            this.imagePaths = new ArrayList<>();
        }
        
        this.imagePaths.add(imagePath);
    }
}
