package main.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.models.User;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class TokenUtils {

    private static final String SECRET_KEY = "your_secret_key";
    private static final Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

    @SuppressWarnings("deprecation")
    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiry
                .signWith(key) // Use SecretKeySpec
                .compact();
    }
}
