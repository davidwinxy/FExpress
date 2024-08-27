package com.FacturaExpress.FacturaExpress.Repositorio;

import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaRepository extends JpaRepository<Factura, Integer> {
}
