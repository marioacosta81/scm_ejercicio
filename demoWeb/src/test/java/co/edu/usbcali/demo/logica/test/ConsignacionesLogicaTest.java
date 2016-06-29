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
import co.edu.usbcali.demo.logica.IConsignacionesLogica;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionesLogicaTest {

	private static final Logger log=LoggerFactory.getLogger(ConsignacionesLogicaTest.class);

	@Autowired
	private IConsignacionesLogica  consignacionesLogica;

	@Autowired
	private IUsuariosDAO usuariosDAO;

	@Autowired
	private ICuentasDAO cuentasDAO;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
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

		consignacionesLogica.grabar(consignaciones);
	}

	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		ConsignacionesId consignacionesId = new ConsignacionesId();
		consignacionesId.setConCodigo(100L);
		consignacionesId.setCueNumero("4008-5305-0080");

		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignacion no exsiste",consignaciones);
		log.info(consignaciones.getId().getCueNumero()+"-"+consignaciones.getId().getConCodigo());
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		ConsignacionesId consignacionesId = new ConsignacionesId();
		consignacionesId.setConCodigo(100L);
		consignacionesId.setCueNumero("4008-5305-0080");

		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignacion no exsiste",consignaciones);

		consignaciones.setConValor(new BigDecimal("3000000"));

		consignacionesLogica.modificar(consignaciones);
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		ConsignacionesId consignacionesId = new ConsignacionesId();
		consignacionesId.setConCodigo(100L);
		consignacionesId.setCueNumero("4008-5305-0080");

		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignacion no exsiste",consignaciones);

		consignacionesLogica.borrar(consignaciones);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<Consignaciones> lasConsignaciones=   consignacionesLogica.consultarTodos();
		for (Consignaciones consignaciones : lasConsignaciones) {
			log.info(consignaciones.getId().getCueNumero()+"-"+consignaciones.getId().getConCodigo());
		}
	}



}
