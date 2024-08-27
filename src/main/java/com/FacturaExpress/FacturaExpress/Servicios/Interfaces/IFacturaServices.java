package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFacturaServices {
    Page<Factura> BuscarTodosPaginados(Pageable pageable);
    List<Factura> ObtenerTodos();
    Optional<Factura> BuscarporId(Integer id);
    Factura CrearOEditar(Factura factura);
    void EliminarPorId(Integer id);
}
