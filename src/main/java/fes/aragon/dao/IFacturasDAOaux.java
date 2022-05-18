package fes.aragon.dao;


import java.util.List;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;

public interface IFacturasDAOaux {
	public List<Facturas> traerFacturasCliente(Clientes cliente);
	public Facturas factXReferencia(String referencia);
}
