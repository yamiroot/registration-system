package codigo.tabla.proveedores;

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
			
			setTitle("BUSQUEDA PERSONALIZADA");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			add(new BusquedaPesonalizadaPanel());
			setVisible(true);
		}
		
		private class BusquedaPesonalizadaPanel extends JPanel{
			
			 private JTextField campoRuc;
			 private JTextField campoNombre;
			 
			 private JLabel textoRuc;
			 private JLabel textoNombre;;
			 
			 private JButton bntBuscar;
			 private JButton btnCerrarVentana;
			 
			 private JPanel contenedorFormularior;
			 private JPanel contenedorBack;
			 private JPanel contenedorBuscar;
			 public BusquedaPesonalizadaPanel(){

				 setLayout(new BorderLayout());
				 
				 //Creamos nuestros contenedores
				 contenedorFormularior = new JPanel();
				 contenedorBack = new JPanel();
				 contenedorBuscar = new JPanel();
				 
				 campoRuc = new JTextField();
				 campoNombre = new JTextField();
				 
				 ComprobarLongitudRuc receptor = new ComprobarLongitudRuc();
				 campoRuc.getDocument().addDocumentListener(receptor);

				ImageIcon iconoBuscar = new ImageIcon("src/img/search.png");
				Image imgEscaladaBuscar= iconoBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Icon iconoEscaladoBuscar= new ImageIcon(imgEscaladaBuscar);
				
				ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
				Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
				
				ImageIcon iconoUser = new ImageIcon("src/img/userForm.png");
				Image imgEscaladaUser= iconoUser.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Icon iconoEscaladoUser= new ImageIcon(imgEscaladaUser);
				
				ImageIcon iconoRuc = new ImageIcon("src/img/password.png");
				Image imgEscaladaRuc= iconoRuc.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Icon iconoEscaladoRuc= new ImageIcon(imgEscaladaRuc);
				 
				textoRuc = new JLabel("     Ingrese RUC del proovedor:", iconoEscaladoRuc, FlowLayout.LEFT);
				textoNombre = new JLabel("     Nombre del proovedor:",  iconoEscaladoUser, FlowLayout.LEFT);
				
				bntBuscar = new JButton("Buscar proovedor", iconoEscaladoBuscar);
				btnCerrarVentana = new JButton("Regresar", iconoEscaladoBack);
				
				campoNombre.setEditable(false);
				bntBuscar.setEnabled(false);
				
				btnCerrarVentana.setBackground(new Color(79, 133, 229));
				
				contenedorFormularior.setLayout(new OrdenarRegistro());
				contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				contenedorBuscar.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
				
				contenedorFormularior.add(textoRuc);
				contenedorFormularior.add(campoRuc);
				 
				contenedorFormularior.add(textoNombre);
				contenedorFormularior.add(campoNombre);
				 
				contenedorBack.add(btnCerrarVentana);
				contenedorBuscar.add(bntBuscar);
				 
				 BuscarRuc oyente = new BuscarRuc();
				 bntBuscar.addActionListener(oyente);
				 
				 btnCerrarVentana.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
				 
				
				add(contenedorBack,BorderLayout.NORTH);
				add(contenedorFormularior, BorderLayout.CENTER);
				add(contenedorBuscar, BorderLayout.SOUTH);
				 
			 }
			 	
			 
			 
			 	private class BuscarRuc implements ActionListener{

					@Override
					public void actionPerformed(ActionEvent e) {
						
						ProveedorFicheroBinario elFichero=new ProveedorFicheroBinario();
						
				 		String codigoRuc = campoRuc.getText().trim();
				 		boolean verifica=false;
				 			
							try {
								
								if(elFichero.buscarRUC(codigoRuc, verifica)){
									
								      JOptionPane.showMessageDialog(null, "RUC encontrado:\n" +
								              "","Encontrado!",
								              JOptionPane.INFORMATION_MESSAGE);

								      		//Descactivamos el boton de buscar para evitar posibles errores
								    		bntBuscar.setText("Proovedor encontrado!");
								    		bntBuscar.setEnabled(false);
								    		
								    		btnCerrarVentana.setEnabled(true);
								    		btnCerrarVentana.setBackground(new Color(230, 255, 43));
								    		campoRuc.setEditable(false);
								    		campoNombre.setText(elFichero.getNombreProveedor());
								    		
								    					    			
									}else {
										
								           JOptionPane.showMessageDialog(null, "RUC no encontrado:\n"
								                   + "El codigo: " +  campoRuc.getText() +"  , no se encuentra registrado", "No encontrado!",
								                   JOptionPane.ERROR_MESSAGE);
								                   
								                   campoRuc.setText("");
								      
								      
									}
							} catch (HeadlessException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

					}	
			 	}
			 	
			 	
			 	//Comprobacion de la longitud del codigo
				private class ComprobarLongitudRuc implements DocumentListener{

					@Override
					public void insertUpdate(DocumentEvent e) {
						
						String rucLongitud = campoRuc.getText().trim();
						
						
						if(rucLongitud.length() == 11) {
							campoRuc.setBackground(new Color(25, 255, 121));
							bntBuscar.setEnabled(true);
							
						}else if(rucLongitud.length() == 0) {
							campoRuc.setBackground(Color.white);
							bntBuscar.setEnabled(false);
						}
						else {
							campoRuc.setBackground(new Color(255, 0, 59));
							bntBuscar.setEnabled(false);
						}
						
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						String rucLongitud = campoRuc.getText().trim();
						
						if(rucLongitud.length()==11) {
							campoRuc.setBackground(new Color(25, 255, 121));
							bntBuscar.setEnabled(true);
							
						}else if(rucLongitud.length() == 0) {
							campoRuc.setBackground(Color.white);
							bntBuscar.setEnabled(false);
						}
						else {
							campoRuc.setBackground(new Color(255, 0, 59));
							bntBuscar.setEnabled(false);
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
							c.setBounds(x-300,y,300,30);
							
							x+= 300;
							
							if(contador%2==0) {
								x = anchoContenedor/2;
								y += alturaContenedor/8;
							}
						}
						
					}
					
				}
		}
}
