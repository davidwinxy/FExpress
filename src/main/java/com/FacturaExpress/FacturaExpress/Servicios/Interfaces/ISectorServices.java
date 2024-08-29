package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISectorServices {
    Page<Sector> BuscarTodosPaginados(Pageable pageable);
    List<Sector> ObtenerTodos();
    Optional<Sector> BuscarporId(Integer id);
    Sector CrearOEditar(Sector sector);
    void EliminarPorId(Integer id);

    // Dentro de ISectorServices
    public boolean tieneClientesAsignados(Integer sectorId);

}
