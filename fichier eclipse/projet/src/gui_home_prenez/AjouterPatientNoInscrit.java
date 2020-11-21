package gui_home_prenez;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import category.Groupage;
import category.Sexe;
import controllor.MyException;
import gui_home_parametre.FicheUser;
import gui_home_patient.FichePatient;
import gui_login.FormLogIn;
import model.Patient;

public class AjouterPatientNoInscrit extends JFrame{
	private static final long serialVersionUID = 1L;

	//private int idp,idr;
	
	private JLabel numId;
	private JButton fermer;
	
	private JLabel nomTxt,prenomTxt,DateNaiss,sexe;
	private JTextField nom,prenom;
	private JDateChooser dateNaissChooser;
	
	private JLabel adr,tel,email;
	private JTextField adrField,telField,emailField;
	
	private JLabel poid,tension,taille;
	private JTextField poidField,tensionField,tailleField;
	
	private JLabel groupe,rh,maladieTxt;
	
	private JComboBox<String> sexeCombo,groupageCombo,rhCombo;
	private JButton ajouter;
	
	private JTextArea maladies;
	private JScrollPane scm;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	public AjouterPatientNoInscrit(int idp,int idr) {
		//this.idp = idp;
		Patient pCopier = new Patient(0, "", "", "", "", "", null, null, null, 0, 0, 0, null, "");
		for (int i = 0; i < FormLogIn.userNow.getPatients().size(); i++) {
			if(FormLogIn.userNow.getPatients().get(i).getId_patient() == idp) {
				pCopier = FormLogIn.userNow.getPatients().get(i);
			}
		}
		
		setTitle("Ajouter patient");
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FichePatient.class.getResource("/resources/logoApp.png")));

		setSize(500,650);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getContentPane().setLayout(null);
		
		numId = new JLabel("Ajouter patient no Inscrit");
		numId.setBounds(50,0,500,50);
		numId.setFont(new Font("sans serif",Font.BOLD,20));
		numId.setForeground(new Color(203,22,47));
		
		fermer = new JButton();
		fermer.setIcon(new ImageIcon(FicheUser.class.getResource("/resources/exit_icon.png")));
		fermer.setBounds(450,10,40,40);
		fermer.setBorderPainted(false);
		fermer.setContentAreaFilled(false);
		fermer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();   // fermer la fenetre current
			}
		});
		
		//////////////////// etat civil ///////////////////////////
		nomTxt = new JLabel("Nom");
		nomTxt.setBounds(30,20,70,50);
		nom = new JTextField(pCopier.getNom());
		nom.setBounds(90,30,150,30);
		
		prenomTxt = new JLabel("Prenom");
		prenomTxt.setBounds(30,60,70,50);
		prenom = new JTextField(pCopier.getPrenom());
		prenom.setBounds(90,70,150,30);
		
		DateNaiss = new JLabel("Né le");
		DateNaiss.setBounds(270,20,70,50);
		dateNaissChooser = new JDateChooser();
		dateNaissChooser.setDate(pCopier.getDate_naiss());
		dateNaissChooser.setBounds(310,30,170,30);
		
		sexe = new JLabel("Sexe");
		sexe.setBounds(270,60,200,50);

		sexeCombo = new JComboBox<String>();
		sexeCombo.setBackground(Color.white);
		sexeCombo.setBounds(310,70,170,30);
		DefaultComboBoxModel<String> sexeModel = new DefaultComboBoxModel<String>();
		sexeModel.addElement("H");
		sexeModel.addElement("F");
		sexeCombo.setModel(sexeModel);
		if(pCopier.getSexe().equals(Sexe.Homme))
			sexeCombo.setSelectedIndex(0);
		else
			sexeCombo.setSelectedIndex(1);
		
		Border innerBorder1 = BorderFactory.createTitledBorder("Etat Civil");
		Border outerBorder1 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pEtatCivil = new JPanel();
		pEtatCivil.setLayout(null);
		pEtatCivil.setBounds(0,80,500,120);
		pEtatCivil.add(nomTxt);
		pEtatCivil.add(prenomTxt);
		pEtatCivil.add(sexe);
		pEtatCivil.add(nom);
		pEtatCivil.add(prenom);
		pEtatCivil.add(sexeCombo);
		pEtatCivil.add(DateNaiss);
		pEtatCivil.add(dateNaissChooser);
		pEtatCivil.setBorder(BorderFactory.createCompoundBorder(outerBorder1, innerBorder1));
		
		///////////////////// Cordonne ////////////////////////////
		adr = new JLabel("Adresse");
		adr.setBounds(30,20,80,50);
		adrField = new JTextField("");
		adrField.setBounds(90,30,180,30);
		
		tel = new JLabel("Tel");
		tel.setBounds(30,60,80,50);
		telField = new JTextField(pCopier.getTel());
		telField.setBounds(90,70,180,30);
			
		email= new JLabel("E-mail");
		email.setBounds(30,100,80,50);
		emailField = new JTextField("");
		emailField.setBounds(90,110,180,30);
		
		Border innerBorder2 = BorderFactory.createTitledBorder("Cordonnes");
		Border outerBorder2 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pCordonne = new JPanel();
		pCordonne.setLayout(null);
		pCordonne.setBounds(0,190,290,160);
		pCordonne.add(adr);
		pCordonne.add(tel);
		pCordonne.add(email);
		pCordonne.add(adrField);
		pCordonne.add(telField);
		pCordonne.add(emailField);
		pCordonne.setBorder(BorderFactory.createCompoundBorder(outerBorder2, innerBorder2));
		
		/////////////////// Mesure //////////////////////////////////
		poid = new JLabel("Poid");
		poid.setBounds(30,20,80,50);
		poidField = new JTextField(String.valueOf(pCopier.getMesure().getPoids()));
		poidField.setBounds(90,30,110,30);
		
		taille = new JLabel("Taille");
		taille.setBounds(30,60,80,50);
		tailleField = new JTextField(String.valueOf(pCopier.getMesure().getTaille()));
		tailleField.setBounds(90,70,110,30);
			
		tension= new JLabel("Tension");
		tension.setBounds(30,100,80,50);
		tensionField = new JTextField(String.valueOf(pCopier.getMesure().getTension()));
		tensionField.setBounds(90,110,110,30);
		
		Border innerBorder3 = BorderFactory.createTitledBorder("Mesure");
		Border outerBorder3 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pMesure = new JPanel();
		pMesure.setLayout(null);
		pMesure.setBounds(280,190,220,160);
		pMesure.add(poid);
		pMesure.add(taille);
		pMesure.add(tension);
		pMesure.add(poidField);
		pMesure.add(tailleField);
		pMesure.add(tensionField);
		pMesure.setBorder(BorderFactory.createCompoundBorder(outerBorder3, innerBorder3));

		/////////////////// Plus information //////////////////////////////////
		groupe = new JLabel("Groupe");
		groupe.setBounds(30,20,50,50);
		rh = new JLabel("Rh");
		rh.setBounds(270,20,50,50);
		
		groupageCombo = new JComboBox<String>();
		groupageCombo.setBackground(Color.white);
		groupageCombo.setBounds(90,30,170,30);
		DefaultComboBoxModel<String> groupageModel = new DefaultComboBoxModel<String>();
		groupageModel.addElement("O");
		groupageModel.addElement("A");
		groupageModel.addElement("B");
		groupageModel.addElement("AB");
		groupageCombo.setModel(groupageModel);
		
		rhCombo = new JComboBox<String>();
		rhCombo.setBackground(Color.white);
		rhCombo.setBounds(310,30,170,30);
		DefaultComboBoxModel<String> rhModel = new DefaultComboBoxModel<String>();
		rhModel.addElement("+");
		rhModel.addElement("-");
		rhCombo.setModel(rhModel);
			
		maladieTxt= new JLabel("Malade Chronique :");
		maladieTxt.setBounds(30,60,140,50);
		maladies = new JTextArea();
		maladies.setBounds(30,100,170,190);
		maladies.setText("");
		scm = new JScrollPane();
		scm.setBounds(30,100,450,140);
		scm.setViewportView(maladies);
		
		Border innerBorder4 = BorderFactory.createTitledBorder("Plus info");
		Border outerBorder4 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pPlusInfo = new JPanel();
		pPlusInfo.setLayout(null);
		pPlusInfo.setBounds(0,340,500,260);
		pPlusInfo.add(groupe);
		pPlusInfo.add(groupageCombo);
		pPlusInfo.add(rh);
		pPlusInfo.add(rhCombo);
		pPlusInfo.add(maladieTxt);
		pPlusInfo.add(scm);
		pPlusInfo.setBorder(BorderFactory.createCompoundBorder(outerBorder4, innerBorder4));
		
		ajouter = new JButton();
		ajouter.setIcon(new ImageIcon(AjouterPatientNoInscrit.class.getResource("/ressourcesHomeManipulation/Ajouter_parmetre.png")));
		ajouter.setBorderPainted(false);
		ajouter.setBounds(186, 599, 120, 40);
		ajouter.setContentAreaFilled(false);
		ajouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					champSaisir(nom.getText().trim(), prenom.getText().trim(), telField.getText().trim(),
							poidField.getText().trim(), tensionField.getText().trim(), tailleField.getText().trim(),
							emailField.getText().trim());
					
					String nomTxt = nom.getText().toString().trim();
					String prenomTxt = prenom.getText().toString().trim();
					String adrTxt = adrField.getText().toString().trim();
					String telTxt = telField.getText().toString().trim();
					String emailTxt = emailField.getText().toString().trim();
					
					Date dateNaissTxt = dateNaissChooser.getDate();
					verifierDate(dateNaissTxt);
					
					double tailleTxt = Double.parseDouble(tailleField.getText().trim());
					double poidTxt = Double.parseDouble(poidField.getText().trim());
					double tensionTxt = Double.parseDouble(tensionField.getText().trim());
					Sexe sexe = Sexe.Homme;
					if(sexeCombo.getSelectedIndex() == 1) 
						sexe = Sexe.Femme;
					String groupeTxt = groupageCombo.getSelectedItem().toString().concat(rhCombo.getSelectedItem().toString());
					String malad_chroniqueTxt = maladies.getText().trim();
					
					Patient p = new Patient(0,nomTxt, prenomTxt, telTxt, adrTxt, emailTxt, sexe, new Date(),
						dateNaissTxt, poidTxt, tailleTxt, tensionTxt, grpSang(groupeTxt), malad_chroniqueTxt);
					
					FormLogIn.userNow.ajouterPatient(p);
					FormLogIn.userNow.suppRdvInscritNoInscrit(idp,idr);
					dispose();
				} 
				catch (MyException e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ev) {
					poidField.setText("");
					tensionField.setText("");
					tailleField.setText("");
					JOptionPane.showMessageDialog(null,"les champs tension,poids,taille,\n" + 
							"sont de type decimale \nexp = 2.45\n" 
							,"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		getContentPane().add(numId);
		getContentPane().add(fermer);
		getContentPane().add(pEtatCivil);
		getContentPane().add(pCordonne);
		getContentPane().add(pMesure);
		getContentPane().add(pPlusInfo);
		getContentPane().add(ajouter);
		
		setVisible(true);
	}

	/////////////////////////////// choisir le groupe sanguin //////////////////////
	public Groupage grpSang(String groupeTxt) {
		switch (groupeTxt) {
		case "O+":
		return Groupage.O_pos;
		case "O-":
		return Groupage.O_neg;
		case "A+":
		return Groupage.A_pos;
		case "A-":
		return Groupage.A_neg;
		case "B+":
		return Groupage.B_pos;
		case "B-":
		return Groupage.B_neg;
		case "AB+":
		return Groupage.AB_pos;
		default :
		return Groupage.AB_neg;
		}
	}
	
	////////////////////////////// exception ///////////////////////////////////
	public void champSaisir(String nom,String prenom,String tel, String poid,String tension,
			String taille,String email)throws MyException {
		if(nom.equalsIgnoreCase("") || prenom.equalsIgnoreCase("") || tel.equalsIgnoreCase("") ||
				poid.equalsIgnoreCase("") || tension.equalsIgnoreCase("") || taille.equalsIgnoreCase("")) {
			throw new MyException("Il faut remplir les champs possede une etoile '*'");
		}
		else if(nom.trim().split(" ").length>1 || nom.trim().split(" ").length>1) {
			throw new MyException("S'il vous plait ne laisse pas les espaces entre les mots,\n"
					+ "si vous avez par exp 2 prenom spare par un \"-\" \n"
					+ "exp : mohamed amine ==> faux\n"
					+ "mohamed-amine ==> vrai.");
		}
		else if(Double.parseDouble(poid)<=0.0 || Double.parseDouble(poid)>500.0) {
			throw new MyException("Votre poid est incorrect (0.0 < poid < 500.0 kg)\n saisir un poid reel");
		}
		else if(Double.parseDouble(taille)<=0.0 || Double.parseDouble(taille)>3.0) {
			throw new MyException("Votre taille est incorrect (0.0 < taille < 3.0 metres)\n saisir une taille reel");
		}
		else if(Double.parseDouble(tension)<=0.0) {
			throw new MyException("Votre tension est incorrect (0.0 < tension)\n saisir une tension reel");
		}
		else if(tel.length()<10 || tel.length()>15) {
			throw new MyException("Votre num telephone est depasse la taille (entre 10 et 15 chiffres)\n "
					+ "saisir un num telephone dans cette intervalle");
		}
		else if(email.length()>0 && !email.contains("@")) {
			throw new MyException("Votre email est faux \n "
					+ "il faut rajouter '@' dans votre email");
		}
	}
	public void verifierDate(Date d)throws MyException {
		Date currentDate = new Date();
		if(d.compareTo(currentDate)>=0) {
			throw new MyException("La date de naissance est depasse la date\n"
					+ "current, il faut donner une date avant la date actuelle");
		}
	}
}
