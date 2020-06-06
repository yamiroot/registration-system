package codigo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import codigo.reportes.MenuReportesFrame;
import codigo.tabla.MenuTablaFrame;
import codigo.transacciones.MenuTransaccionesFrame;;


public class MenuPrincipalFrame extends JFrame{
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MenuPrincipalFrame MenuPrincipal = new MenuPrincipalFrame();
	}
	
	public MenuPrincipalFrame() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4 , alturaPantalla/4);
		setResizable(false);
		
		setTitle("MENU PRINCIPAL");
		
		Image icono = pantalla.getImage("src/img/listaWhite.png");
		setIconImage(icono);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new OpcionesMenu());
		setVisible(true);
	}
	
	private class OpcionesMenu extends JPanel{
		
		private JButton Btn1;
		private JButton Btn2;
		private JButton Btn3;
		private JButton BtnCerrarSesion;
		
		private JPanel contenedorBtn;
		private JPanel contenedorCerrarSesion;
		
		public OpcionesMenu() {
			
			setLayout(new BorderLayout());
			
			contenedorBtn = new JPanel();
			contenedorCerrarSesion = new JPanel();
			
			ImageIcon iconoMantenimiento = new ImageIcon("src/img/buildWhite.png");
			Image imgEscaladaMantenimiento= iconoMantenimiento.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Icon iconoEscaladoMantenimiento= new ImageIcon(imgEscaladaMantenimiento);
			
			ImageIcon iconoExit = new ImageIcon("src/img/exitBlack.png");
			Image imgEscaladpExit= iconoExit.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoExit= new ImageIcon(imgEscaladpExit);
			
			Btn1 = new JButton("                    Tabla", iconoEscaladoMantenimiento);
			Btn2 = new JButton("             Transacciones", iconoEscaladoMantenimiento);
			Btn3 = new JButton("           Reportes", iconoEscaladoMantenimiento);
			BtnCerrarSesion = new JButton("    Cerrar Sesion", iconoEscaladoExit);
			
			BtnCerrarSesion.setFocusable(false);
			
			Btn1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MenuTablaFrame tabla = new MenuTablaFrame();
					
					
				}
			});
			
			Btn2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MenuTransaccionesFrame enterMenuTranscacciones = new MenuTransaccionesFrame();
					
				}
			});
			
			Btn3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MenuReportesFrame enterMenuTranscacciones = new MenuReportesFrame();
					
				}
			});
			
			BtnCerrarSesion.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
	                   JOptionPane.showMessageDialog(null, "Sesion Finalizada:\n"
	                           + "Gracias por usar el sistema", "Deslogeado!",
	                           JOptionPane.ERROR_MESSAGE);
					System.exit(EXIT_ON_CLOSE);
				}
			});
			
			Btn1.setBackground(new Color(27, 177, 249));
			Btn2.setBackground(new Color(126, 234, 141));
			Btn3.setBackground(new Color(247, 128, 128));
			BtnCerrarSesion.setBackground(Color.RED);
			
			
			
			contenedorBtn.setLayout(new OrdenarMenu() );
			contenedorCerrarSesion.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
			
			
			contenedorBtn.add(Btn1);
			contenedorBtn.add(Btn2);
			contenedorBtn.add(Btn3);
			
			contenedorCerrarSesion.add(BtnCerrarSesion);
			
			add(contenedorBtn, BorderLayout.CENTER);
			add(contenedorCerrarSesion, BorderLayout.NORTH);
			
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


