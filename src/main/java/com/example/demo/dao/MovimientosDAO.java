package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entidades.Movimientos;

public interface MovimientosDAO extends CrudRepository<Movimientos, Long>{

	@Query("select movimient from Movimientos movimient where movimient.cuenta = ?1 and movimient.fechaMovimiento BETWEEN ?2 and ?3 order by movimient.fechaMovimiento desc")
	List<Movimientos> obtenerListadoMovimientosPorCuentayFechas(String cuenta, Date fechaDesde, Date fechaHasta);
}
