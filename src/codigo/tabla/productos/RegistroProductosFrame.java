package codigo.tabla.productos;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

public class RegistroProductosFrame extends JFrame {
	
	public static void main(String[] args) {
		RegistroProductosFrame newAlmacen = new RegistroProductosFrame();
	
	}

	
	public RegistroProductosFrame() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4, alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/addShopWhite.png");
		setIconImage(icono);
		
		setTitle("REGISTAR PRODUCTO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(new RegistroProductosPanel());
		setVisible(true);
	}
	
	private class RegistroProductosPanel extends JPanel{
		
		 private JTextField campoCodigo;
		 private JTextField campoDescripcion;
		 
		 private JLabel TextoCodigo;
		 private JLabel textoDescripcion;
		 
		 private JButton btnRegistrar;
		 private JButton btnComprobarCodigo;
		 private JButton BtnBack;
		
		 private JPanel contenedorBack;
		 private JPanel contenedorFormulario;
			
		 public RegistroProductosPanel(){
			 
			setLayout(new BorderLayout());
				
			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();
			 
			campoCodigo = new JTextField();
			campoDescripcion = new JTextField();
			 
			ComprobarLongitudRuc receptor = new ComprobarLongitudRuc();
			campoCodigo.getDocument().addDocumentListener(receptor);
			 
			TextoCodigo = new JLabel("Codigo del producto:");
			textoDescripcion = new JLabel("Descripcion del producto:");		 
			 
			ImageIcon iconoRegistro = new ImageIcon("src/img/save.png");
			Image imgEscaladaRegistro = iconoRegistro.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoRegistro = new ImageIcon(imgEscaladaRegistro);
			
			ImageIcon iconoCheck = new ImageIcon("src/img/check.png");
			Image imgEscaladaCheck = iconoCheck.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoCheck = new ImageIcon(imgEscaladaCheck);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			btnRegistrar = new JButton("Registrar producto", iconoEscaladoRegistro);
			btnComprobarCodigo = new JButton("Verificar codigo", iconoEscaladoCheck);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			
			 btnRegistrar.setEnabled(false);
			 btnComprobarCodigo.setEnabled(false);

			 
			 verificarCodigo oyente = new verificarCodigo();
			 btnComprobarCodigo.addActionListener(oyente);
			
			 
			 btnRegistrar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String rucPalabra = campoCodigo.getText().trim();
					
					//ACA ALMACENO MI DESCRIPCION INGRESADO EN LA INTERFAZ
					String descripcionPalabras = campoDescripcion.getText().trim();
					
					if(descripcionPalabras.equals("")) {
						JOptionPane.showMessageDialog(null, "Registro no completado:\n" +
		                           "Porfavor agregue una descripcion a su producto","Error!",
		                           JOptionPane.ERROR_MESSAGE);
						
					}else {
						
						int RUC=Integer.parseInt(rucPalabra);
						
						Productos misProductos=new Productos(RUC, descripcionPalabras);
						
						ProductoFicheroBinario miFichero=new ProductoFicheroBinario();
						
						miFichero.aniadirProductosArray(misProductos);
						
						try {
							miFichero.guardarArrayFichero();
							miFichero.leerFichero();
							miFichero.mostrarArrayPantalla();
							
							JOptionPane.showMessageDialog(null, "Producto agregado:\n" +
			                           "Se registro el producto de manerea correcta","Registrado!",
			                           JOptionPane.INFORMATION_MESSAGE);

								campoCodigo.setEditable(false);
								campoDescripcion.setEditable(false);
								btnRegistrar.setEnabled(false);
								btnRegistrar.setText("Registrado!");
								btnRegistrar.setBackground(null);
								BtnBack.setBackground(Color.YELLOW);
								
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
									
					
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
			 
			contenedorFormulario.add(TextoCodigo);
			contenedorFormulario.add(campoCodigo);
			contenedorFormulario.add(textoDescripcion);
			contenedorFormulario.add(campoDescripcion);
			contenedorFormulario.add(btnComprobarCodigo);
			contenedorFormulario.add(btnRegistrar);
			
			contenedorBack.add(BtnBack);
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormulario, BorderLayout.CENTER);
		 }
		 	
		 
		 	private class verificarCodigo implements ActionListener{
		 		
		 		ProductoFicheroBinario miFichero=new ProductoFicheroBinario();
		 		
		 		//EL CODIGO DEL PRODUCTO SOLO SERA DE 4 DIGITOS
		 		//CODIGO DE PRUEBA 1234
		 		
		 		int codigoNumero;
		 		
				@Override
				public void actionPerformed(ActionEvent e) {
			 		String codigoPalabra = campoCodigo.getText().trim();
			 		
			 		//ESTA EN STRING PORQUE SOLO ME ACEPTA ASI LA COMPARACION
			 		//NECESITARAS CONVERTIR LOS CODIGOS QUE ESTAN GUARDADOS EN EL ARCHIVO BINARIO
			 		//EN STRING SOLO PARA COMPARARLOS
			 		
			 		try {
			 			
			 			//Convertimos todo nuestro codigo en tipo int
			 			codigoNumero=Integer.parseInt(codigoPalabra);
			 			boolean existe = false;
			 						 			
			 			if(!miFichero.buscarCodigo(codigoNumero, existe)) {
			 				
			                   JOptionPane.showMessageDialog(null, "Producto disponible:\n" +
			                           "Este codigo puede ser usado","Verificado!",
			                           JOptionPane.INFORMATION_MESSAGE);
					    		btnComprobarCodigo.setText("Codigo Verificado");
					    		btnRegistrar.setEnabled(true);
					    		btnRegistrar.setBackground(new Color(79, 132, 247));
					    		campoCodigo.setEditable(false);
					    		btnComprobarCodigo.setEnabled(false);
					    		
			 			}
			 			
			 			else {
			                   JOptionPane.showMessageDialog(null, "Codigo no valido:\n"
			    	                   + "Es posible que el codigo ya se encuentre en uso", "En uso!",
			    	                   JOptionPane.ERROR_MESSAGE);
			    	                   
			    	                   campoCodigo.setText("");
			 			}
			 			
			 		}catch (NumberFormatException ex) {
	                    JOptionPane.showMessageDialog(null, "Codigo no valido:\n"
	                            + ex.getMessage()+". Intente nuevamente", "Error!",
	                            JOptionPane.ERROR_MESSAGE);
			        	campoCodigo.setText("");
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
		 		
		 	}
		 	
			private class ComprobarLongitudRuc implements DocumentListener{
				

				@Override
				public void insertUpdate(DocumentEvent e) {
					
					String codigoLongitud = campoCodigo.getText().trim();
					
					
					if(codigoLongitud.length()==4) {
						campoCodigo.setBackground(new Color(25, 255, 121));
						btnComprobarCodigo.setEnabled(true);
						
					}else if(codigoLongitud.length() == 0) {
						campoCodigo.setBackground(Color.white);
						btnComprobarCodigo.setEnabled(false);
					}
					else {
						campoCodigo.setBackground(new Color(255, 0, 59));
						btnComprobarCodigo.setEnabled(false);
					}
					
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					String codigoLongitud = campoCodigo.getText().trim();
					
					if(codigoLongitud.length()==4) {
						campoCodigo.setBackground(new Color(25, 255, 121));
						btnComprobarCodigo.setEnabled(true);
						
					}else if(codigoLongitud.length() == 0) {
						campoCodigo.setBackground(Color.white);
						btnComprobarCodigo.setEnabled(false);
					}
					else {
						campoCodigo.setBackground(new Color(255, 0, 59));
						btnComprobarCodigo.setEnabled(false);
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
