package codigo.tabla.proveedores;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class RegistroProveedoresFrame extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RegistroProveedoresFrame newAlmacen = new RegistroProveedoresFrame();

	}

	public RegistroProveedoresFrame() {

		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;

		setSize(anchuraPantalla / 2, alturaPantalla / 2);
		setLocation(anchuraPantalla / 4, alturaPantalla / 4);

		Image icono = pantalla.getImage("src/img/user.png");
		setIconImage(icono);

		setTitle("REGISTAR PROOVEDOR");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		add(new RegistroProovedoresPanel());
		setVisible(true);

	}

	private class RegistroProovedoresPanel extends JPanel {

		private JTextField campoRuc;
		private JTextField campoNombre;

		private JLabel textoRuc;
		private JLabel textoNombre;

		private JButton btnRegistrar;
		private JButton btnComprobarRuc;
		private JButton BtnBack;

		private JPanel contenedorBack;
		private JPanel contenedorFormulario;

		public RegistroProovedoresPanel() {

			setLayout(new BorderLayout());

			contenedorBack = new JPanel();
			contenedorFormulario = new JPanel();

			campoRuc = new JTextField();
			campoNombre = new JTextField();

			ComprobarLongitudRuc receptor = new ComprobarLongitudRuc();
			campoRuc.getDocument().addDocumentListener(receptor);

			textoRuc = new JLabel("RUC de proovedor:");
			textoNombre = new JLabel("Nombre de proovedor:");

			ImageIcon iconoRegistro = new ImageIcon("src/img/save.png");
			Image imgEscaladaRegistro = iconoRegistro.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoRegistro = new ImageIcon(imgEscaladaRegistro);

			ImageIcon iconoCheck = new ImageIcon("src/img/check.png");
			Image imgEscaladaCheck = iconoCheck.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoCheck = new ImageIcon(imgEscaladaCheck);

			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack = iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack = new ImageIcon(imgEscaladaBack);

			btnRegistrar = new JButton("Registrar proovedor", iconoEscaladoRegistro);
			btnComprobarRuc = new JButton("Verificar RUC", iconoEscaladoCheck);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);

			btnRegistrar.setEnabled(false);
			btnComprobarRuc.setEnabled(false);

			verificarRuc oyente = new verificarRuc();
			btnComprobarRuc.addActionListener(oyente);

			btnRegistrar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					ProveedorFicheroBinario miFichero = new ProveedorFicheroBinario();

					String rucPalabra = campoRuc.getText().trim();
					String nombrePalabras = campoNombre.getText().trim();

					Proveedores proveedor = new Proveedores(rucPalabra, nombrePalabras);

					miFichero.aniadirProveedoresArray(proveedor);

					try {
						miFichero.guardarArrayFichero();
						miFichero.leerFichero();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					JOptionPane.showMessageDialog(null,
							"Proovedor agregado:\n" + "Se registro el proovedor de manerea correcta", "Registrado!",
							JOptionPane.INFORMATION_MESSAGE);

					campoRuc.setEditable(false);
					campoNombre.setEditable(false);

					btnRegistrar.setEnabled(false);
					btnRegistrar.setText("Registrado!");
					btnRegistrar.setBackground(null);
					BtnBack.setBackground(Color.YELLOW);
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
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

			contenedorFormulario.add(textoRuc);
			contenedorFormulario.add(campoRuc);
			contenedorFormulario.add(textoNombre);
			contenedorFormulario.add(campoNombre);
			contenedorFormulario.add(btnComprobarRuc);
			contenedorFormulario.add(btnRegistrar);

			contenedorBack.add(BtnBack);
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorFormulario, BorderLayout.CENTER);
		}

		private class verificarRuc implements ActionListener {
			
			ProveedorFicheroBinario fichero=new ProveedorFicheroBinario();
			
			boolean verifica=false;

			@Override
			public void actionPerformed(ActionEvent e) {
				String rucPalabra = campoRuc.getText().trim();

				try {
					if (!fichero.buscarRUC(rucPalabra, verifica)) {

						JOptionPane.showMessageDialog(null, "Codigo disponible:\n" + "Este codigo puede ser usado",
								"Verificado!", JOptionPane.INFORMATION_MESSAGE);

						btnComprobarRuc.setText("RUC Verificado!");
						btnRegistrar.setEnabled(true);
						btnRegistrar.setBackground(new Color(79, 132, 247));
						campoRuc.setEditable(false);
						btnComprobarRuc.setEnabled(false);

					} else {
						JOptionPane.showMessageDialog(null,
								"RUC no valido:\n" + "Es posible que el RUC ya se encuentre en uso", "En uso!",
								JOptionPane.ERROR_MESSAGE);

						campoRuc.setText("");
					}
				} catch (HeadlessException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}

		private class ComprobarLongitudRuc implements DocumentListener {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String rucLongitud = campoRuc.getText().trim();
				if (rucLongitud.length() == 11) {

					campoRuc.setBackground(new Color(25, 255, 121));
					btnComprobarRuc.setEnabled(true);

				} else if (rucLongitud.length() == 0) {

					campoRuc.setBackground(Color.white);
					btnComprobarRuc.setEnabled(false);
				} else {

					campoRuc.setBackground(new Color(255, 0, 59));
					btnComprobarRuc.setEnabled(false);
				}

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String rucLongitud = campoRuc.getText().trim();
				if (rucLongitud.length() == 11) {
					campoRuc.setBackground(new Color(25, 255, 121));
					btnComprobarRuc.setEnabled(true);

				} else if (rucLongitud.length() == 0) {
					campoRuc.setBackground(Color.white);
					btnComprobarRuc.setEnabled(false);
				} else {
					campoRuc.setBackground(new Color(255, 0, 59));
					btnComprobarRuc.setEnabled(false);
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
