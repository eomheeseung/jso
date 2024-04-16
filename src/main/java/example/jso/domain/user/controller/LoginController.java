package example.jso.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {
    @PostMapping("login/oauth2/code/naver")
    public String login() {
        return "로그인 성공!";
    }
}
