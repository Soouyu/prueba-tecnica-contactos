package com.erick.contactos.database;
import com.erick.contactos.modelo.Contacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// interface para acceder a la base de datos
// al extener el jpaRepository , sping ya me ayuda con save,findById, findAll,delete
public interface ContratoBaseDatos extends JpaRepository<Contacto, Long>{

    //Metodo de busqueda por nombre, spring lo implementa solo segun su nombre
    //busca contactos cuyo nombe contega el texto, sin importar mayusculas
    Page<Contacto> findByNombresContainingIgnoreCase(String texto, Pageable pageable);
}
