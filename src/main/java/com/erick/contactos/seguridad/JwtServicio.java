package com.erick.contactos.seguridad;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// servicio que crea y valida los token jwt
@Service
public class JwtServicio {

    // lee el secreto y la duracion del token desde la configuracion
    @Value("${app.jwt.secreto}")
    private String secreto;

    @Value("${app.jwt.expiracion}")
    private long expiracion;

    // se arama la clave secreta a partir del texto del secreto para firmar
    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));

    }

    // genera un token para que el usuario pueda ingresar
    public String generarToken(String usuario){
        Date ahora = new Date();
        Date expira = new Date(ahora.getTime() + expiracion);

        return Jwts.builder()
                .subject(usuario)  // quien es
                .issuedAt(ahora) // cuando se creo
                .expiration(expira) // cuando vence
                .signWith(obtenerClave()) // lo firma con la clave
                .compact(); // lo convierte en texto
    }

    // obtiene el nombre de un usuario que viene en el token que recibe
    public String obtenerUsuario(String token){
        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // dice si el token es valido o si es falso/ vencido
    public boolean esValido (String token){
        try{
            obtenerUsuario(token);
            return true;
        } catch (Exception e){
            return  false;
        }
    }



}
