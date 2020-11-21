package gui_menuHome;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import managerEntreFrame.FormListenerWindowAfficher;

public class sousButtonsPrenez extends JPanel implements ManagerSousButton,ActionListener{
	private static final long serialVersionUID = 1L;

	private FormListenerWindowAfficher formListenerWindowAfficher;
	private JButton prenezRech;
	private JButton prenezAjt;
	private JButton prenezModifier;
	private JButton prenezAnnuler;
	
	private int pos;
	private int nbrButt = 4;
	
	public sousButtonsPrenez(int pos) {
		this.pos = 35*pos +5;
		setBounds(0,this.pos,200,nbrButt*30);
		setLayout(new GridLayout(nbrButt,1));
		setBackground(Color.white);
		
		prenezRech = new JButton();
		prenezRech.setName("pren_rechercher");
		prenezRech.setBorderPainted(false);
		prenezRech.setContentAreaFilled(false);
		prenezRech.addActionListener(this);
		
		prenezAjt = new JButton();
		prenezAjt.setName("pren_ajouter");
		prenezAjt.setBorderPainted(false);
		prenezAjt.setContentAreaFilled(false);
		prenezAjt.addActionListener(this);
		
		prenezModifier = new JButton();
		prenezModifier.setName("pren_modifier");
		prenezModifier.setBorderPainted(false);
		prenezModifier.setContentAreaFilled(false);
		prenezModifier.addActionListener(this);
		
		prenezAnnuler = new JButton();
		prenezAnnuler.setName("pren_annuler");
		prenezAnnuler.setBorderPainted(false);
		prenezAnnuler.setContentAreaFilled(false);
		prenezAnnuler.addActionListener(this);

		makeAllUnSeleckted();
		
		add(prenezRech);
		add(prenezAjt);
		add(prenezModifier);
		add(prenezAnnuler);
		
		setVisible(false);
	}
	@Override
	public void activerSousButton() {
		setVisible(true);
	}
	@Override
	public void desactiverSousButton() {
		makeAllUnSeleckted();
		setVisible(false);
	}
	public void selectSousButton() {
		ManagerSelectMenu manager = new ManagerSelectMenu(prenezRech,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();  
		manager.selectedPrenezSB();
	}
	@Override
	public int nextPos() {
		return pos + 35*nbrButt -5;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton select = (JButton)e.getSource();
		ManagerSelectMenu manager = new ManagerSelectMenu(select,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();     
		manager.selectedPrenezSB();
		
		formListenerWindowAfficher.afficherWindow(select.getName());
		
	}
	
	@Override
	public void makeAllUnSeleckted() {         // deselectioner tous les sous buttons
		prenezRech.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/rechercher.png")));
		prenezAjt.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/ajouter.png")));
		prenezModifier.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/modifier.png")));
		prenezAnnuler.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/annuler.png")));
	}
	
	public void setFormListenerAfficherWindowSousButton(FormListenerWindowAfficher formListenerWindowAfficher) {
		this.formListenerWindowAfficher = formListenerWindowAfficher;
	}
}

