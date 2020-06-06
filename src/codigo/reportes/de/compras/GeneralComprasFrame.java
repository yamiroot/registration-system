package codigo.reportes.de.compras;


import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import codigo.transacciones.registro.OrdenesFicheroBinario;

public class GeneralComprasFrame extends JFrame{
	
	// TODO Auto-generated method stub
	public static void main(String[] args) {
	
		GeneralComprasFrame tablaSalidaAlmacen = new GeneralComprasFrame();
	}
	
	
	public GeneralComprasFrame() {
	//DE ACA 
	Toolkit pantalla = Toolkit.getDefaultToolkit();
	Dimension tamano = pantalla.getScreenSize();
	int alturaPantalla = tamano.height;
	int anchuraPantalla = tamano.width;
	
	setSize(anchuraPantalla/2, alturaPantalla/2);
	setLocation(anchuraPantalla/4, alturaPantalla/4);
	
	Image icono = pantalla.getImage("src/img/listaWhite.png");
	setIconImage(icono);
	
	setTitle("LISTA DE COMPRAS GENERAL");
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	//HASTA LA FILA DE ARRIBA NO ME TOQUES ESTOS YA QUE ES LA VENTANA SU CONFIGURACION
	
	add(new TablaGeneralProovedor());
	
	setVisible(true);
	}
	
	
	private class TablaGeneralProovedor extends JPanel{
	
		JTable tablaGeneralProovedor;
		
		private JButton BtnBack;
		private JButton btnImprimir;
		
		private JPanel contenedorBack;
		private JPanel contenedorTabla;
		private JPanel contenedorImprimir;
		
		//ESTE ARRAY QUEDA YA QUE ES EL NOMBRE DE COLUMNAS
		private String[] nombreColumnas = {"#Orden", "#RUC" ,"#Producto","Cantidad", "P/U (S/,)", "Total(S/.)"};
		
		OrdenesFicheroBinario ficheroOrdenes = new OrdenesFicheroBinario();
		
		public TablaGeneralProovedor() {
		 	
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
				ficheroOrdenes.leerFichero();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//ACA AGREGAMOS LOS EL VECTOR DE MI NOMBRECOLUMNA Y LA MATRIZ DE DATOSFILA
		 	tablaGeneralProovedor = new JTable(ficheroOrdenes.mostrarArrayTable(),nombreColumnas);
		 	
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
						
						tablaGeneralProovedor.print();
					}catch (PrinterException e2) {
						JOptionPane.showMessageDialog(null, "Error inesperado:\n" +
		                           e2.getMessage(),"¡Error!",
		                           JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			});
			
			btnImprimir.setBackground(new Color(0, 232, 206));
			BtnBack.setBackground(new Color(79, 133, 229));
			
			contenedorBack.setLayout(new BorderLayout(0,0));
			contenedorTabla.setLayout(new BorderLayout(0,0));
			contenedorImprimir.setLayout(new FlowLayout(FlowLayout.CENTER));
		
			contenedorBack.add(BtnBack);
			
			//JScrollPane para agregarle una barra de desplazamiento vertical
			contenedorTabla.add(new JScrollPane(tablaGeneralProovedor));
			
			contenedorImprimir.add(btnImprimir);
			
			add(contenedorBack, BorderLayout.WEST);
			add(contenedorTabla, BorderLayout.CENTER);
			add(contenedorImprimir, BorderLayout.SOUTH);
		}
	}
}
