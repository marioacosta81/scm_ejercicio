package co.edu.usbcali.demo.logica.test;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

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

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.logica.ICuentasLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentaLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(CuentaLogicaTest.class);
	
	@Autowired
	private ICuentasLogica cuentaLogica;
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	
	private String numeroCuenta="4008-5305-555";
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		
		Cuentas cuentas = new Cuentas();
		cuentas.setCueNumero(numeroCuenta);
		Clientes cliente = clienteDAO.consultarClinetePorId(801234L);
		cuentas.setClientes(cliente);
		cuentas.setCueSaldo(new BigDecimal("80984") );
		cuentas.setCueActiva("S");
		cuentas.setCueClave("chirimoya");
		
		cuentaLogica.grabar(cuentas);
		
		
		
	}
	
	
	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		Cuentas cuentas =  cuentaLogica.consultarCuentasPorId(numeroCuenta);
		assertNotNull("La cuenta no existe",cuentas);
		log.info(cuentas.getCueNumero());
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		Cuentas cuentas =  cuentaLogica.consultarCuentasPorId(numeroCuenta);
		assertNotNull("La cuenta no existe",cuentas);
		cuentas.setCueClave("2222");
		
		cuentaLogica.modificar(cuentas);
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		Cuentas cuentas =  cuentaLogica.consultarCuentasPorId(numeroCuenta);
		assertNotNull("La cuenta no existe",cuentas);
		cuentaLogica.borrar(cuentas);
	}
	
	

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<Cuentas> lasCuentas= cuentaLogica.consultarTodos();
		for (Cuentas cuentas : lasCuentas ) {
			log.info(cuentas.getCueNumero());
		}
	}
	
	

}
