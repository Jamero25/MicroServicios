package com.example.demo.entidades;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cuenta {

	@Id
	@GeneratedValue
	private long idCuenta;
	private String numCuenta;
	private int tipoCuenta;
	private Double saldoInicial;
	private Boolean activo;
	
	private String identificacionCliente;


	public long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public int getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getIdentificacionCliente() {
		return identificacionCliente;
	}

	public void setIdentificacionCliente(String identificacionCliente) {
		this.identificacionCliente = identificacionCliente;
	}

}
