package com.example.demo.entidades;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Movimientos {

	@Id
	@GeneratedValue
	private long idMovimiento;

	private String cuenta;
	private int tipoMovimiento;
	private Date fechaMovimiento;
	private Double valor;
	private Double saldo;

	public long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public int getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(int tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

}
