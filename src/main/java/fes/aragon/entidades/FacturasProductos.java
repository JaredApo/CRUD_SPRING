package fes.aragon.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.Mapping;

@Entity
@Table(name="facturas_productos")
@NamedQueries({
	@NamedQuery(name="proFac.todos",query="SELECT u FROM FacturasProductos u"),
	@NamedQuery(name="proFac.porFact",query="SELECT u FROM FacturasProductos u WHERE u.facturas=:ref"),
	@NamedQuery(name="proFac.id",query="SELECT u FROM FacturasProductos u WHERE u.facturas=:idF AND u.productos=:idP")
})
public class FacturasProductos implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected FacturasProductosPK facturasProductosPK;
	
	@Column(name="cantidad_facturas_productos")
	private double cantidadFacturasProductos;
	
	@JoinColumn(name="id_facturas", referencedColumnName = "id_facturas", insertable=false, updatable = false)
	@ManyToOne(fetch=FetchType.EAGER)
	private Facturas facturas;
	
	@JoinColumn(name="id_productos", referencedColumnName = "id_productos",insertable=false, updatable = false)
	@ManyToOne(fetch=FetchType.EAGER)
	private Productos productos;
	
	public FacturasProductos() {
		// TODO Auto-generated constructor stub
	}
	
	
	public FacturasProductos(FacturasProductosPK facturasProductosPK) {
		this.facturasProductosPK = facturasProductosPK;
	}

	public FacturasProductos(FacturasProductosPK facturasProductosPK, double cantidadFacturasProductos) {
		this.facturasProductosPK = facturasProductosPK;
		this.cantidadFacturasProductos = cantidadFacturasProductos;
	}

	public FacturasProductos(int idFacturas, int idProductos) {
		this.facturasProductosPK=new FacturasProductosPK(idFacturas,idProductos);
	}


	public FacturasProductosPK getFacturasProductosPK() {
		return facturasProductosPK;
	}

	public void setFacturasProductosPK(FacturasProductosPK facturasProductosPK) {
		this.facturasProductosPK = facturasProductosPK;
	}

	public double getCantidadFacturasProductos() {
		return cantidadFacturasProductos;
	}

	public void setCantidadFacturasProductos(double cantidadFacturasProductos) {
		this.cantidadFacturasProductos = cantidadFacturasProductos;
	}

	public Facturas getFacturas() {
		return facturas;
	}

	public void setFacturas(Facturas facturas) {
		this.facturas = facturas;
	}

	public Productos getProductos() {
		return productos;
	}

	public void setProductos(Productos productos) {
		this.productos = productos;
	}


	@Override
	public int hashCode() {
		return Objects.hash(cantidadFacturasProductos, facturas, facturasProductosPK);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturasProductos other = (FacturasProductos) obj;
		return Double.doubleToLongBits(cantidadFacturasProductos) == Double
				.doubleToLongBits(other.cantidadFacturasProductos) && Objects.equals(facturas, other.facturas)
				&& Objects.equals(facturasProductosPK, other.facturasProductosPK);
	}


	@Override
	public String toString() {
		return "FacturasProductos [facturasProductosPK=" + facturasProductosPK.getIdFacturas()+" "+facturasProductosPK.getIdProductos() + ", cantidadFacturasProductos="
				+ cantidadFacturasProductos + ", facturas=" + facturas.getReferenciaFacturas() + ", productos=" + 
				productos.getNombreProductos() + "]";
	}
	
	
	
}
