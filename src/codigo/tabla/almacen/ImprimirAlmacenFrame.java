package codigo.tabla.almacen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




public class ImprimirAlmacenFrame extends JFrame {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImprimirAlmacenFrame deleteAlmacen = new ImprimirAlmacenFrame();

	}

	
	public ImprimirAlmacenFrame() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4, alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/print.png");
		setIconImage(icono);
		setResizable(false);
		setTitle("Imprimir almacen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(new ImprimirAlmacenPanel());
		setVisible(true);
	}
	
	private class ImprimirAlmacenPanel extends JPanel {
		
		JButton btnBusquedaPersonalizada;
		JButton btnBusquedaTotal;
		private JButton BtnBack;
		
		private JPanel contenedorBack;
		private JPanel contenedorBtn;
		
		public ImprimirAlmacenPanel() {
			
			setLayout(new BorderLayout());
			contenedorBack = new JPanel();
			contenedorBtn = new JPanel();
			
			ImageIcon iconoBuscar = new ImageIcon("src/img/search.png");
			Image imgEscaladaBuscar= iconoBuscar.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBuscar= new ImageIcon(imgEscaladaBuscar);
			
			ImageIcon iconoLista = new ImageIcon("src/img/list.png");
			Image imgEscaladaLista= iconoLista.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Icon iconoEscaladoLista = new ImageIcon(imgEscaladaLista);
						
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			
			btnBusquedaPersonalizada = new JButton("Busqueda Personalizada", iconoEscaladoBuscar);
			btnBusquedaTotal = new JButton("Mostrar Tabla de Almacenes", iconoEscaladoLista);
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			
			btnBusquedaPersonalizada.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					BusquedaPersonalizadaFrame irBusqueda = new BusquedaPersonalizadaFrame();
					
				}
			});
			
			
			btnBusquedaTotal.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MostrarTablaGeneral tablaGeneral = new MostrarTablaGeneral();			
				}
			});
			
			
			BtnBack.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					
				}
			});
			
			
			btnBusquedaPersonalizada.setBackground(new Color(230, 255, 43));
			btnBusquedaTotal.setBackground(new Color(0, 255, 72));
			BtnBack.setBackground(new Color(79, 133, 229));
			
			contenedorBtn.setLayout(new OrdenarMenu());
			contenedorBack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			
			contenedorBtn.add(btnBusquedaPersonalizada);
			contenedorBtn.add(btnBusquedaTotal);
			contenedorBack.add(BtnBack);
			
			add(contenedorBack, BorderLayout.NORTH);
			add(contenedorBtn, BorderLayout.CENTER);
		}
		
		//Layout Personalziado para el Frame
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
				
				x= anchoContenedor/2;
				y = alturaContenedor/4;
				
				int n= parent.getComponentCount();
				
				for(int i=0;i<n;i++) {
					
					contador++;
					
					Component c = parent.getComponent(i);
					c.setBounds(x-150,y,300,80);
					
					y+= alturaContenedor/3;
					
					if(contador==1) {
						x = anchoContenedor/2;
					}
				}
				
			}
			
		}
	}
}		
		
