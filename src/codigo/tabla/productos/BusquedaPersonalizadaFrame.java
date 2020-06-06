package codigo.tabla.productos;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

public class BusquedaPersonalizadaFrame extends JFrame {

	public static void main(String[] args) {

		BusquedaPersonalizadaFrame deleteAlmacen = new BusquedaPersonalizadaFrame();

	}

	public BusquedaPersonalizadaFrame() {

		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;

		setSize(anchuraPantalla / 2, alturaPantalla / 2);
		setLocation(anchuraPantalla / 4, alturaPantalla / 4);

		Image icono = pantalla.getImage("src/img/search.png");
		setIconImage(icono);

		setTitle("BUSQUEDA PERSONALIZADA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		add(new BusquedaPesonalizadaPanel());
		setVisible(true);
	}

	private class BusquedaPesonalizadaPanel extends JPanel {

		private JTextField campoCodigo;
		private JTextField campoDescripcion;

		private JLabel textoCodigo;
		private JLabel textoDescripcion;;

		private JButton bntBuscar;
		private JButton btnCerrarVentana;

		private JPanel contenedorFormularior;
		private JPanel contenedorBack;
		private JPanel contenedorBuscar;

		public BusquedaPesonalizadaPanel() {

			setLayout(new BorderLayout());

			// Creamos nuestros contenedores
			contenedorFormularior = new JPanel();
			contenedorBack = new JPanel();
			contenedorBuscar = new JPanel();

			campoCodigo = new JTextField();
			campoDescripcion = new JTextField();

			ComprobarLongitudCodigo receptor = new ComprobarLongitudCodigo();
			campoCodigo.getDocument().addDocumentListener(receptor);

			ImageIcon iconoBuscar = new ImageIcon("src/img/search.png");
			Image imgEscaladaBuscar = iconoBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBuscar = new ImageIcon(imgEscaladaBuscar);

			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack = iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack = new ImageIcon(imgEscaladaBack);

			ImageIcon iconoUser = new ImageIcon("src/img/userForm.png");
			Image imgEscaladaUser = iconoUser.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoUser = new ImageIcon(imgEscaladaUser);

			ImageIcon iconoCodigo = new ImageIcon("src/img/password.png");
			Image imgEscaladaCodigo = iconoCodigo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoCodigo = new ImageIcon(imgEscaladaCodigo);

			textoCodigo = new JLabel("     Ingrese codigo del producto:", iconoEscaladoCodigo, FlowLayout.LEFT);
			textoDescripcion = new JLabel("     Descripcion del producto:", iconoEscaladoUser, FlowLayout.LEFT);

			bntBuscar = new JButton("Buscar producto", iconoEscaladoBuscar);
			btnCerrarVentana = new JButton("Regresar", iconoEscaladoBack);

			campoDescripcion.setEditable(false);
			bntBuscar.setEnabled(false);

			btnCerrarVentana.setBackground(new Color(79, 133, 229));

			contenedorFormularior.setLayout(new OrdenarRegistro());
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			contenedorBuscar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			contenedorFormularior.add(textoCodigo);
			contenedorFormularior.add(campoCodigo);

			contenedorFormularior.add(textoDescripcion);
			contenedorFormularior.add(campoDescripcion);

			contenedorBack.add(btnCerrarVentana);
			contenedorBuscar.add(bntBuscar);

			BuscarCodigo oyente = new BuscarCodigo();
			bntBuscar.addActionListener(oyente);

			btnCerrarVentana.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();

				}
			});

			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormularior, BorderLayout.CENTER);
			add(contenedorBuscar, BorderLayout.SOUTH);

		}

		private class BuscarCodigo implements ActionListener {

			int codigoNumero;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ProductoFicheroBinario miFichero=new ProductoFicheroBinario();

				String codigoPalabra = campoCodigo.getText().trim();
				String descripcion=campoDescripcion.getText().trim();

				try {

					// Convertimos todo nuestro codigo en tipo int
					codigoNumero = Integer.parseInt(codigoPalabra);
					
					boolean existe=false;

					if (miFichero.buscarCodigo(codigoNumero, existe)) {

						JOptionPane.showMessageDialog(null, "Producto encontrado:\n" + "", "Encontrado!",
								JOptionPane.INFORMATION_MESSAGE);

						// Descactivamos el boton de buscar para evitar posibles errores
						bntBuscar.setText("Producto encontrado!");
						campoDescripcion.setText(miFichero.getDescripcionProducto());
						bntBuscar.setEnabled(false);

						btnCerrarVentana.setEnabled(true);
						btnCerrarVentana.setBackground(new Color(230, 255, 43));
						campoCodigo.setEditable(false);

					} else {

						JOptionPane.showMessageDialog(null,
								"Codigo no encontrado:\n" + "El codigo: " + campoCodigo.getText()
										+ "  , no se encuentra registrado",
								"No encontrado!", JOptionPane.ERROR_MESSAGE);

						campoCodigo.setText("");

					}

				} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(null,
							"Codigo no valido:\n" + ex.getMessage() + ". Intente nuevamente", "�Error!",
							JOptionPane.ERROR_MESSAGE);
					campoCodigo.setText("");
					
				} catch (HeadlessException e1) {
					
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					
					e1.printStackTrace();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}

			}
		}

		// Comprobacion de la longitud del codigo
		private class ComprobarLongitudCodigo implements DocumentListener {

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
					bntBuscar.setEnabled(false);
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
					c.setBounds(x - 300, y, 300, 30);

					x += 300;

					if (contador % 2 == 0) {
						x = anchoContenedor / 2;
						y += alturaContenedor / 8;
					}
				}

			}

		}
	}
}
