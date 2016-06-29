package co.edu.usbcali.demo.logica.transacciones.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.logica.transacciones.ITransaccionesLogica;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TransaccionesLogicaTest {

	private static final Logger log=LoggerFactory.getLogger(TransaccionesLogicaTest.class);

	@Autowired
	private ITransaccionesLogica  transaccionesLogica;

	

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void consignar()throws Exception {
		
		transaccionesLogica.consignar(
				"4008-5305-0010",
				10L,
				4565L,
				"1234"
				);
		
		
		
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void retirar()throws Exception {
		
		transaccionesLogica.retirar(
				"4008-5305-0010",
				10L,
				4565L,
				"1234"
				);
		
		
		
	}



}
