package gui_home_parametre;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import category.Sexe;
import controllor.ConnexionMySql;
import controllor.MyException;
import gui_login.FormLogIn;
import model.Medecin;


public class FicheUser extends JFrame{
	private static final long serialVersionUID = 1L;

	private String id;
	
	private JLabel numId,dateTxt;
	private JButton fermer;
	
	private JLabel nomTxt,sexe;
	private JTextField nom,prenom;
	private JComboBox<String> sexeCombo;
	
	private JLabel adr,tel,email;
	private JTextField adrField,telField,emailField;
	
	private JLabel userName,mdp;
	private JTextField userField,mdpField;
	
	private JButton modifier,save,annuler,supp;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private String proffessionBD = "medecin";
	private String specialiteBD = "";
	private String dateBD = "";
	
	private String nomBD = "";
	private String prenomBD = "";
	private String sexeBD = "";
	private int gender;
	private String adrBD = "";
	private String telBD = "";
	private String emailBD = "";
	private String nomUserBD = "";
	private String mdpBD = "";
	
	public FicheUser(String id) {
		this.id = id;
		
		setTitle("Fiche utilisateur");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FicheUser.class.getResource("/resources/logoApp.png")));
		
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from utilisateur where id_utilisateur = " + Integer.parseInt(id);
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();

			if(resultat.next()) {
				if(!resultat.getBoolean("medecin")) {
					proffessionBD = "secretaire";					
				}
				specialiteBD = resultat.getString("specialite");
				dateBD = resultat.getString("date_inscrit");
				
				nomBD = resultat.getString("nom");
				prenomBD = resultat.getString("prenom");
				adrBD = resultat.getString("adr");
				telBD = resultat.getString("tel");
				emailBD = resultat.getString("email");
				nomUserBD = resultat.getString("nomUser");
				mdpBD = resultat.getString("mdp");
				
				sexeBD = resultat.getString("sexe");
				gender = 0;
				if(sexeBD.equals("F"))
					gender = 1;
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
		
		numId = new JLabel("N° " + this.id + " - " + proffessionBD + " " + specialiteBD);
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
		nomTxt = new JLabel("Nom Complet");
		nomTxt.setBounds(50,20,200,50);
		
		nom = new JTextField(nomBD);
		nom.setBounds(140,30,170,30);
		nom.setEditable(false);
		
		prenom = new JTextField(prenomBD);
		prenom.setBounds(320,30,160,30);
		prenom.setEditable(false);
		
		sexe = new JLabel("Sexe");
		sexe.setBounds(50,60,200,50);
		
		sexeCombo = new JComboBox<String>();
		sexeCombo.setBounds(140,70,50,30);
		DefaultComboBoxModel<String> sexeModel = new DefaultComboBoxModel<String>();
		sexeModel.addElement("H");
		sexeModel.addElement("F");
		sexeCombo.setModel(sexeModel);
		sexeCombo.setSelectedIndex(gender);
		sexeCombo.setEnabled(false);
		sexeCombo.setBackground(Color.white);
		
		Border innerBorder1 = BorderFactory.createTitledBorder("Etat Civil");
		Border outerBorder1 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pEtatCivil = new JPanel();
		pEtatCivil.setLayout(null);
		pEtatCivil.setBounds(0,100,500,130);
		pEtatCivil.add(nomTxt);
		pEtatCivil.add(sexe);
		pEtatCivil.add(nom);
		pEtatCivil.add(prenom);
		pEtatCivil.add(sexeCombo);
		pEtatCivil.setBorder(BorderFactory.createCompoundBorder(outerBorder1, innerBorder1));
		
		///////////////////// Cordonne ////////////////////////////
		adr = new JLabel("Adresse");
		adr.setBounds(50,20,80,50);
		
		adrField = new JTextField(adrBD);
		adrField.setBounds(140,30,340,30);
		adrField.setEditable(false);
		
		tel = new JLabel("Telephone");
		tel.setBounds(50,60,80,50);
		
		telField = new JTextField(telBD);
		telField.setBounds(140,70,340,30);
		telField.setEditable(false);
			
		email= new JLabel("E-mail");
		email.setBounds(50,100,80,50);
		
		emailField = new JTextField(emailBD);
		emailField.setBounds(140,110,340,30);
		emailField.setEditable(false);
		
		Border innerBorder2 = BorderFactory.createTitledBorder("Cordonnes");
		Border outerBorder2 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pCordonne = new JPanel();
		pCordonne.setLayout(null);
		pCordonne.setBounds(0,230,500,170);
		pCordonne.add(adr);
		pCordonne.add(tel);
		pCordonne.add(email);
		pCordonne.add(adrField);
		pCordonne.add(telField);
		pCordonne.add(emailField);
		pCordonne.setBorder(BorderFactory.createCompoundBorder(outerBorder2, innerBorder2));
		
		/////////////////// compte //////////////////////////////////
		userName= new JLabel("Nom d'Utilisateur");
		userName.setBounds(50,20,80,50);
		
		userField = new JTextField(nomUserBD);
		userField.setBounds(140,30,340,30);
		userField.setEditable(false);
		
		mdp= new JLabel("Mot de passe");
		mdp.setBounds(50,60,80,50);
		
		mdpField = new JTextField(mdpBD);
		mdpField.setBounds(140,70,340,30);
		mdpField.setEditable(false);
		
		Border innerBorder3 = BorderFactory.createTitledBorder("Compte");
		Border outerBorder3 = BorderFactory.createEmptyBorder(10,10,10,10);
		JPanel pCompte = new JPanel();
		pCompte.setLayout(null);
		pCompte.setBounds(0,400,500,130);
		pCompte.add(userName);
		pCompte.add(mdp);
		pCompte.add(userField);
		pCompte.add(mdpField);
		pCompte.setBorder(BorderFactory.createCompoundBorder(outerBorder3, innerBorder3));

		///////////////////// buttons ///////////////////////
		save = new JButton();
		save.setIcon(new ImageIcon(FicheUser.class.getResource("/ressourcesHomeManipulation/save_parametre.png")));
		save.setBounds(150,580,100,40);
		save.setVisible(false);
		save.setBorderPainted(false);
		save.setContentAreaFilled(false);
		
		annuler = new JButton();
		annuler.setIcon(new ImageIcon(FicheUser.class.getResource("/ressourcesHomeManipulation/annuler_parametre_2.png")));
		annuler.setBounds(260,580,100,40);
		annuler.setBorderPainted(false);
		annuler.setContentAreaFilled(false);
		annuler.setVisible(false);
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					champSaisir(telField.getText().toString().trim(), 
							emailField.getText().toString().trim() ,mdpField.getText().toString().trim());
					
					String nomBD2 = nom.getText();
					String prenomBD2 = prenom.getText();
					String adrBD2 = adrField.getText();
					String telBD2 = telField.getText();
					String emailBD2 = emailField.getText();
					String mdpBD2 = mdpField.getText();
					String sexeBD2 = sexeCombo.getSelectedItem().toString();
					Sexe sexeChoix = Sexe.Homme;
					if(sexeBD2.equalsIgnoreCase("F") || sexeBD2.equalsIgnoreCase("Femme")) 
						sexeChoix = Sexe.Femme;
					
					Medecin medModifier = new Medecin(nomBD2, prenomBD2, telBD2, adrBD2, emailBD2, sexeChoix, mdpBD2);
					
					if(FormLogIn.userNow.modifierUser(id, medModifier)) {
						userField.setText(nom.getText().concat(".").concat(prenom.getText()));
						
						save.setVisible(false);
						annuler.setVisible(false);
						
						nom.setEditable(false);
						prenom.setEditable(false);
						sexeCombo.setEnabled(false);
						adrField.setEditable(false);
						telField.setEditable(false);
						emailField.setEditable(false);
						userField.setEditable(false);
						mdpField.setEditable(false);
					}
				}
				catch(MyException ee){
					JOptionPane.showMessageDialog(null, ee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save.setVisible(false);
				annuler.setVisible(false);
				
				nom.setText(nomBD);
				nom.setEditable(false);
				
				prenom.setText(prenomBD);
				prenom.setEditable(false);
				
				sexeCombo.setEnabled(false);
				sexeCombo.setSelectedIndex(0);

				adrField.setText(adrBD);
				adrField.setEditable(false);
				
				telField.setText(telBD);
				telField.setEditable(false);
				
				emailField.setText(emailBD);
				emailField.setEditable(false);
				
				userField.setText(nomUserBD);
				userField.setEditable(false);
				
				mdpField.setText(mdpBD);
				mdpField.setEditable(false);
			}
		});

		modifier = new JButton();
		modifier.setIcon(new ImageIcon(FicheUser.class.getResource("/ressourcesHomeManipulation/modifier_parametre.png")));
		modifier.setBounds(20,580,100,40);
		modifier.setBorderPainted(false);
		modifier.setContentAreaFilled(false);
		modifier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nom.setEditable(true);
				prenom.setEditable(true);
				sexeCombo.setEnabled(true);
				adrField.setEditable(true);
				telField.setEditable(true);
				emailField.setEditable(true);
				mdpField.setEditable(true);
				
				save.setVisible(true);
				annuler.setVisible(true);
			}
		});
		
		
		supp = new JButton();
		supp.setIcon(new ImageIcon(FicheUser.class.getResource("/ressourcesHomeManipulation/supprimer_parametre.png")));
		supp.setBounds(380,580,100,40);
		supp.setBorderPainted(false);
		supp.setContentAreaFilled(false);
		supp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(FormLogIn.userNow.suppUser(id))
					dispose();
			}
		});
		
		getContentPane().add(numId);
		getContentPane().add(dateTxt);
		getContentPane().add(fermer);
		getContentPane().add(pEtatCivil);
		getContentPane().add(pCordonne);
		getContentPane().add(pCompte);
		getContentPane().add(modifier);
		getContentPane().add(save);
		getContentPane().add(annuler);
		getContentPane().add(supp);
		
		setVisible(true);
	}
	
	public void champSaisir(String tel, String email,String mdp)throws MyException {
		if(tel.length()<10 || tel.length()>15) {
			throw new MyException("Votre num telephone est depasse la taille (entre 10 et 15 chiffres)\n "
					+ "saisir un num telephone dans cette intervalle");
		}
		else if(email.length()>0 && !email.contains("@")) {
			throw new MyException("Votre email est faux \n "
					+ "il faut rajouter '@' dans votre email");
		}
		else if(mdp.length()<8) {
			throw new MyException("entre un mot de passe au moins 8 caracteres");
		}
	}
}
