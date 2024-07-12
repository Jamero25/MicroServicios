package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dao.CuentaDAO;
import com.example.demo.dao.EstadoDeCuentaDTO;
import com.example.demo.dao.MovimientosDAO;
import com.example.demo.dao.MovimientosDTO;
import com.example.demo.entidades.Cuenta;
import com.example.demo.entidades.Movimientos;

@Service
public class MovimientosService {

	@Autowired
	private MovimientosDAO movimientosDAO;
	@Autowired
	private CuentaDAO cuentaDAO;

	private WebClient webClient;

	@KafkaListener(topics = "movimientos", groupId = "my-group")
	public void listenerMovimientos(MovimientosDTO movimientosDTO) {
		// Registro movimientos
		System.out.println(" MENSAJE RECIBIDO Creacion movimientos ");
		registrarMovimientos(movimientosDTO);
	}

	public String guardarRegistroMovimiento(MovimientosDTO movimientosDTO) {
		String resultado = "";

		boolean saldoDisponible = validarFondosSuficientes(movimientosDTO);
		if (saldoDisponible) {
			Movimientos movimientos = new Movimientos();
			movimientos.setCuenta(movimientosDTO.getCuenta());
			movimientos.setTipoMovimiento(movimientosDTO.getTipo());
			movimientos.setValor(movimientosDTO.getMonto());
			movimientos.setSaldo(obtenerSaldoActualAfterTransaction(movimientosDTO));
			movimientos.setFechaMovimiento(new Date());
			movimientosDAO.save(movimientos);
			resultado = "Movimiento Realizado con éxito.";
		} else {
			resultado = "Saldo insuficiente para esta operación";
		}

		return resultado;
	}

	private boolean validarFondosSuficientes(MovimientosDTO movimientosDTO) {
		boolean permitido = false;
		Cuenta cuenta = cuentaDAO.obtenerCuentaByNumCuenta(movimientosDTO.getCuenta());
		Double montoTransaccion = movimientosDTO.getMonto() * -1;
		if (cuenta != null && cuenta.getSaldoInicial() > 0.0 && cuenta.getSaldoInicial() > montoTransaccion) {
			permitido = true;
		}
		return permitido;
	}

	private void registrarMovimientos(MovimientosDTO movimientosDTO) {

		Movimientos movimientos = new Movimientos();
		movimientos.setCuenta(movimientosDTO.getCuenta());
		movimientos.setTipoMovimiento(movimientosDTO.getTipo());
		movimientos.setValor(movimientosDTO.getMonto());
		movimientos.setSaldo(obtenerSaldoActualAfterTransaction(movimientosDTO));
		movimientos.setFechaMovimiento(new Date());
		movimientosDAO.save(movimientos);

	}

	private Double obtenerSaldoActualAfterTransaction(MovimientosDTO movimientosDTO) {
		Double saldoActual = 0.0;
		Cuenta cuenta = cuentaDAO.obtenerCuentaByNumCuenta(movimientosDTO.getCuenta());
		if (movimientosDTO.getMonto() < 0) {
			Double montoTransaccion = movimientosDTO.getMonto() * -1;
			// Se resta
			saldoActual = cuenta.getSaldoInicial() - montoTransaccion;
		} else {
			saldoActual = cuenta.getSaldoInicial() + movimientosDTO.getMonto();
		}

		if (saldoActual >= 0.0) {
			cuenta.setSaldoInicial(saldoActual);
			cuentaDAO.save(cuenta);
		}

		return saldoActual;
	}

	public List<EstadoDeCuentaDTO> obtenerListadoMovimientosPorCuenta(Properties datosProp) {
		List<EstadoDeCuentaDTO> estadoCuentaList = new ArrayList<>();
		List<Cuenta> cuentaList = cuentaDAO
				.obtenerListadoCuentasByIdentificacion(datosProp.getProperty("identificacion"));
		if (!cuentaList.isEmpty()) {
			for (Cuenta cuenta : cuentaList) {
				EstadoDeCuentaDTO estadoCuentaDTO = new EstadoDeCuentaDTO();
				estadoCuentaDTO.setIdentificacion(cuenta.getIdentificacionCliente());
				estadoCuentaDTO.setNumCuenta(cuenta.getNumCuenta());

				estadoCuentaDTO.setMovimientosList(consultarListadoMovimientosByCuentaYFechas(cuenta.getNumCuenta(),
						datosProp.getProperty("fechaDesde"), datosProp.getProperty("fechaHasta")));

				estadoCuentaList.add(estadoCuentaDTO);
			}
		}
		return estadoCuentaList;
	}

	private List<MovimientosDTO> consultarListadoMovimientosByCuentaYFechas(String numCuenta, String fechaDesde,
			String fechaHasta) {
		List<MovimientosDTO> listaMovimientosDTO = new ArrayList<>();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		Date fDesde;
		Date fHasta;
		try {
			fDesde = formatoFecha.parse(fechaDesde);
			fHasta = formatoFecha.parse(fechaHasta);
			List<Movimientos> listaMovimientos = movimientosDAO.obtenerListadoMovimientosPorCuentayFechas(numCuenta,
					fDesde, fHasta);
			if (!listaMovimientos.isEmpty()) {
				for (Movimientos mov : listaMovimientos) {
					MovimientosDTO movimientosDTO = new MovimientosDTO();
					movimientosDTO.setTipo(mov.getTipoMovimiento());
					movimientosDTO.setCuenta(mov.getCuenta());
					movimientosDTO.setMonto(mov.getValor());
					movimientosDTO.setSaldo(mov.getSaldo());
					movimientosDTO.setFecha(formaterFecha(mov.getFechaMovimiento()));
					listaMovimientosDTO.add(movimientosDTO);
				}

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaMovimientosDTO;

	}

	private String formaterFecha(Date fecha) {
		String format = "";
		if (fecha != null) {
			format = new SimpleDateFormat("yyy/MM/dd").format(fecha);
		}
		return format;
	}

}
