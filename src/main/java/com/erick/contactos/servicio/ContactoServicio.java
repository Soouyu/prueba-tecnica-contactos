package com.erick.contactos.servicio;

import com.erick.contactos.database.ContratoBaseDatos;
import com.erick.contactos.dto.ContactoRequests;
import com.erick.contactos.dto.ContactoResponses;
import com.erick.contactos.excepciones.NoEncontrados;
import com.erick.contactos.modelo.Contacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

// servicio con la logia del crud de contactos
@Service
public class ContactoServicio {

    private final ContratoBaseDatos base;

    // recibe el repositorio por inyeccio de dependencias
    public ContactoServicio(ContratoBaseDatos base) {
        this.base = base;
    }

    // crea un contacto nuevo, copia los datos del dto y lo guarda y devuelve como dto
    public ContactoResponses crear(ContactoRequests request) {
        Contacto contacto = new Contacto();
        copiarDatos(request, contacto);
        return aResponse(base.save(contacto));
    }

    // trae una pagina de contactos y convierte cada uno a dto
    public Page<ContactoResponses> listar(Pageable pageable) {
        return base.findAll(pageable).map(this::aResponse);
    }

    // igual que listar pero permite filtrar por nombre
    public Page<ContactoResponses> buscar(String texto, Pageable pageable) {
        return base.findByNombresContainingIgnoreCase(texto, pageable).map(this::aResponse);
    }

    // busca un contacto por id y lo devuelve como dto
    public ContactoResponses obtenerPorId(Long id) {
        return aResponse(buscarContacto(id));
    }

    // actualiza busca el contacto, le copia los datos nuevo y guarda hace un update
    public ContactoResponses actualizar(Long id, ContactoRequests request) {
        Contacto contacto = buscarContacto(id);
        copiarDatos(request, contacto);
        return aResponse(base.save(contacto));
    }

    // elimina un contacto si no existe buscarContacto lanza el error
    public void eliminar(Long id) {
        base.delete(buscarContacto(id));
    }

    // helper: busca por id o lanza una excepcion por si no existe
    private Contacto buscarContacto(Long id) {
        return base.findById(id)
                .orElseThrow(() -> new NoEncontrados("No se encontró el contacto con id " + id));
    }

    //helper copia los datos del dto al contacto
    // si las etiquetas vienen null, pone un conjunto vacio
    private void copiarDatos(ContactoRequests request, Contacto contacto) {
        contacto.setNombres(request.nombres());
        contacto.setApellidos(request.apellidos());
        contacto.setTelefono(request.telefono());
        contacto.setCorreo(request.correo());
        contacto.setDireccion(request.direccion());
        contacto.setNotas(request.notas());
        contacto.setEmpresa(request.empresa());
        contacto.setCargo(request.cargo());
        contacto.setEtiquetas(request.etiquetas() != null ? request.etiquetas() : new HashSet<>());
    }

    //helper: traduce la entidad contacto a su dto de salida
    private ContactoResponses aResponse(Contacto c) {
        return new ContactoResponses(
                c.getId(), c.getNombres(), c.getApellidos(), c.getTelefono(),
                c.getCorreo(), c.getDireccion(), c.getNotas(), c.getEmpresa(),
                c.getCargo(), c.getEtiquetas(), c.getFechaCreacion(), c.getFechaActualizacion()
        );
    }
}