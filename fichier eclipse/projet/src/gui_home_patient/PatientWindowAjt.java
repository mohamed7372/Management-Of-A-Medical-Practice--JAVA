package gui_home_patient;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import category.Groupage;
import category.Sexe;
import controllor.HintTextField;
import controllor.MyException;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;
import model.Patient;

public class PatientWindowAjt extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	private JTextField search;
	private JComboBox<String> idCombo;
	private JLabel exist,titre;
	private Container content1,content2;
	private JLabel nom,prenom,tel,email,dateNaiss,sexe,adr,groupage,poid,taille ,tension,maladeChro;
	private JTextField nomField,prenomField,telField,emailField,adrField,poidField,tailleField,tensionField;
	private JCheckBox maladesCheck;
	private JTextArea malades;
	private JComboBox<String> sexeCombo,groupageCombo,rhCombo;
	private JDateChooser dateChooseNaiss,dateInscrit;
	private JScrollPane scp;
	private JButton ajouter,annuler;
	DefaultComboBoxModel<String> idModel;
	
	public PatientWindowAjt() {
		//////////////////////////// title /////////////////////////////
		titre = new JLabel("Ajouter un Patient");
		titre.setFont(new Font("sans serif",Font.BOLD,30));
		titre.setForeground(new Color(203,22,47));
		
		///////////////////// ici pour la rechercher ///////////////////
		search = new HintTextField("rechercher (nom prenom)");
		search.setToolTipText("Saisir nom et prenom pour chercher un patient.");
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
					if(!nameComplet.equals("")) {
						idModel = FormLogIn.userNow.rechercherPatientBD(nomSearch);
						if(idModel.getSize()>0) { 
							idCombo.setModel(idModel);
							exist.setVisible(true);
							idCombo.setSelectedIndex(0);
						}
						else {
							exist.setVisible(false);
							viderChamps();
						}
						idCombo.setModel(idModel);
					}
				} catch (MyException e) {
					exist.setVisible(false);
					viderChamps();
					search.setText("");
					DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
					idCombo.setModel(emptyModel);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		///////////////////// les ids de nom chercher ///////////////
		idCombo = new JComboBox<String>();
		idCombo.setBackground(Color.white);
		idCombo.setToolTipText("Afficher tous les ID des patients de ces mot et prenom taper, "
				+ "plus le num tel pour preciser un patient.");
		
		exist = new JLabel("exist dans la base des donnes");
		exist.setForeground(new Color(11,102,35));
		exist.setVisible(false);
		
		///////////////////////// nom des champs ///////////////////////////////
		nom = new JLabel("Nom *");
		prenom = new JLabel("Prenom *");
		dateNaiss = new JLabel("Date Naissance *");
		sexe = new JLabel("Sexe *");
		adr = new JLabel("Adresse");
		tel = new JLabel("Tel *");
		email = new JLabel("E-mail");
		groupage = new JLabel("Groupe Sanguin *");
		poid = new JLabel("Poid(kg) *");
		taille = new JLabel("Taille(m) *");
		tension = new JLabel("Tension *");
		maladeChro = new JLabel("Malades Chroniques");
		
		///////////////////////// champs de saisie /////////////////////////////
		nomField = new HintTextField("votre nom");
		nomField.setBackground(Color.white);
		prenomField = new HintTextField("votre prenom");
		prenomField.setBackground(Color.white);
		dateChooseNaiss = new JDateChooser();
		dateChooseNaiss.setBackground(Color.white);
		dateChooseNaiss.setDate(new Date());
		dateInscrit = new JDateChooser();
		dateInscrit.setDate(new Date());
		dateInscrit.setBackground(Color.white);
		adrField = new HintTextField("Adresse");
		adrField.setBackground(Color.white);
		telField = new HintTextField("Tel: (0X) XX XXX XXX");
		telField.setBackground(Color.white);
		emailField = new HintTextField("xxxxxxxxx@xxxx.xxx");
		emailField.setBackground(Color.white);
		poidField = new HintTextField("exp : 65.6 kg");
		poidField.setBackground(Color.white);
		tailleField = new HintTextField("exp : 1.75 m");
		tailleField.setBackground(Color.white);
		tensionField = new HintTextField("exp : 12.5");
		tensionField.setBackground(Color.white);
		maladesCheck = new JCheckBox();
		maladesCheck.setBackground(Color.white);
		maladesCheck.setToolTipText("Activer pour ecrire les malades chroniques de cette patinets.");
		malades = new JTextArea(10, 30);
		malades.setBackground(new Color(203,22,47));
		malades.setForeground(Color.white);
		malades.setEnabled(false);
		scp = new JScrollPane();
		scp.setViewportView(malades);
		
		sexeCombo = new JComboBox<String>();
		sexeCombo.setBackground(Color.white);
		DefaultComboBoxModel<String> sexeModel = new DefaultComboBoxModel<String>();
		sexeModel.addElement("H");
		sexeModel.addElement("F");
		sexeCombo.setModel(sexeModel);
		
		groupageCombo = new JComboBox<String>();
		groupageCombo.setBackground(Color.white);
		DefaultComboBoxModel<String> groupageModel = new DefaultComboBoxModel<String>();
		groupageModel.addElement("O");
		groupageModel.addElement("A");
		groupageModel.addElement("B");
		groupageModel.addElement("AB");
		groupageCombo.setModel(groupageModel);
		
		rhCombo = new JComboBox<String>();
		rhCombo.setBackground(Color.white);
		DefaultComboBoxModel<String> rhModel = new DefaultComboBoxModel<String>();
		rhModel.addElement("+");
		rhModel.addElement("-");
		rhCombo.setModel(rhModel);
		
		infoVisiteGui();
		
		/////////////////////////// action check box //////////////////////////////
		maladesCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				malades.setEnabled(maladesCheck.isSelected());
				malades.setText("");
			}
		});
		
		////////////////////////// button ajouter /////////////////////////////////
		ajouter = new JButton();
		ajouter.setIcon(new ImageIcon(PatientWindowAjt.class.getResource("/ressourcesHomeManipulation/Ajouter_parmetre.png")));
		ajouter.setBorderPainted(false);
		ajouter.setContentAreaFilled(false);
		ajouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					champSaisir(nomField.getText().trim(), prenomField.getText().trim(), telField.getText().trim(),
							poidField.getText().trim(), tensionField.getText().trim(), tailleField.getText().trim(),
							emailField.getText().trim());
					
					String nomTxt = nomField.getText().toString().trim();
					String prenomTxt = prenomField.getText().toString().trim();
					String adrTxt = adrField.getText().toString().trim();
					String telTxt = telField.getText().toString().trim();
					String emailTxt = emailField.getText().toString().trim();
					
					Date dateNaissTxt = dateChooseNaiss.getDate();
					verifierDate(dateNaissTxt);
					
					double tailleTxt = Double.parseDouble(tailleField.getText().trim());
					double poidTxt = Double.parseDouble(poidField.getText().trim());
					double tensionTxt = Double.parseDouble(tensionField.getText().trim());
					Sexe sexe = Sexe.Homme;
					if(sexeCombo.getSelectedIndex() == 1) 
						sexe = Sexe.Femme;
					String groupeTxt = groupageCombo.getSelectedItem().toString().concat(rhCombo.getSelectedItem().toString());
					String malad_chroniqueTxt = malades.getText().trim();
					
					Patient p = new Patient(0,nomTxt, prenomTxt, telTxt, adrTxt, emailTxt, sexe, dateInscrit.getDate(),
						dateNaissTxt, poidTxt, tailleTxt, tensionTxt, grpSang(groupeTxt), malad_chroniqueTxt);
					FormLogIn.userNow.ajouterPatient(p);
					viderChamps();
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
		
		/////////////////////////// button annuler ///////////////////////////
		annuler = new JButton();
		annuler.setIcon(new ImageIcon(PatientWindowAjt.class.getResource("/ressourcesHomeManipulation/Annuler_parmetre.png")));
		annuler.setBorderPainted(false);
		annuler.setContentAreaFilled(false);
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				viderChamps();
			}
		});
		
		content2 = new JPanel();
		content2.setLayout(new BoxLayout(content2, BoxLayout.LINE_AXIS));
		content2.setBackground(Color.white);
		content2.add(Box.createRigidArea(new Dimension(400, 0)));
		content2.add(ajouter);
		content2.add(Box.createRigidArea(new Dimension(70, 0)));
		content2.add(annuler);

		
		prenezAjtGui();
		desactiverWindow();
	}

	/////////////////////////////////// exceptions /////////////////////////////////
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
	
    /////////////////////////// manager d'afficher window //////////////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	//////////////////////////// vider les champs de saisie ////////////////////////
	public void viderChamps() {
		nomField.setText("");
		prenomField.setText("");
		emailField.setText("");
		adrField.setText("");
		tensionField.setText("");
		malades.setText("");
		malades.setEnabled(false);
		sexeCombo.setSelectedIndex(0);
		telField.setText("");
		groupageCombo.setSelectedIndex(0);
		rhCombo.setSelectedIndex(0);
		tailleField.setText("");
		tensionField.setText("");
		poidField.setText("");
		maladesCheck.setSelected(false);
		dateInscrit.setDate(new Date());
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
	
	////////////////////////////// design gui window ///////////////////////////////
	public void prenezAjtGui() {
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
	    c.insets = new Insets(10, 10, 10, 10);
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    c.weighty = 0;
	    this.add(titre, c);
	    
	    c.gridy = 1;
	    this.add(search, c);
	    
	    c.gridx = 2;
	    this.add(exist, c);
	    
	    c.gridx = 0;
	    c.gridy = 2;
	    this.add(idCombo, c);
	    
	    c.gridy = 3;
	    c.gridwidth = 4;
	    c.gridheight = 4;
	    c.weightx = 1;
	    c.weighty = 1;
	    this.add(content1, c);
	    
	    c.gridy = 7;
	    c.gridx = 0;
	    c.gridwidth = 4;
	    c.gridheight = 1;
	    c.weightx = 1;
	    c.weighty = 0;
	    this.add(content2, c);
	}
    public void infoVisiteGui() {
		content1= new JPanel();
		content1.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; 
		content1.setBackground(Color.white);
	    
    	//////////////////////////1 raw : nom et prenom ////////////////////////
	    c.insets = new Insets(10, 10, 10, 10);
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.5;
	    content1.add(nom, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.weightx = 1;
	    content1.add(nomField, c);
	    
	    c.gridx = 3;
	    c.gridwidth = 1;
	    c.weightx = 0.2;
	    content1.add(prenom, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(prenomField, c);

	    //////////////////////////2 raw : dateNaiss et sexe /////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.2;
	    content1.add(dateNaiss, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.weightx = 1;
	    content1.add(dateChooseNaiss, c);
	    
	    c.gridx = 3;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.2;
	    content1.add(sexe, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.weightx = 1;
	    content1.add(sexeCombo, c);

	    //////////////////////////4 raw : email et tel ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.2;
	    content1.add(email, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.weightx = 1;
	    content1.add(emailField, c);
	    
	    c.gridx = 3;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.2;
	    content1.add(tel, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    c.gridheight = 1;
	    c.weightx = 1;
	    content1.add(telField, c);
	    
	    //////////////////////////5 raw : adr et groupage ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.weightx = 0.2;
	    content1.add(adr, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(adrField, c);
	    
	    c.gridx = 3;
	    c.gridwidth = 1;
	    c.weightx = 0.2;
	    content1.add(groupage, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 1;
	    c.weightx = 1;
	    content1.add(groupageCombo, c);
	    
	    c.gridx = 5;
	    c.gridwidth = 1;
	    c.weightx = 1;
	    content1.add(rhCombo, c);
	    
		//////////////////////////8 raw : poid et taille et tension ////////////////////////
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 1;
		c.weightx = 0.2;
		content1.add(poid, c);
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.weightx = 1;
		content1.add(poidField, c);
		
		c.gridx = 2;
		c.gridwidth = 1;
		content1.add(taille, c);
		
		c.gridx = 3;
		c.gridwidth = 1;
		content1.add(tailleField, c);
		
		c.gridx = 4;
		c.gridwidth = 1;
		content1.add(tension, c);
		
		c.gridx = 5;
		c.gridwidth = 1;
		content1.add(tensionField, c);
		
		/////////////////////////// last raw ////////////////////////
		c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.weightx = 0.2;
	    content1.add(maladeChro, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.weightx = 1;
	    content1.add(maladesCheck, c);
	    
	    c.gridx = 1;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.weightx = 1;
	    c.gridheight=2;
	    c.weighty = 8;
	    content1.add(scp, c);
	}
}
	
