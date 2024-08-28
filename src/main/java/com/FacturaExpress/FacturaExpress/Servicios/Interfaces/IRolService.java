package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    List<Rol> obtenerTodos();
    Optional<Rol> buscarPorId(Integer id);
}
