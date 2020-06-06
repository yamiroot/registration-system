
package codigo.tabla.productos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ProductoFicheroBinario {

	public static void main(String[] args) {

		ProductoFicheroBinario miLista = new ProductoFicheroBinario();
		//miLista.aniadirProductosArray(new Productos(2018, "Lacteos"));
		//miLista.aniadirProductosArray(new Productos(2019, "Lacteos2"));
		try {

			// Guardamos el array en el archivo
			//miLista.reescribirFichero();
			miLista.leerFichero();
			// Imprimimos el array en consola
			miLista.mostrarArrayPantalla();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Productos> listadoDeProductos;

	private String nombreFichero = "Productos.bin";
	private File guardarArchivoBinario = new File(nombreFichero);

	private FileOutputStream inicializadorEscribir;
	private DataOutputStream dos;
	private String devuelveDescripcion;

	public ProductoFicheroBinario() {
		listadoDeProductos = new ArrayList<Productos>();
	}

	// Metodo para guardar el array list en el fichero
	public void guardarArrayFichero() throws IOException {

		// SINO EXISTE
		if (!guardarArchivoBinario.exists()) {

			// SE CREA UN NUEVO FICHERO
			guardarArchivoBinario.createNewFile();

			try {

				FileOutputStream inicializadorEscribirPrimero = new FileOutputStream(guardarArchivoBinario);
				DataOutputStream dosPrimerArchivo = new DataOutputStream(inicializadorEscribirPrimero);

				for (Productos a : listadoDeProductos) {

					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeInt(a.getCodigoProducto());
						dosPrimerArchivo.writeUTF(a.getDescripcionProducto());
					}
				}
				dosPrimerArchivo.close();
				inicializadorEscribirPrimero.close();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n" +
                        "No se pudo crear el archivo binario","Error!",
                        JOptionPane.ERROR_MESSAGE);
			}

		} else {

			try {

				inicializadorEscribir = new FileOutputStream(guardarArchivoBinario, true);
				dos = new DataOutputStream(inicializadorEscribir);

				for (Productos a : listadoDeProductos) {

					if (dos != null) {
						dos.writeInt(a.getCodigoProducto());
						dos.writeUTF(a.getDescripcionProducto());
					}
				}
				
				System.out.println("Datos insertados correctamente en el fichero existente.");
				dos.close();
				inicializadorEscribir.close();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n" +
                        "No se pudo reescribir en el archivo binario","Error!",
                        JOptionPane.ERROR_MESSAGE);
			}

		}
	}


	public void leerFichero() throws FileNotFoundException, IOException {

		File archivoBinarioLectura = new File(nombreFichero);

		if (!archivoBinarioLectura.exists()) {
			throw new FileNotFoundException("Fichero no encontrado.");
		}

		FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
		DataInputStream dis = new DataInputStream(inicializadorLeer);

		boolean salir = false;

		do {

			try {
				
				int codigoProducto = dis.readInt();
				String descripcionProducto = dis.readUTF();
				
				Productos a = new Productos(codigoProducto, descripcionProducto);
				
				aniadirProductosArray(a);

				// Manda cuando es el final del fichero,
			} catch (EOFException e) {
				//Final del archivo
				salir = true;
			}

		} while (!salir);
		dis.close();
		inicializadorLeer.close();

	}

	public void aniadirProductosArray(Productos a) {

		if (listadoDeProductos == null) {
			listadoDeProductos = new ArrayList<Productos>();
		}

		listadoDeProductos.add(a);

	}

	public String[][] mostrarArrayTable() {
		
		String matriz[][]=new String[listadoDeProductos.size()][2];
		
		for (int i = 0; i < listadoDeProductos.size(); i++) {
			
			matriz[i][0]=Integer.toString(listadoDeProductos.get(i).getCodigoProducto());
			matriz[i][1]=listadoDeProductos.get(i).getDescripcionProducto();
		}
		
		return matriz;

	}

	public boolean buscarCodigo(int codigoVerificar, boolean existe) throws FileNotFoundException, IOException {

		File archivoBinarioLectura = new File(nombreFichero);

		if (!archivoBinarioLectura.exists()) {
			guardarArrayFichero();
			existe = false;
		} else {
			FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
			DataInputStream dis = new DataInputStream(inicializadorLeer);

			boolean salir = false;

			do {

				try {

					int codigoProducto = dis.readInt();
					String descripcionProducto = dis.readUTF();

					if (codigoVerificar == codigoProducto) {
						devuelveDescripcion = descripcionProducto;
						existe = true;
					}

					// Manda cuando es el final del fichero,
				} catch (EOFException e) {
					salir = true;
				}

			} while (!salir);
			dis.close();
			inicializadorLeer.close();
		}

		return existe;

	}

	public void buscarCodigoEliminar(int codigoVerificar) throws FileNotFoundException, IOException {

		File archivoBinarioLectura = new File(nombreFichero);

		if (!archivoBinarioLectura.exists()) {
			guardarArrayFichero();
		}

		FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
		DataInputStream dis = new DataInputStream(inicializadorLeer);

		boolean salir = false;
		int pos = 0;
		int posicion = 0;
		do {

			try {
				int codigoProducto = dis.readInt();
				String descripcionProducto = dis.readUTF();

				Productos a = new Productos(codigoProducto, descripcionProducto);
				
				aniadirProductosArray(a);

				if (codigoVerificar == codigoProducto) {
					posicion += pos;
				}

				pos++;

				// Manda cuando es el final del fichero,
			} catch (EOFException e) {
				salir = true;
			}

		} while (!salir);

		eliminarProducto(posicion);
		dis.close();
		inicializadorLeer.close();

	}

	public void eliminarProducto(int posicion) {

		// REMOVEMOS EL DATO CREADO EN ESA POSICION
		// System.out.println(listadoDeAlmacenes.get(posicion));
		listadoDeProductos.remove(posicion);

	}
	
	
	public void reescribirFichero() throws IOException {

		// SI EXISTE, VOLVER A ACTUALIZAR TODO MI FICHERO
		if (guardarArchivoBinario.exists()) {

			// SE CREA UN NUEVO FICHERO
			guardarArchivoBinario.createNewFile();

			try {

				FileOutputStream inicializadorEscribirPrimero = new FileOutputStream(guardarArchivoBinario);
				DataOutputStream dosPrimerArchivo = new DataOutputStream(inicializadorEscribirPrimero);

				for (Productos a : listadoDeProductos) {

					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeInt(a.getCodigoProducto());
						dosPrimerArchivo.writeUTF(a.getDescripcionProducto());
					}
				}
				
				System.out.println("Se reescribio el archivo con exito.");

				dosPrimerArchivo.close();
				inicializadorEscribirPrimero.close();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n" +
                        "No se pudo eliminar el producto","Error!",
                        JOptionPane.ERROR_MESSAGE);
			}

		}

	}
	
	
	// Nos retorna el numero de filas o tama�o del arrayList
	public int obtenerTamanioArrayList() {
		return listadoDeProductos.size();
	}

	// Nos retorna la posici�n del objeto dentro del ArrayList de la clase Almacen
	public Productos obtenerProducto(int pos) {
		return listadoDeProductos.get(pos);

	}
	
	public void mostrarArrayPantalla() {
		
		for(int i=0;i<listadoDeProductos.size();i++) {
			
			System.out.println(listadoDeProductos.get(i));
		}
		
		//for(Almacen item: listadoDeAlmacenes) {
			//System.out.println(item.toString());
		//}
	}
	
	public String getDescripcionProducto() {
		return devuelveDescripcion;
	}

}
