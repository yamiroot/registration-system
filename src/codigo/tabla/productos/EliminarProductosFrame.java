package codigo.tabla.productos;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

public class EliminarProductosFrame extends JFrame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EliminarProductosFrame deleteProovedor = new EliminarProductosFrame();
	}

	public EliminarProductosFrame() {

		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;

		setSize(anchuraPantalla / 2, alturaPantalla / 2);
		setLocation(anchuraPantalla / 4, alturaPantalla / 4);

		Image icono = pantalla.getImage("src/img/deleteWhite.png");
		setIconImage(icono);

		setTitle("ELIMINAR PRODUCTO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		add(new EliminarProductoPanel());
		setVisible(true);
		
	}

	private class EliminarProductoPanel extends JPanel {

		private JTextField campoCodigo;
		private JTextField campoDescripcion;

		private JLabel textoCodigo;
		private JLabel textoDescripcion;

		private JButton bntBuscar;
		private JButton btnEliminar;
		private JButton BtnBack;

		private JPanel contenedorBack;
		private JPanel contenedorFormulario;

		public EliminarProductoPanel() {

			setLayout(new BorderLayout());

			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();

			campoCodigo = new JTextField();
			campoDescripcion = new JTextField();

			ComprobarLongitudRuc receptor = new ComprobarLongitudRuc();
			campoCodigo.getDocument().addDocumentListener(receptor);

			textoCodigo = new JLabel("Ingrese el codigo de producto:");
			textoDescripcion = new JLabel("Descripcion del producto:");

			ImageIcon iconoBuscar = new ImageIcon("src/img/search.png");
			Image imgEscaladaBuscar = iconoBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBuscar = new ImageIcon(imgEscaladaBuscar);

			ImageIcon iconoEliminar = new ImageIcon("src/img/delete.png");
			Image imgEscaladaEliminar = iconoEliminar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoEliminar = new ImageIcon(imgEscaladaEliminar);

			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack = iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack = new ImageIcon(imgEscaladaBack);

			bntBuscar = new JButton("Buscar codigo", iconoEscaladoBuscar);
			btnEliminar = new JButton("Eiminar producto", iconoEscaladoEliminar);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			BtnBack.setBackground(new Color(79, 133, 229));

			campoDescripcion.setEditable(false);
			btnEliminar.setEnabled(false);
			bntBuscar.setEnabled(false);

			BuscarCodigo oyente = new BuscarCodigo();
			bntBuscar.addActionListener(oyente);

			btnEliminar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					ProductoFicheroBinario miFichero=new ProductoFicheroBinario();
					
					String codigoPalabra = campoCodigo.getText().trim();
					
					int codigoNum=Integer.parseInt(codigoPalabra);
					
					try {
						miFichero.buscarCodigoEliminar(codigoNum);
						miFichero.reescribirFichero();
						miFichero.leerFichero();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					campoCodigo.setText("");
					campoDescripcion.setText("");

					JOptionPane.showMessageDialog(null, "Producto eliminado:\n" + "", "Eliminado!",
							JOptionPane.INFORMATION_MESSAGE);

					btnEliminar.setText("Producto Eliminado!");
					btnEliminar.setBackground(null);
					btnEliminar.setEnabled(false);
					campoCodigo.setEnabled(false);
					BtnBack.setBackground(Color.YELLOW);

				}
			});

			BtnBack.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();

				}
			});

			contenedorFormulario.setLayout(new OrdenarRegistro());
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

			contenedorFormulario.add(textoCodigo);
			contenedorFormulario.add(campoCodigo);
			contenedorFormulario.add(textoDescripcion);
			contenedorFormulario.add(campoDescripcion);
			;
			contenedorFormulario.add(bntBuscar);
			contenedorFormulario.add(btnEliminar);
			contenedorBack.add(BtnBack);

			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormulario, BorderLayout.CENTER);

		}

		private class BuscarCodigo implements ActionListener {

			int codigoNumero;

			@Override
			public void actionPerformed(ActionEvent e) {

				String codigoPalabra = campoCodigo.getText().trim();

				try {
	
						// Convertimos todo nuestro codigo en tipo int
						codigoNumero = Integer.parseInt(codigoPalabra);
						boolean existe=false;
						
						ProductoFicheroBinario miFichero=new ProductoFicheroBinario();
						try {
							
							if (miFichero.buscarCodigo(codigoNumero, existe)) {
		
								JOptionPane.showMessageDialog(null, "Mensaje del sistema:\n"
										+ "Codigo encontrado", "Encontrado!",
										JOptionPane.INFORMATION_MESSAGE);
		
								// Descactivamos el boton de buscar para evitar posibles errores
								campoDescripcion.setText(miFichero.getDescripcionProducto());
								bntBuscar.setText("Codigo encontrado!");
								btnEliminar.setEnabled(true);
								btnEliminar.setBackground(new Color(255, 112, 112));
								bntBuscar.setEnabled(false);
							}else {
	
									JOptionPane.showMessageDialog(null,
											"Codigo no encontrado:\n" + "El codigo: " + campoCodigo.getText()
													+ "  , no se encuentra registrado",
											"No encontrado!", JOptionPane.ERROR_MESSAGE);
	
									campoCodigo.setText("");
	
							}
							
						}catch (IOException e2) {
							e2.printStackTrace();
						}

					} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(null,
							"Codigo no valido:\n" + ex.getMessage() + ". Intente nuevamente", "Error!",
							JOptionPane.ERROR_MESSAGE);
					campoCodigo.setText("");
				}
			}
		}

		// Comprobacion de la longitud del codigo
		private class ComprobarLongitudRuc implements DocumentListener {

			@Override
			public void insertUpdate(DocumentEvent e) {

				String codigoLongitud = campoCodigo.getText().trim();

				if (codigoLongitud.length() == 4) {
					campoCodigo.setBackground(new Color(25, 255, 121));
					bntBuscar.setEnabled(true);

				} else if (codigoLongitud.length() == 0) {
					campoCodigo.setBackground(Color.white);
					bntBuscar.setEnabled(false);
				} else {
					campoCodigo.setBackground(new Color(255, 0, 59));

				}

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String codigoLongitud = campoCodigo.getText().trim();

				if (codigoLongitud.length() == 4) {
					campoCodigo.setBackground(new Color(25, 255, 121));
					bntBuscar.setEnabled(true);

				} else if (codigoLongitud.length() == 0) {
					campoCodigo.setBackground(Color.white);
					bntBuscar.setEnabled(false);
				} else {
					campoCodigo.setBackground(new Color(255, 0, 59));
					bntBuscar.setEnabled(false);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		}

		// Layout Personalziado para el Frame
		private class OrdenarRegistro implements LayoutManager {

			private int x;
			private int y;

			@Override
			public void addLayoutComponent(String name, Component comp) {}

			@Override
			public void removeLayoutComponent(Component comp) {}

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
