package com.erick.contactos.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

// dto con los datos que entran para crear o editar un contacto
public record ContactoRequests (
    @NotBlank(message = "el nombre es obligatorio")
    String nombres,

    String apellidos,
    String telefono,

    //si viene un correo valida que tenga el formato correcto
    @Email(message = "el correo no tiene un formato correcto")
    String correo,

    String direccion,
    String notas,
    String empresa,
    String cargo,
    Set<String> etiquetas
) {}