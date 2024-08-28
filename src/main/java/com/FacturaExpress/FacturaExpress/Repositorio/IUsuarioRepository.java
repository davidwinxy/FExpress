package com.FacturaExpress.FacturaExpress.Repositorio;

import com.FacturaExpress.FacturaExpress.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
