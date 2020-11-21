package gui_home_home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import gui_login.FormLogIn;
import managerEntreFrame.*;

public class HomeWindow extends JPanel implements ManagerWindow,ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JButton consultation,patient,facture,prenez,ordonnance,agenda,parametre,statique;
	
	public HomeWindow() {
		
		////////////// ligne 1 ///////////////////
		consultation = new JButton();
		consultation.setBorderPainted(false);
		consultation.setContentAreaFilled(false);
		consultation.setName("home-consultation");
		
		patient = new JButton();
		patient.setBorderPainted(false);
		patient.setContentAreaFilled(false);
		patient.setName("home-patient");
		
		facture = new JButton();
		facture.setBorderPainted(false);
		facture.setContentAreaFilled(false);
		facture.addActionListener(this);
		facture.setName("home-facture");

		//////////////////// ligne 2 /////////////////
		prenez = new JButton();
		prenez.setBorderPainted(false);
		prenez.setContentAreaFilled(false);
		prenez.addActionListener(this);
		prenez.setName("home-prenez");
		
		ordonnance = new JButton();
		ordonnance.setBorderPainted(false);
		ordonnance.setContentAreaFilled(false);
		ordonnance.setName("home-ordonnance");
		
		agenda = new JButton();
		agenda.setBorderPainted(false);
		agenda.setContentAreaFilled(false);
		agenda.addActionListener(this);
		agenda.setName("home-agenda");
		
		////////////// ligne 3 ////////////////////
		parametre = new JButton();
		parametre.setBorderPainted(false);
		parametre.setContentAreaFilled(false);
		parametre.setName("home-parametre");
		
		statique = new JButton();
		statique.setBorderPainted(false);
		statique.setContentAreaFilled(false);
		statique.addActionListener(this);
		statique.setName("home-statique");
		
		System.out.println("secre/mede : "+FormLogIn.userNow.isMedecin());
		if(FormLogIn.userNow.isMedecin()) {
			buttonHomeUserMedecin();
			parametre.addActionListener(this);
			patient.addActionListener(this);
			consultation.addActionListener(this);
			ordonnance.addActionListener(this);
		}
		else {
			buttonHomeUserSecretaire();
		}
		homeGui();
	}
	
	public void buttonHomeUserMedecin() {
		statique.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/statiqueHome.png")));
		parametre.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/parametreHome.png")));
		consultation.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/consultationHome.png")));
		patient.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/patientHome.png")));
		facture.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/factureHome.png")));
		prenez.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/prenezHome.png")));
		ordonnance.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/ordonnanceHome.png")));
		agenda.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/agendaHome.png")));
	}
	
	public void buttonHomeUserSecretaire() {
		statique.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/statiqueHome.png")));
		parametre.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/parametreHomeLock.png")));
		consultation.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/consultationHomeLock.png")));
		patient.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/patientHomeLock.png")));
		facture.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/factureHome.png")));
		prenez.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/prenezHome.png")));
		ordonnance.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/ordonnanceHomeLock.png")));
		agenda.setIcon(new ImageIcon(HomeWindow.class.getResource("/ressourcesHomeManipulation/agendaHome.png")));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton select = (JButton)e.getSource();
		System.out.println(select.getName());
		switch (select.getName()) {
		case "home-parametre":
			FormLogIn.h.activerParametreWindow();
			HomeFrame.getHomeFrameVar().selectButton("parametre");
			break;
		case "home-patient":
			FormLogIn.h.activerPatientWindow();
			HomeFrame.getHomeFrameVar().selectButton("patient");
			break;					
		case "home-facture":
			FormLogIn.h.activerFactureWindow();
			HomeFrame.getHomeFrameVar().selectButton("facture");
			break;
		case "home-prenez":
			FormLogIn.h.activerPrenewWindow();
			HomeFrame.getHomeFrameVar().selectButton("prenez");
			break;
		case "home-ordonnance":
			FormLogIn.h.activerOrdonnanceWindow();
			HomeFrame.getHomeFrameVar().selectButton("ordonnance");
			break;
		case "home-consultation":
			FormLogIn.h.activerConsultationWindow();
			HomeFrame.getHomeFrameVar().selectButton("consultation");
			break;
		case "home-statique":
			FormLogIn.h.activerStatiqueWindow();
			HomeFrame.getHomeFrameVar().selectButton("statique");
			break;
		case "home-agenda":
			FormLogIn.h.activerAgendaWindow();
			HomeFrame.getHomeFrameVar().selectButton("agenda");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void activerWindow() {
		setVisible(true);
	}

	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	 public void homeGui() {
			setLayout(new GridBagLayout());
		    GridBagConstraints c = new GridBagConstraints();
		    c.fill = GridBagConstraints.BOTH; 
		    
	    	//////////////////////////1 raw  ////////////////////////
		    c.gridx = 0;
		    c.gridy = 0;
		    c.gridwidth = 1;
		    c.gridheight = 1;
		    c.weightx = 0.5; 
		    c.weighty = 0.5;
		    add(consultation, c);
		    
		    c.gridx = 1;
		    add(patient, c);
		    
		    c.gridx = 2;
		    add(facture, c);

		    //////////////////////////2 raw  ////////////////////////
		    c.gridx = 0;
		    c.gridy++;
		    c.gridwidth = 1;
		    c.gridheight = 1;
		    c.weightx = 0.5;
		    add(prenez, c);
		    
		    c.gridx = 1;
		    add(ordonnance, c);
		    
		    c.gridx = 2;
		    add(agenda, c);
		    
		    //////////////////////////3 raw  ////////////////////////
		    c.gridx = 0;
		    c.gridy++;
		    c.gridwidth = 1;
		    c.gridheight = 1;
		    c.weightx = 0.5;
		    add(parametre, c);
		    
		    c.gridx = 1;
		    add(statique, c);
		}

}