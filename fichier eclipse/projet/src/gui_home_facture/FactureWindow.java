package gui_home_facture;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui_home_ordonnance.OrdonnanceWindow;
import managerEntreFrame.ManagerWindow;

public class FactureWindow extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;

	private JLabel img;
	
	public FactureWindow() {
		img = new JLabel();
		img.setIcon(new ImageIcon(OrdonnanceWindow.class.getResource("/ressourcesEmptyWindow/FactureEmpty.png")));
		
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
