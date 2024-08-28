package com.FacturaExpress.FacturaExpress.Servicios.Interfaces;

import com.FacturaExpress.FacturaExpress.Entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Page<Usuario> buscarTodosPaginados(Pageable pageable);

    List<Usuario> obtenerTodos();

    Optional<Usuario> buscarPorId(Integer id);

    Usuario crearOEditar(Usuario Usuario);

    void eliminarPorId(Integer id);
}
