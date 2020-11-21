package gui_home_parametre;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

import com.toedter.calendar.JDateChooser;

import category.Sexe;
import controllor.ConnexionMySql;
import controllor.HintTextField;
import controllor.MyException;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;
import model.Medecin;

public class ParametreWindow extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private Container content1;
	private JLabel imgUser,userNbr;
	
	private JTextField search;
	
	private Container content3;
	private JLabel NewCompte;
	private JLabel nom;
	private JComboBox<String> etatCombo;
	private JTextField nomNom;
	private JTextField nomPrenom;
	private JLabel adr;
	private JTextField adrField;
	private JLabel tel;
	private JTextField telField;
	private JLabel contact;
	private JTextField contactField;
	private JLabel medecin;
	private JComboBox<String> medecinCombo;
	private JLabel date;
	private JCheckBox manuel;
	private JDateChooser dateChooser;
	private JLabel specialite;
	private JTextField specialiteField;
	private JLabel mdp;
	private JTextField mdpField;
	private JButton ajouter;
	private JButton annuler;

	private JTable tab;
	private JScrollPane sc;
	
	public ParametreWindow() {
		//////////////////// nbr user /////////////////////////////////
		userNbr = new JLabel("",SwingConstants.CENTER);
		userNbr.setFont(new Font("SansSerif", Font.BOLD, 35));
		
		imgUser = new JLabel();
		imgUser.setIcon(new ImageIcon(ParametreWindow.class.getResource("/ressourcesHomeManipulation/img_parametre_user.png")));
		
		content1= new JPanel();
		content1.setLayout(new BoxLayout(content1,BoxLayout.PAGE_AXIS));
		content1.setBackground(Color.white);
		content1.add(imgUser);
		content1.add(userNbr);
		content1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		/////////////// rechercher un utilusateur /////////////////////
		search = new HintTextField("Search");
		search.setToolTipText("Sasair nom et prenom pour afficher les utilisateurs dans la table.");
		search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ev) {
				String nameComplet = search.getText().toString().trim();
				String []nomPrenom = nameComplet.split(" ");
				String []nomSearch = {"","","","","","","",""};
				int j=0;
				for (int i = 0; i < nomPrenom.length; i++) {
					if(!nomPrenom[i].equals("")) {
						nomSearch[j] = nomPrenom[i];
						j++;
					}
				}
				// verifier combien de mot saisir (il faut saisir juste 2 mots)
				try {
					nomPrenomSaisir(nomSearch);
					if(nameComplet.equals("")) {
						updateTable();
					}
					else {
						updateTable(nomSearch);
					}
				} catch (MyException e) {
					updateTable();
					search.setText("");
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		///////////////////// tableau information ///////////////////
		tab = new JTable();
		tab.getTableHeader().setFont(new Font("Sans serif",Font.BOLD,15));
		tab.getTableHeader().setBackground(new Color(255,255,255));
		tab.getTableHeader().setForeground(new Color(203,22,47));
		tab.setRowHeight(30);
		tab.setFont(new Font("Sans serif",Font.BOLD,12));
		tab.setSelectionBackground(new Color(203,22,47));
		tab.setSelectionForeground(new Color(255,255,255));
		
		tab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tab.getSelectedRow();
				String id = tab.getValueAt(index, 0).toString();
				new FicheUser(id);
			}
		});
		sc = new JScrollPane();
		sc.setViewportView(tab);
		
		nouveauCompteGui();
		parametreGui();
		desactiverWindow();
	}
	
	///////////////////////////// exception /////////////////////////////////
	public void nomPrenomSaisir(String [] nomComplet)throws MyException {
		int nbr = 0;
		for (int i = 0; i < nomComplet.length; i++) {
			if(!nomComplet[i].equalsIgnoreCase(""))
				nbr++;
		}
		if(nbr > 2) {
			throw new MyException("Ecrire un seule nom et un seule prenom separer par un espace\n"
			+ "si vous avez deux noms ou prenoms separer par un ters '-' \n"
			+ "exp : benrabah mohamed amine  ==> ne faire pas ca\n"
			+ "benrabah mohamed-amine  ==> ca est correct\n"
			+ "benrabah-mohamed amine  ==> ca est correct");
		}
	}
	public void champSaisir(String nomm, String prenom, String tel, String email,String mdp)throws MyException {
		if(nomm.equals("") && prenom.equals("") && tel.equals("") && email.equals("") &&
				mdp.equals("")) {
			throw new MyException("il faut remplir les champs possede une etoile *");
		}
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
	
	////////////////////////// manager window //////////////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	////////////////////////// fin manager window //////////////////////
	
	////////////////////////// design window ///////////////////////////
	public void parametreGui() {
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
	    c.insets = new Insets(10, 10, 10, 10);
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 2;
	    c.gridheight = 3;
	    c.weightx = 0;
	    c.weighty = 1;
	    this.add(content1, c);
	    
	    c.gridx = 0;
	    c.gridy = 3;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.weighty = 1.0;
	    this.add(search, c);

	    c.gridx = 4;
	    c.gridy = 0;
	    c.gridwidth = 6;
	    c.gridheight = 4;
	    c.weightx = c.weighty = 1.0;
	    this.add(content3, c);
	    
	    c.gridx = 0;
	    c.gridy = 4;
	    c.gridwidth = 10;
	    c.gridheight = 3;
	    c.weighty = 7;
	    this.add(sc,c);
	}
	public void nouveauCompteGui() {
		NewCompte = new JLabel();
		NewCompte.setIcon(new ImageIcon(ParametreWindow.class.getResource("/ressourcesHomeManipulation/nouveau-compte.png")));
		
		// nom complet
		nom = new JLabel("Nom Complet *");
		etatCombo = new JComboBox<String>();
		DefaultComboBoxModel<String> etatModel = new DefaultComboBoxModel<String>();
		etatModel.addElement("Mr");
		etatModel.addElement("Mme");
		etatCombo.setModel(etatModel);
		etatCombo.setSelectedIndex(0);
		etatCombo.setBackground(Color.white);
		nomNom = new HintTextField("Nom");
		nomPrenom = new HintTextField("Prenom");
		
		// adresse
		adr = new JLabel("Adresse");
		adrField = new HintTextField("adresse");
		
		// telephone
		tel = new JLabel("Telephone *");
		telField = new HintTextField("tel : (0X) XX XXX XXX");
		
		// contact
		contact = new JLabel("Contact *");
		contactField = new HintTextField("E-mail");
		
		// specialite
		specialite = new JLabel("Specialite");
		specialiteField = new HintTextField("specialite");
		
		// medecin
		medecin = new JLabel("Medecin");
		medecinCombo = new JComboBox<String>();
		DefaultComboBoxModel<String> medecinModel = new DefaultComboBoxModel<String>();
		medecinModel.addElement("Oui");
		medecinModel.addElement("Non");
		medecinCombo.setModel(medecinModel);
		medecinCombo.setSelectedIndex(0);
		medecinCombo.setBackground(Color.white);
		medecinCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(medecinCombo.getSelectedIndex() == 0) 
					specialiteField.setEnabled(true);
				else
					specialiteField.setEnabled(false);
				specialiteField.setText("");
			}
		});
		
		// date
		date = new JLabel("Date");
		manuel = new JCheckBox();
		manuel.setBackground(Color.white);	
		manuel.setSelected(false);
		
		dateChooser = new JDateChooser();
		dateChooser.setDate(new Date());
		dateChooser.setEnabled(false);
		
		// pour changer la date automatique
		manuel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean isTicked = manuel.isSelected();
				manuel.setSelected(isTicked);
				dateChooser.setEnabled(isTicked);
				dateChooser.setDate(new Date());
			}
		});
		
		// mot de passe
		mdp = new JLabel("Mot de passe *");
		mdpField = new HintTextField("8 caractere au minumun");
		
		//recupere les donnes dans la base des donnes
		updateTable();
		//nbr des utilisateur
		updateUserNbr();
		
		// button
		ajouter = new JButton();
		ajouter.setIcon(new ImageIcon(ParametreWindow.class.getResource("/ressourcesHomeManipulation/Ajouter_parmetre.png")));
		ajouter.setBorderPainted(false);
		ajouter.setContentAreaFilled(false);
		ajouter.setToolTipText("Ajouter un utilisateur apres remplir les champs.");
		// only for test apres change
		ajouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					champSaisir(nomNom.getText().toString().trim(), nomPrenom.getText().toString().trim(), telField.getText().toString().trim(),
							contactField.getText().toString().trim(), mdpField.getText().toString().trim());
					
					String nomTxt = nomNom.getText().toString().trim();
					String prenomTxt = nomPrenom.getText().toString().trim();
					String nomUtlTxt = nomTxt.concat(".").concat(prenomTxt);
					String adrTxt = adrField.getText().toString().trim();
					String telTxt = telField.getText().toString().trim();
					String emailTxt = contactField.getText().toString().trim();
					String mdpTxt = mdpField.getText().toString().trim();
					Date d = dateChooser.getDate();
					String specialiteTxt = specialiteField.getText().toString().trim();
					
					Sexe sexe = Sexe.Homme;
					if(etatCombo.getSelectedIndex() == 1) 
						sexe = Sexe.Femme;
					
					Medecin med = new Medecin(nomTxt, prenomTxt, telTxt, adrTxt, emailTxt, sexe, d, nomUtlTxt, mdpTxt, specialiteTxt);
					
					if(!nomTxt.equals("") && !prenomTxt.equals("") && !telTxt.equals("") && !emailTxt.equals("") &&
							!mdpTxt.equals("")) {
						if(medecinCombo.getSelectedIndex() == 1) 
							FormLogIn.userNow.ajouterUser(false,med);
						else 
							FormLogIn.userNow.ajouterUser(true,med);
						updateTable();
						updateUserNbr();
						viderChamps();
					}
				} catch (MyException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		annuler = new JButton();
		annuler.setIcon(new ImageIcon(ParametreWindow.class.getResource("/ressourcesHomeManipulation/Annuler_parmetre.png")));
		annuler.setBorderPainted(false);
		annuler.setToolTipText("Vider les champs et annuler d'ajouter un nouveau utilisateur.");
		annuler.setContentAreaFilled(false);
		// renisialier les champs de saisie
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viderChamps();
			}
		});
		
		gui(); //tassimin les champs de saisir
	}
	public void gui() {
		content3= new JPanel();
		content3.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
		content3.setBackground(Color.white);
		
		//////////////////////////1 raw : title ////////////////////////
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 7;
		c.gridheight = 1;
		c.weightx = 0.5; 
		c.weighty = 0.5;
		content3.add(NewCompte, c);
		
		//////////////////////////2 raw : nom ////////////////////////
		c.insets = new Insets(0, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5; 
		content3.add(nom, c);
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		content3.add(etatCombo, c);
		
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		content3.add(nomNom, c);
		
		c.gridx = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		content3.add(nomPrenom, c);
		
		//////////////////////////3 raw : adresse ////////////////////////
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		content3.add(adr, c);
		
		c.gridx = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 7;
		content3.add(adrField, c);
		
		//////////////////////////4 raw : telephone ////////////////////////
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		content3.add(tel, c);
		
		c.gridx = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 7;
		content3.add(telField, c);
		
		//////////////////////////5 raw : contact ////////////////////////
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 0.5;
		content3.add(contact, c);
		
		c.gridx = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 7;
		content3.add(contactField, c);
		
		//////////////////////////6 raw : medecin ////////////////////////
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 0.5;
		content3.add(medecin, c);
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		content3.add(medecinCombo, c);
		
		//////////////////////////7 raw : date ////////////////////////
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 0.5;
		content3.add(date, c);
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		content3.add(manuel, c);
		
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		content3.add(dateChooser, c);
		
		//////////////////////////8 raw : specialite ////////////////////////
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 0.5;
		content3.add(specialite, c);
		
		c.gridx = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 7;
		content3.add(specialiteField,c);
		
		//////////////////////////9 raw : mot de passe ////////////////////////
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 0.5;
		content3.add(mdp, c);
		
		c.gridx = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 7;
		content3.add(mdpField, c);
		
		////////////////////////////// last raw ////////////////////////////////
		
		c.gridx = 2;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.1;
		content3.add(ajouter, c);
		
		c.gridx = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		content3.add(annuler, c);
	}
	////////////////////// fin design window ///////////////////////////
	
	//////////// vider les champs apres ajouter un user ////////////////
	public void viderChamps() {
		nomNom.setText("");
		nomPrenom.setText("");
		telField.setText("");
		adrField.setText("");
		contactField.setText("");
		specialiteField.setText("");
		mdpField.setText("");
		manuel.setSelected(false);
		dateChooser.setEnabled(false);
		medecinCombo.setSelectedIndex(0);
		etatCombo.setSelectedIndex(0);
		dateChooser.setDate(new Date());
	}
    ///////// fin vider les champs apres ajouter un user ///////////////
	
	//////////// methodes concerner les changement de table ////////////
	public void updateTable() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_utilisateur,nomUser,medecin,specialite,date_inscrit,tel from utilisateur";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			
			tab.setModel(DbUtils.resultSetToTableModel(resultat));
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
	}
	public void updateTable(String[] nomSearch) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_utilisateur,nomUser,medecin,specialite,date_inscrit,tel from utilisateur where "
				+ "nom = '" + nomSearch[0] + "' and prenom = '"+ nomSearch[1] + "'";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			
			tab.setModel(DbUtils.resultSetToTableModel(resultat));
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
	}
	public void updateUserNbr() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select count(id_utilisateur) from utilisateur";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			
			if(resultat.next()){
				String cnt = resultat.getString("count(id_utilisateur)");
				userNbr.setText("        "+cnt);
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
	}
	//////// fin methodes concerner les changement de table ////////////
}