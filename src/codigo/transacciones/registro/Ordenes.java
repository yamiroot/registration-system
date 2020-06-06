package codigo.transacciones.registro;

public class Ordenes {

	private int numOrden;
	private String RUC;
	private int codigoProducto;
	private float cantidad;
	private float precio;
	private float total;

	public Ordenes(int numOrden, String RUC, int codigoProducto, float cantidad, float precio, float total) {
		this.numOrden = numOrden;
		this.RUC = RUC;
		this.codigoProducto = codigoProducto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = total;
	}

	public int getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(int numOrden) {
		this.numOrden = numOrden;
	}

	public String getRUC() {
		return RUC;
	}

	public void setRUC(String RUC) {
		this.RUC = RUC;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Ordenes [numOrden=" + numOrden + ", RUC=" + RUC + ", codigoProducto=" + codigoProducto + ", cantidad="
				+ cantidad + ", precio=" + precio + ", total=" + total + "]";
	}
	
	

}
