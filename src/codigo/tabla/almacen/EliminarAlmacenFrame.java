package codigo.tabla.almacen;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import codigo.tabla.productos.ProductoFicheroBinario;


public class EliminarAlmacenFrame extends JFrame {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EliminarAlmacenFrame deleteAlmacen = new EliminarAlmacenFrame();
	}

	
	public EliminarAlmacenFrame() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4, alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/delete.png");
		setIconImage(icono);
		
		setTitle("Eliminar almacen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(new EliminarAlmacenPanel());
		setVisible(true);
	}
	
	private class EliminarAlmacenPanel extends JPanel {
	     
		 private JTextField campoCodigoAlmacen;
		 private JTextField campoDescripcionAlmacen;
		 private JTextField campoCodigoProducto;
		 private JTextField campoCantidad;
		 
		 private JLabel textoCodigoAlmacen;
		 private JLabel textoDescripcionAlmacen;
		 private JLabel textoCodigoProducto;
		 private JLabel textoCantidad;
		 
		 private JButton btnBuscarAlmacen;
		 private JButton btnEliminar;
		 private JButton BtnBack;
		 
		 private JPanel contenedorBack;
		 private JPanel contenedorFormulario;
		 
		 public EliminarAlmacenPanel(){
			 
			setLayout(new BorderLayout());
			
			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();
			
			campoCodigoAlmacen = new JTextField();
			campoDescripcionAlmacen = new JTextField();
			campoCodigoProducto = new JTextField();
			campoCantidad = new JTextField();
			
			 textoCodigoAlmacen = new JLabel("Codigo almacen:");
			 textoDescripcionAlmacen = new JLabel("Descripcion almacen:");
			 textoCodigoProducto = new JLabel("Codigo producto:");
			 textoCantidad = new JLabel("Cantidad de producto:");
			 
			 
			ImageIcon iconoBuscar = new ImageIcon("src/img/search.png");
			Image imgEscaladaBuscar= iconoBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBuscar= new ImageIcon(imgEscaladaBuscar);
			
			ImageIcon iconoEliminar = new ImageIcon("src/img/delete.png");
			Image imgEscaladaEliminar = iconoEliminar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoEliminar = new ImageIcon(imgEscaladaEliminar);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			btnBuscarAlmacen = new JButton("Buscar almacen", iconoEscaladoBuscar);
			
			btnEliminar = new JButton("Eiminar almacen", iconoEscaladoEliminar);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
		
			ComprobarLongitudCodigoAlmacen receptorAlmacen = new ComprobarLongitudCodigoAlmacen();
			campoCodigoAlmacen.getDocument().addDocumentListener(receptorAlmacen);
			
			BuscarCodigoAlmacenProducto oyenteAlmacen = new BuscarCodigoAlmacenProducto();
			btnBuscarAlmacen.addActionListener(oyenteAlmacen);
			 
			btnEliminar.addActionListener(new ActionListener() {
				 
				 
				@Override
				public void actionPerformed(ActionEvent e) {
					String codigoPalabraAlmacen = campoCodigoAlmacen.getText().trim();
					AlmacenFicheroBinario eliminarAlmacen = new AlmacenFicheroBinario();
					
			 		try {
			 			
			 			//Convertimos todo nuestro codigo en tipo int
			 			int codigoNumero =Integer.parseInt(codigoPalabraAlmacen);
			 			
			 			try {
			 				
			 				eliminarAlmacen.buscarCodigoEliminar(codigoNumero);
			 				eliminarAlmacen.reescribirFichero();
			 				eliminarAlmacen.leerFichero();
			 
			                JOptionPane.showMessageDialog(null, "Almacen eliminado:\n" +
			                        "","¡Eliminado!",
			                        JOptionPane.INFORMATION_MESSAGE);
			                
			                BtnBack.setBackground(Color.YELLOW);
			                
							campoCodigoAlmacen.setText("");
							campoDescripcionAlmacen.setText("");
							campoCodigoProducto.setText("");
							campoCantidad.setText("");
			 				
			                btnEliminar.setText("¡Almacen Eliminado!");
			                btnEliminar.setBackground(null);
			                btnEliminar.setEnabled(false);
			                campoCodigoAlmacen.setEnabled(false);
			                BtnBack.setBackground(Color.YELLOW);
			 				
			 			}catch(IOException e1) {
			 				e1.printStackTrace();
			 			}
			 			
			 		}catch (NumberFormatException e2) {
						System.out.println("No se pudo convertir el codigo a tipo int");
					}

	                

	                
					
				}
			  });
			 
			BtnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
				
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			contenedorFormulario.setLayout(new OrdenarRegistro());
			
			BtnBack.setBackground(new Color(79, 133, 229));
			campoDescripcionAlmacen.setEditable(false);
			campoCodigoProducto.setEditable(false);
			campoCantidad.setEditable(false);
			btnEliminar.setEnabled(false);
			btnBuscarAlmacen.setEnabled(false);
			
			contenedorBack.add(BtnBack);
			
			contenedorFormulario.add(textoCodigoAlmacen);
			contenedorFormulario.add(campoCodigoAlmacen);
			
			contenedorFormulario.add(textoDescripcionAlmacen);
			contenedorFormulario.add(campoDescripcionAlmacen);
			
			contenedorFormulario.add(textoCodigoProducto);
			contenedorFormulario.add(campoCodigoProducto);
			
			contenedorFormulario.add(textoCantidad);
			contenedorFormulario.add(campoCantidad);
			
			contenedorFormulario.add(btnBuscarAlmacen);
			contenedorFormulario.add(btnEliminar); 
			
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormulario, BorderLayout.CENTER);
		 }
		 	
		 
		 private class BuscarCodigoAlmacenProducto implements ActionListener{
		 		
		 		AlmacenFicheroBinario ficheroAlmacenBinario = new AlmacenFicheroBinario();
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String codigoPalabraAlmacen = campoCodigoAlmacen.getText().trim();
		
						//TRY PRINCIPAL
						try {
							int codigoNumeroAlmacen = Integer.parseInt(codigoPalabraAlmacen);
							boolean encontroCodigo = false;
													
							try {

								if (ficheroAlmacenBinario.buscarCodigo(codigoNumeroAlmacen, encontroCodigo)) {
		
									JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n" + "El codigo del almacen se encuentra registrado.",
												"Encontrado!", JOptionPane.INFORMATION_MESSAGE);
									
									btnBuscarAlmacen.setEnabled(false);
									campoCodigoAlmacen.setEditable(false);
									btnBuscarAlmacen.setText("Almacen encontrado!");
						
									campoCodigoProducto.setEditable(false);
									
									campoDescripcionAlmacen.setText(ficheroAlmacenBinario.getDescripcionAlmacen());
									campoCodigoProducto.setText(ficheroAlmacenBinario.getDevolverCodigoProducto());
									campoCantidad.setText(ficheroAlmacenBinario.getCantidadAlmacenProducto());

									btnEliminar.setEnabled(true);
									btnEliminar.setBackground(new Color(79, 132, 247));
									
								}else {
										JOptionPane.showMessageDialog(null,
												"Mensaje del sistema:\n" + "El codigo del almacen no se encuentra registrado."
										, "No encontrado!",JOptionPane.ERROR_MESSAGE);
										campoCodigoProducto.setText("");
									}
								
								
							//EN CASO NO PUEDA LEER EL ARCHIVO
							}catch (IOException e1) {
								e1.printStackTrace();
							}
							
						//EN CASO NO PUEDA CONVERTIR EL CAMPO DE CODIGO EN TIPO INT
						}catch(NumberFormatException e2) {
							JOptionPane.showMessageDialog(null,
									"Codigo no valido:\n" + e2.getMessage() + ". \nEl codigo debe ser unicamente de tipo numerico", "Error!",
									JOptionPane.ERROR_MESSAGE);
							System.out.println("No es un numero" + e2.getMessage());
							campoCodigoAlmacen.setText("");
						
						}
					

				}
		 	}
		 	
		 	
		 	//Comprobacion de la longitud del codigo ALMACEN
			private class ComprobarLongitudCodigoAlmacen implements DocumentListener {

				@Override
				public void insertUpdate(DocumentEvent e) {

					String codigoLongitudAlmacen = campoCodigoAlmacen.getText().trim();
					
					if (codigoLongitudAlmacen.equals("")) {
						campoCodigoAlmacen.setBackground(Color.WHITE);
						btnBuscarAlmacen.setEnabled(false);
						
					}else if(codigoLongitudAlmacen.length() == 6){

							
							campoCodigoAlmacen.setBackground(Color.GREEN);
							btnBuscarAlmacen.setEnabled(true);
						
					}else {
						campoCodigoAlmacen.setBackground(new Color(255, 0, 59));
						btnBuscarAlmacen.setEnabled(false);
					}
					
				}

				@Override
				public void removeUpdate(DocumentEvent e) {

					String codigoLongitudAlmacen = campoCodigoAlmacen.getText().trim();
					
					if (codigoLongitudAlmacen.equals("")) {
						campoCodigoAlmacen.setBackground(Color.WHITE);
						btnBuscarAlmacen.setEnabled(false);
						
					}else if(codigoLongitudAlmacen.length() == 6){

							
							campoCodigoAlmacen.setBackground(Color.GREEN);
							btnBuscarAlmacen.setEnabled(true);
						
					}else {
						campoCodigoAlmacen.setBackground(new Color(255, 0, 59));
						btnBuscarAlmacen.setEnabled(false);
					}

				}

				@Override
				public void changedUpdate(DocumentEvent e) {

				}

			} 
			
			//Layout Personalziado para el Frame
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
