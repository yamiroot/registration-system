package codigo.reportes.de.almacen;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.IOException;

import javax.swing.*;

import codigo.transacciones.registro.MovimientosFicheroBinario;

public class IngresoAlmacenFrame extends JFrame {
	
		
	// TODO Auto-generated method stub
	public static void main(String[] args) {
	
		IngresoAlmacenFrame tablaIngresoAlmacen = new IngresoAlmacenFrame();
	}
	
	
	public IngresoAlmacenFrame() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
		
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4, alturaPantalla/4);
		
		Image icono = pantalla.getImage("src/img/listaWhite.png");
		setIconImage(icono);
		
		setTitle("LISTA GENERAL DE PRODUCTOS INGRESADOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new IngresoTablaAlmacen());
		
		setVisible(true);
	}
	
	
	private class IngresoTablaAlmacen extends JPanel{
	
		JTable tablaAlmacen;
		
		private JButton BtnBack;
		private JButton btnImprimir;
		
		private JPanel contenedorBack;
		private JPanel contenedorTabla;
		private JPanel contenedorImprimir;

		private String[] nombreColumnas = {"Codigo almacen", "Descripcion de almacne", "Operacion", "Cantidad ingresada"};
		
		MovimientosFicheroBinario ficheroMovimientoBinario;

		public IngresoTablaAlmacen() {
			ficheroMovimientoBinario = new MovimientosFicheroBinario();
		 	setLayout(new BorderLayout());
			
			contenedorBack = new JPanel();
			contenedorTabla = new JPanel();
			contenedorImprimir = new JPanel();
			
			
			ImageIcon iconoImprimir = new ImageIcon("src/img/print.png");
			Image imgiconoImprimir= iconoImprimir.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoImprimir= new ImageIcon(imgiconoImprimir);
			
			
			ImageIcon iconoBack = new ImageIcon("src/img/arrowBack.png");
			Image imgEscaladaBack= iconoBack.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Icon iconoEscaladoBack= new ImageIcon(imgEscaladaBack);
			
			
			
			try {
				ficheroMovimientoBinario.leerFichero();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//ACA AGREGAMOS LOS EL VECTOR DE MI NOMBRECOLUMNA Y LA MATRIZ DE DATOSFILA
		 	tablaAlmacen = new JTable(ficheroMovimientoBinario.mostrarArrayTableIngreso(),nombreColumnas);
		 	
			btnImprimir= new JButton("Imprimir",iconoEscaladoImprimir );
			
			BtnBack = new JButton("	Ir atras", iconoEscaladoBack);
			
			
			
			 BtnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						
					}
				});
			 
			btnImprimir.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						
						tablaAlmacen.print();
					}catch (PrinterException e2) {
						JOptionPane.showMessageDialog(null, "Error inesperado:\n" +
		                           e2.getMessage(),"¡Error!",
		                           JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			});
			
			btnImprimir.setBackground(new Color(0, 232, 206));
			BtnBack.setBackground(new Color(79, 133, 229));
			
			contenedorBack.setLayout(new BorderLayout());
			contenedorTabla.setLayout(new BorderLayout());
			contenedorImprimir.setLayout(new BorderLayout());
			
			contenedorBack.add(BtnBack);
			
			//JScrollPane para agregarle una barra de desplazamiento vertical
			contenedorTabla.add(new JScrollPane(tablaAlmacen));
			
			contenedorImprimir.add(btnImprimir);
			
			add(contenedorBack, BorderLayout.WEST);
			add(contenedorTabla, BorderLayout.CENTER);
			add(contenedorImprimir, BorderLayout.SOUTH);
		}
	}
}
