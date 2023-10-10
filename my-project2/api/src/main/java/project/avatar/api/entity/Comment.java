package project.avatar.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Comment {

	private String username; //작성자
	private String content;  //댓글 내용
    
    public Comment(String username, String content) {
	    this.username = username;
	    this.content= content ;
	}
}
