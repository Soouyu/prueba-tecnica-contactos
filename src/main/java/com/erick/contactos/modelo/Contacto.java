package com.erick.contactos.modelo;

import jakarta.persistence.*;
import java.util.HashSet;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name= "contactos")

// clase que representa la tabla contactos en la base de datos
public class Contacto {
    // id autogenerado por la base de datos
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    // exigimos que sea obligatorio nombres no puede ser null en la base
    @Column(nullable = false)
    private String nombres;
    private String apellidos;
    private String telefono;
    //valida que el correo tenga un formato correcto
    @Email(message = "El correo no tiene un formato correcto")
    private String correo;
    private String direccion;
    // permite notas hasta de 1000 caracteres
    @Column(length = 1000)
    private String notas;
    private String empresa;
    private String cargo;
    // las etiquetas se guardan en una tabla aparte porque un contacto
    // puede tener varias etiquetas relacion de 1 a muchos
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "contacto_etiquetas", joinColumns = @JoinColumn(name = "contacto_id"))
    private Set<String> etiquetas = new HashSet<>();

    // la fecha de creacion no se modifica despues de ser creada
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    //  antes de guardar por primera vez, ponemos ambas fechas
    @PrePersist
    public void alCrear(){
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    // antes de actualizar, se refresca solo la fecha de actualizacion para maayor control
    @PreUpdate
    public void alActualizar(){
        this.fechaActualizacion = LocalDateTime.now();
    }


    //getters y setters para leer y escribir los campos privados
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Set<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
