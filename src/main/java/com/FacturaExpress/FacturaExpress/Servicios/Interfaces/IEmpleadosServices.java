package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.Empleados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEmpleadosServices {
    Page<Empleados> BuscarTodosPaginados(Pageable pageable);
    List<Empleados> ObtenerTodos();
    Optional<Empleados> BuscarporId(Integer id);
    Empleados CreaOeditar(Empleados empleados);
    void EliminarPorId(Integer id);
}
