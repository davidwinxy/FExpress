package com.FacturaExpress.FacturaExpress.Servicios.Implementaciones;

import com.FacturaExpress.FacturaExpress.Entidades.SectorCliente;
import com.FacturaExpress.FacturaExpress.Repositorio.ISectorClienteRepository;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorClienteServices implements ISectorClienteServices {

    @Autowired
    private ISectorClienteRepository iSectorClienteRepository;

    @Override
    public Page<SectorCliente> BuscarTodosPaginados(Pageable pageable) {
        return iSectorClienteRepository.findAllByOrderBySectorNombreDesc(pageable);
    }

    @Override
    public List<SectorCliente> ObtenerTodos() {
        return iSectorClienteRepository.findAll();
    }

    @Override
    public Optional<SectorCliente> BuscarporId(Integer id) {
        return iSectorClienteRepository.findById(id);
    }

    @Override
    public SectorCliente CrearOEditar(SectorCliente sectorCliente) {
        return iSectorClienteRepository.save(sectorCliente);
    }

    @Override
    public void EliminarPorId(Integer id) {
        iSectorClienteRepository.deleteById(id);
    }
}
