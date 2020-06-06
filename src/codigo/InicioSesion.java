package codigo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class InicioSesion extends JFrame{

	   public static void main( String args[] )
	   {
		   InicioSesion login = new InicioSesion();
	   }

	
	
	public InicioSesion() {

		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int alturaPantalla = tamano.height;
		int anchuraPantalla = tamano.width;
			
		setSize(anchuraPantalla/2, alturaPantalla/2);
		setLocation(anchuraPantalla/4,alturaPantalla/4);

		setResizable(false);
		setTitle("Iniciar Sesion");
		
		Image icono = pantalla.getImage("src/img/user.png");
		setIconImage(icono);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		LoginPanel LogearAdministrador = new LoginPanel();
		add(LogearAdministrador);
	
		setVisible(true);
		
	}
	
	private class LoginPanel extends JPanel{

		private JTextField CampoUsuario;
		private JPasswordField CampoPassword;
		
		private JLabel TextoUsuario;
		private JLabel TextoPassword;
		
		private JButton BtnIngresar;
		
		private JPanel contenedorFormularioCentrado;
		private JPanel contenedorBtnLogin;
				
		
		public LoginPanel() {
			setLayout(new BorderLayout());
			
			contenedorFormularioCentrado = new JPanel();
			contenedorBtnLogin = new JPanel();
			
			CampoUsuario = new JTextField();
			CampoPassword = new JPasswordField();
			
			ComprobarLongitud receptor = new ComprobarLongitud();
			CampoPassword.getDocument().addDocumentListener(receptor);
			
			ImageIcon iconoUser = new ImageIcon("src/img/user.png");
			Image imgEscaladaUser = iconoUser.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoUser = new ImageIcon(imgEscaladaUser);
			
			ImageIcon iconoPass = new ImageIcon("src/img/password.png");
			Image imgEscaladaPass = iconoPass.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Icon iconoEscaladoPass = new ImageIcon(imgEscaladaPass);
			
			ImageIcon iconoEnter = new ImageIcon("src/img/unlockBlack.png");
			Image imgEscaladaEnter = iconoEnter.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Icon iconoEscaladoEnter = new ImageIcon(imgEscaladaEnter);
			
			BtnIngresar = new JButton("Ingresar", iconoEscaladoEnter);
			
			
			TextoUsuario = new JLabel("Usuario                  :",iconoEscaladoUser ,JLabel.LEFT );
			TextoPassword =new JLabel("Contraseña           : ", iconoEscaladoPass,JLabel.LEFT);
			
			contenedorFormularioCentrado.setLayout(new OrdenarLogin());
			contenedorBtnLogin.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
			
			contenedorFormularioCentrado.add(TextoUsuario);
			contenedorFormularioCentrado.add(CampoUsuario);
			contenedorFormularioCentrado.add(TextoPassword);
			contenedorFormularioCentrado.add(CampoPassword);
			
			
			contenedorBtnLogin.add(BtnIngresar);
			
			add(contenedorFormularioCentrado, BorderLayout.CENTER);
			add(contenedorBtnLogin, BorderLayout.SOUTH);
			
			ComprobarIngreso comprobar = new ComprobarIngreso();
			BtnIngresar.addActionListener(comprobar);
			
			

			
		}
		
		private class ComprobarIngreso implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
			
				
				String usuario = CampoUsuario.getText().trim();
				
				char[] password = CampoPassword.getPassword();
				String passwordEqual = String.valueOf(password);
				int passED;
				
				if(usuario.equals("admin")){
					
					if(passwordEqual.equals("123456")) {
						
						passED = Integer.parseInt(passwordEqual);
						
	                    CampoUsuario.setText("");
	                    CampoPassword.setText("");
	                    JOptionPane.showMessageDialog(null, "Accediendo al sistema\n"
	                    + "", "Acceso Otorgado",
	                    JOptionPane.INFORMATION_MESSAGE);
	                   
	                    MenuPrincipalFrame Menu = new MenuPrincipalFrame();
	                    
	                    dispose();
	                    
	                    
					}
					
					else {
	                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta:\n"
	                    + "Por favor ingrese correctamente la contraseña administrativa", "Acceso denegado",
	                    JOptionPane.ERROR_MESSAGE);
		                CampoPassword.setText("");
		                
					}
					
				}else {
	                JOptionPane.showMessageDialog(null, "Usuario incorrecto:\n"
	                + "Por favor ingrese el usuario administrativo", "Acceso denegado",
	                JOptionPane.ERROR_MESSAGE);
	                CampoPassword.setText("");
	                CampoUsuario.setText("");
				}
			}
			
		}
		
		private class ComprobarLongitud implements DocumentListener{

			@Override
			public void insertUpdate(DocumentEvent e) {
				char[] contrasenia = CampoPassword.getPassword(); 
				
				if(contrasenia.length >= 3 && contrasenia.length <=6) {
					CampoPassword.setBackground(Color.GREEN);
				}
				else if(contrasenia.length == 0){
					CampoPassword.setBackground(Color.WHITE);
					
				}else {
					CampoPassword.setBackground(Color.RED);
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				char[] contrasenia = CampoPassword.getPassword(); 
				
				if(contrasenia.length >= 1 && contrasenia.length <=6) {
					CampoPassword.setBackground(Color.GREEN);
					
				}else if(contrasenia.length == 0){
						CampoPassword.setBackground(Color.WHITE);
						
				}else {
					CampoPassword.setBackground(Color.RED);
				}
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				
				
			}
			
		}
	
		private class OrdenarLogin implements LayoutManager{
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
				
				//Almacenamos el numero de componentes del panel
				int n  = parent.getComponentCount();
				
				for(int i =0;i<n;i++) {
					contador++;
					
					//Almacenamos el ordinal de cada componente
					Component c = parent.getComponent(i);
					c.setBounds(x-200,y,200,40);
					
					x+=200;
					
					if(contador%2==0) {
						x=anchoContenedor/2;
						y+= alturaContenedor/4;
					}
				}
				
			}
		}	
		
		
	}
}


