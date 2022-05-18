package fes.aragon.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fes.aragon.entidades.Productos;

@Repository
public class ProductoDAOauxImp implements IProductoDAOaux{
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	@Override
	public Productos buscarXnombre(String nombre) {
		return (Productos) em.createNamedQuery("producto.datosporref").setParameter("nombre", nombre).getSingleResult();
	}

}
