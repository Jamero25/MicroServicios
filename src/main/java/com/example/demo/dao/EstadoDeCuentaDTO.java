package com.example.demo.dao;

import java.io.Serializable;
import java.util.List;

public class EstadoDeCuentaDTO implements Serializable {

	private String numCuenta;
	private String identificacion;
	List<MovimientosDTO> movimientosList;

	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public List<MovimientosDTO> getMovimientosList() {
		return movimientosList;
	}

	public void setMovimientosList(List<MovimientosDTO> movimientosList) {
		this.movimientosList = movimientosList;
	}

}
