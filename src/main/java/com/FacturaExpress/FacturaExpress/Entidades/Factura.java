package com.FacturaExpress.FacturaExpress.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "La fecha de emisión es obligatoria")
    @Column(name = "fechaEmision")
    private LocalDate fechaEmision;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Column(name = "fechaVencimiento")
    private LocalDate fechaVencimiento;

    @NotNull(message = "El consumo en kWh es obligatorio")
    @Column(name = "consumoKwh")
    private Double consumoKwh;

    // El costo por kWh es fijo y no se almacena en la base de datos.
    private static final double COSTO_POR_KWH = 0.16;

    @NotBlank(message = "El número de medidor es obligatorio")
    @Column(name = "numeroMedidor")
    private String numeroMedidor;

    @NotNull(message = "El total a pagar es obligatorio")
    @Column(name = "totalPagar")
    private Double totalPagar;

    @PrePersist
    @PreUpdate
    protected void calculateTotalPagar() {
        if (this.consumoKwh != null) {
            this.totalPagar = this.consumoKwh * COSTO_POR_KWH;
        }
        if (this.fechaEmision == null) {
            this.fechaEmision = LocalDate.now();
        }
        if (this.fechaVencimiento == null) {
            this.fechaVencimiento = this.fechaEmision.plusDays(8);
        }
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Double getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(Double consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public String getNumeroMedidor() {
        return numeroMedidor;
    }

    public void setNumeroMedidor(String numeroMedidor) {
        this.numeroMedidor = numeroMedidor;
    }

    public Double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Double totalPagar) {
        this.totalPagar = totalPagar;
    }
}