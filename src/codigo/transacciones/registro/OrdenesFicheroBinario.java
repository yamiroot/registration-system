package codigo.transacciones.registro;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class OrdenesFicheroBinario {

	private ArrayList<Ordenes> listadoDeOrdenes;
	private ArrayList<Ordenes> listadoDeOrdenesPersonal;
	
	private String nombreFichero = "Ordenes.bin";
	private File guardarArchivoBinario = new File(nombreFichero);
	
	private FileOutputStream inicializadorEscribir;
	private DataOutputStream dos;
	
	private int contadorOrden;

	public OrdenesFicheroBinario() {
		listadoDeOrdenes = new ArrayList<Ordenes>();
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

				for (Ordenes a : listadoDeOrdenes) {

					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeInt(a.getNumOrden());
						dosPrimerArchivo.writeUTF(a.getRUC());
						dosPrimerArchivo.writeInt(a.getCodigoProducto());
						dosPrimerArchivo.writeFloat(a.getCantidad());
						dosPrimerArchivo.writeFloat(a.getPrecio());
						dosPrimerArchivo.writeFloat(a.getTotal());
					}
				}

				System.out.println("Creo el archivo con exito.");

				dosPrimerArchivo.close();
				inicializadorEscribirPrimero.close();

			} catch (Exception ex) {
				System.out.println("No se pudo crear el archivo.");
			}

		} else {

			try {
				
				inicializadorEscribir = new FileOutputStream(guardarArchivoBinario, true);
				dos = new DataOutputStream(inicializadorEscribir);

				for (Ordenes a : listadoDeOrdenes) {

					if (dos != null) {
						dos.writeInt(a.getNumOrden());
						dos.writeUTF(a.getRUC());
						dos.writeInt(a.getCodigoProducto());
						dos.writeFloat(a.getCantidad());
						dos.writeFloat(a.getPrecio());
						dos.writeFloat(a.getTotal());
					}
				}

				System.out.println("Creo el archivo con exito.");

				dos.close();
				inicializadorEscribir.close();

			} catch (Exception ex) {
				System.out.println("No se pudo crear el archivo.");
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

				for (Ordenes a : listadoDeOrdenes) {

					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeInt(a.getNumOrden());
						dosPrimerArchivo.writeUTF(a.getRUC());
						dosPrimerArchivo.writeInt(a.getCodigoProducto());
						dosPrimerArchivo.writeFloat(a.getCantidad());
						dosPrimerArchivo.writeFloat(a.getPrecio());
						dosPrimerArchivo.writeFloat(a.getTotal());
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
			guardarArchivoBinario.createNewFile();
			throw new FileNotFoundException("Fichero no encontrado.");
		}

		FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
		DataInputStream dis = new DataInputStream(inicializadorLeer);

		boolean salir = false;

		do {

			try {
				
				int numOrden = dis.readInt();
				String RUC = dis.readUTF();
				int codigoProducto = dis.readInt();			
				float cantidad = dis.readFloat();
				float precio = dis.readFloat();
				float total = dis.readFloat();
				
				Ordenes a = new Ordenes(numOrden, RUC, codigoProducto, cantidad, precio, total);
				aniadirOrdenesArray(a);				
				
				if(!salir) {
					contadorOrden = numOrden;
					contadorOrden++;
				}

				// Manda cuando es el final del fichero,
			} catch (EOFException e) {
				salir = true;
			}

		} while (!salir);
		dis.close();
		inicializadorLeer.close();

	}
	
	public int getContadorOrden() {
		return contadorOrden;
	}
	
	public void aniadirOrdenesArray(Ordenes a) {

		if (listadoDeOrdenes == null) {
			listadoDeOrdenes = new ArrayList<Ordenes>();
		}

		listadoDeOrdenes.add(a);

	}
	
	public void mostrarArrayPantalla() {

		for (int i = 0; i < listadoDeOrdenes.size(); i++) {

			System.out.println(listadoDeOrdenes.get(i));
		}

	}


	public int obtenerTamanioArrayList() {
		return listadoDeOrdenes.size();
	}


	public Ordenes obtenerAlmacen(int pos) {
		return listadoDeOrdenes.get(pos);

	}
	
	public boolean buscarCodigo(String codigoVerificar, boolean encontrado) throws FileNotFoundException, IOException {
			
			File archivoBinarioLectura = new File(nombreFichero);
			encontrado = false;
			
			if (!archivoBinarioLectura.exists()){
				guardarArrayFichero();
			}
				
				FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
				DataInputStream dis = new DataInputStream(inicializadorLeer);
	
				boolean salir = false;
	
				do {
					
					try {
						
						int numOrden = dis.readInt();				
						String ruc = dis.readUTF();
						int codigoProducto = dis.readInt();
						float cantidad = dis.readFloat();
						float precio = dis.readFloat();
						float total = dis.readFloat();

						if(codigoVerificar.equals(ruc)) {
							encontrado = true;		
							almacenarTablaOrdenes(codigoVerificar);
							return encontrado;
						}
						
					//Manda cuando es el final del fichero, 
					} catch (EOFException e) {
						salir = true;
					}
					
				} while (!salir);
				dis.close(); 
				inicializadorLeer.close();
				
				return encontrado;
			
						
	}
	
	public void almacenarTablaOrdenes(String codigoVerificar) throws FileNotFoundException, IOException {
		
		File archivoBinarioLectura = new File(nombreFichero);
		
		if (!archivoBinarioLectura.exists()){
			guardarArrayFichero();
		}
			
			FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
			DataInputStream dis = new DataInputStream(inicializadorLeer);

			boolean salir = false;

			do {
				
				try {
					
					int numOrden = dis.readInt();				
					String ruc = dis.readUTF();
					int codigoProducto = dis.readInt();
					float cantidad = dis.readFloat();
					float precio = dis.readFloat();
					float total = dis.readFloat();
					
					if(codigoVerificar.equals(ruc)) {
						Ordenes a = new Ordenes(numOrden, ruc, codigoProducto, cantidad,precio,total );
						aniadirOrdenArrayTable(a);

					}
					
				//Manda cuando es el final del fichero, 
				} catch (EOFException e) {
					salir = true;
				}
				
			} while (!salir);
			dis.close(); 
			inicializadorLeer.close();
					
}
	
	public void aniadirOrdenArrayTable(Ordenes a) {

		if (listadoDeOrdenesPersonal == null) {
			listadoDeOrdenesPersonal = new ArrayList<Ordenes>();
		}

		listadoDeOrdenesPersonal.add(a);

	}
	
	public String[][] mostrarArrayTable() {

		String matriz[][] = new String[listadoDeOrdenes.size()][6];

		for (int i = 0; i < listadoDeOrdenes.size(); i++) {
			
			matriz[i][0] = String.valueOf(listadoDeOrdenes.get(i).getNumOrden());
			matriz[i][1] = listadoDeOrdenes.get(i).getRUC();
			matriz[i][2] = String.valueOf(listadoDeOrdenes.get(i).getCodigoProducto());
			matriz[i][3] = String.valueOf(listadoDeOrdenes.get(i).getCantidad());
			matriz[i][4] = String.valueOf(listadoDeOrdenes.get(i).getPrecio());
			matriz[i][5] = String.valueOf(listadoDeOrdenes.get(i).getTotal());
		}

		return matriz;

	}
	
	public String[][] mostrarArrayTablePersonal() {

		String matriz[][] = new String[listadoDeOrdenesPersonal.size()][4];

		for (int i = 0; i < listadoDeOrdenesPersonal.size(); i++) {

			matriz[i][0] = String.valueOf(listadoDeOrdenesPersonal.get(i).getCodigoProducto());
			matriz[i][1] = String.valueOf(listadoDeOrdenesPersonal.get(i).getCantidad());
			matriz[i][2] = String.valueOf(listadoDeOrdenesPersonal.get(i).getPrecio());
			matriz[i][3] = String.valueOf(listadoDeOrdenesPersonal.get(i).getTotal());
		}

		return matriz;

	}

}
