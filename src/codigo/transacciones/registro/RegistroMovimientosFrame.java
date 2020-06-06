package codigo.transacciones.registro;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.NumericShaper;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.event.*;

import codigo.tabla.almacen.Almacen;
import codigo.tabla.productos.Productos;

public class RegistroMovimientosFrame extends JFrame{
	
	private ArrayList<String> listaCodigoAlmacen;
	private ArrayList<String> listaCodigoProducto;
	
	public static void main(String[] args) {
		RegistroMovimientosFrame newAlmacen = new RegistroMovimientosFrame();
	}

	
	public RegistroMovimientosFrame() {
		
		listaCodigoAlmacen = new  ArrayList<String>();
		listaCodigoProducto = new  ArrayList<String>();
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4,alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/user.png");
		setIconImage(icono);
		
		setTitle("REGISTRO DE MOVIMENTO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(new RegistroMovimientoPanel());
		setVisible(true);
	}
	
	private class RegistroMovimientoPanel extends JPanel{
	
		//SIMULACION DE DATOS DE UN FICHERO BINARIO PARA PODER AGREGARLOS A MI JComboBox
		String[] arregloCodigoAlmacen = new String[listaCodigoAlmacen.size()];
		String[] arregloCodigoProducto= new String[listaCodigoProducto.size()];

		//ESTE ARREGLO SI NO LO TOQUES YA QUE ES PARA DEFINIR EL TIPO DE OPERACION
		String[] arregloTipoOperacion = {"Ingreso", "Salida"};
		 
		 private boolean OperacionIngresoConfirmada = false;
		 private boolean OperacionSalidaConfirmada = false;
		 private boolean comprobarStockSalida = false;
		 
		 private JComboBox comboAlmacen;
		 private JComboBox comboProducto;
		 private JComboBox comboOperacion;
		
		 private JTextField campoStockActual;
		 private JTextField campoIngreso;
		 private JTextField campoSalida;
		 private JTextField campoStockTotal;
		 
		 private JLabel textoNombreAlmacen;
		 private JLabel textoNombreProducto;
		 private JLabel textoTipoOperacion;
		 private JLabel textoStockActual;
		 private JLabel textoEntrada;
		 private JLabel textoSalida;
		 private JLabel textoStockTotal;
		 
		 private JButton btnRegistrar;
		 private JButton BtnBack;
		 
		 private JPanel contenedorBack;
		 private JPanel contenedorFormulario;
		 private JPanel contenedorRegistrar;;
		 
		 private String recolectarCodigoAlmacen;
		 private String recolectarCodigoProducto;
		 private int convertirRecolectarCA;
		 private int convertirRecolectarCP;
		 private float recibirCantidadActual;
		 
		 public RegistroMovimientoPanel(){
			 
			try {
				leerFicheroAlmacen();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			 
			//Conteo de operaciones de orden
			 
			setLayout(new BorderLayout());

			
			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();
			contenedorRegistrar = new JPanel(); 
			
			
			campoStockActual = new JTextField();
			campoIngreso = new JTextField();
			campoSalida= new JTextField();
			campoStockTotal = new JTextField();
			 
			
			//Instanciamos el JComboBox
			comboAlmacen = new JComboBox();
			comboProducto = new JComboBox();
			comboOperacion = new JComboBox();
			
			//Instanciamos el JLabel
			textoNombreAlmacen = new JLabel("Nombre de almacen: ", FlowLayout.LEFT);
			textoNombreProducto = new JLabel("Producto de almacen: ", FlowLayout.LEFT);
			textoTipoOperacion = new JLabel("Tipo de operacion", FlowLayout.LEFT);
			textoStockActual = new JLabel("Stock disponible: ", FlowLayout.LEFT);
			textoEntrada = new JLabel("Entrada de producto: ", FlowLayout.LEFT);
			textoSalida = new JLabel("Salida de producto: ", FlowLayout.LEFT);
			textoStockTotal = new JLabel("Stock actualizado: ", FlowLayout.LEFT);
			
			comboAlmacen.addItem("---------------------------------------");
			comboProducto.addItem("---------------------------------------");
			comboOperacion.addItem("---------------------------------------");
			
			
			//Creamos un bucle para poder agregar de forma automatica los nombre del Almacen en el JComboBox 
			
			for(int i=0; i<listaCodigoAlmacen.size();i++) {
				comboAlmacen.addItem(listaCodigoAlmacen.get(i));
			}
			

			//Creamos un bucle para poder agregar de forma automatica la cantidad de almacen en el JComboBox 
			for(int i=0; i<arregloTipoOperacion.length;i++) {
				
				comboOperacion.addItem((String)arregloTipoOperacion[i]);
			}
			
			ImageIcon iconoRegistro = new ImageIcon("src/img/save.png");
			Image imgEscaladaRegistro = iconoRegistro.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoRegistro = new ImageIcon(imgEscaladaRegistro);
			
			ImageIcon iconoCheck = new ImageIcon("src/img/check.png");
			Image imgEscaladaCheck = iconoCheck.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoCheck = new ImageIcon(imgEscaladaCheck);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			btnRegistrar = new JButton("Registrar movimiento", iconoEscaladoRegistro);			
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			
			//Deshabilitando algunos botones y/o JTextField
			btnRegistrar.setEnabled(false);
			comboProducto.setEnabled(false);
			comboOperacion.setEnabled(false);
			
			campoStockActual.setEditable(false);
			campoStockTotal.setEditable(false);
			campoIngreso.setEditable(false);
			campoSalida.setEditable(false);
			
			//El que nunca cambiara de estado
			campoStockTotal.setEditable(false);
			
			
			comboAlmacen.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					recolectarCodigoAlmacen = (String)comboAlmacen.getSelectedItem();
					comboProducto.setEnabled(true);
					
					try {
							convertirRecolectarCA = Integer.parseInt(recolectarCodigoAlmacen);
					
						try {
							recibirCodigoAlmacen(convertirRecolectarCA);
							//Creamos un bucle para poder agregar de forma automatica los nombre de producto en el JComboBox 		
							for(int i=0; i<listaCodigoProducto.size();i++) {
								comboProducto.addItem(listaCodigoProducto.get(i));
							}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}catch (NumberFormatException e2) {
						System.out.println(e2);
					}					

				}
			});
			
			comboProducto.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					comboAlmacen.setEnabled(false);
					
					recolectarCodigoProducto = (String) comboProducto.getSelectedItem();
					
					try {
						
						convertirRecolectarCP = Integer.parseInt(recolectarCodigoProducto);
						try {
							
							recibirCantidadActual = recibirCantidadAlmacen(convertirRecolectarCP);
							String mostrarCantidadActual= String.valueOf(recibirCantidadActual);
							System.out.println(mostrarCantidadActual);
							campoStockActual.setText(mostrarCantidadActual);
							comboOperacion.setEnabled(true);
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}catch (NumberFormatException e2) {
						System.out.println(e2);
					}
				}
			});
			
			//ESTO NO ES UNA SIMULACION  ES MI CAJA DE TIPO DE OPERACION INGRESO(1) SALIDA(2)
			//Evento del JComboBox comboOperacion
			comboOperacion.addActionListener(new ActionListener() {
				
				//Creamos una variable de tipo Obeject para que podamos reescribir en nuestro JComboBox
				String compararTipoOperacion;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					comboProducto.setEnabled(false);
					compararTipoOperacion = (String)comboOperacion.getSelectedItem();
					
						if(compararTipoOperacion.equals("Ingreso")) {
							
							OperacionIngresoConfirmada = true;
							OperacionSalidaConfirmada = false;
							System.out.println("TODO CORRECTO EN INGRESO TIPO DE ORDEN");
							campoIngreso.setEditable(true);
							comboOperacion.setEnabled(false);				
						}
						
						else if(compararTipoOperacion.equals("Salida")) {
							OperacionSalidaConfirmada = true;
							OperacionIngresoConfirmada = false;
							System.out.println("TODO CORRECTO EN SALIDA TIPO DE ORDEN");
							campoSalida.setEditable(true);
							comboOperacion.setEnabled(false);
							
						}
					
				}
			});
			
			//Calculamos de manera automatica del precio total y verificamos el tipo de dato de entrada
			CalcularMontoTotalIngreso calcularMontoEntrada = new CalcularMontoTotalIngreso();
			CalcularMontoTotalSalida calcularMontoSalida = new CalcularMontoTotalSalida();
			
			campoIngreso.getDocument().addDocumentListener(calcularMontoEntrada);
			campoSalida.getDocument().addDocumentListener(calcularMontoSalida);
			
			
			VerificarRegistro registroBtn = new  VerificarRegistro();
			btnRegistrar.addActionListener(registroBtn);
			
			 BtnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
			 
			 
			BtnBack.setBackground(new Color(79, 133, 229));
	
			contenedorFormulario.setLayout(new OrdenarRegistro());
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			contenedorRegistrar.setLayout(new FlowLayout(FlowLayout.CENTER,10,10)); 
			
			contenedorFormulario.add(textoNombreAlmacen);
			contenedorFormulario.add(comboAlmacen);
			contenedorFormulario.add(textoNombreProducto);
			contenedorFormulario.add(comboProducto);
			contenedorFormulario.add(textoTipoOperacion);
			contenedorFormulario.add(comboOperacion);
			contenedorFormulario.add(textoStockActual);
			contenedorFormulario.add(campoStockActual);
			contenedorFormulario.add(textoEntrada);
			contenedorFormulario.add(campoIngreso);
			contenedorFormulario.add(textoSalida);
			contenedorFormulario.add(campoSalida);
			contenedorFormulario.add(textoStockTotal);
			contenedorFormulario.add(campoStockTotal);
			
			contenedorBack.add(BtnBack);
			contenedorRegistrar.add(btnRegistrar);
			
			
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormulario, BorderLayout.CENTER);
			add(contenedorRegistrar, BorderLayout.SOUTH);
			
		 }
		 
		 public void leerFicheroAlmacen() throws FileNotFoundException, IOException {

				File archivoBinarioLectura = new File("Almacen.bin");

				if (!archivoBinarioLectura.exists()) {
					archivoBinarioLectura.createNewFile();
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
						float cantidad = dis.readFloat();
						
						Almacen a = new Almacen(codigoAlmacen,descripcionAlmacen,codigoProducto,cantidad);
						
						String convertirCodigoAlmacen = String.valueOf(codigoAlmacen);
						
						if (listaCodigoAlmacen == null) {
							listaCodigoAlmacen = new ArrayList<String>();
						}
						
						listaCodigoAlmacen.add(convertirCodigoAlmacen);

						// Manda cuando es el final del fichero,
					} catch (EOFException e) {
						salir = true;
					}

				} while (!salir);
				
				arregloCodigoAlmacen=listaCodigoAlmacen.toArray(new String[listaCodigoAlmacen.size()]);
				dis.close();
				inicializadorLeer.close();
				
			}
		 
		 public void recibirCodigoAlmacen(int codigoAlmacenRecibido) throws FileNotFoundException, IOException {

				File archivoBinarioLectura = new File("Almacen.bin");

				if (!archivoBinarioLectura.exists()) {
					archivoBinarioLectura.createNewFile();
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
								float cantidad = dis.readFloat();
								
								if(codigoAlmacenRecibido == codigoAlmacen) {
									Almacen a = new Almacen(codigoAlmacen,descripcionAlmacen,codigoProducto,cantidad);
									String convertirCodigoProducto = String.valueOf(codigoProducto);
									
									if (listaCodigoProducto == null) {
										listaCodigoProducto = new ArrayList<String>();
									}
									
									listaCodigoProducto.add(convertirCodigoProducto);
								}				
						}// Manda cuando es el final del fichero,
						catch (EOFException e) {
							salir = true;
						}

					} while (!salir);
					
					arregloCodigoProducto=listaCodigoProducto.toArray(new String[listaCodigoProducto.size()]);
					dis.close();
					inicializadorLeer.close();		
			}
		 
		 public float recibirCantidadAlmacen(int codigoProductoRecibido) throws FileNotFoundException, IOException {

				File archivoBinarioLectura = new File("Almacen.bin");

				if (!archivoBinarioLectura.exists()) {
					archivoBinarioLectura.createNewFile();
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
								float cantidad = dis.readFloat();
								
								if(codigoProductoRecibido == codigoProducto) {
										return cantidad;
								}
									
						}
						catch (EOFException e) {
							salir = true;
						}

					} while (!salir);
					dis.close();
					inicializadorLeer.close();
					
					return 0;		
			}
		 
		 private class CalcularMontoTotalIngreso implements DocumentListener{	
			
				@Override
				public void insertUpdate(DocumentEvent e) {	
									
					float convertirNumeroIngreso;
					float convertirCantidadActual;
					if(campoIngreso.getText().trim().equals("")) {
						
		                campoIngreso.setText("");
		                campoStockTotal.setText("");
						btnRegistrar.setText("Registro no disponible");
						btnRegistrar.setEnabled(false);
						btnRegistrar.setBackground(Color.RED);
						
					}else {
						
						String capturarIngreso= campoIngreso.getText().trim();
						String capturarCantidadActual = campoStockActual.getText().trim();
						
						try {
							
							convertirNumeroIngreso= Float.parseFloat(capturarIngreso);
							convertirCantidadActual = Float.parseFloat(capturarCantidadActual);
							
							//Calculamos el monto total
							float montoTotal = convertirCantidadActual + convertirNumeroIngreso;
							
							//Mostramos el Stock que nos quedara sumando el ingreso
							String mostrarMontoTotal = String.valueOf(montoTotal);
							campoStockTotal.setText(mostrarMontoTotal);
							
							if(montoTotal > 0) {
								
								btnRegistrar.setText("Registro disponible");
								btnRegistrar.setEnabled(true);
								btnRegistrar.setBackground(Color.GREEN);
									 if(convertirNumeroIngreso == 0) {
										 
										btnRegistrar.setText("Registro no disponible");
										btnRegistrar.setEnabled(false);
										btnRegistrar.setBackground(Color.RED);
									}
								
								
							}else{
								btnRegistrar.setText("Registro no disponible");
								btnRegistrar.setEnabled(false);
								btnRegistrar.setBackground(Color.RED);
							}
							
						} catch (NumberFormatException | IllegalStateException e2) {
							
			                   JOptionPane.showMessageDialog(null, "Ingrese correctamente los datos:\n"
			    	                   + e2.getMessage() + "  No es un dato de tipo numerico\n" +"\nSolo se admite valores numericos", "¡Error!",
			    	                   JOptionPane.ERROR_MESSAGE);
			    	           campoIngreso.setText("");
						}
					}
					
					
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					
					float convertirNumeroIngreso;
					float convertirCantidadActual;
					
					if(campoIngreso.getText().trim().equals("")) {
						
		                campoIngreso.setText("");
		                campoStockTotal.setText("");
						btnRegistrar.setText("Registro no disponible");
						btnRegistrar.setEnabled(false);
						btnRegistrar.setBackground(Color.RED);
					}else {
						
						String capturarIngreso= campoIngreso.getText().trim();
						String capturarCantidadActual = campoStockActual.getText().trim();
						
						try {
							convertirNumeroIngreso= Float.parseFloat(capturarIngreso);
							convertirCantidadActual = Float.parseFloat(capturarCantidadActual);
							
							//Calculamos el monto total
							float montoTotal = convertirCantidadActual + convertirNumeroIngreso;
							
							//Mostramos el Stock que nos quedara sumando el ingreso
							String mostrarMontoTotal = String.valueOf(montoTotal);
							campoStockTotal.setText(mostrarMontoTotal);
							
							if(montoTotal > 0) {
								
								btnRegistrar.setText("Registro disponible");
								btnRegistrar.setEnabled(true);
								btnRegistrar.setBackground(Color.GREEN);
							
									 if(convertirNumeroIngreso == 0) {
										 
										btnRegistrar.setText("Registro no disponible");
										btnRegistrar.setEnabled(false);
										btnRegistrar.setBackground(Color.RED);
									}
								
								
							}else{
								btnRegistrar.setText("Registro no disponible");
								btnRegistrar.setEnabled(false);
								btnRegistrar.setBackground(Color.RED);
							}
							
						} catch (NumberFormatException | IllegalStateException e2) {
							
			                   JOptionPane.showMessageDialog(null, "Ingrese correctamente los datos:\n"
			    	                   + e2.getMessage() + "  No es un dato de tipo numerico\n" +"\nSolo se admite valores numericos", "¡Error!",
			    	                   JOptionPane.ERROR_MESSAGE);
			    	           campoIngreso.setText("");
		                   
						}
					}
				}

				@Override
				public void changedUpdate(DocumentEvent e) {}	
				
			} 
		
		 
		 private class CalcularMontoTotalSalida implements DocumentListener{
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				
				float convertirNumeroSalida;
				float recolectarCantidadActual;
				
				comprobarStockSalida = false;
				
				if(campoSalida.getText().trim().equals("")) {
	                   campoSalida.setText("");
	                   campoStockTotal.setText("");
	                   
	                   comprobarStockSalida = false;
	                   btnRegistrar.setText("Registro no disponible");
	                   btnRegistrar.setEnabled(false);
	                   btnRegistrar.setBackground(Color.RED);
						
				}else {
					
					String capturarSalida = campoSalida.getText().trim();
					String capturarCantidadActual = campoStockActual.getText().trim();
					
					try {
						//Almacenamos en una variable de tipo Float e int
						convertirNumeroSalida = Float.parseFloat(capturarSalida);
						recolectarCantidadActual = Float.parseFloat(capturarCantidadActual);
						
						//Calculamos el monto total
						float montoTotal = recolectarCantidadActual - convertirNumeroSalida;
						
						//Mostramos el Stock que nos quedara sumando el ingreso
						String mostrarMontoTotal = String.valueOf(montoTotal);
						campoStockTotal.setText(mostrarMontoTotal);
						
						if(montoTotal > 0) {
							
							comprobarStockSalida = true;
							btnRegistrar.setText("Registro disponible");
							btnRegistrar.setBackground(Color.GREEN);
							
							if(comprobarStockSalida) {
								
								btnRegistrar.setEnabled(true);
								
								 if(convertirNumeroSalida == 0) {
									comprobarStockSalida = false;
									btnRegistrar.setText("Registro no disponible");
									btnRegistrar.setEnabled(false);
									btnRegistrar.setBackground(Color.RED);
									
								}
							}
							
						}else{
							btnRegistrar.setText("Registro no disponible");
							btnRegistrar.setEnabled(false);
							btnRegistrar.setBackground(Color.RED);
						}
						
						
					} catch (NumberFormatException | IllegalStateException e2) {
						
		                   JOptionPane.showMessageDialog(null, "Ingrese correctamente los datos:\n"
		    	                   + e2.getMessage() + "  No es un dato de tipo numerico\n" +"\nSolo se admite valores numericos", "¡Error!",
		    	                   JOptionPane.ERROR_MESSAGE);
		    	           campoSalida.setText("");
	                   
					}
				}
				
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				float convertirNumeroSalida;
				float recolectarCantidadActual;
				
				comprobarStockSalida = false;
				
				if(campoSalida.getText().trim().equals("")) {
	                   campoSalida.setText("");
	                   campoStockTotal.setText("");
	                   
	                   comprobarStockSalida = false;
	                   btnRegistrar.setText("Registro no disponible");
	                   btnRegistrar.setEnabled(false);
	                   btnRegistrar.setBackground(Color.RED);
						
				}else {
					
					String capturarSalida = campoSalida.getText().trim();
					String capturarCantidadActual = campoStockActual.getText().trim();
					
					try {
						
						convertirNumeroSalida = Float.parseFloat(capturarSalida);
						recolectarCantidadActual = Float.parseFloat(capturarCantidadActual);
						
						//Calculamos el monto total
						float montoTotal = recolectarCantidadActual - convertirNumeroSalida;
						
						//Mostramos el Stock que nos quedara sumando el ingreso
						String mostrarMontoTotal = String.valueOf(montoTotal);
						
						campoStockTotal.setText(mostrarMontoTotal);
						
						if(montoTotal > 0) {
							
							comprobarStockSalida = true;
							btnRegistrar.setText("Registro disponible");
							btnRegistrar.setBackground(Color.GREEN);
							
							if(comprobarStockSalida) {
								btnRegistrar.setEnabled(true);
								
								 if(convertirNumeroSalida == 0) {
									comprobarStockSalida = false;
									btnRegistrar.setText("Registro no disponible");
									btnRegistrar.setEnabled(false);
									btnRegistrar.setBackground(Color.RED);
									
								}
							}
							
						}else{
							btnRegistrar.setText("Registro no disponible");
							btnRegistrar.setEnabled(false);
							btnRegistrar.setBackground(Color.RED);
						}
						
						
					} catch (NumberFormatException | IllegalStateException e2) {
						
		                   JOptionPane.showMessageDialog(null, "Ingrese correctamente los datos:\n"
		    	                   + e2.getMessage() + "  No es un dato de tipo numerico\n" +"\nSolo se admite valores numericos", "¡Error!",
		    	                   JOptionPane.ERROR_MESSAGE);
		    	           
		    	           campoSalida.setText("");
	                   
					}
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {}	
			
		} 
		
		
		private class VerificarRegistro implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int tipoOperacion;
				String cantidadIngresada;
				String cantidadSalida;
				
				float convertirCI;
				float convertirCS;
				
				if(OperacionIngresoConfirmada) {
					tipoOperacion = 1;
					cantidadIngresada = campoIngreso.getText().trim();
					
					try {
						
						convertirCI = Float.parseFloat(cantidadIngresada);
						Movimientos movimientoRegistro = new Movimientos(convertirRecolectarCA, 
								convertirRecolectarCP, tipoOperacion,convertirCI);
						
						MovimientosFicheroBinario ficheroMovimiento = new MovimientosFicheroBinario();
						try {
							ficheroMovimiento.aniadirAlmacenesArray(movimientoRegistro);
							ficheroMovimiento.guardarArrayFichero();
							ficheroMovimiento.leerFichero();
							ficheroMovimiento.mostrarArrayPantalla();
						}catch (IOException e1) {
							
							e1.printStackTrace();
						}
						
					}catch (NumberFormatException e2) {
						System.out.println(e2);
					}
					System.out.println("OperacionIngresoConfirmada");
					System.out.println(tipoOperacion);
					
				}
				
				else if(OperacionSalidaConfirmada) {
					tipoOperacion = 2;
					cantidadSalida= campoSalida.getText().trim();
					try {
						convertirCS = Float.parseFloat(cantidadSalida);
						Movimientos movimientoRegistro = new Movimientos(convertirRecolectarCA, 
								convertirRecolectarCP, tipoOperacion,convertirCS);
						
						MovimientosFicheroBinario ficheroMovimiento = new MovimientosFicheroBinario();
						try {
						ficheroMovimiento.aniadirAlmacenesArray(movimientoRegistro);
						ficheroMovimiento.guardarArrayFichero();
						ficheroMovimiento.leerFichero();
						ficheroMovimiento.mostrarArrayPantalla();
						}catch (IOException e1) {
							
							e1.printStackTrace();
						}
					}catch (NumberFormatException e2) {
						System.out.println(e2);
					}
					System.out.println("OperacionIngresoConfirmada");
					System.out.println(tipoOperacion);;

				}
				
				
				comboAlmacen.setEnabled(false);
				comboProducto.setEnabled(false);
				comboOperacion.setEditable(false);
				campoIngreso.setEditable(false);
				campoSalida.setEditable(false);
				
				btnRegistrar.setEnabled(false);
				btnRegistrar.setText("Movimiento registrado!");
				btnRegistrar.setBackground(null);
				BtnBack.setBackground(Color.YELLOW);
				JOptionPane.showMessageDialog(null,
	                       "Se registro el pedido de manera correcta","Registrado!",
	                        JOptionPane.INFORMATION_MESSAGE);
                					
			}
			
		}
		
		
		
		
		private class OrdenarRegistro implements LayoutManager{
				
			private int x;
			private int y;
				
				@Override
				public void addLayoutComponent(String name, Component comp) {
					
					
				}
			
				@Override
				public void removeLayoutComponent(Component comp) {
					
					
				}
			
				@Override
				public Dimension preferredLayoutSize(Container parent) {
					
					return null;
				}
			
				@Override
				public Dimension minimumLayoutSize(Container parent) {
					
					return null;
				}
			
				@Override
				public void layoutContainer(Container parent) {
					int contador=0;
					int anchoContenedor = parent.getWidth();
					int alturaContenedor = parent.getHeight();
					
					x= anchoContenedor/2;
					y = alturaContenedor/8;
					
					int n= parent.getComponentCount();
					
					for(int i=0;i<n;i++) {
						
						contador++;
						
						Component c = parent.getComponent(i);
						c.setBounds(x-200,y,200,30);
						
						x+= 200;
						
						if(contador%2==0) {
							x = anchoContenedor/2;
							y += alturaContenedor/8;
						}
					}
					
				}
				
			}
				
	}
}
