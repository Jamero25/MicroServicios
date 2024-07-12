package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CuentaDAO;
import com.example.demo.dao.CuentaDTO;
import com.example.demo.entidades.Cuenta;

@Service
public class CuentaService {

	
	@Autowired
	private CuentaDAO cuentaDAO;
	
	@KafkaListener(topics = "cuentas", groupId = "my-group")
	public void listen(CuentaDTO cuentaDTO) {
		//Registrar Cuenta
		System.out.println(" MENSAJE RECIBIDO Aperturar cuenta ");
		guardarCuenta(cuentaDTO);
		 
	}
	
	private void guardarCuenta(CuentaDTO cuentaDTO) {
		Cuenta cuenta = new Cuenta();
		cuenta.setNumCuenta(cuentaDTO.getNumCuenta());
		cuenta.setSaldoInicial(cuentaDTO.getSaldo());
		cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
		cuenta.setActivo(cuentaDTO.getEstado());
		cuenta.setIdentificacionCliente(cuentaDTO.getIdentificacion());
		
		cuentaDAO.save(cuenta);
	}
}
