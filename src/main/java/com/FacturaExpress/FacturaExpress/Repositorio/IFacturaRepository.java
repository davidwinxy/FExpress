package com.FacturaExpress.FacturaExpress.Repositorio;

import com.FacturaExpress.FacturaExpress.Entidades.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFacturaRepository extends JpaRepository<Factura, Integer> {
    Page<Factura> findByClienteNombreContainingOrClienteApellidoContaining(String nombre, String apellido, Pageable pageable);
    Page<Factura> findByClienteSectorNombreContaining(String sectorNombre, Pageable pageable);
    @Query("SELECT DISTINCT c.sector FROM Cliente c JOIN c.facturas f")
    List<Sector> findDistinctSectores();
    Page<Factura> findByClienteSectorNombreContainingAndClienteNombreContainingOrClienteApellidoContaining(String sector, String nombre, String apellido, Pageable pageable);
}
