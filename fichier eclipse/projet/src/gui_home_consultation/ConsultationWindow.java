package gui_home_consultation;

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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controllor.HintTextField;
import controllor.MyException;
import gui_home_patient.FichePatient;
import gui_home_patient.PatientWindowAjt;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;
import model.Patient;

public class ConsultationWindow extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	private String idp;
	private JTextField search;
	private JComboBox<String> idCombo;
	private JLabel exist,titre;
	private Container content1,content2;
	private JLabel poidN,tensionN,tailleN,maladeChroTxt,maladeDiagTxt,observationTxt,ordonnance,analyseTxt,conje,montant;
	private JTextField poidNField,tailleNField,tensionNField,montantField;
	private JComboBox<String> conjeCombo;
	private JTextArea maladeChro,maladeDiag,observation,ordoArea,analyse;
	private JScrollPane scmc,scmd,scob,sco,sca;
	private JButton afficher,save,annuler;
	DefaultComboBoxModel<String> idModel;
	
	public ConsultationWindow() {
		///////////////////// title ///////////////////////////
		titre = new JLabel("Faire une consultation");
		titre.setFont(new Font("sans serif",Font.BOLD,30));
		titre.setForeground(new Color(203,22,47));
		
		////////////////////////////// ici pour la rechercher /////////////////////
		search = new HintTextField("rechercher (nom prenom)");
		search.setToolTipText("Saisir nom et prenom pour chercher un patient.");
		search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ev) {
				// faire ca pour recupere le nom et le prenom
				String nameComplet = search.getText().toString().trim();
				String []nomPrenom = nameComplet.split(" ");
				String []nomSearch = {"","","","","","","",""};
				int j=0;
				// copier les mots qui n est pas vides
				for (int i = 0; i < nomPrenom.length; i++) {
					if(!nomPrenom[i].equals("") && j<nomSearch.length) {
						nomSearch[j] = nomPrenom[i];
						j++;
					}
				}
				// verifier combien de mot saisir (il faut saisir juste 2 mots)
				try {
					nomPrenomSaisir(nomSearch);
					if(!nameComplet.equals("")) {
						//idModel = FormLogIn.userNow.rechercherPatientBD(nomSearch);
						idModel = FormLogIn.userNow.rechercherCons(nomSearch);
						if(idModel.getSize()>0) { 
							idCombo.setModel(idModel);
							exist.setVisible(true);
							save.setEnabled(true);
							annuler.setEnabled(true);
							activerChamps();
							idCombo.setSelectedIndex(0);
							afficher.setEnabled(true);
						}
						else {
							exist.setVisible(false);
							desactiverChamps();
							save.setEnabled(false);
							annuler.setEnabled(false);
							afficher.setEnabled(false);
						}
						idCombo.setModel(idModel);
					}
				} catch (MyException e) {
					exist.setVisible(false);
					desactiverChamps();
					annuler.setEnabled(false);
					save.setEnabled(false);
					afficher.setEnabled(false);
					search.setText("");
					DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
					idCombo.setModel(emptyModel);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		/////////////////////////// afficher le dossier patient ///////////////////
		afficher = new JButton();
		afficher.setIcon(new ImageIcon(ConsultationWindow.class.getResource("/ressourcesHomeManipulation/afficher_consultatoin.png")));
		afficher.setBorderPainted(false);
		afficher.setContentAreaFilled(false);
		afficher.setEnabled(false);
		afficher.setToolTipText("Click pour afficher le dossier de cette patient.");
		afficher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Patient p = FormLogIn.userNow.chercherPatient(Integer.parseInt(idp));
				if(p.isInscrit())
					new FichePatient(idp);
				else if(!p.isInscrit())
					JOptionPane.showMessageDialog(null, "cette patient n'est pas inscrit donc il n y a pas un dossier pour voir");
			}
		});
		
		////////////////////////// les ids de nom chercher ///////////////////////
		idCombo = new JComboBox<String>();
		idCombo.setBackground(Color.white);
		idCombo.setToolTipText("Afficher tous les ID des patients de ces mot et prenom taper, "
				+ "plus le num tel pour preciser un patient. selectioner une ligne pour recuperer "
				+ "les informations et faire votre consultation");
		idCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(idModel.getSize()>0) {
					String donne[] = idCombo.getSelectedItem().toString().split(" ");
					idp = donne[1];
				}
			}
		});
		
		////////////////////////// exit d'un patient /////////////////////////////
		exist = new JLabel("exist dans la base des donnes");
		exist.setForeground(new Color(11,102,35));
		exist.setVisible(false);
		
		///////////////////////// nom des champs /////////////////////////////////
		poidN = new JLabel("nouveau poid *");
		tailleN = new JLabel("nouvelle taille *");
		tensionN = new JLabel("nouvelle tension *");
		
		maladeChroTxt = new JLabel("Malades Chroniques");
		maladeDiagTxt = new JLabel("Malades Diagnostiques");
		observationTxt = new JLabel("Observation");
		ordonnance = new JLabel("Traitement/Medicament");
		analyseTxt = new JLabel("Les Analyses Demandes");
		
		conje = new JLabel("Conje maladie");
		montant = new JLabel("Montant *"); 
		
		///////////////////////// champs de saisie ///////////////////////////////
		poidNField = new HintTextField("exp : 65.6 kg");
		poidNField.setBackground(Color.white);
		tailleNField = new HintTextField("exp : 1.75 m");
		tailleNField.setBackground(Color.white);
		tensionNField = new HintTextField("exp : 12.5");
		tensionNField.setBackground(Color.white);
		
		maladeChro = new JTextArea();
		maladeChro.setBackground(new Color(203,22,47));
		maladeChro.setForeground(Color.white);
		scmc = new JScrollPane();
		scmc.setViewportView(maladeChro);
		
		maladeDiag = new JTextArea();
		maladeDiag.setBackground(new Color(203,22,47));
		maladeDiag.setForeground(Color.white);
		scmd = new JScrollPane();
		scmd.setViewportView(maladeDiag);
		
		observation = new JTextArea();
		observation.setBackground(new Color(203,22,47));
		observation.setForeground(Color.white);
		scob = new JScrollPane();
		scob.setViewportView(observation);
		
		ordoArea = new JTextArea();
		ordoArea.setBackground(new Color(203,22,47));
		ordoArea.setForeground(Color.white);
		sco = new JScrollPane();
		sco.setViewportView(ordoArea);
		
		analyse = new JTextArea();
		analyse.setBackground(new Color(203,22,47));
		analyse.setForeground(Color.white);
		sca = new JScrollPane();
		sca.setViewportView(analyse);
		
		
		conjeCombo = new JComboBox<String>(); 
		conjeCombo.setBackground(Color.white);
		DefaultComboBoxModel<String> conjeModel = new DefaultComboBoxModel<String>();
		conjeModel.addElement("0   jour");
		conjeModel.addElement("1   jour");
		conjeModel.addElement("2   jours");
		conjeModel.addElement("3   jours");
		conjeModel.addElement("4   jours");
		conjeModel.addElement("5   jours");
		conjeModel.addElement("7   jours");
		conjeModel.addElement("10  jours");
		conjeModel.addElement("15  jours");
		conjeModel.addElement("30  jours");
		conjeModel.addElement("45  jours");
		conjeModel.addElement("60  jours");
		conjeModel.addElement("90  jours");
		conjeModel.addElement("120 jours");
		conjeModel.addElement("150 jours");
		conjeModel.addElement("180 jours");
		conjeModel.addElement("210 jours");
		conjeModel.addElement("240 jours");
		conjeModel.addElement("270 jours");
		conjeModel.addElement("300 jours");
		conjeCombo.setModel(conjeModel);
		conjeCombo.setSelectedIndex(0);
		
		montantField = new HintTextField("exp : 2400 DA");
		montantField.setBackground(Color.white);
		
		infoVisiteGui();
		
		////////////////// buttons sauvegarder consultation ///////////////////////
		save = new JButton();
		save.setIcon(new ImageIcon(ConsultationWindow.class.getResource("/ressourcesHomeManipulation/save_consultation.png")));
		save.setBorderPainted(false);
		save.setContentAreaFilled(false);
		save.setEnabled(false);
		save.setToolTipText("Click pour sauvegarder consultation et nouveaux meseure et ajouter une facture.");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					champSaisir(poidNField.getText().trim(), tensionNField.getText().trim(), 
						tailleNField.getText().trim(), montantField.getText().trim());
					
					double tensionTxt = Double.parseDouble(tensionNField.getText().trim());
					double poidsTxt = Double.parseDouble(poidNField.getText().trim());
					double tailleTxt = Double.parseDouble(tailleNField.getText().trim());
					double montantTxt = Double.parseDouble(montantField.getText().trim());
					String jour = conjeCombo.getSelectedItem().toString().substring(0, 3).trim();
					
					String mcTxt = maladeChro.getText().trim();
					String mdTxt = maladeDiag.getText().trim();
					String obTxt = observation.getText().trim();
					String trTxt = ordoArea.getText().trim();
					String anTxt = analyse.getText().trim();
					
					String consul = "* Malades Diagnostiques :\n".concat(mdTxt).concat("\n")
							.concat("* Observation :\n").concat(obTxt).concat("\n")
							.concat("* Analyses Demande :\n").concat(anTxt).concat("\n")
							.concat("* Conje Maladie : ").concat(jour);
					
					FormLogIn.userNow.consulterPatient(Integer.parseInt(idp), tensionTxt, poidsTxt, tailleTxt,mcTxt,anTxt,
							consul, montantTxt,Integer.parseInt(jour),trTxt.concat("\n").concat("Analyses Demande :\n").concat(anTxt));
					viderChamps();
					desactiverChamps();
					exist.setVisible(false);
				} 
				catch (MyException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception ev) {
					JOptionPane.showMessageDialog(null, "les champs tension,poids,taille,montant\n"
							+ "sont de type decimale \nexp = 2.45",
							"Error", JOptionPane.ERROR_MESSAGE);
					System.err.println(ev.getMessage());
				}
			}
		});
		
		////////////////////// buttons annuler consultation ///////////////////////
		annuler = new JButton();
		annuler.setIcon(new ImageIcon(PatientWindowAjt.class.getResource("/ressourcesHomeManipulation/Annuler_parmetre.png")));
		annuler.setBorderPainted(false);
		annuler.setContentAreaFilled(false);
		annuler.setEnabled(false);
		annuler.setToolTipText("Click pour annuler le changement.");
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				viderChamps();
				desactiverChamps();
			}
		});
		
		content2 = new JPanel();
		content2.setLayout(new BoxLayout(content2, BoxLayout.LINE_AXIS));
		content2.setBackground(Color.white);
		content2.add(Box.createRigidArea(new Dimension(250, 0)));
		content2.add(save);
		content2.add(Box.createRigidArea(new Dimension(70, 0)));
		content2.add(annuler);
		content2.add(Box.createRigidArea(new Dimension(70, 0)));
		content2.add(afficher);
		
		prenezAjtGui();
		desactiverWindow();
		desactiverChamps();
	}

    //////////////////////// manager d'afficher window /////////////////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	///////////////////////// vider les champs de saisie ///////////////////////////
	public void viderChamps() {
		poidNField.setText("");
		tensionNField.setText("");
		tailleNField.setText("");
		conjeCombo.setSelectedIndex(0);
		montantField.setText("");
		maladeChro.setText("");
		maladeDiag.setText("");
		observation.setText("");
		ordoArea.setText("");
		analyse.setText("");
		search.setText("");
		DefaultComboBoxModel<String> idModelVide = new DefaultComboBoxModel<String>();
		idCombo.setModel(idModelVide);
	}
	
	////////////////////////// desactiver actvier les champs  //////////////////////
	public void desactiverChamps() {
		poidNField.setEnabled(false);
		tailleNField.setEnabled(false);
		tensionNField.setEnabled(false);
		maladeChro.setEnabled(false);
		maladeDiag.setEnabled(false);
		observation.setEnabled(false);
		ordoArea.setEnabled(false);
		analyse.setEnabled(false);
		conjeCombo.setEnabled(false);
		montantField.setEnabled(false);
		
		afficher.setEnabled(false);
	}
	public void activerChamps() {
		poidNField.setEnabled(true);
		tailleNField.setEnabled(true);
		tensionNField.setEnabled(true);
		maladeChro.setEnabled(true);
		maladeDiag.setEnabled(true);
		observation.setEnabled(true);
		ordoArea.setEnabled(true);
		analyse.setEnabled(true);
		conjeCombo.setEnabled(true);
		montantField.setEnabled(true);
		
		afficher.setEnabled(true);
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
	public void champSaisir(String poid,String tension,String taille,String montant)throws MyException {
		if(poid.equalsIgnoreCase("") || tension.equalsIgnoreCase("") || taille.equalsIgnoreCase("") || montant.equalsIgnoreCase("")) {
			throw new MyException("Il faut remplir les champs possede une etoile '*'");
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
		else if(Integer.parseInt(montant)<0.0) {
			throw new MyException("Votre montant est negatif (0.0 < montant)\n saisir une montant positif");
		}
	}
	
	
	/////////////////////////////// design gui window //////////////////////////////
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
	    content1.add(poidN, c);
	    
	    c.gridx = 1;
	    c.gridheight = 1;
	    c.weightx = 1;
	    content1.add(poidNField, c);
	    
	    c.gridx = 2;
	    c.weightx = 0.2;
	    content1.add(tailleN, c);
	    
	    c.gridx = 3;
	    c.weightx = 1;
	    content1.add(tailleNField, c);
	    
	    c.gridx = 4;
	    c.weightx = 0.2;
	    content1.add(tensionN, c);
	    
	    c.gridx = 5;
	    c.weightx = 1;
	    content1.add(tensionNField, c);
	    
	    //////////////////// area ////////////////////////
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.5;
	    content1.add(maladeChroTxt, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.weightx = 0.2;
	    content1.add(scmc, c);
	    
	    c.gridx = 0;
	    c.gridy = 2;
	    c.gridwidth = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.5;
	    content1.add(maladeDiagTxt, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.weightx = 0.2;
	    content1.add(scmd, c);
	    
	    c.gridx = 0;
	    c.gridy = 3;
	    c.gridwidth = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.5;
	    content1.add(observationTxt, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.weightx = 0.2;
	    content1.add(scob, c);
	    
	    c.gridx = 0;
	    c.gridy = 4;
	    c.gridwidth = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.5;
	    content1.add(ordonnance, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.weightx = 0.2;
	    content1.add(sco, c);
	    
	    c.gridx = 0;
	    c.gridy = 5;
	    c.gridwidth = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.5;
	    content1.add(analyseTxt, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.weightx = 0.2;
	    content1.add(sca, c);

	    ///////////////// montant et conje //////////////////////
	    c.gridx = 0;
	    c.gridy = 6;
	    c.gridwidth = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.5;
	    content1.add(conje, c);
	    
	    c.gridx = 1;
	    c.gridheight = 1;
	    c.weightx = 1;
	    content1.add(conjeCombo, c);
	    
	    c.gridx = 4;
	    c.weightx = 0.2;
	    content1.add(montant, c);
	    
	    c.gridx = 5;
	    c.weightx = 1;
	    content1.add(montantField, c);
	    
	}
}


	