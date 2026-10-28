package com.erick.contactos.excepciones;

// excpcion que se lanza cuando no se encuentra un contacto
public class NoEncontrados extends RuntimeException{

    //recibe el mensaje de error y se lo pasa a RuntimeException
    public NoEncontrados(String mensaje){
        super(mensaje);
    }
}
