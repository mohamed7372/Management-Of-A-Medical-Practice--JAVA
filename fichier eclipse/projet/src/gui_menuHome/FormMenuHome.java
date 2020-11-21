package gui_menuHome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui_login.FormLogIn;
import managerEntreFrame.FormListenerWindowAfficher;

public class FormMenuHome extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JLabel logoApp;
	private JButton overviewButt;
	private JButton prenezButt;
	private sousButtonsPrenez prenezSB;
	private JButton consultationButt;
	private JButton patientButt;
	private sousButtonsPatient patientSB;
	private JButton ordonnanceButt;
	private sousButtonOrdonnance ordonnanceSB;
	private JButton factureButt;
	private sousButtonFacture factureSB;
	private JButton agendaButt;
	private JButton statButt;
	private JButton parametreButt;
	private JButton exitButt;
	
	private FormListenerWindowAfficher formListenerWindowAfficher;
	
	public FormMenuHome() {
		// logo de l'application
		logoApp = new JLabel(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/app-logo.png")));
		
		// diff button
		overviewButt = new JButton();
		overviewButt.setName("home");
		overviewButt.setBorderPainted(false);
		overviewButt.setContentAreaFilled(false);
		overviewButt.addActionListener(this);
		
		prenezButt = new JButton();
		prenezButt.setName("prenez");
		prenezButt.setBorderPainted(false);
		prenezButt.setContentAreaFilled(false);
		prenezButt.addActionListener(this);
		prenezSB = new sousButtonsPrenez(2);
		
		consultationButt = new JButton();
		consultationButt.setName("consultation");
		consultationButt.setBorderPainted(false);
		consultationButt.setContentAreaFilled(false);
		consultationButt.addActionListener(this);
		
		patientButt = new JButton();
		patientButt.setName("patient");
		patientButt.setBorderPainted(false);
		patientButt.setContentAreaFilled(false);
		patientButt.addActionListener(this);
		patientSB = new sousButtonsPatient(4);
		
		ordonnanceButt = new JButton();
		ordonnanceButt.setName("ordonnance");
		ordonnanceButt.setBorderPainted(false);
		ordonnanceButt.setContentAreaFilled(false);
		ordonnanceButt.addActionListener(this);
		ordonnanceSB = new sousButtonOrdonnance(5);
		
		factureButt = new JButton();
		factureButt.setName("facture");
		factureButt.setBorderPainted(false);
		factureButt.setContentAreaFilled(false);
		factureButt.addActionListener(this);
		factureSB = new sousButtonFacture(6);
		
		agendaButt = new JButton();
		agendaButt.setName("agenda");
		agendaButt.setBorderPainted(false);
		agendaButt.setContentAreaFilled(false);
		agendaButt.addActionListener(this);
		
		statButt = new JButton();
		statButt.setName("statique");
		statButt.setBorderPainted(false);
		statButt.setContentAreaFilled(false);
		statButt.addActionListener(this);
		
		parametreButt = new JButton();
		parametreButt.setName("parametre");
		parametreButt.setBorderPainted(false);
		parametreButt.setContentAreaFilled(false);
		parametreButt.addActionListener(this);
		
		exitButt = new JButton();
		exitButt.setName("sortie");
		exitButt.setBorderPainted(false);
		exitButt.setContentAreaFilled(false);
		exitButt.addActionListener(this);
		
		// desactiver les buttons dans le cas d'un user est un secretaire
		if(!FormLogIn.userNow.isMedecin()) {
			parametreButt.setEnabled(false);
			ordonnanceButt.setEnabled(false);
			patientButt.setEnabled(false);
			consultationButt.setEnabled(false);
		}
		
		
		/* mettre bounds pour chaque buttons(sauf le dernier button exit)
		 * mettre pour chaque button leur propre button image
		 * mettre par defaut 1er button selectionner
		 */
		boundsButtons();
		makeAllUnSeleckted();  
		overviewButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/homeSelect.png")));
		
		// information de menu panel
		setPreferredSize(new Dimension(200,600));
		setBackground(Color.white);
		setLayout(new BorderLayout());
		add(logoApp,BorderLayout.PAGE_START);
		
		JPanel sousPanel = new JPanel(null);
		sousPanel.setBackground(Color.white);
		sousPanel.add(overviewButt);
		sousPanel.add(prenezButt);
		sousPanel.add(prenezSB);
		sousPanel.add(consultationButt);
		sousPanel.add(patientButt);
		sousPanel.add(patientSB);
		sousPanel.add(ordonnanceButt);
		sousPanel.add(ordonnanceSB);
		sousPanel.add(factureButt);
		sousPanel.add(factureSB);
		sousPanel.add(agendaButt);
		sousPanel.add(statButt);
		sousPanel.add(parametreButt);
		add(sousPanel,BorderLayout.CENTER);
		
		add(exitButt,BorderLayout.PAGE_END);
	}

	public void selectButton(String nameButt) {
		boundsButtons();
		desactiverTousSousButton();
		makeAllUnSeleckted();         // mettre tous les button unselected
		ManagerSelectMenu manager;
		switch (nameButt) {
		case "parametre":
			manager = new ManagerSelectMenu(parametreButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			break;
		case "agenda":
			manager = new ManagerSelectMenu(agendaButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			break;
		case "statique":
			manager = new ManagerSelectMenu(statButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			break;
		case "consultation":
			manager = new ManagerSelectMenu(consultationButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			break;
		case "patient":
			manager = new ManagerSelectMenu(patientButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			patientSB.activerSousButton();
			boundsButton2(patientSB.nextPos());
			patientSB.selectSousButton();
			break;
		case "prenez":
			manager = new ManagerSelectMenu(prenezButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			prenezSB.activerSousButton();
			boundsButton1(prenezSB.nextPos());
			prenezSB.selectSousButton();
			break;
		case "ordonnance":
			manager = new ManagerSelectMenu(ordonnanceButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			ordonnanceSB.activerSousButton();
			boundsButton3(ordonnanceSB.nextPos());
			ordonnanceSB.selectSousButton();
			break;
		case "facture":
			manager = new ManagerSelectMenu(factureButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			factureSB.activerSousButton();
			boundsButton4(factureSB.nextPos());
			factureSB.selectSousButton();
			break;
		case "home":
			manager = new ManagerSelectMenu(overviewButt,"/ressourcesButtonMenu/", ".png");
			manager.selectedButton();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {     // action de chaque button
		JButton select = (JButton)e.getSource();   //affecter le button qui on a choisir
		
		boundsButtons();
		desactiverTousSousButton();
		
		// create object et passe les attribut pour mettre le button selected
		ManagerSelectMenu manager = new ManagerSelectMenu(select,"/ressourcesButtonMenu/", ".png");
		makeAllUnSeleckted();         // mettre tous les button unselected
		manager.selectedButton();     // select le button
		
		// set le nom de button selectionner
		formListenerWindowAfficher.afficherWindow(select.getName()); // envoyer le nom de window selected
		
		// afficher les sous buttons
		switch (select.getName()) {
		case "prenez":
			prenezSB.activerSousButton();
			boundsButton1(prenezSB.nextPos());
			break;
		case "patient":
			patientSB.activerSousButton();
			boundsButton2(patientSB.nextPos());
			break;
		case "ordonnance":
			ordonnanceSB.activerSousButton();
			boundsButton3(ordonnanceSB.nextPos());
			break;
		case "facture":
			factureSB.activerSousButton();
			boundsButton4(factureSB.nextPos());
			break;
		}
		
		// afficher window de sous buttons
		prenezSB.setFormListenerAfficherWindowSousButton(new FormListenerWindowAfficher() {
			@Override
			public void afficherWindow(String name) {
				formListenerWindowAfficher.afficherWindow(name); // envoyer le nom de window selected
			}
		});
		patientSB.setFormListenerAfficherWindowSousButton(new FormListenerWindowAfficher() {
			@Override
			public void afficherWindow(String name) {
				formListenerWindowAfficher.afficherWindow(name); // envoyer le nom de window selected
			}
		});
		ordonnanceSB.setFormListenerAfficherWindowSousButton(new FormListenerWindowAfficher() {
			@Override
			public void afficherWindow(String name) {
				formListenerWindowAfficher.afficherWindow(name); // envoyer le nom de window selected
			}
		});
		factureSB.setFormListenerAfficherWindowSousButton(new FormListenerWindowAfficher() {
			@Override
			public void afficherWindow(String name) {
				formListenerWindowAfficher.afficherWindow(name); // envoyer le nom de window selected
			}
		});
		
		
	}
	// set form listener 
	public void setFormListenerWindowAfficher(FormListenerWindowAfficher formListenerWindowAfficher) {
		this.formListenerWindowAfficher = formListenerWindowAfficher;
	}
	
	public void makeAllUnSeleckted() {         // deselectioner tous les buttons
		overviewButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/home.png")));
		prenezButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/prenez.png")));
		consultationButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/consultation.png")));
		patientButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/patient.png")));
		ordonnanceButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/ordonnance.png")));
		factureButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/facture.png")));
		agendaButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/agenda.png")));
		statButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/statique.png")));
		parametreButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/parametre.png")));
		exitButt.setIcon(new ImageIcon(FormMenuHome.class.getResource("/ressourcesButtonMenu/sortie.png")));
	}
	public void desactiverTousSousButton() {   // cacher tous les sous buttons
		prenezSB.desactiverSousButton();
		patientSB.desactiverSousButton();
		ordonnanceSB.desactiverSousButton();
		factureSB.desactiverSousButton();
	}
	
	// bounds pour chaque button
	public void boundsButtons() {
		overviewButt.setBounds(0,10,200,30);
		prenezButt.setBounds(0,45,200,30);
		boundsButton1(80);
	}
	public void boundsButton1(int pos) {
		consultationButt.setBounds(0,pos,200,30);
		patientButt.setBounds(0,pos + 35,200,30);
		boundsButton2(pos + 35*2);
	}
	public void boundsButton2(int pos) {
		ordonnanceButt.setBounds(0,pos,200,30);
		boundsButton3(pos+35);
	}
	public void boundsButton3(int pos) {
		factureButt.setBounds(0,pos,200,30);
		boundsButton4(pos+35);
	}
	public void boundsButton4(int pos) {
		agendaButt.setBounds(0,pos,200,30);
		statButt.setBounds(0,pos + 35,200,30);
		parametreButt.setBounds(0,pos + 35*2,200,30);
	}
}