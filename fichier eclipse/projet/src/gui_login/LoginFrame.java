package gui_login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class LoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JLabel background;
	private FormLogIn signIn;
	
	public LoginFrame() {
		setTitle("Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/resources/logoApp.png")));
		
		// background image
		background = new JLabel();
		background.setIcon(new ImageIcon(LoginFrame.class.getResource("/resources/background3.png")));
		
		// la partie desgin de connecter
		signIn = new FormLogIn();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(background,BorderLayout.LINE_START);
		getContentPane().add(signIn,BorderLayout.CENTER);
		
		// forme de window
		setSize(1000,600);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
	}
}
