package codigo.tabla.productos;

public class Productos {

	private int codigoProducto;
	private String descripcionProducto;

	public Productos(int codigoProducto, String descripcionProducto) {
		this.codigoProducto = codigoProducto;
		this.descripcionProducto = descripcionProducto;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	@Override
	public String toString() {
		return "Productos [codigoProducto=" + codigoProducto + ", descripcionProducto=" + descripcionProducto + "]";
	}
	
	

}

