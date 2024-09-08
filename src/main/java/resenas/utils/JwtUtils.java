package resenas.utils;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtUtils {

    public static String generateToken(String rol, String usuario) {
        Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("USUARIO", usuario)
                .withClaim("ROL", rol)
                .withExpiresAt(new Date(System.currentTimeMillis() + Utils.PASSWORD_TOKEN_EXPIRATION))
                .sign(algorithm);
    }

    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("Token válido: " + jwt);
            return true;
        } catch (JWTVerificationException exception) {
            System.out.println("Token inválido: " + exception.getMessage());
            return false;
        }
    }

    public static String generateRefreshToken() {
        Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
        return JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(new Date(System.currentTimeMillis() + Utils.PASSWORD_TOKEN_EXPIRATION_REFRESH))
                .sign(algorithm);
    }

    public static String renewAccessToken(String refreshToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            if (decodedJWT.getExpiresAt().before(new Date())) {
                System.out.println("Refresh Token expirado.");
                return null;
            }
            return JWT.create()
                    .withIssuer("auth0")
                    .withExpiresAt(new Date(System.currentTimeMillis() + Utils.PASSWORD_TOKEN_EXPIRATION_REFRESH))
                    .sign(algorithm);
        } catch (JWTVerificationException e) {
            System.out.println("Refresh Token inválido: " + e.getMessage());
            return null;
        }
    }

    public static boolean isTokenExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Date expirationDate = decodedJWT.getExpiresAt();
            return expirationDate.before(new Date());
        } catch (JWTVerificationException e) {
            System.out.println("Token inválido: " + e.getMessage());
            return true;
        }
    }
}
