package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entidades.Cuenta;

public interface CuentaDAO extends CrudRepository<Cuenta, Long>{

	@Query("select account from Cuenta account where account.numCuenta = ?1")
	Cuenta obtenerCuentaByNumCuenta(String numCuenta);
	
	@Query("select account from Cuenta account where account.identificacionCliente = ?1")
	List<Cuenta> obtenerListadoCuentasByIdentificacion(String identificacion);
	
}
