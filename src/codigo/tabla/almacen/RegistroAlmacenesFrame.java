package codigo.tabla.almacen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import codigo.tabla.productos.ProductoFicheroBinario;

public class RegistroAlmacenesFrame extends JFrame {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RegistroAlmacenesFrame registroAlmacen = new RegistroAlmacenesFrame();
	}
	

	public RegistroAlmacenesFrame() {
		
		setTitle("Registro de almacen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		setSize(anchuraPantalla / 2, alturaPantalla / 2);
		setLocation(anchuraPantalla / 4, alturaPantalla / 4);
		Image icono = pantalla.getImage("src/img/addRegistroWhite.png");
		setIconImage(icono);
		add(new RegistroAlmacenesPanel());
		
		setVisible(true);
		
	}

	private class RegistroAlmacenesPanel extends JPanel {
		
		private int contadorVerificador;
		private JTextField campoCodigoAlmacen;
		private JTextField campoDescripcionAlmacen;
		private JTextField campoCodigoProducto;
		private JTextField campoCantidadProducto;
		
		private JLabel textoCodigoAlmacen;
		private JLabel textoDescripcionALmacen;
		private JLabel textoCodigoProducto;
		private JLabel textoCantidadProducto;

		private JButton btnRegistrar;
		private JButton btnComprobarCodigoAlmacen;
		private JButton btnComprobarCodigoProducto;
		private JButton BtnBack;

		private JPanel contenedorBack; 		
		private JPanel contenedorFormulario;
		private JPanel contenedorBtnRegistrar;

		public RegistroAlmacenesPanel() {
			
			setLayout(new BorderLayout());

			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();
			contenedorBtnRegistrar = new JPanel();

			campoCodigoAlmacen = new JTextField();
			campoDescripcionAlmacen = new JTextField();
			campoCodigoProducto = new JTextField();
			campoCantidadProducto = new JTextField();


			textoCodigoAlmacen = new JLabel("Codigo almacen:");
			textoDescripcionALmacen = new JLabel("Descripcion almacen:");
			textoCodigoProducto = new JLabel("codigo producto:");
			textoCantidadProducto = new JLabel("Cantidad de producto:");

			ImageIcon iconoRegistro = new ImageIcon("src/img/save.png");
			Image imgEscaladaRegistro = iconoRegistro.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoRegistro = new ImageIcon(imgEscaladaRegistro);

			ImageIcon iconoCheck = new ImageIcon("src/img/check.png");
			Image imgEscaladaCheck = iconoCheck.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoCheck = new ImageIcon(imgEscaladaCheck);

			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack = iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack = new ImageIcon(imgEscaladaBack);

			btnRegistrar = new JButton("Registrar almacen", iconoEscaladoRegistro);
			btnComprobarCodigoAlmacen = new JButton("Verificar Cod. Almacen", iconoEscaladoCheck);
			btnComprobarCodigoProducto = new JButton("Verificar Cod. Producto", iconoEscaladoCheck);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);

			btnRegistrar.setEnabled(false);
			btnComprobarCodigoAlmacen.setEnabled(false);
			btnComprobarCodigoProducto.setEnabled(false);
			
			ComprobarLongitudCodigoAlmacen receptorAlmacen = new ComprobarLongitudCodigoAlmacen();
			campoCodigoAlmacen.getDocument().addDocumentListener(receptorAlmacen);
			
			ComprobarLongitudCodigoProducto receptorProducto = new ComprobarLongitudCodigoProducto();
			campoCodigoProducto.getDocument().addDocumentListener(receptorProducto);
			
			
			VerificarCodigoAlmacen oyenteAlmacen = new VerificarCodigoAlmacen();
			btnComprobarCodigoAlmacen.addActionListener(oyenteAlmacen);
			
			VerificarCodigoProducto oyenteProducto = new VerificarCodigoProducto();
			btnComprobarCodigoProducto.addActionListener(oyenteProducto);
			
			btnRegistrar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					
					String codigoPalabraAlmacen = campoCodigoAlmacen.getText().trim();
					String descripcionPalabraAlmacen = campoDescripcionAlmacen.getText().trim();
					String codigoPalabraProducto = campoCodigoProducto.getText().trim();
					String unidadesPalabra = campoCantidadProducto.getText().trim();

					
					if(descripcionPalabraAlmacen.equals("") && unidadesPalabra.equals("")) {
						JOptionPane.showMessageDialog(null,
								"Necesita rellenar los campos de informacion", "FALTA INFORMACION!",
								JOptionPane.ERROR_MESSAGE);
						
						campoDescripcionAlmacen.setText("");
						campoCantidadProducto.setText("");
						
						
						
					}else {
					
						try {
							
							int codigoNumeroAlmacen = Integer.parseInt(codigoPalabraAlmacen);
							int codigoNumeroProducto = Integer.parseInt(codigoPalabraProducto);
							float unidadesFisicas = Float.parseFloat(unidadesPalabra);

							Almacen almacenRegistro = new Almacen(codigoNumeroAlmacen, descripcionPalabraAlmacen, codigoNumeroProducto, unidadesFisicas);
							
							AlmacenFicheroBinario ingresarNuevoAlmacen = new AlmacenFicheroBinario();
							
							try {
								ingresarNuevoAlmacen.aniadirAlmacenesArray(almacenRegistro);
								ingresarNuevoAlmacen.guardarArrayFichero();
								ingresarNuevoAlmacen.leerFichero();
							} catch (IOException e1) {
								
								e1.printStackTrace();
							}
							
							JOptionPane.showMessageDialog(null,
									"Almacen agregado:\n" + "Se registro el almacen de manerea correcta", "Registrado!",
									JOptionPane.INFORMATION_MESSAGE);
	
							campoCantidadProducto.setEditable(false);
							campoDescripcionAlmacen.setEditable(false);
	
							btnRegistrar.setEnabled(false);
							btnRegistrar.setText("Registrado!");
							btnRegistrar.setBackground(null);
							BtnBack.setBackground(Color.YELLOW);
	
						} catch (NumberFormatException e2) {
	
							JOptionPane.showMessageDialog(null,
									"Error de cantidad:\n" + "La cantidad:  " + campoCantidadProducto.getText().trim()
											+ " no se encuentra permitida" + "\nSolo se admite cantidades numericas",
									"Error!", JOptionPane.ERROR_MESSAGE);
							campoCantidadProducto.setText("");
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
			
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			contenedorFormulario.setLayout(new OrdenarRegistro());
			contenedorBtnRegistrar.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			contenedorBack.add(BtnBack);
			
			contenedorFormulario.add(textoCodigoAlmacen);
			contenedorFormulario.add(campoCodigoAlmacen);
			
			contenedorFormulario.add(textoDescripcionALmacen);
			contenedorFormulario.add(campoDescripcionAlmacen);
			
			contenedorFormulario.add(textoCodigoProducto);
			contenedorFormulario.add(campoCodigoProducto);
			
			contenedorFormulario.add(textoCantidadProducto);
			contenedorFormulario.add(campoCantidadProducto);
			
			contenedorFormulario.add(btnComprobarCodigoAlmacen);
			contenedorFormulario.add(btnComprobarCodigoProducto);
			
			contenedorBtnRegistrar.add(btnRegistrar);
			
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormulario, BorderLayout.CENTER);
			add(contenedorBtnRegistrar, BorderLayout.SOUTH);
		}
		
		private class VerificarCodigoAlmacen implements ActionListener {

			AlmacenFicheroBinario ficheroAlmacenBinario = new AlmacenFicheroBinario();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				String codigoPalabraAlmacen = campoCodigoAlmacen.getText().trim();
					
					//TRY PRINCIPAL
					try {

						int codigoNumeroAlmacen = Integer.parseInt(codigoPalabraAlmacen);
						boolean encontroCodigo = false;
												
						try {

							if (ficheroAlmacenBinario.buscarCodigo(codigoNumeroAlmacen, encontroCodigo) == false) {
	
								JOptionPane.showMessageDialog(null, "Codigo disponible:\n" + "Este codigo puede ser usado",
											"Verificado!", JOptionPane.INFORMATION_MESSAGE);
								
								contadorVerificador++;
								
								btnComprobarCodigoAlmacen.setText("Almacen disponible!");
								btnComprobarCodigoAlmacen.setEnabled(false);
								campoCodigoAlmacen.setEditable(false);
								
								if(contadorVerificador == 2) {
									btnRegistrar.setEnabled(true);
									btnRegistrar.setBackground(new Color(79, 132, 247));;
								}
								
							}else {
									JOptionPane.showMessageDialog(null,
											"Codigo no valido:\n" + "Es posible que el codigo del almacen ya se encuentre en uso", "En uso!",
											JOptionPane.ERROR_MESSAGE);
									campoCodigoAlmacen.setText("");
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
						campoCodigoAlmacen.setText("");
					
					}
				

			}

		}
		
		
		private class VerificarCodigoProducto implements ActionListener {

			ProductoFicheroBinario ficheroProductoBinario = new ProductoFicheroBinario();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				String codigoPalabraProducto = campoCodigoProducto.getText().trim();
					
					//TRY PRINCIPAL
					try {

						int codigoNumeroProducto = Integer.parseInt(codigoPalabraProducto);
						boolean encontroCodigo = false;
												
						try {

							if (ficheroProductoBinario.buscarCodigo(codigoNumeroProducto, encontroCodigo)) {
	
								JOptionPane.showMessageDialog(null, "Codigo verificado:\n" + "El codigo se encuentra registrado en la BD de productos",
											"Verificado!", JOptionPane.INFORMATION_MESSAGE);
								
										
								contadorVerificador++;
								
								
								btnComprobarCodigoProducto.setText("Producto encontrado!");
								btnComprobarCodigoProducto.setEnabled(false);
								campoCodigoProducto.setEditable(false);
								
								if(contadorVerificador == 2) {
									btnRegistrar.setEnabled(true);
									btnRegistrar.setBackground(new Color(79, 132, 247));
								}
								
							}else {
									JOptionPane.showMessageDialog(null,
											"El codigo no se encuentra registrado en la BD de productos", "No encontrado!",
											JOptionPane.ERROR_MESSAGE);
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
						campoCodigoAlmacen.setText("");
					
					}
				

			}

		}
		
		
		private class ComprobarLongitudCodigoAlmacen implements DocumentListener {

			@Override
			public void insertUpdate(DocumentEvent e) {

				String codigoLongitudAlmacen = campoCodigoAlmacen.getText().trim();
				
				if (codigoLongitudAlmacen.equals("")) {
					campoCodigoAlmacen.setBackground(Color.WHITE);
					btnComprobarCodigoAlmacen.setEnabled(false);
					
				}else if(codigoLongitudAlmacen.length() == 6){

						
						campoCodigoAlmacen.setBackground(Color.GREEN);
						btnComprobarCodigoAlmacen.setEnabled(true);
					
				}else {
					campoCodigoAlmacen.setBackground(new Color(255, 0, 59));
					btnComprobarCodigoAlmacen.setEnabled(false);
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				String codigoLongitudAlmacen = campoCodigoAlmacen.getText().trim();
				
				if (codigoLongitudAlmacen.equals("")) {
					campoCodigoAlmacen.setBackground(Color.WHITE);
					btnComprobarCodigoAlmacen.setEnabled(false);
					
				}else if(codigoLongitudAlmacen.length() == 6){

						
						campoCodigoAlmacen.setBackground(Color.GREEN);
						btnComprobarCodigoAlmacen.setEnabled(true);
					
				}else {
					campoCodigoAlmacen.setBackground(new Color(255, 0, 59));
					btnComprobarCodigoAlmacen.setEnabled(false);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		}
		
		
		private class ComprobarLongitudCodigoProducto implements DocumentListener {

			@Override
			public void insertUpdate(DocumentEvent e) {

				String codigoLongitudProducto = campoCodigoProducto.getText().trim();
				
				if (codigoLongitudProducto.equals("")) {
					campoCodigoProducto.setBackground(Color.WHITE);
					btnComprobarCodigoProducto.setEnabled(false);
					
				}else if(codigoLongitudProducto.length() == 4){

						
						campoCodigoProducto.setBackground(Color.GREEN);
						btnComprobarCodigoProducto.setEnabled(true);
					
				}else {
					campoCodigoProducto.setBackground(new Color(255, 0, 59));
					btnComprobarCodigoProducto.setEnabled(false);
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				String codigoLongitudProducto = campoCodigoProducto.getText().trim();
				
				if (codigoLongitudProducto.equals("")) {
					campoCodigoProducto.setBackground(Color.WHITE);
					btnComprobarCodigoProducto.setEnabled(false);
					
				}else if(codigoLongitudProducto.length() == 4){

						
						campoCodigoProducto.setBackground(Color.GREEN);
						btnComprobarCodigoProducto.setEnabled(true);
					
				}else {
					campoCodigoProducto.setBackground(new Color(255, 0, 59));
					btnComprobarCodigoProducto.setEnabled(false);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		}

		private class OrdenarRegistro implements LayoutManager {

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
				int contador = 0;
				int anchoContenedor = parent.getWidth();
				int alturaContenedor = parent.getHeight();

				x = anchoContenedor / 2;
				y = alturaContenedor / 8;

				int n = parent.getComponentCount();

				for (int i = 0; i < n; i++) {

					contador++;

					Component c = parent.getComponent(i);
					c.setBounds(x - 200, y, 200, 30);

					x += 200;

					if (contador % 2 == 0) {
						x = anchoContenedor / 2;
						y += alturaContenedor / 8;
					}
				}

			}

		}

	}
}
