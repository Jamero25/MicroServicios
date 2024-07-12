package com.example.demo.webService;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EstadoDeCuentaDTO;
import com.example.demo.dao.MovimientosDTO;
import com.example.demo.service.MovimientosService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/movimientos")
public class MovimientosWebService {

	@Autowired
	private MovimientosService movimientosService;

	@PostMapping("/registrarMovimiento")
	public String registarMovimiento(@RequestBody MovimientosDTO movimientosDTO) {
		String respuesta = "";
		if (movimientosDTO.getCuenta() != null) {
			respuesta = movimientosService.guardarRegistroMovimiento(movimientosDTO);
		}
		return respuesta;
	}

	@PostMapping("/estadoDecuenta")
	public EstadoDeCuentaDTO[] obtenerListadoEstadoCuenta(@RequestBody String json) {
		Gson gson = new Gson();
		EstadoDeCuentaDTO[] estadoArray = null;
		Properties datosProp = gson.fromJson(json, Properties.class);
		List<EstadoDeCuentaDTO> listado = movimientosService.obtenerListadoMovimientosPorCuenta(datosProp);
		if (!listado.isEmpty()) {
			estadoArray = new EstadoDeCuentaDTO[listado.size()];
			estadoArray = listado.toArray(estadoArray);
		}
		return estadoArray;
	}
}
