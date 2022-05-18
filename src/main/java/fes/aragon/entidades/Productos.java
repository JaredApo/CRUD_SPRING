package fes.aragon.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="productos")
@NamedQueries({
	@NamedQuery(name="producto.todos",query="SELECT u FROM Productos u"),
	@NamedQuery(name="producto.datos",query="SELECT u FROM Productos u WHERE u.idProductos=:id"),
	@NamedQuery(name="producto.datosporref",query="SELECT u FROM Productos u WHERE u.nombreProductos=:nombre")
})
public class Productos implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_productos")
	private int idProductos;
	
	@Column(name="nombre_productos")
	@NotEmpty()
	private String nombreProductos;
	
	@Column(name="precio_productos")
	@NotEmpty()
	private String precioProductos;
	
	@OneToMany(mappedBy = "productos", fetch=FetchType.EAGER)
	private List<FacturasProductos> facturasProductosList;
	
	public Productos() {
		// TODO Auto-generated constructor stub
	}

	public int getIdProductos() {
		return idProductos;
	}

	public void setIdProductos(int idProductos) {
		this.idProductos = idProductos;
	}

	public String getNombreProductos() {
		return nombreProductos;
	}

	public void setNombreProductos(String nombreProductos) {
		this.nombreProductos = nombreProductos;
	}

	public String getPrecioProductos() {
		return precioProductos;
	}

	public void setPrecioProductos(String precioProductos) {
		this.precioProductos = precioProductos;
	}

	public List<FacturasProductos> getFacturasProductosList() {
		return facturasProductosList;
	}

	public void setFacturasProductosList(List<FacturasProductos> facturasProductosList) {
		this.facturasProductosList = facturasProductosList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(facturasProductosList, idProductos, nombreProductos, precioProductos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Productos other = (Productos) obj;
		return Objects.equals(facturasProductosList, other.facturasProductosList)
				&& Objects.equals(idProductos, other.idProductos)
				&& Objects.equals(nombreProductos, other.nombreProductos)
				&& Objects.equals(precioProductos, other.precioProductos);
	}
	
}
