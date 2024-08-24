package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IClienteServices {

    Page<Cliente> BuscarTodosPaginados(Pageable pageable);
    List<Cliente> ObtenerTodos();
    Optional<Cliente> BuscarporId(Integer id);
    Cliente CreaOeditar(Cliente cliente);
    void EliminarPorId(Integer id);
}
