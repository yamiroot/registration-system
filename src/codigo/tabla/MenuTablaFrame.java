package codigo.tabla;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import codigo.tabla.almacen.*;
import codigo.tabla.productos.MenuMantenimientoProductosFrame;
import codigo.tabla.proveedores.*;



public class MenuTablaFrame extends JFrame{
	
	public static void main(String[] args) {
			// TODO Auto-generated method stub
			MenuTablaFrame MenuTabla = new MenuTablaFrame();
		}
		
	public MenuTablaFrame() {
			
			Toolkit pantalla = Toolkit.getDefaultToolkit();
			Dimension tamano = pantalla.getScreenSize();
			int alturaPantalla = tamano.height;
			int anchuraPantalla = tamano.width;
			setSize(anchuraPantalla/2, alturaPantalla/2);
			setLocation(anchuraPantalla/4, alturaPantalla/4);
			
			Image icono = pantalla.getImage("src/img/listaWhite.png");
			setIconImage(icono);
			
			setResizable(false);
			setTitle("MENU DE TABLAS");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			add(new OpcionesMenuTable());
			setVisible(true);
			
		}
	
class OpcionesMenuTable extends JPanel{
		
		private JButton Btn1;
		private JButton Btn2;
		private JButton Btn3;
		private JButton BtnBack;
		private JPanel contenedorBack;
		private JPanel contenedorBtn;
		
		public OpcionesMenuTable() {
			
			setLayout(new BorderLayout());
			
			contenedorBack = new JPanel();
			contenedorBtn = new JPanel();
			
			ImageIcon iconoMantenimiento = new ImageIcon("src/img/buildWhite.png");
			Image imgEscaladaMantenimiento= iconoMantenimiento.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Icon iconoEscaladoMantenimiento= new ImageIcon(imgEscaladaMantenimiento);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			Btn1 = new JButton("    Mantenimiento de almacenes", iconoEscaladoMantenimiento);
			Btn2 = new JButton("	Mantenimiento de proveedores", iconoEscaladoMantenimiento);
			Btn3 = new JButton("	Mantenimiento de productos", iconoEscaladoMantenimiento);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			
			

			
			Btn1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MenuMantenimientoAlmacenFrame newRegistroAlmacen = new MenuMantenimientoAlmacenFrame();
					
				}
			});
			Btn2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MenuMantenimientoProveedoresFrame newRegistroProovedor = new MenuMantenimientoProveedoresFrame();
					
				}
			});
			
			Btn3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MenuMantenimientoProductosFrame newRegistroProducto = new MenuMantenimientoProductosFrame();
					
				}
			});
			
			BtnBack.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					
				}
			});
			
			Btn1.setBackground(new Color(249, 187, 104));
			Btn2.setBackground(new Color(77, 225, 242));
			Btn3.setBackground(new Color(79, 133, 229));
			BtnBack.setBackground(new Color(79, 133, 229));
			
			contenedorBtn.setLayout(new OrdenarMenuTable());
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			
			contenedorBtn.add(Btn1);
			contenedorBtn.add(Btn2);
			contenedorBtn.add(Btn3);
			contenedorBack.add(BtnBack);
			
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorBtn, BorderLayout.CENTER);
			
		}
		

		
		private class OrdenarMenuTable implements LayoutManager{
	
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
		
}
	

