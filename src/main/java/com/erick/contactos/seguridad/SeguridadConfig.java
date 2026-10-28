package com.erick.contactos.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;


// configuracion de seguridad aqui se definen las reglas de acceso y el cors
@Configuration
public class SeguridadConfig {

    private final FiltradorJwt filtradorJwt;


    public SeguridadConfig(FiltradorJwt filtradorJwt) {
        this.filtradorJwt = filtradorJwt;
    }
    @Bean
    public SecurityFilterChain filtros(HttpSecurity http) throws Exception {

        http
                // aqui se activa el cors y usa la configuracion del bean de abajo
                .cors(Customizer.withDefaults())
                // apaga el CSRF porque usamos token no sesiones con formularios
                .csrf(csrf -> csrf.disable())
                // sin sesiones cada peticion se identifica con su token
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(permisos -> permisos
                        // el login es libre sin token
                        .requestMatchers("/api/auth/**").permitAll()
                         //todo lo demas exige token
                        .anyRequest().authenticated()
                )
                // mete mi filtro jwt antes del filtro por defecto de spring
                .addFilterBefore(filtradorJwt, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


// reglas del cors permite que el frontend localhost:3000 hable con la api
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // quien tiene acceso
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS")); // que acciones puede hacer
        config.setAllowedHeaders(List.of("*"));  // que cabeceras igual incluye authorization

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // aplica a todas las rutas
        return source;
    }
}
