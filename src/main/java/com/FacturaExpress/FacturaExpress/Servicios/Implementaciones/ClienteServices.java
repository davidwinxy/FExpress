package com.FacturaExpress.FacturaExpress.Servicios.Implementaciones;

import com.FacturaExpress.FacturaExpress.Entidades.Cliente;
import com.FacturaExpress.FacturaExpress.Repositorios.IClienteRepository;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices implements IClienteServices {

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public Page<Cliente> BuscarTodosPaginados(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public List<Cliente> ObtenerTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> BuscarporId(Integer id) {
        return clienteRepository.findById(id);
    }
    @Override
    public Cliente CreaOeditar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    @Override
    public void EliminarPorId(Integer id) {
        clienteRepository.deleteById(id);
    }
}
