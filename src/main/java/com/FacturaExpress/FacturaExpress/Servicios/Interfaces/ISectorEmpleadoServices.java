package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.SectorEmpleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISectorEmpleadoServices {
    Page<SectorEmpleado> BuscarTodosPaginados(Pageable pageable);
    List<SectorEmpleado> ObtenerTodos();
    Optional<SectorEmpleado> BuscarporId(Integer id);
    SectorEmpleado CrearOEditar(SectorEmpleado sectorEmpleado);
    void EliminarPorId(Integer id);
}
