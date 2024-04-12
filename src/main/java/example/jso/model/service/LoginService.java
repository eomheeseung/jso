package example.jso.model.service;

import example.jso.config.token.TokenProvider;
import example.jso.domain.user.User;
import example.jso.domain.user.dto.LoginRequestDto;
import example.jso.domain.user.dto.LoginResponseDto;
import example.jso.domain.user.dto.SignInRequestDto;
import example.jso.domain.user.Role;
import example.jso.exception.NotFoundDataException;
import example.jso.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final TokenProvider tokenProvider;

    public boolean signUp(SignInRequestDto dto) {
        String email = dto.getEmail();
        String name = dto.getName();
        String password = dto.getPassword();

        Role role;

        if (name.equals("admin")) {
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }

        try {
            String encodePassword = passwordEncoder.encode(password);

            Optional<User> optionalUsers = repository.findByEmail(email);


            if (optionalUsers.isEmpty()) {
                repository.save(User.builder()
                        .email(email)
                        .password(encodePassword)
                        .name(name)
                        .roles(role)
                        .build());
            } else {
                User user = optionalUsers.get();
                repository.save(user.update(email, name, encodePassword, role));
            }
        } catch (RuntimeException e) {
            return false;
        }

        return true;
    }

    public LoginResponseDto signIn(LoginRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        Optional<User> optionalUsers = repository.findByEmail(email);

        if (optionalUsers.isEmpty()) {
            throw new NotFoundDataException("no data");
        } else {
            User user = optionalUsers.get();

            boolean isMatch = passwordEncoder.matches(password, user.getPassword());

            if (!isMatch) {
                throw new NotFoundDataException("no password matches");
            }

            String token = tokenProvider.createToken(email);

            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setEmail(user.getEmail());
            loginResponseDto.setRoles(user.getRoles().name());
            loginResponseDto.setToken(token);

            return loginResponseDto;
        }


    }
}
