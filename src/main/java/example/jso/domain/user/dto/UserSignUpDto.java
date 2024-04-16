
package example.jso.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {
    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "nickname")
    private String nickname;

    @JsonProperty(value = "age")
    private int age;

    @JsonProperty(value = "city")
    private String city;
}
