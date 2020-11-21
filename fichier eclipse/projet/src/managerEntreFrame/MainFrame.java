package managerEntreFrame;

import javax.swing.SwingUtilities;

import gui_login.LoginFrame;

public class MainFrame {
	public static LoginFrame l;
	
	public static void main(String []args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				l = new LoginFrame();
			}
		});
		
	}
	
}


