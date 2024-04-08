package example.jso.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @JsonProperty(value = "password")
    @Schema(example = "1234")
    private String password;

    @JsonProperty(value = "email")
    @Schema(example = "test1234@gmail.com")
    private String email;
}
