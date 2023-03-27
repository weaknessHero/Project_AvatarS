package project.avatar.api.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Data
@Document(collection = "users")

public class Users {

    public Users() {}

    @Id
    //private String _id;
    private String uid;
    private String password;
    private String name;

    public Users(String uid, String password, String name){
        //this._id = _id;
        this.uid = uid;
        this.password = password;
        this.name = name;
    }
    /*public String get_id() {
        return _id;
    }*/
    public String getUid(Users users) {
        return uid;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("User[uid=%s, password=%s, name=%s]", uid, password, name);
    }
}
