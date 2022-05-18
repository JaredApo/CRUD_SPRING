package fes.aragon.formulario;


public class ClientesFormulario {
	private String nombreClientes;
	private String apellidoClientes;
	private int idFactura;
	public String getNombreClientes() {
		return nombreClientes;
	}
	public void setNombreClientes(String nombreClientes) {
		this.nombreClientes = nombreClientes;
	}
	public String getApellidoClientes() {
		return apellidoClientes;
	}
	public void setApellidoClientes(String apellidoClientes) {
		this.apellidoClientes = apellidoClientes;
	}
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	@Override
	public String toString() {
		return "ClientesFormulario [nombreClientes=" + nombreClientes + ", apellidoClientes=" + apellidoClientes
				+ ", idFactura=" + idFactura + "]";
	}
	
	
}
