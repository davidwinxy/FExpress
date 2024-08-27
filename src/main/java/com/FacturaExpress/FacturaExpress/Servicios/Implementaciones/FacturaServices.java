package com.FacturaExpress.FacturaExpress.Servicios.Implementaciones;

import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import com.FacturaExpress.FacturaExpress.Repositorio.IFacturaRepository;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IFacturaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServices implements IFacturaServices {
    @Autowired
    private IFacturaRepository facturaRepository;

    @Override
    public Page<Factura> BuscarTodosPaginados(Pageable pageable) {
        return facturaRepository.findAll(pageable);
    }

    @Override
    public List<Factura> ObtenerTodos() {
        return facturaRepository.findAll();
    }

    @Override
    public Optional<Factura> BuscarporId(Integer id) {
        return facturaRepository.findById(id);
    }

    @Override
    public Factura CrearOEditar(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public void EliminarPorId(Integer id) {
        facturaRepository.deleteById(id);
    }
}
