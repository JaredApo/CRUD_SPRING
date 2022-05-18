package fes.aragon.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.JDBCException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;
import fes.aragon.entidades.FacturasProductos;
import fes.aragon.entidades.Productos;
import fes.aragon.formulario.ClientesFormulario;

@Repository
public class ProductosFacturasDAOImp implements IProductosFacturasDAO {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Productos> traerProductos() {
		return em.createNamedQuery("producto.todos").getResultList();
	}

	@Override
	public List<Facturas> traerporFacturas(Facturas factura) {
		return em.createNamedQuery("proFac.porFact").setParameter("ref", factura).getResultList();
	}

	@Override
	@Transactional(readOnly=false)
	public void registrarProdFacturas(FacturasProductos factura) {
		em.persist(factura);
	}

	@Override
	public List<FacturasProductos> traerRel() {
		return em.createNamedQuery("proFac.todos").getResultList();
	}

	@Override
	public void eliminarProdEnFact(int idFactura, int idProducto) {
		StoredProcedureQuery proc= em.createStoredProcedureQuery("eliminarFP");
		proc.registerStoredProcedureParameter("idF", Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter("idP", Integer.class, ParameterMode.IN);
		proc.setParameter("idF", idFactura);
		proc.setParameter("idP", idProducto);
		proc.execute();
	}

	@Override
	@Transactional(readOnly=false)
	public void modificarFacturas(FacturasProductos facProd) {
		em.merge(facProd);
	}
	
	@Override
	@Transactional(readOnly=false)
	public FacturasProductos traerFacturasProductos(Facturas idF, Productos idP) {
		Query query=em.createNamedQuery("proFac.id");
		query.setParameter("idF", idF);
		query.setParameter("idP", idP);
		return (FacturasProductos) query.getSingleResult();
	}
	
	
}
