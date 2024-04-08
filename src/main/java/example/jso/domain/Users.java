package example.jso.domain;

import example.jso.domain.roles.UserRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;
    private UserRoles roles;

    @Builder
    public Users(String email, String name, String password, UserRoles roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public Users update(String email, String name, String password, UserRoles roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;

        return this;
    }
}
