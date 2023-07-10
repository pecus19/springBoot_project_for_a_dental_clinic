package ee.ttu.veebirakendus.dentalclinic.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {
    protected static final long TEN_DAYS = 864000000;
    protected static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(
            "V29GVGM0Lw==NE9DWTd+ZW9gIGBjRX0JGs6dD4zIGBdanY5LGlHZC0vKg=="));

    private JwtTokenProvider() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateToken(String email) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return Jwts.builder()
                .setSubject("subject")
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TEN_DAYS))
                .signWith(key)
                .compact();
    }
}
