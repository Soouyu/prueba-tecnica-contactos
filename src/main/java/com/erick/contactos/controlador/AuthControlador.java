package com.erick.contactos.controlador;

import com.erick.contactos.dto.LoginRequest;
import com.erick.contactos.seguridad.JwtServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//controlador del login recibe usuarios y clave y devuelve un token
@RestController
@RequestMapping("/api/auth")
public class AuthControlador {
  private final JwtServicio jwtServicio;

  // revisa el usuario y la clave sean validos desde la configuracion variables de entorno
  @Value("${app.usuario}")
    private String usuariosValido;

  @Value("${app.clave}")
    private String claveValida;

  public AuthControlador(JwtServicio jwtServicio){
      this.jwtServicio = jwtServicio;
  }

  @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest datos){

      // verifica que la clave y el user conincidan con la configuracion
      if (datos.usuario().equals(usuariosValido) && datos.clave().equals(claveValida)) {
          String token = jwtServicio.generarToken(datos.usuario());
          return ResponseEntity.ok(Map.of("token", token));
      }

      // si no coincide devuelve 401
      return ResponseEntity.status(401).body(Map.of("error", "Usuario o clave incorrectos"));
  }
}
