package com.FacturaExpress.FacturaExpress.Servicios.Implementaciones;


import com.FacturaExpress.FacturaExpress.Entidades.Usuario;
import com.FacturaExpress.FacturaExpress.Repositorio.IUsuarioRepository;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService implements IUsuarioService {


    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public Page<Usuario> buscarTodosPaginados(Pageable pageable) {
        return iUsuarioRepository.findAll(pageable);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return iUsuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return iUsuarioRepository.findById(id);
    }

    @Override
    public Usuario crearOEditar(Usuario usuario) {
        return iUsuarioRepository.save(usuario);

    }

    @Override
    public void eliminarPorId(Integer id) {
        iUsuarioRepository.deleteById(id);

    }




}

