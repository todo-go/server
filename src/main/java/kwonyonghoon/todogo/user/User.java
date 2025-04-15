package kwonyonghoon.todogo.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_TABLE")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String phoneNumber;


    @Column(nullable = false)
    private String name;

    @Builder
    public User(String phoneNumber, String name){
        this.uuid = java.util.UUID.randomUUID().toString();
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

}
