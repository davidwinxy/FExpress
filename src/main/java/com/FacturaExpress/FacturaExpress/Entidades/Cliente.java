package com.FacturaExpress.FacturaExpress.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Ingrese el nombre del cliente")
    private String nombre;

    @NotBlank(message = "Ingrese el apellido del cliente")
    private String apellido;

    @NotBlank(message = "Ingrese el correo del cliente")
    @Email(message = "Ingrese un correo válido")
    private String correo;

    @ManyToMany
    @JoinTable(
            name = "sector_clientes",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private Set<Sector> sectores = new HashSet<>();

    @NotBlank(message = "Ingrese su dui")
    private String dui;

    @NotBlank(message = "Ingrese el numero de casa")
    private String numerodecasa;

    @NotBlank(message = "Ingrese direccion de casa")
    private String direcciondecasa;

    @NotBlank(message = "Ingrese numero de telefono")
    private String telefono;

    @NotBlank(message = "Ingrese numero de telefono")
    private String sector;

    public @NotBlank(message = "Ingrese numero de telefono") String getSector() {
        return sector;
    }

    public void setSector(@NotBlank(message = "Ingrese numero de telefono") String sector) {
        this.sector = sector;
    }
    // Getters y setters...


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Ingrese el nombre del cliente") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "Ingrese el nombre del cliente") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "Ingrese el apellido del cliente") String getApellido() {
        return apellido;
    }

    public void setApellido(@NotBlank(message = "Ingrese el apellido del cliente") String apellido) {
        this.apellido = apellido;
    }

    public @NotBlank(message = "Ingrese el correo del cliente") @Email(message = "Ingrese un correo válido") String getCorreo() {
        return correo;
    }

    public void setCorreo(@NotBlank(message = "Ingrese el correo del cliente") @Email(message = "Ingrese un correo válido") String correo) {
        this.correo = correo;
    }

    public Set<Sector> getSectores() {
        return sectores;
    }

    public void setSectores(Set<Sector> sectores) {
        this.sectores = sectores;
    }

    public @NotBlank(message = "Ingrese su dui") String getDui() {
        return dui;
    }

    public void setDui(@NotBlank(message = "Ingrese su dui") String dui) {
        this.dui = dui;
    }

    public @NotBlank(message = "Ingrese el numero de casa") String getNumerodecasa() {
        return numerodecasa;
    }

    public void setNumerodecasa(@NotBlank(message = "Ingrese el numero de casa") String numerodecasa) {
        this.numerodecasa = numerodecasa;
    }

    public @NotBlank(message = "Ingrese direccion de casa") String getDirecciondecasa() {
        return direcciondecasa;
    }

    public void setDirecciondecasa(@NotBlank(message = "Ingrese direccion de casa") String direcciondecasa) {
        this.direcciondecasa = direcciondecasa;
    }

    public @NotBlank(message = "Ingrese numero de telefono") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotBlank(message = "Ingrese numero de telefono") String telefono) {
        this.telefono = telefono;
    }
}
