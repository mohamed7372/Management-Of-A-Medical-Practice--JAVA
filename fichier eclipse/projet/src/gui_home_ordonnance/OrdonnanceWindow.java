package gui_home_ordonnance;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import managerEntreFrame.ManagerWindow;
import javax.swing.ImageIcon;

public class OrdonnanceWindow extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;

	private JLabel img;
	
	public OrdonnanceWindow() {
		img = new JLabel();
		img.setIcon(new ImageIcon(OrdonnanceWindow.class.getResource("/ressourcesEmptyWindow/OrdonnanceEmpty.png")));
		
		setLayout(new BorderLayout());
		add(img,BorderLayout.CENTER);
		setVisible(false);
	}
	
	@Override
	public void activerWindow() {
		setVisible(true);
	}

	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
}
