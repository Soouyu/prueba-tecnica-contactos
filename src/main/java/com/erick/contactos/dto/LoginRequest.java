package com.erick.contactos.dto;

//dto con los datos que se envian al inicia sesion (usuarios y clave)
public record LoginRequest(String usuario, String clave) {
}
