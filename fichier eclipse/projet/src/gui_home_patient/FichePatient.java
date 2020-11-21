package gui_home_patient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import controllor.ConnexionMySql;
import gui_home_parametre.FicheUser;

public class FichePatient extends JFrame{
	private static final long serialVersionUID = 1L;

	private String idp;
	
	private JLabel numId,dateTxt;
	private JButton fermer;
	
	private JLabel nomTxt,prenomTxt,DateNaiss,sexe,age;
	private JTextField nom,prenom,DateNaissField,sexeField;
	
	private JLabel adr,tel,email;
	private JTextField adrField,telField,emailField;
	
	private JLabel poid,tension,taille;
	private JTextField poidField,tensionField,tailleField;
	
	private JLabel groupe,rh,maladieTxt,analyseTxt;
	private JTextField groupeField,rhField;
	
	private JTextArea maladies, analyseDem;
	private JTextArea consulter;
	private JScrollPane scm,scc,sca;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private String dateBD = "";
	private String dateNaissBD = "";
	private String nomBD = "";
	private String prenomBD = "";
	private String sexeBD = "";
	private String adrBD = "";
	private String telBD = "";
	private String emailBD = "";
	private double poidsBD = 0;
	private double tailleBD = 0;
	private double tensionBD = 0;
	private String groupageBD = "";
	private String maladeChroBD = "";
	private String consultationBD = "";
	private String analyseBD = "";
	
	public FichePatient(String id) {
		this.idp = id;
		
		setTitle("Fiche patient");
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FichePatient.class.getResource("/resources/logoApp.png")));
		
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from patient where id_patient = " + Integer.parseInt(id);
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			if(resultat.next()) {
				dateBD = resultat.getString("date_inscp");
				dateNaissBD = resultat.getString("date_naiss");
				nomBD = resultat.getString("nom");
				prenomBD = resultat.getString("prenom");
				sexeBD = resultat.getString("sexe");
				adrBD = resultat.getString("adr");
				telBD = resultat.getString("tel");
				emailBD = resultat.getString("email");
				poidsBD = resultat.getDouble("poids");
				tailleBD = resultat.getDouble("taille");
				tensionBD = resultat.getDouble("tension");
				groupageBD = resultat.getString("groupage");
				maladeChroBD = resultat.getString("malades");
				consultationBD = resultat.getString("consultation");
				analyseBD = resultat.getString("analyse");
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		
		setSize(500,650);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getContentPane().setLayout(null);
		
		// info de cette patient
		numId = new JLabel("N° Dossier " + idp	);
		numId.setBounds(50,0,500,50);
		numId.setFont(new Font("sans serif",Font.BOLD,20));
		numId.setForeground(new Color(203,22,47));
		
		dateTxt = new JLabel("Date d'inscription : " + dateBD);
		dateTxt.setBounds(50,30,300,50);
		dateTxt.setFont(new Font("sans serif",Font.BOLD,14));
		dateTxt.setForeground(Color.gray);
		
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
		nom = new JTextField(nomBD);
		nom.setBounds(90,30,150,30);
		nom.setEditable(false);
		
		prenomTxt = new JLabel("Prenom");
		prenomTxt.setBounds(30,60,70,50);
		prenom = new JTextField(prenomBD);
		prenom.setBounds(90,70,150,30);
		prenom.setEditable(false);
		
		String []d = dateNaissBD.split("/");
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		int anneCurrent = Integer.parseInt(ft.format(date));
		int ageCalculer = anneCurrent - Integer.parseInt(d[0]);
		
		DateNaiss = new JLabel("Né le");
		DateNaiss.setBounds(270,20,70,50);
		DateNaissField = new JTextField(dateNaissBD);
		DateNaissField.setBounds(320,30,80,30);
		DateNaissField.setEditable(false);
		age = new JLabel(String.valueOf(ageCalculer) + " ans");
		age.setBounds(420,30,40,30);
		
		sexe = new JLabel("Sexe");
		sexe.setBounds(270,60,200,50);
		sexeField = new JTextField(sexeBD);
		sexeField.setBounds(320,70,50,30);
		sexeField.setEditable(false);
		
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
		pEtatCivil.add(sexeField);
		pEtatCivil.add(DateNaiss);
		pEtatCivil.add(age);
		pEtatCivil.add(DateNaissField);
		pEtatCivil.setBorder(BorderFactory.createCompoundBorder(outerBorder1, innerBorder1));
		
		///////////////////// Cordonne ////////////////////////////
		adr = new JLabel("Adresse");
		adr.setBounds(30,20,80,50);
		adrField = new JTextField(adrBD);
		adrField.setBounds(90,30,180,30);
		adrField.setEditable(false);
		
		tel = new JLabel("Tel");
		tel.setBounds(30,60,80,50);
		telField = new JTextField(telBD);
		telField.setBounds(90,70,180,30);
		telField.setEditable(false);
			
		email= new JLabel("E-mail");
		email.setBounds(30,100,80,50);
		emailField = new JTextField(emailBD);
		emailField.setBounds(90,110,180,30);
		emailField.setEditable(false);
		
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
		poidField = new JTextField(String.valueOf(poidsBD));
		poidField.setBounds(90,30,110,30);
		poidField.setEditable(false);
		
		taille = new JLabel("Taille");
		taille.setBounds(30,60,80,50);
		tailleField = new JTextField(String.valueOf(tailleBD));
		tailleField.setBounds(90,70,110,30);
		tailleField.setEditable(false);
			
		tension= new JLabel("Tension");
		tension.setBounds(30,100,80,50);
		tensionField = new JTextField(String.valueOf(tensionBD));
		tensionField.setBounds(90,110,110,30);
		tensionField.setEditable(false);
		
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
		String g="",r="";
		switch (groupageBD) {
		case "O+":
			g = "O";
			r = "+";
			break;
		case "A+":
			g = "A";
			r = "+";
			break;
		case "B+":
			g = "B";
			r = "+";
			break;
		case "AB+":
			g = "AB";
			r = "+";
			break;
		case "O-":
			g = "0";
			r = "-";
			break;
		case "A-":
			g = "A";
			r = "-";
			break;
		case "B-":
			g = "B";
			r = "-";
			break;
		case "AB-":
			g = "AB";
			r = "-";
			break;
		}
		
		groupe = new JLabel("Groupe");
		groupe.setBounds(30,20,50,50);
		groupeField = new JTextField(" " + g);
		groupeField.setBounds(90,30,20,30);
		groupeField.setEditable(false);
		
		rh = new JLabel("Rh");
		rh.setBounds(140,20,50,50);
		rhField = new JTextField("  " + r);
		rhField.setBounds(170,30,20,30);
		rhField.setEditable(false);
			
		maladieTxt= new JLabel("Malade Chronique :");
		maladieTxt.setBounds(30,60,140,50);
		maladies = new JTextArea();
		maladies.setBounds(30,100,170,70);
		maladies.setEditable(false);
		maladies.setText(maladeChroBD);
		scm = new JScrollPane();
		scm.setBounds(30,100,170,70);
		scm.setViewportView(maladies);
		
		analyseTxt= new JLabel("Analyses Demandes :");
		analyseTxt.setBounds(30,170,140,50);
		analyseDem = new JTextArea();
		analyseDem.setBounds(30,220,170,70);
		analyseDem.setEditable(false);
		analyseDem.setText(analyseBD);
		sca = new JScrollPane();
		sca.setBounds(30,220,170,70);
		sca.setViewportView(analyseDem);
		
		Border innerBorder4 = BorderFactory.createTitledBorder("Plus info");
		Border outerBorder4 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pPlusInfo = new JPanel();
		pPlusInfo.setLayout(null);
		pPlusInfo.setBounds(280,340,220,310);
		pPlusInfo.add(groupe);
		pPlusInfo.add(groupeField);
		pPlusInfo.add(rh);
		pPlusInfo.add(rhField);
		pPlusInfo.add(maladieTxt);
		pPlusInfo.add(scm);
		pPlusInfo.add(analyseTxt);
		pPlusInfo.add(sca);
		pPlusInfo.setBorder(BorderFactory.createCompoundBorder(outerBorder4, innerBorder4));
		
		///////////////////// Consultation ////////////////////////////
		consulter = new JTextArea();
		consulter.setText(consultationBD);
		consulter.setBounds(30,40,230,240);
		consulter.setEditable(false);
		scc = new JScrollPane();
		scc.setBounds(30,40,230,240);
		scc.setViewportView(consulter);
		
		Border innerBorder6 = BorderFactory.createTitledBorder("Consultation");
		Border outerBorder6 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pConsultation = new JPanel();
		pConsultation.setLayout(null);
		pConsultation.setBounds(0,340,290,310);
		pConsultation.add(scc);
		pConsultation.setBorder(BorderFactory.createCompoundBorder(outerBorder6, innerBorder6));
		
		
		getContentPane().add(numId);
		getContentPane().add(dateTxt);
		getContentPane().add(fermer);
		getContentPane().add(pEtatCivil);
		getContentPane().add(pCordonne);
		getContentPane().add(pMesure);
		getContentPane().add(pPlusInfo);
		getContentPane().add(pConsultation);
		
		setVisible(true);
	}
}
