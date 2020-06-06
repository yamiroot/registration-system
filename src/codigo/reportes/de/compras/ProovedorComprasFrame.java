package codigo.reportes.de.compras;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import codigo.transacciones.registro.OrdenesFicheroBinario;



public class ProovedorComprasFrame extends JFrame {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProovedorComprasFrame tablaPersonalProovedor = new ProovedorComprasFrame();
		

	}

	
	public ProovedorComprasFrame() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4, alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/search.png");
		setIconImage(icono);
		
		setTitle("BUSQUEDA DE COMPRAS PERSONALIZADA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(new TablaEspecificaProovedor());
		setVisible(true);
	}
	
	private class TablaEspecificaProovedor extends JPanel{
		
		 private JTable tablaEspecificaProovedor;
		 
		 private JTextField campoCodigoRuc;
	
		 private JLabel textoCodigoRuc;
		 
		 private JButton bntBuscar;
		 private JButton btnImprimir;
		 private JButton btnCerrarVentana;
		 
		 private JPanel contenedorTabla;
		 private JPanel contenedorBack;
		 private JPanel contenedorBuscarImprimir;
		 private JPanel contenedorFormulario;
		 
		 //ESTE ARRAY QUEDA YA QUE ES EL NOMBRE DE COLUMNAS
		 private String[] nombreColumnas = { "#Producto","Cantidad", "P/U (S/,)", "Total(S/.)"};
		//ESTE ARRAY SI ES A MODO DE PRUEBA NOMAS		 
		 public TablaEspecificaProovedor(){
			 
			 setLayout(new BorderLayout());
			
			//Creamos nuestros contenedores
			contenedorTabla = new JPanel();
			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();
			contenedorBuscarImprimir = new JPanel();
			 
			campoCodigoRuc = new JTextField(15);
			 
			ComprobarLongitudCodigo receptor = new ComprobarLongitudCodigo();
			campoCodigoRuc.getDocument().addDocumentListener(receptor);

			ImageIcon iconoBuscar = new ImageIcon("src/img/search.png");
			Image imgEscaladaBuscar= iconoBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBuscar= new ImageIcon(imgEscaladaBuscar);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			ImageIcon iconoCodigo = new ImageIcon("src/img/password.png");
			Image imgEscaladaCodigo= iconoCodigo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoCodigo= new ImageIcon(imgEscaladaCodigo);
			
			ImageIcon iconoImprimir = new ImageIcon("src/img/print.png");
			Image imgiconoImprimir= iconoImprimir.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoImprimir= new ImageIcon(imgiconoImprimir);
			 			
			textoCodigoRuc = new JLabel("     Ingrese RUC del producto:", iconoEscaladoCodigo, FlowLayout.LEFT);
			bntBuscar = new JButton("Buscar proovedor", iconoEscaladoBuscar);
			btnCerrarVentana = new JButton("Ir atras", iconoEscaladoBack);
			
			btnImprimir = new JButton("Imprimir", iconoEscaladoImprimir);
			btnImprimir.setEnabled(false);
			
			bntBuscar.setEnabled(false);
			
			btnCerrarVentana.setBackground(new Color(79, 133, 229));
			
			contenedorBack.setLayout(new BorderLayout(0, 0));
			//contenedorFormulario.setLayout(new FlowLayout(FlowLayout.CENTER,10,60));
			contenedorTabla.setLayout(new BorderLayout(0,0));
			contenedorBuscarImprimir.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
			
			contenedorBack.add(btnCerrarVentana);
			
			contenedorFormulario.add(textoCodigoRuc);
			contenedorFormulario.add(campoCodigoRuc);
			
			contenedorBuscarImprimir.add(bntBuscar);
			contenedorBuscarImprimir.add(btnImprimir);
			
			 
			 BuscarCodigo oyente = new BuscarCodigo();
			 bntBuscar.addActionListener(oyente);
			 
			 btnCerrarVentana.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					
				}
			});
			 
			btnImprimir.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							
							tablaEspecificaProovedor.print();
						}catch (PrinterException e2) {
							JOptionPane.showMessageDialog(null, "Error inesperado:\n" +
			                           e2.getMessage(),"¡Error!",
			                           JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
				});
			 
			
			add(contenedorBack,BorderLayout.WEST);
			add(contenedorTabla, BorderLayout.CENTER);
			add(contenedorBuscarImprimir, BorderLayout.SOUTH);
			add(contenedorFormulario, BorderLayout.NORTH);
			 
		 }
		 	
		 
		 
		 	private class BuscarCodigo implements ActionListener{
		 		
				@Override
				public void actionPerformed(ActionEvent e) {
					OrdenesFicheroBinario ficheroOrdenes = new OrdenesFicheroBinario();
			 		String codigoPalabraRuc = campoCodigoRuc.getText().trim();
			 		
			 		boolean encontrarCodigo = false;
			 		
			 			//Verificando que el RUC se encuentre en el archivo binario
						try {
							
							if(ficheroOrdenes.buscarCodigo(codigoPalabraRuc, encontrarCodigo)){
								
							      JOptionPane.showMessageDialog(null, "Proovedor encontrado:\n" +
							              "","¡Encontrado!",
							              JOptionPane.INFORMATION_MESSAGE);

							      		//Descactivamos el boton de buscar para evitar posibles errores
							    		bntBuscar.setText("¡Codigo de RUC encontrado!");
							    		bntBuscar.setEnabled(false);
							    		
							    		btnImprimir.setEnabled(true);
										btnImprimir.setBackground(new Color(0, 232, 206));
										
							    		btnCerrarVentana.setEnabled(true);
							    		btnCerrarVentana.setBackground(new Color(230, 255, 43));
							    		campoCodigoRuc.setEditable(false);
							    		
										//ACA AGREGAMOS LOS EL VECTOR DE MI NOMBRECOLUMNA Y LA MATRIZ DE DATOSFILA
										tablaEspecificaProovedor = new JTable(ficheroOrdenes.mostrarArrayTablePersonal(),nombreColumnas);
							    		
										//JScrollPane para agregarle una barra de desplazamiento vertical
							    		contenedorTabla.add(new JScrollPane(tablaEspecificaProovedor));
							    		
							    					    			
								}else {
									
							           JOptionPane.showMessageDialog(null, "Codigo de ruc no encontrado:\n"
							                   + "El codigo: " +  campoCodigoRuc.getText() +"  , no se encuentra registrado", "¡No encontrado!",
							                   JOptionPane.ERROR_MESSAGE);
							                   
							           campoCodigoRuc.setText("");
							      
							      
								}
							
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
		 	
		 	
		 	//Comprobacion de la longitud del codigo
			private class ComprobarLongitudCodigo implements DocumentListener{

				@Override
				public void insertUpdate(DocumentEvent e) {
					
					String codigoLongitud = campoCodigoRuc.getText().trim();
					
					
					if(codigoLongitud.length() == 11) {
						campoCodigoRuc.setBackground(new Color(25, 255, 121));
						bntBuscar.setEnabled(true);
						
					}else if(codigoLongitud.length() == 0) {
						campoCodigoRuc.setBackground(Color.white);
						bntBuscar.setEnabled(false);
					}
					else {
						campoCodigoRuc.setBackground(new Color(255, 0, 59));
						bntBuscar.setEnabled(false);
					}
					
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					String codigoLongitud = campoCodigoRuc.getText().trim();
					
					if(codigoLongitud.length()==11) {
						campoCodigoRuc.setBackground(new Color(25, 255, 121));
						bntBuscar.setEnabled(true);
						
					}else if(codigoLongitud.length() == 0) {
						campoCodigoRuc.setBackground(Color.white);
						bntBuscar.setEnabled(false);
					}
					else {
						campoCodigoRuc.setBackground(new Color(255, 0, 59));
						bntBuscar.setEnabled(false);
					}
					
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					
					
				}
				
			}
	}

}
