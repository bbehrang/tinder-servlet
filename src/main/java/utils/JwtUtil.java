package utils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Properties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.apache.commons.dbcp2.BasicDataSource;

public class JwtUtil {
    private static String SECRET_KEY;
    private static final long EXPIRE = 3600 * 1000;
    private static JwtUtil instance;

    private JwtUtil() throws IOException {
        try(InputStream inputStream = JwtUtil.class.getClassLoader().getResourceAsStream("config.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            SECRET_KEY = properties.getProperty("jwt.secret");
        }
        catch (IOException ex){
            ex.printStackTrace();
            throw ex;
        }
    }
    public static JwtUtil getInstance() throws IOException {
        if(instance == null){
            instance = new JwtUtil();
        }
        return instance;
    }
    public static String createJWT(String issuer, String subject) throws IOException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        long expMillis = nowMillis + EXPIRE;
        Date exp = new Date(expMillis);

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .setExpiration(exp)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
