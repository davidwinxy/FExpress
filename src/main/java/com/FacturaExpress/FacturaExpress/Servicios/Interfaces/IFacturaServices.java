package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import com.FacturaExpress.FacturaExpress.Entidades.Sector;
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

    Page<Factura> BuscarPorTerminoPaginados(String searchTerm, Pageable pageable);
    Page<Factura> BuscarPorSectorPaginados(String searchSect, Pageable pageable);
    List<Sector> obtenerSectoresUnicos();
    public Page<Factura> BuscarPorSectorYNombresPaginados(String sector, String searchTerm, Pageable pageable);
}
