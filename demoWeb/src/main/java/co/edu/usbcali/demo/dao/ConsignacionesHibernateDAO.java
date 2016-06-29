package co.edu.usbcali.demo.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;

@Repository
@Scope("singleton")
public class ConsignacionesHibernateDAO implements IConsignacionesDAO  {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void grabar(Consignaciones consignaciones) {
		sessionFactory.getCurrentSession().save(consignaciones);		
	}

	@Override
	public void modificar(Consignaciones consignaciones) {
		sessionFactory.getCurrentSession().update(consignaciones);		
	}

	@Override
	public void borrar(Consignaciones consignaciones) {
		sessionFactory.getCurrentSession().delete(consignaciones);
	}

	@Override
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId consignacionesId) {
		return sessionFactory.getCurrentSession().get(Consignaciones.class, consignacionesId);
	}

	@Override
	public List<Consignaciones> consultarTodos() {
		return sessionFactory.getCurrentSession().createCriteria(Consignaciones.class).list();
	}

	@Override
	public Long getNextConCodigo(){
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"select max(CON_CODIGO)+1 CON_CODIGO from  consignaciones ");
		BigDecimal bigDecimal = (BigDecimal)query.list().get(0);   
		return bigDecimal.longValue();
	}
	


}
