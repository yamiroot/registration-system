package codigo.tabla.proveedores;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

public class EliminarProveedoresFrame extends JFrame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EliminarProveedoresFrame deleteProovedor = new EliminarProveedoresFrame();
	}

	
	public EliminarProveedoresFrame() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4, alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/delete.png");
		setIconImage(icono);
		
		setTitle("ELIMINAR PROOVEDOR");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(new EliminarProovedorPanel());
		setVisible(true);
	}
	
	private class EliminarProovedorPanel extends JPanel {
		
		 private JTextField campoRuc;
		 private JTextField campoNombre;
		 
		 private JLabel textoRuc;
		 private JLabel textoNombre;
		 
		 private JButton bntBuscar;
		 private JButton btnEliminar;
		 private JButton BtnBack;
		 
		 private JPanel contenedorBack;
		 private JPanel contenedorFormulario;
		 
		 public EliminarProovedorPanel(){
			 
			setLayout(new BorderLayout());
			
			 contenedorBack = new JPanel();
			 contenedorFormulario = new JPanel();
			 
			 campoRuc = new JTextField();
			 campoNombre = new JTextField();

			 
			 ComprobarLongitudRuc receptor = new ComprobarLongitudRuc();
			 campoRuc.getDocument().addDocumentListener(receptor);
			 
			 textoRuc = new JLabel("Ingresar RUC del proovedor:");
			 textoNombre = new JLabel("Nombre del proovedor:");			 
			 
			ImageIcon iconoBuscar = new ImageIcon("src/img/search.png");
			Image imgEscaladaBuscar= iconoBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBuscar= new ImageIcon(imgEscaladaBuscar);
			
			ImageIcon iconoEliminar = new ImageIcon("src/img/delete.png");
			Image imgEscaladaEliminar = iconoEliminar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoEliminar = new ImageIcon(imgEscaladaEliminar);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			bntBuscar = new JButton("Buscar RUC", iconoEscaladoBuscar);
			btnEliminar = new JButton("Eiminar Proovedor", iconoEscaladoEliminar);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			BtnBack.setBackground(new Color(79, 133, 229));
			
			campoNombre.setEditable(false);
			btnEliminar.setEnabled(false);
			bntBuscar.setEnabled(false);
			
		 
			 BuscarCodigo oyente = new BuscarCodigo();
			 bntBuscar.addActionListener(oyente);
			 
			 btnEliminar.addActionListener(new ActionListener() {
				 
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String rucPalabra = campoRuc.getText().trim();
					
					ProveedorFicheroBinario elFichero=new ProveedorFicheroBinario();
					
					try {
						elFichero.buscarRucEliminar(rucPalabra);
						elFichero.reescribirFichero();
						elFichero.leerFichero();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					campoRuc.setText("");
					campoNombre.setText("");
					
	                JOptionPane.showMessageDialog(null, "Proovedor eliminado:\n" +
	                        "","Eliminado!",
	                        JOptionPane.INFORMATION_MESSAGE);
	                
	                btnEliminar.setText("Proovedor Eliminado!");
	                btnEliminar.setBackground(null);
	                btnEliminar.setEnabled(false);
	                campoRuc.setEnabled(false);
					
				}
			  });
			 
				BtnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
				
				
				contenedorFormulario.setLayout(new OrdenarRegistro());
				contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				 
				 contenedorFormulario.add(textoRuc);
				 contenedorFormulario.add(campoRuc);
				 contenedorFormulario.add(textoNombre);
				 contenedorFormulario.add(campoNombre);;
				 contenedorFormulario.add(bntBuscar);
				 contenedorFormulario.add(btnEliminar);
				 contenedorBack.add(BtnBack);
				 
				 add(contenedorBack, BorderLayout.NORTH);
				 add(contenedorFormulario, BorderLayout.CENTER);
				 
		 }
		 	
		 
		 
		 	private class BuscarCodigo implements ActionListener{
		 		
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ProveedorFicheroBinario miFichero=new ProveedorFicheroBinario();
					
			 		String rucPalabra = campoRuc.getText().trim();
			 		boolean verifica=false;	
			 					 			
						try {
							if(miFichero.buscarRUC(rucPalabra, verifica)){
								
							      JOptionPane.showMessageDialog(null, "RUC encontrado" +
							              "","Encontrado!",
							              JOptionPane.INFORMATION_MESSAGE);

							      		//Descactivamos el boton de buscar para evitar posibles errores
							    		bntBuscar.setText("RUC encontrado!");
							    		btnEliminar.setEnabled(true);
							    		btnEliminar.setBackground(new Color(255, 112, 112));
							    		bntBuscar.setEnabled(false);
							    		BtnBack.setBackground(Color.YELLOW);
							    								    						    		
							    		//Hacemos simulaci�n de los datos encontrados
							    		campoNombre.setText(miFichero.getNombreProveedor());
							    					    			
								}else {
									
							           JOptionPane.showMessageDialog(null, "RUC no encontrado:\n"
							                   + "El RUC: " +  campoRuc.getText() +"  , no se encuentra registrado", "No encontrado!",
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
						
					}
					
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					String rucLongitud = campoRuc.getText().trim();
					
					if(rucLongitud.length()==12) {
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