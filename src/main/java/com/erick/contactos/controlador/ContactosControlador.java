package com.erick.contactos.controlador;
import com.erick.contactos.dto.ContactoRequests;
import com.erick.contactos.dto.ContactoResponses;
import com.erick.contactos.servicio.ContactoServicio;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// controlador del crud de contactos, la ruta base es /api/contactos
@RestController
@RequestMapping("/api/contactos")
public class ContactosControlador {

    private final ContactoServicio servicio;
    public ContactosControlador(ContactoServicio servicio){
        this.servicio = servicio;
    }

    //POST /api/contactos crea un contacto y devuelve 201 de exito
    @PostMapping
    public ResponseEntity<ContactoResponses> crear(@Valid @RequestBody ContactoRequests requests){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(requests));
    }


   // GET /api/contactos lista todos los contactos con paginacion
    // si busca algo como  ?buscar=x filtra por el nombre
    @GetMapping
    public Page<ContactoResponses> listar (
            @RequestParam(required = false) String buscar,
            Pageable pageable){
        if (buscar != null && !buscar.isBlank()){
            return servicio.buscar(buscar, pageable);
        }
        return servicio.listar(pageable);
    }

    // GET /api/contactos/{id} trae un contacto por su id
    @GetMapping("/{id}")
    public ContactoResponses obtener (@PathVariable Long id){
        return servicio.obtenerPorId(id);
    }

    // PUT /api/contactos/{id} permite editar un contacto existente
    @PutMapping("/{id}")
    public ContactoResponses editar (@PathVariable Long id, @Valid @RequestBody ContactoRequests requests){
    return servicio.actualizar(id,requests);
    }

    // DELETE /api/contactos/{id} elimina un contacto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar (@PathVariable Long id){
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    
}
