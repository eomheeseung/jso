package example.jso.model.controller;

import example.jso.domain.user.dto.LoginRequestDto;
import example.jso.domain.user.dto.LoginResponseDto;
import example.jso.domain.user.dto.SignInRequestDto;
import example.jso.model.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "login", description = "회원가입 / 로그인")
@RequestMapping("/login")
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @Operation(summary = "회원가입")
    @PostMapping(value = "/signUp")
    public ResponseEntity<Object> signUp(@RequestBody SignInRequestDto dto) {
        boolean flag = loginService.signUp(dto);
        return ResponseEntity.ok().body(flag);
    }

    @Operation(summary = "로그인")
    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody LoginRequestDto dto) {
        LoginResponseDto loginResponseDto = loginService.signIn(dto);
        return ResponseEntity.ok().body(loginResponseDto);
    }
}
