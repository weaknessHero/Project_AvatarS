package project.avatar.api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@NoArgsConstructor
@Document(collection = "users")
public class Users {

    @Id
    private String id;

    private String uid;

    private String password;

    private String name;

    public Users(String uid, String password, String name){
        this.uid = uid;
        this.password = password;
        this.name = name;
    }

}
