package com.ngsoftware.leon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utilidad para el trabajo de tokens jwt
 * 
 * @author Francisco Guerrero Peláez
 */
@Component
@Log4j2
public class JWTUtil {

    /**
     * Nombre de quién firma el token
     */
    @Value("${app.security.issuer}")
    private String issuer;
    /**
     * Clave de cifrado del token
     */
    @Value("${app.security.secret}")
    private String secret;
    /**
     * Duración del token
     */
    @Value("${app.security.expires}")
    private int expires;

    /**
     * Crea un token JWT incluyendo el nombre de usuario como asunto.
     * 
     * @param username Nombre del usuario propietario del token.
     * @param claim    Mapa con los valores que se agregarán como claims del token.
     * @return Token jwt valido.
     */
    public String create(String username, Map<String, String> claims) {
        var jwt = JWT.create().withSubject(username)
                .withIssuer(this.issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expires)));
        if (claims.size() > 0) {
            for (Entry<String, String> claim : claims.entrySet()) {
                jwt.withClaim(claim.getKey(), claim.getValue());
            }
        }
        return jwt.sign(Algorithm.HMAC256(secret));
    }

    /**
     * Verifica la validez de un token jwt enviado por el usuario
     * 
     * @param jwt token para verificar
     * @return true si el token es valido, false en caso contrario
     */
    public boolean isValid(String jwt) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(jwt);
            return true;
        } catch (Exception e) {
            log.error("El token {} no es valido, {}", jwt, e.getMessage());
            return false;
        }
    }

    /**
     * Retorna el nombre del usuario registrado como asunto del token
     * 
     * @param jwt token de donde se va a extraer
     * @return Nombre de usuario
     */
    public String getUsername(String jwt) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(jwt).getSubject();
    }

    /**
     * Permite obtener un claim del token a partir de su nombre
     * 
     * @param jwt  token del cual se obtendrá el claim
     * @param name nombre del claim
     * @return valor solicitado si existe.
     */
    public String getClaim(String jwt, String name) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(jwt)
                .getClaim(name)
                .asString();
    }
}
