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

import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.logica.IRetirosLogica;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RetirosLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(RetirosLogicaTest.class);
	
	@Autowired
	private IRetirosLogica retirosLogica;   
	
	@Autowired
	private IUsuariosDAO usuarioDAO;
	
	@Autowired
	private ICuentasDAO cuentaDAO;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		
		Retiros retiros= new Retiros();
		
		RetirosId id = new RetirosId();
		id.setCueNumero("4008-5305-0080");
		id.setRetCodigo(100L);
		retiros.setId(id);
		
		Usuarios usuarios = usuarioDAO.consultarUsuarioPorId(10L);
		retiros.setUsuarios(usuarios);
		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId("4008-5305-0080");
		retiros.setCuentas(cuentas);
		
		retiros.setRetValor(new BigDecimal("636535"));
		retiros.setRetFecha(new java.util.Date());
		retiros.setRetDescripcion("Descripcion del retiro");
		
		retirosLogica.grabar(retiros);
		
		
		
	}
	
	
	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		RetirosId id = new RetirosId();
		id.setCueNumero("4008-5305-0080");
		id.setRetCodigo(100L);
		
		Retiros retiro=  retirosLogica.consultarRetirosPorId(id);
		assertNotNull("El retiro no exsiste",retiro);
		log.info(retiro.getId().getCueNumero()+"-"+retiro.getId().getRetCodigo());
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		RetirosId id = new RetirosId();
		id.setCueNumero("4008-5305-0080");
		id.setRetCodigo(100L);
		
		Retiros retiro=  retirosLogica.consultarRetirosPorId(id);
		assertNotNull("El retiro no exsiste",retiro);
		retiro.setRetValor(new BigDecimal("53433"));
		retirosLogica .grabar(retiro);
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		RetirosId id = new RetirosId();
		id.setCueNumero("4008-5305-0080");
		id.setRetCodigo(100L);
		
		Retiros retiro=  retirosLogica.consultarRetirosPorId(id);
		assertNotNull("El retiro no exsiste",retiro);
		retirosLogica.borrar(retiro);
	}
	
	

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<Retiros> losRetiros = retirosLogica.consultarTodos();
		for (Retiros retiro : losRetiros) {
			log.info(retiro.getId().getCueNumero()+"-"+retiro.getId().getRetCodigo());
		}
	}
	
	

}
