package project.avatar.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {
    @Id //primary 키임을 알려준다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk생성전략을 DB에 위임한다는 뜻
    private long msrl;
    @Column(nullable = false, unique = true, length = 30) //uid column 명시, 필수이며 유니크한 필드이고 길이는 30
    private String uid;
    @Column(nullable = false, length = 100) // name column을 명시. 필수이고 길이는 100입니다.
    private String name;

/*    public User() {}

    public User(String uid, String name){
        this.uid = uid;
        this.name = name;
    }

    public long getMsrl(){
        return msrl;
    }

    public void setMsrl(long msrl){
        this.msrl = msrl;
    }

    public String getUid(){
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }*/
}
