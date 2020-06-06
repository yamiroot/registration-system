package codigo.tabla.almacen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuMantenimientoAlmacenFrame extends JFrame {

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			MenuMantenimientoAlmacenFrame MenuPrincipal = new MenuMantenimientoAlmacenFrame();
		}
		
		public MenuMantenimientoAlmacenFrame() {
			
			Toolkit pantalla = Toolkit.getDefaultToolkit();
			Dimension tamano = pantalla.getScreenSize();
			int alturaPantalla = tamano.height;
			int anchuraPantalla = tamano.width;
			setSize(anchuraPantalla/2, alturaPantalla/2);
			setLocation(anchuraPantalla/4, alturaPantalla/4);
			setResizable(false);
			
			Image icono = pantalla.getImage("src/img/buildWhite.png");
			setIconImage(icono);
			
			setTitle("MANTENIMIENTO DE ALMACENES");
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			add(new OpcionesMenu());
			setVisible(true);
		}
		
		private class OpcionesMenu extends JPanel{
			
			AlmacenFicheroBinario fichero=new AlmacenFicheroBinario();
			
			private JButton Btn1;
			private JButton Btn2;
			private JButton Btn3;
			private JButton BtnBack;
			
			private JPanel contenedorBack;
			private JPanel contenedorBtn;
			
			public OpcionesMenu() {
				
				setLayout(new BorderLayout());
				contenedorBack = new JPanel();
				contenedorBtn = new JPanel();
				
				ImageIcon iconoAdd = new ImageIcon("src/img/addBlack.png");
				Image imgEscaladaAdd= iconoAdd.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				Icon iconoEscaladoAdd= new ImageIcon(imgEscaladaAdd);
				
				
				ImageIcon iconoRest = new ImageIcon("src/img/deleteBlack.png");
				Image imgEscaladaRest= iconoRest.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				Icon iconoEscaladoRest= new ImageIcon(imgEscaladaRest);
				
				
				ImageIcon iconoView = new ImageIcon("src/img/viewBlack.png");
				Image imgEscaladaView= iconoView.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				Icon iconoEscaladoView= new ImageIcon(imgEscaladaView);
				
				ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
				Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
				
				Btn1 = new JButton("Registro de almacenes", iconoEscaladoAdd);
				Btn2 = new JButton("Eliminar almacenes", iconoEscaladoRest);
				Btn3 = new JButton("Imprimir almacenes",iconoEscaladoView );
				BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
				
				Btn1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						RegistroAlmacenesFrame registroAlmacen = new RegistroAlmacenesFrame();
						
					}
				});
				
				Btn2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						EliminarAlmacenFrame eliminarAlmacen = new EliminarAlmacenFrame();
						
					}
				});
				
				Btn3.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ImprimirAlmacenFrame almacenFrame = new ImprimirAlmacenFrame();
						
					}
				});
				
				BtnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
				
				Btn1.setBackground(new Color(0, 255, 4));
				Btn2.setBackground(new Color(255, 71, 92));
				Btn3.setBackground(new Color(27, 177, 249));
				BtnBack.setBackground(new Color(79, 133, 229));
				
				contenedorBtn.setLayout(new OrdenarMenu());
				contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				
				contenedorBtn.add(Btn1);
				contenedorBtn.add(Btn2);
				contenedorBtn.add(Btn3);
				contenedorBack.add(BtnBack);
				
				add(contenedorBack, BorderLayout.NORTH);
				add(contenedorBtn, BorderLayout.CENTER);
				
			}
		}

		private class OrdenarMenu implements LayoutManager{

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
				
				x = anchoContenedor/2;
				y = alturaContenedor/4;
				
				//Nos indica el numero de componentes en el panel
				int n = parent.getComponentCount();
				
				for(int i=0;i<n;i++) {
					
					contador=0;
					contador++;
					
					//Nos devuelve el ordinal de cada componente
					Component c = parent.getComponent(i);
					c.setBounds(x-150,y-50,300,100);
					
					x+=300;
					
					if(contador==1) {
						x=anchoContenedor/2;
						y+=alturaContenedor/4;
					}
				}
				
			}
			
		}
	}