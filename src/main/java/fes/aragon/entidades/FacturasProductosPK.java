package fes.aragon.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

//Es una clase de una tabla intermedia, que se llena con dos llaves foráneas
@Embeddable
public class FacturasProductosPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name="id_facturas")
	private int idFacturas;
	
	@Basic(optional = false)
	@Column(name="id_productos")
	private int idProductos;
	
	public FacturasProductosPK() {
		// TODO Auto-generated constructor stub
	}

	public FacturasProductosPK(int idFacturas, int idProductos) {
		this.idFacturas = idFacturas;
		this.idProductos = idProductos;
	}

	public int getIdFacturas() {
		return idFacturas;
	}

	public void setIdFacturas(int idFacturas) {
		this.idFacturas = idFacturas;
	}

	public int getIdProductos() {
		return idProductos;
	}

	public void setIdProductos(int idProductos) {
		this.idProductos = idProductos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idFacturas, idProductos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturasProductosPK other = (FacturasProductosPK) obj;
		return idFacturas == other.idFacturas && idProductos == other.idProductos;
	}
	
	

}
