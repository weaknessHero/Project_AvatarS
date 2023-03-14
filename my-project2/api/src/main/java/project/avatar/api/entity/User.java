package project.avatar.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private String msrl;
    private String uid;
    private String password;
    private String name;

}
