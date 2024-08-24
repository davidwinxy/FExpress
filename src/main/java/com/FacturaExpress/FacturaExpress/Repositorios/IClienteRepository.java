package com.FacturaExpress.FacturaExpress.Repositorios;

import com.FacturaExpress.FacturaExpress.Entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
}
