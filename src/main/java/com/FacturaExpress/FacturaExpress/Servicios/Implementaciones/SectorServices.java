package com.FacturaExpress.FacturaExpress.Servicios.Implementaciones;

import com.FacturaExpress.FacturaExpress.Entidades.Sector;
import com.FacturaExpress.FacturaExpress.Repositorio.ISectorRepository;
import com.FacturaExpress.FacturaExpress.Repositorios.IClienteRepository;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorServices implements ISectorServices {
    @Autowired
    private ISectorRepository sectorRepository;
    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public Page<Sector> BuscarTodosPaginados(Pageable pageable)
    {
        return sectorRepository.findAll(pageable);
    }

    @Override
    public List<Sector> ObtenerTodos() {
        return sectorRepository.findAll();
    }

    @Override
    public Optional<Sector> BuscarporId(Integer id) {
        return sectorRepository.findById(id);
    }

    @Override
    public Sector CrearOEditar(Sector sector) {
        return sectorRepository.save(sector);
    }

    @Override
    public void EliminarPorId(Integer id) {
        sectorRepository.deleteById(id);
    }
    @Override
    public boolean tieneClientesAsignados(Integer sectorId) {
        Sector sector = sectorRepository.findById(sectorId).orElse(null);
        return sector != null && !sector.getClientes().isEmpty();
    }
}