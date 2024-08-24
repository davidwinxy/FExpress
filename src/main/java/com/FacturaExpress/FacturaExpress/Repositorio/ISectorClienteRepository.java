package com.FacturaExpress.FacturaExpress.Repositorio;

import com.FacturaExpress.FacturaExpress.Entidades.SectorCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISectorClienteRepository extends JpaRepository<SectorCliente, Integer> {
    Page<SectorCliente> findAllByOrderBySectorNombreDesc(Pageable pageable);}
