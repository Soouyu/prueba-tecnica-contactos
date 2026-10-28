package com.erick.contactos.dto;
import java.time.LocalDateTime;
import java.util.Set;

// dto con los datos que salen de la api lo que se devuelve al cliente
public record ContactoResponses (
        Long id,
        String nombres,
        String apellidos,
        String telefono,
        String correo,
        String direccion,
        String notas,
        String empresa,
        String cargo,
        Set<String> etiquetas,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
){}
