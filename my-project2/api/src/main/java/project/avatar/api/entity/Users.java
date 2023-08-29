package project.avatar.api.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "users")
public class Users {

    public Users() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uid;

    private String password;

    private String name;

    public Users(String uid, String password, String name){
        this.uid = uid;
        this.password = password;
        this.name = name;
    }

    public String toString() {
        return String.format("User[id=%d, uid=%s, password=%s, name=%s]", id, uid, password, name);
    }
}
