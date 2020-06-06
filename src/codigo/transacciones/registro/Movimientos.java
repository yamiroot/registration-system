package codigo.transacciones.registro;

public class Movimientos {

	private int codigoAlmacen;
	private int codigoProducto;
	private int tipoOperacion;
	private float cantidad;

	public Movimientos(int codigoAlmacen, int codigoProducto, int tipoOperacion, float cantidad) {
		this.codigoAlmacen = codigoAlmacen;
		this.codigoProducto = codigoProducto;
		this.tipoOperacion = tipoOperacion;
		this.cantidad = cantidad;
	}

	public int getCodigoAlmacen() {
		return codigoAlmacen;
	}

	public void setCodigoAlmacen(int codigoAlmacen) {
		this.codigoAlmacen = codigoAlmacen;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public int getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(int tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Movimientos [codigoAlmacen=" + codigoAlmacen + ", codigoProducto=" + codigoProducto + ", tipoOperacion="
				+ tipoOperacion + ", cantidad=" + cantidad + "]";
	}
	
	

}
