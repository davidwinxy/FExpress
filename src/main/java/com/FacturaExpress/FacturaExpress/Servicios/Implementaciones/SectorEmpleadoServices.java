package com.FacturaExpress.FacturaExpress.Servicios.Implementaciones;

import com.FacturaExpress.FacturaExpress.Entidades.SectorEmpleado;
import com.FacturaExpress.FacturaExpress.Repositorio.ISectorEmpleadoRepository;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorEmpleadoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorEmpleadoServices implements ISectorEmpleadoServices {

    @Autowired
    private ISectorEmpleadoRepository iSectorEmpleadoRepository;

    @Override
    public Page<SectorEmpleado> BuscarTodosPaginados(Pageable pageable)
    {
        return iSectorEmpleadoRepository.findAll(pageable);
    }

    @Override
    public List<SectorEmpleado> ObtenerTodos() {
        return iSectorEmpleadoRepository.findAll();
    }

    @Override
    public Optional<SectorEmpleado> BuscarporId(Integer id) {
        return iSectorEmpleadoRepository.findById(id);
    }

    @Override
    public SectorEmpleado CrearOEditar(SectorEmpleado sectorEmpleado) {
        return iSectorEmpleadoRepository.save(sectorEmpleado);
    }

    @Override
    public void EliminarPorId(Integer id) {
        iSectorEmpleadoRepository.deleteById(id);
    }
}
