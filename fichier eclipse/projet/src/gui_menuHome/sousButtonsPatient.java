package gui_menuHome;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import managerEntreFrame.FormListenerWindowAfficher;

public class sousButtonsPatient extends JPanel implements ManagerSousButton,ActionListener{
	private static final long serialVersionUID = 1L;

	private FormListenerWindowAfficher formListenerWindowAfficher;
	private JButton patientRech;
	private JButton patientAjt;
	private JButton patientModifier;
	private JButton patientSupp;
	private int pos;
	private int nbrButt = 4;
	
	public sousButtonsPatient(int pos) {
		this.pos = 35*pos +5;
		setBounds(0,this.pos,200,nbrButt*30);
		setLayout(new GridLayout(nbrButt,1));
		setBackground(Color.white);
		
		patientRech = new JButton();
		patientRech.setName("pati_rechercher");
		patientRech.setBorderPainted(false);
		patientRech.setContentAreaFilled(false);
		patientRech.addActionListener(this);
		
		patientAjt = new JButton();
		patientAjt.setName("pati_ajouter");
		patientAjt.setBorderPainted(false);
		patientAjt.setContentAreaFilled(false);
		patientAjt.addActionListener(this);
		
		patientModifier = new JButton();
		patientModifier.setName("pati_modifier");
		patientModifier.setBorderPainted(false);
		patientModifier.setContentAreaFilled(false);
		patientModifier.addActionListener(this);
		
		patientSupp = new JButton();
		patientSupp.setName("pati_supprimer");
		patientSupp.setBorderPainted(false);
		patientSupp.setContentAreaFilled(false);
		patientSupp.addActionListener(this);

		makeAllUnSeleckted();
		
		add(patientRech);
		add(patientAjt);
		add(patientModifier);
		add(patientSupp);
		
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
		manager.selectedPatientSB(); 
		
		formListenerWindowAfficher.afficherWindow(select.getName());
	}
	public void selectSousButton() {
		ManagerSelectMenu manager = new ManagerSelectMenu(patientRech,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();  
		manager.selectedPatientSB();
	}
	@Override
	public void makeAllUnSeleckted() {         // deselectioner tous les sous buttons
		patientRech.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/rechercher.png")));
		patientAjt.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/ajouter.png")));
		patientModifier.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/modifier.png")));
		patientSupp.setIcon(new ImageIcon(sousButtonsPatient.class.getResource("/ressourcesButtonMenu/supprimer.png")));
	}
	
	public void setFormListenerAfficherWindowSousButton(FormListenerWindowAfficher formListenerWindowAfficher) {
		this.formListenerWindowAfficher = formListenerWindowAfficher;
	}
}