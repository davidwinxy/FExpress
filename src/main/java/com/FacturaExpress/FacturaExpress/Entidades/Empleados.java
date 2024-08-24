package com.FacturaExpress.FacturaExpress.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empleados")
public class Empleados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Ingrese el nombre del empleado")
    private String nombre;

    @NotBlank(message = "Ingrese el apellido del empleado")
    private String apellido;

    @NotBlank(message = "Ingrese el email del empleado")
    @Email(message = "Ingrese un email válido")
    private String email;

    @NotBlank(message = "Ingrese el teléfono del empleado")
    private String telefono;

    @NotBlank(message = "Ingrese la dirección del empleado")
    private String direccion;

    @NotBlank(message = "Ingrese el género del empleado")
    private String genero;

    @ManyToMany
    @JoinTable(
            name = "sector_empleados",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private Set<Sector> sectores = new HashSet<>();
    // Getters and Setters

    public Set<Sector> getSectores() {
        return sectores;
    }

    public void setSectores(Set<Sector> sectores) {
        this.sectores = sectores;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
