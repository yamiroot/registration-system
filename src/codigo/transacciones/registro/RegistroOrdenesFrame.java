package codigo.transacciones.registro;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import codigo.tabla.productos.Productos;
import codigo.tabla.proveedores.Proveedores;

public class RegistroOrdenesFrame extends JFrame {
	
	private ArrayList<String> listaRucEncontrado;
	private ArrayList<String> listaCodigoEncontrado;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RegistroOrdenesFrame newAlmacen = new RegistroOrdenesFrame();
	}

	
	public RegistroOrdenesFrame() {
		
		listaRucEncontrado = new ArrayList<String>();
		listaCodigoEncontrado = new ArrayList<String>();

		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4,alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/user.png");
		setIconImage(icono);
		
		setTitle("REGISTRO DE ORDENES");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		RegistroOrdenPanel registro=new RegistroOrdenPanel();
		
		add(registro);
		setVisible(true);

	}
	
	private class RegistroOrdenPanel extends JPanel{


		 String[] arregloRucPrueba=new String[listaRucEncontrado.size()];
		 String[] arregloCodigoPrueba = new String[listaCodigoEncontrado.size()];

		 private JComboBox comboProovedor;
		 private JComboBox comboProducto;
	
		 private JTextField campoCantidad;
		 private JTextField campoPrecioUnitario;
		 private JTextField campoPrecioTotal;
		 
		 private JLabel textoPedidoOrden;
		 
		 private JLabel textoProovedorRuc;
		 private JLabel textoProductoCodigo;
		 
		 private JLabel textoCantidad;
		 private JLabel textoPrecioUnitario;
		 private JLabel textoPrecioTotal;
		 
		 private JButton btnRegistrar;
		 private JButton BtnBack;
		
		 private JPanel contenedorBack;
		 private JPanel contenedorFormulario;
		 private JPanel contenedorRegistrar;
		 
		 private float convertirNumeroCantidad;
		 private float convertirPrecioUnitario;
		 private float montoTotal;
		 
		 private String recolectarRucProovedor;
		 private String recolectarNombreProducto;
		 private int recolectarCodigoProducto;
		 
		 private OrdenesFicheroBinario contadorFichero;
		 private int contadorOrdenFichero;
		 
		 public RegistroOrdenPanel(){
			
			 contadorFichero = new OrdenesFicheroBinario();
			 
			 try { 	
				leerFicheroProveedor();
				leerFicheroProducto();
				
				contadorFichero.leerFichero();
				contadorOrdenFichero = contadorFichero.getContadorOrden();

				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 			 
			setLayout(new BorderLayout());
			
			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();
			contenedorRegistrar = new JPanel(); 
			
			
			campoCantidad = new JTextField();
			campoPrecioUnitario = new JTextField();
			campoPrecioTotal = new JTextField();
			 
			
			//Instanciamos el JComboBox
			comboProovedor = new JComboBox();
			comboProducto = new JComboBox();
			
			comboProducto.addItem("---------------------------------------");
			comboProovedor.addItem("---------------------------------------");
			
			
			//Creamos un bucle para poder agregar de forma automatica los RUC en el JComboBox 
			
			for(int i=0; i<listaRucEncontrado.size();i++) {
				comboProovedor.addItem(listaRucEncontrado.get(i));
			}
			
			//Creamos un bucle para poder agregar de forma automatica los codigos de proovedor en el JComboBox 
			for(int i=0; i<listaCodigoEncontrado.size();i++) {
				comboProducto.addItem(listaCodigoEncontrado.get(i));
			}
			
			textoPedidoOrden = new JLabel("Pedido de Orden N: " + contadorOrdenFichero);
			textoProovedorRuc = new JLabel("Proovedor: ");
			textoProductoCodigo = new JLabel("Producto:");
			textoCantidad = new JLabel("Cantidad a ordenar: ");
			textoPrecioUnitario = new JLabel("Precio unitario (S/.): ");
			textoPrecioTotal = new JLabel("Precio total (S/.): ");
			 
			textoPedidoOrden.setFont(new Font("Serif", Font.PLAIN,18));
			
			ImageIcon iconoRegistro = new ImageIcon("src/img/save.png");
			Image imgEscaladaRegistro = iconoRegistro.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoRegistro = new ImageIcon(imgEscaladaRegistro);
			
			ImageIcon iconoCheck = new ImageIcon("src/img/check.png");
			Image imgEscaladaCheck = iconoCheck.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoCheck = new ImageIcon(imgEscaladaCheck);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			btnRegistrar = new JButton("Registrar orden", iconoEscaladoRegistro);			
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			
			//Deshabilitando algunos botones y/o JTextField
			comboProducto.setEnabled(false);
			btnRegistrar.setEnabled(false);
			campoCantidad.setEditable(false);
			campoPrecioTotal.setEditable(false);
			campoPrecioUnitario.setEditable(false);
			
			//Evento del JComboBox comboProovedor
			comboProovedor.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					recolectarRucProovedor = (String) comboProovedor.getSelectedItem();
					comboProducto.setEnabled(true);				
				}
			});
			comboProducto.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					recolectarNombreProducto = (String) comboProducto.getSelectedItem();
					recolectarCodigoProducto = Integer.parseInt(recolectarNombreProducto);
					
					comboProovedor.setEnabled(false);
					campoCantidad.setEditable(true);
					
				}
			});
			
			
			//Calculamos de manera automatica del precio total y verificamos el tipo de dato de entrada
			VerificarDatoCantidad validarCantidad = new VerificarDatoCantidad();
			CalcularMontoTotal calcularMonto = new CalcularMontoTotal();
			
			campoCantidad.getDocument().addDocumentListener(validarCantidad);
			campoPrecioUnitario.getDocument().addDocumentListener(calcularMonto);
			
			
			
			 
			 btnRegistrar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						
						
						String recolectarCantidad = campoCantidad.getText().trim();
						String recolectarPrecioUnitario = campoPrecioUnitario.getText().trim();
						String recolectarPrecioTotal = campoPrecioTotal.getText().trim();
					
					try {

						float cantidadConvertidaNumerica = Float.parseFloat(recolectarCantidad);
						float cantidadConvertidaPU= Float.parseFloat(recolectarPrecioUnitario);
						float cantidadConvertidaPT = Float.parseFloat(recolectarPrecioTotal);
						
						float montoTotal = cantidadConvertidaPU*cantidadConvertidaPT;
												
						Ordenes ordenRegistro = new Ordenes(contadorOrdenFichero, recolectarRucProovedor, 
								recolectarCodigoProducto, cantidadConvertidaNumerica,cantidadConvertidaPU,cantidadConvertidaPT );
						
						OrdenesFicheroBinario ficheroOrdenar = new OrdenesFicheroBinario();
						
						try {
							ficheroOrdenar.aniadirOrdenesArray(ordenRegistro);
							ficheroOrdenar.guardarArrayFichero();
							ficheroOrdenar.leerFichero();
							ficheroOrdenar.mostrarArrayPantalla();
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						
						campoCantidad.setEditable(false);
						campoPrecioUnitario.setEditable(false);
						
						btnRegistrar.setEnabled(false);
						btnRegistrar.setText("Orden registrada!");
						btnRegistrar.setBackground(null);
						BtnBack.setBackground(Color.YELLOW);
						
						JOptionPane.showMessageDialog(null, "Pedido de orden agregada:\n" +
	                           "Se registro el pedido de manera correcta","Registrado!",
	                           JOptionPane.INFORMATION_MESSAGE);
						
					} catch (NumberFormatException | IllegalStateException e2) {
						
		                   JOptionPane.showMessageDialog(null, "Mensaje del sistema: \nOcurrio un evento inesperado, porfavor ingrese correctamente los datos pedidos."
		                   		+ "\nCerrando la ventana del programa.", "Error inesperado!",
		    	                   JOptionPane.ERROR_MESSAGE);
		    	           dispose();
					}
	                   					
				}
			});
			
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
			
			contenedorFormulario.add(textoProovedorRuc);
			contenedorFormulario.add(comboProovedor);
			contenedorFormulario.add(textoProductoCodigo);
			contenedorFormulario.add(comboProducto);
			contenedorFormulario.add(textoCantidad);
			contenedorFormulario.add(campoCantidad);
			contenedorFormulario.add(textoPrecioUnitario);
			contenedorFormulario.add(campoPrecioUnitario);
			contenedorFormulario.add(textoPrecioTotal);
			contenedorFormulario.add(campoPrecioTotal);
			
			contenedorBack.add(BtnBack);
			contenedorRegistrar.add(textoPedidoOrden);
			contenedorRegistrar.add(btnRegistrar);
			
			
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormulario, BorderLayout.CENTER);
			add(contenedorRegistrar, BorderLayout.SOUTH);
			
		 }
		 
		 public void leerFicheroProveedor()  throws FileNotFoundException, IOException {

				File archivoBinarioLectura = new File("Proveedores.bin");

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
						
						if (listaRucEncontrado == null) {
							listaRucEncontrado = new ArrayList<String>();
						}
						
						listaRucEncontrado.add(RUC);
						
						
						

						// Manda cuando es el final del fichero,
					} catch (EOFException e) {
						salir = true;
					}

				} while (!salir);
				
				arregloRucPrueba=listaRucEncontrado.toArray(new String[listaRucEncontrado.size()]);
				dis.close();
				inicializadorLeer.close();
				
			}
		 
		 
		 public void leerFicheroProducto() throws FileNotFoundException, IOException {

				File archivoBinarioLectura = new File("Productos.bin");

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
						
						String convertirCodigoProducto = String.valueOf(codigoProducto);
						
						if (listaCodigoEncontrado == null) {
							listaCodigoEncontrado = new ArrayList<String>();
						}
						
						listaCodigoEncontrado.add(convertirCodigoProducto);
						
						
						

						// Manda cuando es el final del fichero,
					} catch (EOFException e) {
						salir = true;
					}

				} while (!salir);
				
				arregloCodigoPrueba=listaCodigoEncontrado.toArray(new String[listaCodigoEncontrado.size()]);

				dis.close();
				inicializadorLeer.close();
				
			}
		 
		 
		 //Verificamos el tipo de dato de entrada en el campoCantidad
		 private class VerificarDatoCantidad implements DocumentListener{
				
				@Override
				public void insertUpdate(DocumentEvent e) {	
					campoPrecioUnitario.setEditable(true);
					comboProducto.setEnabled(false);
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					campoPrecioUnitario.setEditable(true);
					comboProducto.setEnabled(false);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
				}
				
		} 
		 
		 
		private class CalcularMontoTotal implements DocumentListener{
			
				@Override
				public void insertUpdate(DocumentEvent e) {	
					
					//Almacenamos los datos del campoPrecioUnitario
					String capturarPrecioUnitario = campoPrecioUnitario.getText().trim();
					String capturarCantidad = campoCantidad.getText().trim();
					campoCantidad.setEditable(false);
					
					try {
						
						//Almacenamos en una variable de tipo Float e int
						convertirPrecioUnitario = Float.parseFloat(capturarPrecioUnitario);
						convertirNumeroCantidad = Float.parseFloat(capturarCantidad);
						//convertirNumeroCantidad = Integer.parseInt(capturarCantidad);
						
						if(convertirNumeroCantidad == 0) {
			                   JOptionPane.showMessageDialog(null, "Menaje del sistem: \nDebe al menos poner una unidad numerica,"
			                   		+ " \ncomo minimo en la cantidad de producto a ordenar", "Error!",
			    	                   JOptionPane.ERROR_MESSAGE);
						}
						
						//Calculamos el monto total
						montoTotal = convertirNumeroCantidad * convertirPrecioUnitario;
						
						 if(montoTotal > 0) {
							 btnRegistrar.setEnabled(true);
							 btnRegistrar.setBackground(Color.GREEN);
						 }
						 
						//Mostramos el monto total
						String mostrarMontoTotal = String.valueOf(montoTotal);
						campoPrecioTotal.setText(mostrarMontoTotal);
						
					} catch (NumberFormatException | IllegalStateException e2) {
		    	          System.out.print(e2.getMessage());
		    	           campoPrecioUnitario.setText("");
					}
					

				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					
					campoCantidad.setEditable(false);
					//Almacenamos los datos del campoPrecioUnitario
					String capturarPrecioUnitario = campoPrecioUnitario.getText().trim();
					String capturarCantidad = campoCantidad.getText().trim();
					
					try {
						
						//Almacenamos en una variable de tipo Float e int
						convertirNumeroCantidad = Float.parseFloat(capturarCantidad);
						convertirPrecioUnitario = Float.parseFloat(capturarPrecioUnitario);

						
						if(convertirNumeroCantidad == 0) {
			                   JOptionPane.showMessageDialog(null, "Menaje del sistem: \nDebe al menos poner una unidad numerica, "
			                   		+ "\ncomo minimo en la cantidad de producto a ordenar", "Error!",
			    	                   JOptionPane.ERROR_MESSAGE);
						}
						
						//Calculamos el monto total
						 montoTotal = convertirNumeroCantidad * convertirPrecioUnitario;
						 
						 if(montoTotal > 0) {
							 btnRegistrar.setEnabled(true);
							 btnRegistrar.setBackground(Color.GREEN);
						 }
						
						//Mostramos el monto total
						String mostrarMontoTotal = String.valueOf(montoTotal);
						campoPrecioTotal.setText(mostrarMontoTotal);
						
					} catch (NumberFormatException | IllegalStateException e2) {
						
		    	           System.out.print(e2.getMessage());
		    	           campoPrecioUnitario.setText("");
	                   
					}
				}

				@Override
				public void changedUpdate(DocumentEvent e) {}	
				
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
