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
    private List<String> imagePaths; // 이미지 리스트
    
    private String gender; // 성별
    
    private String style; // 스타일
    
    private List<String> likes = new ArrayList<>(); // 좋아요를 누른 사용자의 username을 저장하는 배열
    
    private List<Comment> comments = new ArrayList<>(); // 댓글 목록

    public Posts(String username, String title, List<String> imagePaths, String gender, String style) {
        this.username = username;
        this.title = title;
        this.imagePaths = imagePaths;
        this.gender = gender;
        this.style = style;
        
        // likes와 comments를 비어있는 상태로 초기화
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

	public void addImagePath(String imagePath) {
        if (this.imagePaths == null) {
            this.imagePaths = new ArrayList<>();
        }
        
        this.imagePaths.add(imagePath);
    }
}

