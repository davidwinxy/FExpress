package com.FacturaExpress.FacturaExpress.Servicios.Implementaciones;

import com.FacturaExpress.FacturaExpress.Entidades.Empleados;
import com.FacturaExpress.FacturaExpress.Repositorio.IEmpleadoRepository;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IEmpleadosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadosServices implements IEmpleadosServices {
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Override
    public Page<Empleados> BuscarTodosPaginados(Pageable pageable) {
        return empleadoRepository.findAll(pageable);
    }

    @Override
    public List<Empleados> ObtenerTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleados> BuscarporId(Integer id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Empleados CreaOeditar(Empleados empleados) {
        return empleadoRepository.save(empleados);
    }

    @Override
    public void EliminarPorId(Integer id) {
        empleadoRepository.deleteById(id);
    }
}
