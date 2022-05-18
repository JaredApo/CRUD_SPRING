package fes.aragon.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="facturas")
@NamedQueries({
	@NamedQuery(name="factura.todos",query="SELECT u FROM Facturas u"),
	@NamedQuery(name="factura.datos",query="SELECT u FROM Facturas u WHERE u.idClientes=:id"),
	@NamedQuery(name="factura.datosporref",query="SELECT u FROM Facturas u WHERE u.referenciaFacturas=:referencia")
})
public class Facturas implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_facturas")
	private Integer idFacturas;
	
	@JoinColumn(name="id_clientes",referencedColumnName = "id_clientes")
	@ManyToOne(fetch=FetchType.EAGER)
	//así se le llama al mapeo en la clase Clientes
	//debe ser el mismo nombre, a huevo
	private Clientes idClientes;
	
	@Column(name="referencia_facturas")
	private String referenciaFacturas;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_facturas")
	private Date fechaFacturas;
	
	@OneToMany(mappedBy = "facturas", fetch=FetchType.EAGER, cascade= CascadeType.ALL)
	private List<FacturasProductos> facturasProductosList;
	
	public Facturas() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdFacturas() {
		return idFacturas;
	}

	public void setIdFacturas(Integer idFacturas) {
		this.idFacturas = idFacturas;
	}

	public Clientes getIdClientes() {
		return idClientes;
	}

	public void setIdClientes(Clientes idClientes) {
		this.idClientes = idClientes;
	}
	
	
	
	public String getReferenciaFacturas() {
		return referenciaFacturas;
	}

	public void setReferenciaFacturas(String referenciaFacturas) {
		this.referenciaFacturas = referenciaFacturas;
	}

	public Date getFechaFacturas() {
		return fechaFacturas;
	}

	public void setFechaFacturas(Date fechaFacturas) {
		this.fechaFacturas = fechaFacturas;
	}

	public List<FacturasProductos> getFacturasProductosList() {
		return facturasProductosList;
	}

	public void setFacturasProductosList(List<FacturasProductos> facturasProductosList) {
		this.facturasProductosList = facturasProductosList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(facturasProductosList, fechaFacturas, idClientes, idFacturas, referenciaFacturas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Facturas other = (Facturas) obj;
		return Objects.equals(facturasProductosList, other.facturasProductosList)
				&& Objects.equals(fechaFacturas, other.fechaFacturas) && Objects.equals(idClientes, other.idClientes)
				&& Objects.equals(idFacturas, other.idFacturas)
				&& Objects.equals(referenciaFacturas, other.referenciaFacturas);
	}


	
	
	 
}
