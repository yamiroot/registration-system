package codigo.tabla.almacen;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

public class BusquedaPersonalizadaFrame extends JFrame {
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			BusquedaPersonalizadaFrame deleteAlmacen = new BusquedaPersonalizadaFrame();
			

		}

		
		public BusquedaPersonalizadaFrame() {
			
			Toolkit pantalla = Toolkit.getDefaultToolkit();
			Dimension tamano = pantalla.getScreenSize();
			int alturaPantalla = tamano.height;
			int anchuraPantalla = tamano.width;
			
			setSize(anchuraPantalla/2, alturaPantalla/2);
			setLocation(anchuraPantalla/4, alturaPantalla/4);
			
			Image icono = pantalla.getImage("src/img/search.png");
			setIconImage(icono);
			
			setTitle("Busqueda personalizada");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			add(new BusquedaPesonalizadaPanel());
			setVisible(true);
		}
		
		private class BusquedaPesonalizadaPanel extends JPanel{
			
			 private JTextField campoCodigoAlmacen;
			 private JTextField campoDescripcionAlmacen;
			 private JTextField campoCodigoProducto;
			 private JTextField campoCantidad;
			 
			 private JLabel textoCodigoAlmacen;
			 private JLabel textoDescripcionAlmacen;
			 private JLabel textoCodigoProducto;
			 private JLabel textoCantidad;
			 
			 private JButton bntBuscar;
			 private JButton btnBack;
			 
			 private JPanel contenedorBack;
			 private JPanel contenedorFormulario;
			 private JPanel contenedorBuscar;
			 
			 public BusquedaPesonalizadaPanel(){
				 
				 setLayout(new BorderLayout());
				 
				 contenedorBack = new JPanel();
				 contenedorFormulario = new JPanel();
				 contenedorBuscar = new JPanel();
				 
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
				
				ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
				Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
				
				bntBuscar = new JButton("Buscar almacen", iconoEscaladoBuscar);
				btnBack = new JButton("Ir atras", iconoEscaladoBack);
				btnBack.setBackground(Color.BLUE);
				 
				ComprobarLongitudCodigoAlmacen receptor = new ComprobarLongitudCodigoAlmacen();
				campoCodigoAlmacen.getDocument().addDocumentListener(receptor);
				
				campoDescripcionAlmacen.setEditable(false);
				campoCodigoProducto.setEditable(false);
				campoCantidad.setEditable(false);
				bntBuscar.setEnabled(false);
				
				btnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
				
				contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				contenedorFormulario.setLayout(new OrdenarRegistro());
				contenedorBuscar.setLayout(new FlowLayout(FlowLayout.CENTER));
				
				contenedorFormulario.add(textoCodigoAlmacen);
				contenedorFormulario.add(campoCodigoAlmacen);
				contenedorFormulario.add(textoDescripcionAlmacen);
				contenedorFormulario.add(campoDescripcionAlmacen);
				contenedorFormulario.add(textoCodigoProducto);
				contenedorFormulario.add(campoCodigoProducto);
				contenedorFormulario.add(textoCantidad);
				contenedorFormulario.add(campoCantidad);
				 
				contenedorBack.add(btnBack);
				contenedorBuscar.add(bntBuscar);
				
				add(contenedorBack, BorderLayout.NORTH);
				add(contenedorFormulario, BorderLayout.CENTER);
				add(contenedorBuscar, BorderLayout.SOUTH);
				
				BuscarCodigo oyente = new BuscarCodigo();
				bntBuscar.addActionListener(oyente);
				 
				btnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
				 
			}
			 			 
			 private class BuscarCodigo implements ActionListener{
				 
			 		int codigoNumero;
			 		
					@Override
					public void actionPerformed(ActionEvent e) {
						
						AlmacenFicheroBinario ficheroAlmacen = new AlmacenFicheroBinario();
						
				 		String codigoPalabra = campoCodigoAlmacen.getText().trim();
				 		boolean existe = false;
				 		
				 		try {
				 			
				 			//Convertimos todo nuestro codigo en tipo int
				 			codigoNumero=Integer.parseInt(codigoPalabra);
				 			
				 			try {
				 				
								if(ficheroAlmacen.buscarCodigo(codigoNumero, existe)){
									
					                  JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n" +
					                          "Almacen verificado y encontrado.","Encontrado!",
					                          JOptionPane.INFORMATION_MESSAGE);
	
					                  		//Descactivamos el boton de buscar para evitar posibles errores
								    		bntBuscar.setText("Almacen encontrado!");
								    		bntBuscar.setEnabled(false);
								    		
								    		campoDescripcionAlmacen.setText(ficheroAlmacen.getDescripcionAlmacen());
								    		campoCodigoProducto.setText(ficheroAlmacen.getDevolverCodigoProducto());
								    		campoCantidad.setText(ficheroAlmacen.getCantidadAlmacenProducto());
								    		btnBack.setEnabled(true);
								    		btnBack.setBackground(new Color(230, 255, 43));
								    		
								    		campoCodigoAlmacen.setEditable(false);
								    		campoDescripcionAlmacen.setEditable(false);
								    		campoCodigoProducto.setEditable(false);
								    		campoCantidad.setEditable(false);
	
								    					    			
									}else {
										
						                   JOptionPane.showMessageDialog(null, "Codigo no encontrado:\n"
						    	                   + "El codigo: " +  campoCodigoAlmacen.getText() +"  , no se encuentra registrado", "¡No encontrado!",
						    	                   JOptionPane.ERROR_MESSAGE);
						    	                   
						    	                   campoCodigoAlmacen.setText("");
					                  
									}         
								}catch (IOException e1) {
									e1.printStackTrace();
								}
								
				 		}catch(NumberFormatException ex){
				 			
		                    JOptionPane.showMessageDialog(null, "Codigo no valido:\n"
		                            + ex.getMessage()+". Intente nuevamente", "¡Error!",
		                            JOptionPane.ERROR_MESSAGE);
				        	System.out.println("No es un número" + ex.getMessage());
				        	campoCodigoAlmacen.setText("");
				    	}
				 		
					}	
			 	}
			 	
			 	
			 	//Comprobacion de la longitud del codigo
				private class ComprobarLongitudCodigoAlmacen implements DocumentListener{

					@Override
					public void insertUpdate(DocumentEvent e) {
						
						String codigoLongitud = campoCodigoAlmacen.getText().trim();
						
						
						if(codigoLongitud.length()==6) {
							campoCodigoAlmacen.setBackground(new Color(25, 255, 121));
							bntBuscar.setEnabled(true);
							
						}else if(codigoLongitud.length() == 0) {
							campoCodigoAlmacen.setBackground(Color.white);
							bntBuscar.setEnabled(false);
						}
						else {
							bntBuscar.setEnabled(false);
							campoCodigoAlmacen.setBackground(new Color(255, 0, 59));
							
						}
						
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						String codigoLongitud = campoCodigoAlmacen.getText().trim();
						
						if(codigoLongitud.length()==6) {
							campoCodigoAlmacen.setBackground(new Color(25, 255, 121));
							bntBuscar.setEnabled(true);
							
						}else if(codigoLongitud.length() == 0) {
							campoCodigoAlmacen.setBackground(Color.white);
							bntBuscar.setEnabled(false);
						}
						else {
							bntBuscar.setEnabled(false);
							campoCodigoAlmacen.setBackground(new Color(255, 0, 59));
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
