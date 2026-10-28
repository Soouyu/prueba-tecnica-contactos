package com.erick.contactos.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// un filtro que revisa el token en cada peticion solo se ejecuta una vez por peticion
@Component
public class FiltradorJwt extends OncePerRequestFilter {

    private final JwtServicio jwtServicio;

    public FiltradorJwt(JwtServicio jwtServicio) {
        this.jwtServicio = jwtServicio;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //busca la cabecera authorization donde viaja el token
        String cabecera = request.getHeader("Authorization");

        // el token debe venir con el prefijo Bearer
        if (cabecera != null && cabecera.startsWith("Bearer ")) {
            String token = cabecera.substring(7); // quita "Bearer" y deja solo el token

            // si el token es valido se identifica el usuario
            if (jwtServicio.esValido(token)) {
                String usuario = jwtServicio.obtenerUsuario(token);

                // confirma que el usuario ya esta identificado
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(usuario, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // permite la peticion
        filterChain.doFilter(request, response);
    }
}
