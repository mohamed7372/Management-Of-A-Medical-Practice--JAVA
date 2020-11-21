package gui_menuHome;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import managerEntreFrame.FormListenerWindowAfficher;

public class sousButtonFacture extends JPanel implements ManagerSousButton,ActionListener{
	private static final long serialVersionUID = 1L;

	private FormListenerWindowAfficher formListenerWindowAfficher;
	private JButton factureRech;
	private JButton factureMod;
	private int pos;
	private int nbrButt = 2;
	
	public sousButtonFacture(int pos) {
		this.pos = 35*pos +5;
		setBounds(0,this.pos,200,nbrButt*30);
		setLayout(new GridLayout(nbrButt,1));
		setBackground(Color.white);
		
		factureRech = new JButton();
		factureRech.setName("fact_rechercher");
		factureRech.setBorderPainted(false);
		factureRech.setContentAreaFilled(false);
		factureRech.addActionListener(this);
		
		factureMod = new JButton();
		factureMod.setName("fact_modifier");
		factureMod.setBorderPainted(false);
		factureMod.setContentAreaFilled(false);
		factureMod.addActionListener(this);
		
		makeAllUnSeleckted();
		
		add(factureRech);
		add(factureMod);
		
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
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton select = (JButton)e.getSource();
		ManagerSelectMenu manager = new ManagerSelectMenu(select,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();  
		manager.selectedFactureSB();
		
		formListenerWindowAfficher.afficherWindow(select.getName());
	}
	public void selectSousButton() {
		ManagerSelectMenu manager = new ManagerSelectMenu(factureRech,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();  
		manager.selectedFactureSB();
	}
	@Override
	public void makeAllUnSeleckted() {         // deselectioner tous les sous buttons
		factureRech.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/rechercher.png")));
		factureMod.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/modifier.png")));
	}
	
	public void setFormListenerAfficherWindowSousButton(FormListenerWindowAfficher formListenerWindowAfficher) {
		this.formListenerWindowAfficher = formListenerWindowAfficher;
	}
}
