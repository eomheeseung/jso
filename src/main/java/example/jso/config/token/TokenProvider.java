package example.jso.config.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@PropertySource("classpath:jwt.yml")
public class TokenProvider {
    @Value("${secret-key}")
    private String secretKey;

    @Value("${expiration-minutes}")
    private long expiredTime;

    @Value("${issuer}")
    private String issuer;

    public String createToken(String userInfo) {
        return Jwts.builder()
                .setSubject(userInfo)
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setIssuer(issuer)
                .setExpiration(Date.from(Instant.now().plus(expiredTime, ChronoUnit.MINUTES)))
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .compact();
    }
}
