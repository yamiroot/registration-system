/*
 * ESTE ES EL CODIGO QUE ME LEE BIEN EL FICHERO BINARIO**/


package codigo.tabla.almacen;

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

public class AlmacenFicheroBinario {
	
	//CREAMOS UN ARRAY LIST DE LA CLASE ALMACEN
	private ArrayList<Almacen> listadoDeAlmacenes;
	
	private String nombreFichero = "Almacen.bin";
	private File guardarArchivoBinario = new File(nombreFichero);
	
	private FileOutputStream inicializadorEscribir;
	private DataOutputStream dos;
	
	private String devolverDescripcionAlmacen;
	private String devolverCodigoProducto;
	private String devolverCantidadEnAlmacen;
	

	
	public AlmacenFicheroBinario() {
		listadoDeAlmacenes = new ArrayList<Almacen>();
	}
	
	//Metodo para guardar el array list en el fichero
	public void guardarArrayFichero() throws IOException {
		 
		
		
		//SINO EXISTE
		if (!guardarArchivoBinario.exists()) {
			
			//SE CREA UN NUEVO FICHERO
			guardarArchivoBinario.createNewFile();
			
			try {
			
			FileOutputStream inicializadorEscribirPrimero = new FileOutputStream(guardarArchivoBinario);
			DataOutputStream dosPrimerArchivo = new DataOutputStream(inicializadorEscribirPrimero);
			
			for (Almacen a : listadoDeAlmacenes) {
				//codigoAlmacen(INT), descripcionAlmacen(STRING), cantidadAlmacen(FLOAT)
				
				if (dosPrimerArchivo != null) {
					dosPrimerArchivo.writeInt(a.getCodigoAlmacen());
					dosPrimerArchivo.writeUTF(a.getDescripcionAlmacen());
					dosPrimerArchivo.writeInt(a.getCodigoProducto());
					dosPrimerArchivo.writeFloat(a.getCantidadProducto());
				}
			}
			
			System.out.println("Creo el archivo exito.");

			dosPrimerArchivo.close();
			inicializadorEscribirPrimero.close();
			
			} catch (Exception ex) {
				System.out.println("No se pudo crear el archivo.");
			}
			
		}else {
			
			try {
				
				inicializadorEscribir = new FileOutputStream(guardarArchivoBinario,true);
				dos = new DataOutputStream(inicializadorEscribir);
				
					for (Almacen a : listadoDeAlmacenes) {
						//codigoAlmacen(INT), descripcionAlmacen(STRING), cantidadAlmacen(FLOAT)
						
						if (dos != null) {
							dos.writeInt(a.getCodigoAlmacen());
							dos.writeUTF(a.getDescripcionAlmacen());
							dos.writeInt(a.getCodigoProducto());
							dos.writeFloat(a.getCantidadProducto());
						}
					}
				
				System.out.println("Los datos se insertaron correctamente en el fichero Almacen.bin");

				dos.close();
				inicializadorEscribir.close();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n" +
                        "No se puedo insertar los datos previos, en el archivo binario","Error!",
                        JOptionPane.ERROR_MESSAGE);
			}	
				
		}
	}
	
	public void reescribirFichero() throws IOException {
		
		//SI EXISTE, VOLVER A ACTUALIZAR TODO MI FICHERO
		if (!guardarArchivoBinario.exists() || guardarArchivoBinario.exists()) {
			
			//SE CREA UN NUEVO FICHERO
			guardarArchivoBinario.createNewFile();
		
	
			try {
				
				FileOutputStream inicializadorEscribirPrimero = new FileOutputStream(guardarArchivoBinario);
				DataOutputStream dosPrimerArchivo = new DataOutputStream(inicializadorEscribirPrimero);
				
				for (Almacen a : listadoDeAlmacenes) {
					//codigoAlmacen(INT), descripcionAlmacen(STRING), cantidadAlmacen(FLOAT)
					
					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeInt(a.getCodigoAlmacen());
						dosPrimerArchivo.writeUTF(a.getDescripcionAlmacen());
						dosPrimerArchivo.writeInt(a.getCodigoProducto());
						dosPrimerArchivo.writeFloat(a.getCantidadProducto());
					}
			}
			
			System.out.println("se reescribio el archivo con exito.");

			dosPrimerArchivo.close();
			inicializadorEscribirPrimero.close();
			
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n" +
                        "No se puedo eliminar el almacen del archivo binario","Error!",
                        JOptionPane.ERROR_MESSAGE);
			}
			
		}	
		
	}
	
	public void leerFichero() throws FileNotFoundException, IOException {
		
		File archivoBinarioLectura = new File(nombreFichero);
		
		if (!archivoBinarioLectura.exists()){
			throw new FileNotFoundException("Fichero no encontrado.");
		}
			
			FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
			DataInputStream dis = new DataInputStream(inicializadorLeer);

			boolean salir = false;

			do {
				
				try {
									
					int codigoAlmacen = dis.readInt();				
					String descripcionAlmacen = dis.readUTF();
					int codigoProducto = dis.readInt();
					float cantidadEnAlmacen = dis.readFloat();
					
					Almacen a = new Almacen(codigoAlmacen, descripcionAlmacen,codigoProducto ,cantidadEnAlmacen);
					aniadirAlmacenesArray(a);
		
				//Manda cuando es el final del fichero, 
				} catch (EOFException e) {
					salir = true;
					}
				
			} while (!salir);
			
			dis.close(); 
			inicializadorLeer.close();
		
					
	}
	
	public void aniadirAlmacenesArray(Almacen a) {
		
		if(listadoDeAlmacenes == null) {
			listadoDeAlmacenes = new ArrayList<Almacen>();
		}
			
		listadoDeAlmacenes.add(a);
		
	}
	
	public boolean buscarCodigo(int codigoVerificar, boolean encontrado) throws FileNotFoundException, IOException {
		
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
					
					int codigoAlmacen = dis.readInt();				
					String descripcionAlmacen = dis.readUTF();
					int codigoProducto = dis.readInt();
					float cantidadEnAlmacen = dis.readFloat();
					
					if(codigoVerificar == codigoAlmacen) {
						
						encontrado = true;
						
						devolverDescripcionAlmacen = descripcionAlmacen;
						devolverCodigoProducto = String.valueOf(codigoProducto);
						devolverCantidadEnAlmacen = String.valueOf(cantidadEnAlmacen);
						
						return encontrado;
					}
					
				//Manda cuando es el final del fichero, 
				} catch (EOFException e) {
					//Final del archivo
					salir = true;
					}
				
			} while (!salir);
			
			dis.close(); 
			inicializadorLeer.close();
			
			return encontrado;
		
					
	}
	
	
	public void buscarCodigoEliminar(int codigoVerificar) throws FileNotFoundException, IOException {
		
		File archivoBinarioLectura = new File(nombreFichero);

		if (!archivoBinarioLectura.exists()){
			guardarArrayFichero();
		}
			
			FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
			DataInputStream dis = new DataInputStream(inicializadorLeer);

			boolean salir = false;
			int pos=0;
			int posicion = 0;
			do {
				
				
				try {
					int codigoAlmacen = dis.readInt();				
					String descripcionAlmacen = dis.readUTF();
					int codigoProducto = dis.readInt();
					float cantidadEnAlmacen = dis.readFloat();
	
					Almacen a = new Almacen(codigoAlmacen, descripcionAlmacen,codigoProducto ,cantidadEnAlmacen);
					aniadirAlmacenesArray(a);
					
					if(codigoVerificar == codigoAlmacen) {	
						 posicion += pos;
						 eliminarAlmacen(posicion);
					}
					
					pos++;

				//Manda cuando es el final del fichero, 
				} catch (EOFException e) {
					salir = true;
				}
				
			} while (!salir);
			
			dis.close(); 
			inicializadorLeer.close();			
	}
	
	
	public void eliminarAlmacen(int posicion) {
		
		//REMOVEMOS EL DATO CREADO EN ESA POSICION
		//System.out.println(listadoDeAlmacenes.get(posicion));
		listadoDeAlmacenes.remove(posicion);
		
	}
	
	//Nos retorna el numero de filas o tama�o del arrayList
	public int obtenerTamanioArrayList() {
		return listadoDeAlmacenes.size();
	}
	
	//Nos retorna la posici�n del objeto dentro del ArrayList de la clase Almacen
	public Almacen obtenerAlmacen(int pos) {
		return listadoDeAlmacenes.get(pos);
		
	}
	
	public String getDescripcionAlmacen() {
		return devolverDescripcionAlmacen;
	}
	
	public String getDevolverCodigoProducto() {
		return devolverCodigoProducto;
	}
	
	public String getCantidadAlmacenProducto() {
		return devolverCantidadEnAlmacen;
	}
	
	public static void main(String[] args) {

		AlmacenFicheroBinario miLista = new AlmacenFicheroBinario();
		
		try {
			
			//Guardamos el array en el archivo
			//miLista.guardarArrayFichero();
			miLista.leerFichero();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[][] mostrarArrayTable() {
		
		String matriz[][]=new String[listadoDeAlmacenes.size()][4];
		
		for (int i = 0; i < listadoDeAlmacenes.size(); i++) {
			
			matriz[i][0]=String.valueOf(listadoDeAlmacenes.get(i).getCodigoAlmacen());
			matriz[i][1]=listadoDeAlmacenes.get(i).getDescripcionAlmacen();
			matriz[i][2]=String.valueOf(listadoDeAlmacenes.get(i).getCodigoProducto());
			matriz[i][3]=String.valueOf(listadoDeAlmacenes.get(i).getCantidadProducto());
		}
		return matriz;
	}
	
	
}
