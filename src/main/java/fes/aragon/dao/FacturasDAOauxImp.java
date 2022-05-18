package fes.aragon.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;
import fes.aragon.formulario.ClientesFormulario;

@Repository
public class FacturasDAOauxImp implements IFacturasDAOaux {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Facturas> traerFacturasCliente(Clientes cliente) {
		return em.createNamedQuery("factura.datos").setParameter("id", cliente).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Facturas factXReferencia(String referencia) {
		return (Facturas) em.createNamedQuery("factura.datosporref").setParameter("referencia", referencia).getSingleResult();
	}
}
