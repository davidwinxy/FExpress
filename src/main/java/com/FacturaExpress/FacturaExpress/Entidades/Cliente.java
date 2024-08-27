package com.FacturaExpress.FacturaExpress.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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

    @NotBlank(message = "Ingrese su DUI")
    private String dui;

    @NotBlank(message = "Ingrese la dirección")
    private String direccion;

    @NotBlank(message = "Ingrese el número de teléfono")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    // Getters y setters


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

    public @NotBlank(message = "Ingrese su DUI") String getDui() {
        return dui;
    }

    public void setDui(@NotBlank(message = "Ingrese su DUI") String dui) {
        this.dui = dui;
    }

    public @NotBlank(message = "Ingrese la dirección") String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NotBlank(message = "Ingrese la dirección") String direccion) {
        this.direccion = direccion;
    }

    public @NotBlank(message = "Ingrese el número de teléfono") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotBlank(message = "Ingrese el número de teléfono") String telefono) {
        this.telefono = telefono;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
}
