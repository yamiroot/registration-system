package codigo.tabla.proveedores;


public class Proveedores {
	
	private String RUC;
	private String nombreProveedor;
	
	
	public Proveedores(String RUC, String nombreProveedor) {
		this.RUC = RUC;
		this.nombreProveedor = nombreProveedor;
	}

	public String getRUC() {
		return RUC;
	}

	public void setRUC(String rUC) {
		RUC = rUC;
	}		

	/**
	 * @return the nombreProveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}

	/**
	 * @param nombreProveedor the nombreProveedor to set
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	
	
}
