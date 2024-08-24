package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.SectorCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISectorClienteServices {
    Page<SectorCliente> BuscarTodosPaginados(Pageable pageable);
    List<SectorCliente> ObtenerTodos();
    Optional<SectorCliente> BuscarporId(Integer id);
    SectorCliente CrearOEditar(SectorCliente sectorCliente);
    void EliminarPorId(Integer id);
}
