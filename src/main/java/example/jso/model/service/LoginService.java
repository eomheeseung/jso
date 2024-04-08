package example.jso.model.service;

import example.jso.config.token.TokenProvider;
import example.jso.domain.Users;
import example.jso.domain.dto.LoginRequestDto;
import example.jso.domain.dto.LoginResponseDto;
import example.jso.domain.dto.SignInRequestDto;
import example.jso.domain.roles.UserRoles;
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

        UserRoles userRoles;

        if (name.equals("admin")) {
            userRoles = UserRoles.ADMIN;
        } else {
            userRoles = UserRoles.USER;
        }

        try {
            String encodePassword = passwordEncoder.encode(password);

            Optional<Users> optionalUsers = repository.findByEmail(email);


            if (optionalUsers.isEmpty()) {
                repository.save(Users.builder()
                        .email(email)
                        .password(encodePassword)
                        .name(name)
                        .roles(userRoles)
                        .build());
            } else {
                Users users = optionalUsers.get();
                repository.save(users.update(email, name, encodePassword, userRoles));
            }
        } catch (RuntimeException e) {
            return false;
        }

        return true;
    }

    public LoginResponseDto signIn(LoginRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        Optional<Users> optionalUsers = repository.findByEmail(email);

        if (optionalUsers.isEmpty()) {
            throw new NotFoundDataException("no data");
        } else {
            Users users = optionalUsers.get();

            boolean isMatch = passwordEncoder.matches(password, users.getPassword());

            if (!isMatch) {
                throw new NotFoundDataException("no password matches");
            }

            String token = tokenProvider.createToken(email);

            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setEmail(users.getEmail());
            loginResponseDto.setRoles(users.getRoles().name());
            loginResponseDto.setToken(token);

            return loginResponseDto;
        }


    }
}
