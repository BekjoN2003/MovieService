package ms.movie_service.util;

import io.jsonwebtoken.*;
import ms.movie_service.config.CustomUserDetails;
import org.aspectj.bridge.MessageUtil;
import org.aspectj.weaver.bcel.BcelTypeMunger;
import org.aspectj.weaver.tools.DefaultTrace;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    Logger logger = null;
    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final String issuer = "movie.uz";

    public String generateAccessToken(UserDetails userDetails){
        CustomUserDetails user = (CustomUserDetails) userDetails;

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setId("some Id");
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setSubject(String.format("%s,%s", user.getId(), userDetails.getUsername()));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, jwtSecret);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(24 * 60 * 60 * 1000)));
        jwtBuilder.setIssuer(issuer);

        return jwtBuilder.compact();
    }

    public String generateAccessToken(Integer id, String email){

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setId("some Id");
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setSubject(String.format("%s,%s", id, email));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, jwtSecret);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(24*60*60*1000)));
        jwtBuilder.setIssuer(issuer);

        return jwtBuilder.compact();
    }

    public String getUserID(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject().split(",")[0];
    }

    public String getUserName(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
             logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
             logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
             logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
             logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
