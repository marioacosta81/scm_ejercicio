package co.edu.usbcali.demo.dao.test;

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
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentasDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(CuentasDAOTest.class);
	
	@Autowired
	private ICuentasDAO cuentaDAO;
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	
	private String numeroCuenta="4008-5305-555";
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Cuentas cuentas = new Cuentas();
		cuentas.setCueNumero(numeroCuenta);
		Clientes cliente = clienteDAO.consultarClinetePorId(801234L);
		cuentas.setClientes(cliente);
		cuentas.setCueSaldo(new BigDecimal("80984") );
		cuentas.setCueActiva("S");
		cuentas.setCueClave("chirimoya");
		
		cuentaDAO.grabar(cuentas);
	}
	
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Cuentas cuentas =  cuentaDAO.consultarCuentasPorId(numeroCuenta);
		assertNotNull("La cuenta no existe",cuentas);
		log.info(cuentas.getCueNumero());
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Cuentas cuentas =  cuentaDAO.consultarCuentasPorId(numeroCuenta);
		assertNotNull("La cuenta no existe",cuentas);
		cuentas.setCueClave("2222");
		
		cuentaDAO.modificar(cuentas);
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Cuentas cuentas =  cuentaDAO.consultarCuentasPorId(numeroCuenta);
		assertNotNull("La cuenta no existe",cuentas);
		cuentaDAO.borrar(cuentas);
	}
	
	
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Cuentas> lasCuentas= cuentaDAO.consultarTodos();
		for (Cuentas cuentas : lasCuentas ) {
			log.info(cuentas.getCueNumero());
		}
	}
	
	

}
