package fes.aragon.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="clientes")
@NamedQueries({
	@NamedQuery(name="cliente.todos",query="SELECT u FROM Clientes u"),
	@NamedQuery(name="cliente.datos",query="SELECT u FROM Clientes u WHERE u.idClientes=:id")
})
public class Clientes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_clientes")
	private int idClientes;
	
	@Column(name="nombre_clientes")
	@NotEmpty()
	@Size(min = 3,max = 15)
	private String nombreClientes;
	
	@Column(name="apellido_clientes")
	@NotEmpty()
	private String apellidoClientes;
	
	@OneToMany(mappedBy = "idClientes",fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Facturas> facturasList=new ArrayList<>();

	public Clientes() {
		// TODO Auto-generated constructor stub
	}
	
	public int getIdClientes() {
		return idClientes;
	}

	public void setIdClientes(int idCliente) {
		this.idClientes = idCliente;
	}

	public String getNombreClientes() {
		return nombreClientes;
	}

	public void setNombreClientes(String nombreCliente) {
		this.nombreClientes = nombreCliente;
	}

	public String getApellidoClientes() {
		return apellidoClientes;
	}

	public void setApellidoClientes(String apellidoCliente) {
		this.apellidoClientes = apellidoCliente;
	}

	public List<Facturas> getFacturasList() {
		return facturasList;
	}

	public void setFacturasList(List<Facturas> facturas) {
		this.facturasList = facturas;
	}
	
	public void agregarFactura(Facturas factura) {
		facturasList.add(factura);
	}
//Como entidades se implementan hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(apellidoClientes, facturasList, idClientes, nombreClientes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clientes other = (Clientes) obj;
		return apellidoClientes == other.apellidoClientes && Objects.equals(facturasList, other.facturasList)
				&& idClientes == other.idClientes && nombreClientes == other.nombreClientes;
	}

	@Override
	public String toString() {
		return "Clientes [idClientes=" + idClientes + ", nombreClientes=" + nombreClientes + ", apellidoClientes="
				+ apellidoClientes +"]";
	}
	
	
}
