package example.jso.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String email;
    private String token;
    private String roles;
}
