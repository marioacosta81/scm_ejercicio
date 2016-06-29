package co.edu.usbcali.demo.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import co.edu.usbcali.demo.dto.Resultado;

public class OperacionesMatematicasTest {
	
	private static final Logger log=LoggerFactory.getLogger(OperacionesMatematicasTest.class);

	@Test
	public void atest() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/operacionesMatematicas/sumar/3/2", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}
	
	@Test
	public void btest() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/operacionesMatematicas/restar/7/3", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}
	
	@Test
	public void ctest() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/operacionesMatematicas/multiplicar/7/6", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}
	
	@Test
	public void dtest() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:9098/demoWeb/controller/operacionesMatematicas/dividir/18/2", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}

}
