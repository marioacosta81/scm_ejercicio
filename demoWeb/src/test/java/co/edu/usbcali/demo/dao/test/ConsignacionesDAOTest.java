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

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionesDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(ConsignacionesDAOTest.class);
	
	@Autowired
	private IConsignacionesDAO  consignacionesDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Consignaciones consignaciones = new Consignaciones();
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		
		consignacionesId.setConCodigo(100L);
		consignacionesId.setCueNumero("4008-5305-0080");
		consignaciones.setId(consignacionesId);
		
		Usuarios usuarios = usuariosDAO.consultarUsuarioPorId(10L);
		consignaciones.setUsuarios(usuarios);
		
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId("4008-5305-0080");
		consignaciones.setCuentas(cuentas);
		
		consignaciones.setConValor(new BigDecimal("50000000"));
		
		consignaciones.setConFecha(new java.util.Date());
		
		consignaciones.setConDescripcion("Descripcion de consignacion");
		
		consignacionesDAO.grabar(consignaciones);
	}
	
	
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		consignacionesId.setConCodigo(100L);
		consignacionesId.setCueNumero("4008-5305-0080");
		
		Consignaciones consignaciones = consignacionesDAO.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignacion no exsiste",consignaciones);
		log.info(consignaciones.getId().getCueNumero()+"-"+consignaciones.getId().getConCodigo());
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		ConsignacionesId consignacionesId = new ConsignacionesId();
		consignacionesId.setConCodigo(100L);
		consignacionesId.setCueNumero("4008-5305-0080");
		
		Consignaciones consignaciones = consignacionesDAO.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignacion no exsiste",consignaciones);
		
		consignaciones.setConValor(new BigDecimal("3000000"));
		
		consignacionesDAO.modificar(consignaciones);
	}
	
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		ConsignacionesId consignacionesId = new ConsignacionesId();
		consignacionesId.setConCodigo(100L);
		consignacionesId.setCueNumero("4008-5305-0080");
		
		Consignaciones consignaciones = consignacionesDAO.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignacion no exsiste",consignaciones);
		
		consignacionesDAO.borrar(consignaciones);
	}

	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Consignaciones> lasConsignaciones=   consignacionesDAO.consultarTodos();
		for (Consignaciones consignaciones : lasConsignaciones) {
			log.info(consignaciones.getId().getCueNumero()+"-"+consignaciones.getId().getConCodigo());
		}
	}
	
	
	@Test
	@Transactional(readOnly=true)
	public void ftest() {
		
		Long max = consignacionesDAO.getNextConCodigo();
		log.info(max+"");
		
		
	}
	
	

}
