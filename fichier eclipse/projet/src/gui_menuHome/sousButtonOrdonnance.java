package gui_menuHome;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import managerEntreFrame.FormListenerWindowAfficher;

public class sousButtonOrdonnance extends JPanel implements ManagerSousButton,ActionListener{
	private static final long serialVersionUID = 1L;

	private FormListenerWindowAfficher formListenerWindowAfficher;
	private JButton ordonnanceRech,ordonnanceMod;
	private int pos;
	private int nbrButt = 2;
	
	public sousButtonOrdonnance(int pos) {
		this.pos = 35*pos +5;
		setBounds(0,this.pos,200,nbrButt*30);
		setLayout(new GridLayout(nbrButt,1));
		setBackground(Color.white);
		
		ordonnanceRech= new JButton();
		ordonnanceRech.setName("ordo_rechercher");
		ordonnanceRech.setBorderPainted(false);
		ordonnanceRech.setContentAreaFilled(false);
		ordonnanceRech.addActionListener(this);
		
		ordonnanceMod= new JButton();
		ordonnanceMod.setName("ordo_modifier");
		ordonnanceMod.setBorderPainted(false);
		ordonnanceMod.setContentAreaFilled(false);
		ordonnanceMod.addActionListener(this);

		makeAllUnSeleckted();
		
		add(ordonnanceRech);
		add(ordonnanceMod);
		
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
	@Override
	public int nextPos() {
		return pos + 35*nbrButt -5;
	}
	public void selectSousButton() {
		ManagerSelectMenu manager = new ManagerSelectMenu(ordonnanceRech,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();  
		manager.selectedOrdonnanceSB();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton select = (JButton)e.getSource();
		ManagerSelectMenu manager = new ManagerSelectMenu(select,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();      
		manager.selectedOrdonnanceSB();
		
		formListenerWindowAfficher.afficherWindow(select.getName());
	}
	@Override
	public void makeAllUnSeleckted() {         // deselectioner tous les sous buttons
		ordonnanceRech.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/rechercher.png")));
		ordonnanceMod.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/modifier.png")));
	}
	
	public void setFormListenerAfficherWindowSousButton(FormListenerWindowAfficher formListenerWindowAfficher) {
		this.formListenerWindowAfficher = formListenerWindowAfficher;
	}
}
