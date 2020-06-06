package codigo.tabla.almacen;

import java.io.Serializable;

public class Almacen implements Serializable{
	
	private int codigoAlmacen;
	private String descripcionAlmacen;
	private int codigoProducto;
	private float cantidadProducto;

	
	//Constructor
	public Almacen(int codigoAlmacen, String descripcionAlmacen, int codigoProducto ,float cantidadProducto) {
		this.codigoAlmacen = codigoAlmacen;
		this.descripcionAlmacen = descripcionAlmacen;
		this.codigoProducto = codigoProducto;
		this.cantidadProducto = cantidadProducto;
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
	 * @return the descripcionAlmacen
	 */
	public String getDescripcionAlmacen() {
		return descripcionAlmacen;
	}


	/**
	 * @param descripcionAlmacen the descripcionAlmacen to set
	 */
	public void setDescripcionAlmacen(String descripcionAlmacen) {
		this.descripcionAlmacen = descripcionAlmacen;
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
	 * @return the cantidadProducto
	 */
	public float getCantidadProducto() {
		return cantidadProducto;
	}


	/**
	 * @param cantidadProducto the cantidadProducto to set
	 */
	public void setCantidadProducto(float cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}


	@Override
	public String toString() {
		return "Almacen [codigoAlmacen=" + codigoAlmacen + ", descripcionAlmacen=" + descripcionAlmacen
				+ ", codigoProducto=" + codigoProducto + ", cantidadProducto=" + cantidadProducto + "]";
	}
	
	

	
}
