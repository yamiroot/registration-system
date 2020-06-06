package codigo.transacciones;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import codigo.*;
import codigo.transacciones.registro.*;

public class MenuTransaccionesFrame extends JFrame {
	
	
	public static void main(String[] args) {
			// TODO Auto-generated method stub
			MenuTransaccionesFrame MenuTabla = new MenuTransaccionesFrame();
		}
		
	
	public MenuTransaccionesFrame() {
			
			Toolkit pantalla = Toolkit.getDefaultToolkit();
			Dimension tamano = pantalla.getScreenSize();
			int alturaPantalla = tamano.height;
			int anchuraPantalla = tamano.width;
			setSize(anchuraPantalla/2, alturaPantalla/2);
			setLocation(anchuraPantalla/4, alturaPantalla/4);
			
			Image icono = pantalla.getImage("src/img/listaWhite.png");
			setIconImage(icono);
			
			setResizable(false);
			setTitle("MENU DE TRANSACCIONES");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			add(new OpcionesMenuTable());
			setVisible(true);
			
		}
	
	private class OpcionesMenuTable extends JPanel{
		
		private JButton Btn1;
		private JButton Btn2;
		private JButton BtnBack;
		
		private JPanel contenedorBack;
		private JPanel contenedorBtn;
		
		public OpcionesMenuTable() {
			
			setLayout(new BorderLayout());
			
			contenedorBack = new JPanel();
			contenedorBtn = new JPanel();
			
			
			ImageIcon iconoMantenimiento = new ImageIcon("src/img/addShopBlack.png");
			Image imgEscaladaMantenimiento= iconoMantenimiento.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Icon iconoEscaladoMantenimiento= new ImageIcon(imgEscaladaMantenimiento);
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			Btn1 = new JButton("    	Registro de ordenes", iconoEscaladoMantenimiento);
			Btn2 = new JButton("	    Registro de movimientos", iconoEscaladoMantenimiento);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			
			Btn1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					RegistroOrdenesFrame newRegistroOrden = new RegistroOrdenesFrame();
					
				}
			});
			
			Btn2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					RegistroMovimientosFrame newRegistroMovimiento = new RegistroMovimientosFrame();
					
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
			BtnBack.setBackground(new Color(79, 133, 229));
			
			contenedorBtn.setLayout(new OrdenarMenuTable());
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			
			contenedorBtn.add(Btn1);
			contenedorBtn.add(Btn2);
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
					
					y=300;
					
					if(contador==1) {
						x=anchoContenedor/2;;
					}
				}
				
			}
		}
	}
}		
	
