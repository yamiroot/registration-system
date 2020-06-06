package codigo.transacciones.registro;

public class MovimientoTable {
	private int codigoAlmacen;
	private int codigoProducto;
	private String nombreOperacion;
	private float cantidad;
	
	public MovimientoTable(int codigoAlmacen, int codigoProducto, String nombreOperacion, float cantidad) {
		this.codigoAlmacen = codigoAlmacen;
		this.codigoProducto = codigoProducto;
		this.nombreOperacion = nombreOperacion;
		this.cantidad = cantidad;
	}

	/**
	 * @return the codigoAlmacen
	 */
	public int getCodigoAlmacen() {
		return codigoAlmacen;
	}

	/**
	 * @param codigoAlmacen the codigoAlmacen to set
	 */
	public void setCodigoAlmacen(int codigoAlmacen) {
		this.codigoAlmacen = codigoAlmacen;
	}

	/**
	 * @return the codigoProducto
	 */
	public int getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * @param codigoProducto the codigoProducto to set
	 */
	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	/**
	 * @return the nombreOperacion
	 */
	public String getNombreOperacion() {
		return nombreOperacion;
	}

	/**
	 * @param nombreOperacion the nombreOperacion to set
	 */
	public void setNombreOperacion(String nombreOperacion) {
		this.nombreOperacion = nombreOperacion;
	}

	/**
	 * @return the cantidad
	 */
	public float getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "MovimientoTable [codigoAlmacen=" + codigoAlmacen + ", codigoProducto=" + codigoProducto
				+ ", nombreOperacion=" + nombreOperacion + ", cantidad=" + cantidad + "]";
	}
	
	
	
	
}
