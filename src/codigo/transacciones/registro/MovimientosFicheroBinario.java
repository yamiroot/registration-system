
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


public class MovimientosFicheroBinario {
	
	private ArrayList<Movimientos> listadoDeTransacciones;
	private ArrayList<MovimientoTable> reporteDeMovimientoIngreso;
	private ArrayList<MovimientoTable> reporteDeMovimientoSalida;
	
	private String nombreFichero = "Movimientos.bin";
	private File guardarArchivoBinario = new File(nombreFichero);
	
	private FileOutputStream inicializadorEscribir;
	private DataOutputStream dos;
	
	public MovimientosFicheroBinario() {
		listadoDeTransacciones = new ArrayList<Movimientos>();
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
			
			for (Movimientos a : listadoDeTransacciones) {
				
				if (dosPrimerArchivo != null) {
					dosPrimerArchivo.writeInt(a.getCodigoAlmacen());
					dosPrimerArchivo.writeInt(a.getCodigoProducto());
					dosPrimerArchivo.writeInt(a.getTipoOperacion());
					dosPrimerArchivo.writeFloat(a.getCantidad());
				}
			}
			
			System.out.println("Creo el archivo con exito.");

			dosPrimerArchivo.close();
			inicializadorEscribirPrimero.close();
			
			} catch (Exception ex) {
				System.out.println("No se pudo crear el archivo.");
			}
			
		}else {
			
			try {
				
				FileOutputStream inicializadorEscribirPrimero = new FileOutputStream(guardarArchivoBinario, true);
				DataOutputStream dosPrimerArchivo = new DataOutputStream(inicializadorEscribirPrimero);
				
				for (Movimientos a : listadoDeTransacciones) {
					
					if (dosPrimerArchivo != null) {
						dosPrimerArchivo.writeInt(a.getCodigoAlmacen());
						dosPrimerArchivo.writeInt(a.getCodigoProducto());
						dosPrimerArchivo.writeInt(a.getTipoOperacion());
						dosPrimerArchivo.writeFloat(a.getCantidad());
					}
				}
				
				
				System.out.println("SE REESCRIBIO EL ARCHIVO.");
				
				dosPrimerArchivo.close();
				inicializadorEscribirPrimero.close();


			} catch (Exception ex) {
				System.out.println("No se pudo crear el archivo.");
			}	
				
		}
	}
	
	public void reescribirFichero() throws IOException {
		
		//SI EXISTE, VOLVER A ACTUALIZAR TODO MI FICHERO
		if (guardarArchivoBinario.exists()) {
			
			//SE CREA UN NUEVO FICHERO
			guardarArchivoBinario.createNewFile();
			
			try {
			
			FileOutputStream inicializadorEscribirPrimero = new FileOutputStream(guardarArchivoBinario);
			DataOutputStream dosPrimerArchivo = new DataOutputStream(inicializadorEscribirPrimero);
			
			for (Movimientos a : listadoDeTransacciones) {
				
				if (dosPrimerArchivo != null) {
					dosPrimerArchivo.writeInt(a.getCodigoAlmacen());
					dosPrimerArchivo.writeInt(a.getCodigoProducto());
					dosPrimerArchivo.writeInt(a.getTipoOperacion());
					dosPrimerArchivo.writeFloat(a.getCantidad());
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
		
		if (!archivoBinarioLectura.exists()){
			throw new FileNotFoundException("Fichero no encontrado.");
		}
			
			FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
			DataInputStream dis = new DataInputStream(inicializadorLeer);

			boolean salir = false;

			do {
				
				try {
					
					int codigoAlmacen=dis.readInt();
					int codigoProducto=dis.readInt();
					int tipoOperacion=dis.readInt();
					float cantidad=dis.readFloat();
					
					Movimientos a = new Movimientos(codigoAlmacen, codigoProducto, tipoOperacion, cantidad);
					
					if(tipoOperacion == 1) {
						String nombreOperacion = "Ingreso";
						MovimientoTable arregloIngreso = new MovimientoTable(codigoAlmacen, 
								codigoProducto, nombreOperacion, cantidad);
						aniadirMovimientoTableIngreso(arregloIngreso);
					}
					
					else if(tipoOperacion ==2) {
						String nombreOperacion = "Salida";
						MovimientoTable arregloSalida = new MovimientoTable(codigoAlmacen, 
								codigoProducto, nombreOperacion, cantidad);
						aniadirMovimientoTableSalida(arregloSalida);
					}
					
					aniadirAlmacenesArray(a);					
					
				//Manda cuando es el final del fichero, 
				} catch (EOFException e) {
					salir = true;
					}
				
			} while (!salir);
			dis.close(); 
			inicializadorLeer.close();
		
					
	}
	
	public void aniadirMovimientoTableIngreso(MovimientoTable a) {
		
		if(reporteDeMovimientoIngreso == null) {
			reporteDeMovimientoIngreso = new ArrayList<MovimientoTable>();
		}
			
		reporteDeMovimientoIngreso.add(a);
		
	}
	
	public void aniadirMovimientoTableSalida(MovimientoTable a) {
		
		if(reporteDeMovimientoSalida == null) {
			reporteDeMovimientoSalida = new ArrayList<MovimientoTable>();
		}
			
		reporteDeMovimientoSalida.add(a);
		
	}
	
	public void aniadirAlmacenesArray(Movimientos a) {
		
		if(listadoDeTransacciones == null) {
			listadoDeTransacciones = new ArrayList<Movimientos>();
		}
			
		listadoDeTransacciones.add(a);
		
	}
	
	public void mostrarArrayPantalla() {

		for(int i=0;i<listadoDeTransacciones.size();i++) {
			
			System.out.println(listadoDeTransacciones.get(i));
		}
	
	}
	
	public boolean buscarCodigo(int codigoVerificar, boolean desaprobado) throws FileNotFoundException, IOException {
		
		File archivoBinarioLectura = new File(nombreFichero);
		desaprobado = false;
		
		if (!archivoBinarioLectura.exists()){
			guardarArrayFichero();
		}
			
			FileInputStream inicializadorLeer = new FileInputStream(archivoBinarioLectura);
			DataInputStream dis = new DataInputStream(inicializadorLeer);

			boolean salir = false;

			do {
				
				try {
					
					int codigoAlmacen=dis.readInt();
					int codigoProducto=dis.readInt();
					int tipoOperacion=dis.readInt();
					float cantidad=dis.readFloat();
					/*
					if(codigoVerificar == codigoProducto) {
						System.out.println("Codigo verificado");
						desaprobado = true;
						return desaprobado;
					}*/
					
				//Manda cuando es el final del fichero, 
				} catch (EOFException e) {
					salir = true;
					}
				
			} while (!salir);
			dis.close(); 
			inicializadorLeer.close();
			
			return desaprobado;
	
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
					
					int codigoAlmacen=dis.readInt();
					int codigoProducto=dis.readInt();
					int tipoOperacion=dis.readInt();
					float cantidad=dis.readFloat();
					
					Movimientos a = new Movimientos(codigoAlmacen, codigoProducto, tipoOperacion, cantidad);
					aniadirAlmacenesArray(a);

				//Manda cuando es el final del fichero, 
				} catch (EOFException e) {
					salir = true;
				}
				
			} while (!salir);
			
			eliminarAlmacen(posicion);
			dis.close(); 
			inicializadorLeer.close();		
			
	}
	
	
	public void eliminarAlmacen(int posicion) {
		
		//REMOVEMOS EL DATO CREADO EN ESA POSICION
		listadoDeTransacciones.remove(posicion);
		
	}
	
	//Nos retorna el numero de filas o tamaño del arrayList
	public int obtenerTamanioArrayList() {
		return listadoDeTransacciones.size();
	}
	
	//Nos retorna la posicion del objeto dentro del ArrayList de la clase Almacen
	public Movimientos obtenerAlmacen(int pos) {
		return listadoDeTransacciones.get(pos);
	}
	
	public String[][] mostrarArrayTableIngreso() {
		
		String matriz[][]=new String[reporteDeMovimientoIngreso.size()][4];
		
		for (int i = 0; i < reporteDeMovimientoIngreso.size(); i++) {			
			matriz[i][0]=String.valueOf(reporteDeMovimientoIngreso.get(i).getCodigoAlmacen());
			matriz[i][1]=String.valueOf(reporteDeMovimientoIngreso.get(i).getCodigoProducto());
			matriz[i][2]=String.valueOf(reporteDeMovimientoIngreso.get(i).getNombreOperacion());
			matriz[i][3]=String.valueOf(reporteDeMovimientoIngreso.get(i).getCantidad());
		}
		return matriz;
	}
	
	public String[][] mostrarArrayTableSalida() {
		
		String matriz[][]=new String[reporteDeMovimientoSalida.size()][4];

		for (int i = 0; i < reporteDeMovimientoSalida.size(); i++) {			
			matriz[i][0]=String.valueOf(reporteDeMovimientoSalida.get(i).getCodigoAlmacen());
			matriz[i][1]=String.valueOf(reporteDeMovimientoSalida.get(i).getCodigoProducto());
			matriz[i][2]=String.valueOf(reporteDeMovimientoSalida.get(i).getNombreOperacion());
			matriz[i][3]=String.valueOf(reporteDeMovimientoSalida.get(i).getCantidad());
		}
		return matriz;
	}
	
}
