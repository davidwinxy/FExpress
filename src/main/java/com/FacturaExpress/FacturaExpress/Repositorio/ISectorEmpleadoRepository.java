package com.FacturaExpress.FacturaExpress.Repositorio;

import com.FacturaExpress.FacturaExpress.Entidades.SectorEmpleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ISectorEmpleadoRepository extends JpaRepository<SectorEmpleado, Integer> {
    Page<SectorEmpleado> findAllByOrderBySectorNombreDesc(Pageable pageable);
}
