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
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + Utils.PASSWORD_TOKEN_EXPIRATION))
                .sign(algorithm);
    }

    public static String generateTokenPassword(String correo) {
        Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("Correo", correo)
                .sign(algorithm);
    }

    public static String obtenerCorreo(String token) {
        Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("Correo").asString();
    }

    public static String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Utils.PASSWORD_TOKEN);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return "Token valido";
        } catch (JWTVerificationException exception) {
            return "Token invalido";
        }
    }

}
