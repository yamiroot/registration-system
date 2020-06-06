
package codigo.tabla.proveedores;

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

public class ProveedorFicheroBinario {

	public static void main(String[] args) {

		ProveedorFicheroBinario miLista = new ProveedorFicheroBinario();

		try {

			// Guardamos el array en el archivo
			// miLista.guardarArrayFichero();
			miLista.leerFichero();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Proveedores> listadoDeProveedores;

	private String nombreFichero = "Proveedores.bin";
	private File guardarArchivoBinario = new File(nombreFichero);

	private FileOutputStream inicializadorEscribir;
	private DataOutputStream dos;

	private String nombreProveedor;

	public ProveedorFicheroBinario() {
		listadoDeProveedores = new ArrayList<Proveedores>();
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

				for (Proveedores a : listadoDeProveedores) {

					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeUTF(a.getRUC());
						dosPrimerArchivo.writeUTF(a.getNombreProveedor());
					}
				}

				System.out.println("Creo el archivo con exito.");

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

				for (Proveedores a : listadoDeProveedores) {

					if (dos != null) {
						dos.writeUTF(a.getRUC());
						dos.writeUTF(a.getNombreProveedor());
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

	public void reescribirFichero() throws IOException {

		// SI EXISTE, VOLVER A ACTUALIZAR TODO MI FICHERO
		if (guardarArchivoBinario.exists()) {

			// SE CREA UN NUEVO FICHERO
			guardarArchivoBinario.createNewFile();

			try {

				FileOutputStream inicializadorEscribirPrimero = new FileOutputStream(guardarArchivoBinario);
				DataOutputStream dosPrimerArchivo = new DataOutputStream(inicializadorEscribirPrimero);

				for (Proveedores a : listadoDeProveedores) {

					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeUTF(a.getRUC());
						dosPrimerArchivo.writeUTF(a.getNombreProveedor());
					}
				}

				System.out.println("Reescribio el archivo con exito.");

				dosPrimerArchivo.close();
				inicializadorEscribirPrimero.close();

			} catch (Exception ex) {
				System.out.println("No se pudo reescribir el archivo.");
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
				String RUC = dis.readUTF();
				String nombreProveedor = dis.readUTF();

				Proveedores a = new Proveedores(RUC, nombreProveedor);
				aniadirProveedoresArray(a);

				// Manda cuando es el final del fichero,
			} catch (EOFException e) {
				salir = true;
			}

		} while (!salir);
		dis.close();
		inicializadorLeer.close();

	}

	
	public String[][] mostrarArrayTable() {

		String matriz[][] = new String[listadoDeProveedores.size()][2];
		
		for (int i = 0; i < listadoDeProveedores.size(); i++) {
			matriz[i][0] = listadoDeProveedores.get(i).getRUC();
			matriz[i][1] = listadoDeProveedores.get(i).getNombreProveedor();
		}

		return matriz;

	}
	

	public boolean buscarRUC(String rucVerificar, boolean verifica) throws FileNotFoundException, IOException {

		File archivoBinarioLectura = new File(nombreFichero);
		verifica = false;

		if (!archivoBinarioLectura.exists()) {
			guardarArrayFichero();
		}

		FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
		DataInputStream dis = new DataInputStream(inicializadorLeer);

		boolean salir = false;

		do {

			try {

				String RUC = dis.readUTF();
				String nombreProveedor = dis.readUTF();

				if (RUC.equalsIgnoreCase(rucVerificar)) {
					verifica = true;
					this.nombreProveedor = nombreProveedor;
					return verifica;
				}

			// Manda cuando es el final del fichero,
			} catch (EOFException e) {
				salir = true;
			}

		} while (!salir);
		dis.close();
		inicializadorLeer.close();

		return verifica;

	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void buscarRucEliminar(String rucVerificar) throws FileNotFoundException, IOException {

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
				String RUC = dis.readUTF();
				String nombreProveedor = dis.readUTF();

				Proveedores a = new Proveedores(RUC, nombreProveedor);
				aniadirProveedoresArray(a);

				if (RUC.equalsIgnoreCase(rucVerificar)) {
					posicion += pos;

				}

				pos++;

				// Manda cuando es el final del fichero,
			} catch (EOFException e) {
				salir = true;
			}

		} while (!salir);

		eliminarProveedor(posicion);
		dis.close();
		inicializadorLeer.close();

	}

	public void aniadirProveedoresArray(Proveedores a) {

		if (listadoDeProveedores == null) {
			listadoDeProveedores = new ArrayList<Proveedores>();
		}

		listadoDeProveedores.add(a);

	}

	public void eliminarProveedor(int posicion) {

		// REMOVEMOS EL DATO CREADO EN ESA POSICION
		listadoDeProveedores.remove(posicion);

	}

	// Nos retorna el numero de filas o tama�o del arrayList
	public int obtenerTamanioArrayList() {
		return listadoDeProveedores.size();
	}

	// Nos retorna la posici�n del objeto dentro del ArrayList de la clase Almacen
	public Proveedores obtenerProveedor(int pos) {
		return listadoDeProveedores.get(pos);

	}

}
