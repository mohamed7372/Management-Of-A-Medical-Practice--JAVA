package gui_home_ordonnance;

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
import java.text.SimpleDateFormat;
import java.util.Date;

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

import category.Sexe;
import controllor.HintTextField;
import controllor.MyException;
import gui_home_parametre.FicheUser;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;
import model.Ordonnance;
import model.Patient;

public class OrdonnanceWindowMod extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	private JTextField search;
	private JComboBox<String> idCombo;
	private JLabel exist,titre;
	private Container content1,content2;
	private JLabel nomPatient,agePatient,dateOrd,conje;
	private String traitementBDD="";
	private Date dateBDD = new Date();
	private int conjeBDD = 0;
	private int indexConje;
	private JTextArea traitement;
	private JScrollPane scp;
	private JComboBox<String> conjeCombo;
	DefaultComboBoxModel<String> idModel;
	private JButton modifier,save,annuler;
	DefaultComboBoxModel<String> conjeModel;
	private int ido,idp;
	
	public OrdonnanceWindowMod() {
		////////////////////////////// title /////////////////////////////
		titre = new JLabel("Modifier une Ordonnance");
		titre.setFont(new Font("sans serif",Font.BOLD,30));
		titre.setForeground(new Color(203,22,47));
		
		///////////////////// ici pour la rechercher /////////////////////
		search = new HintTextField("rechercher (nom prenom)");
		search.setToolTipText("Saisir un nom et prenom pour afficher les ordonnance	de cette patient.");
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
						idModel = FormLogIn.userNow.rechercherOrd(nomSearch);
						if(idModel.getSize()>0) { 
							idCombo.setModel(idModel);
							exist.setVisible(true);
							idCombo.setSelectedIndex(0);
							modifier.setEnabled(true);
						}
						else {
							exist.setVisible(false);
							dateOrd.setText("Date : xxxx/xx/xx");
							nomPatient.setText("xxxx xxxx");
							agePatient.setText("xx ans");
							traitement.setText("");
							traitement.setEditable(false);
							conjeCombo.setEnabled(false);
							modifier.setEnabled(false);
							save.setEnabled(false);
							annuler.setEnabled(false);
							DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
							idCombo.setModel(emptyModel);
						}
					}
				} catch (MyException e) {
					exist.setVisible(false);
					search.setText("");
					dateOrd.setText("Date : xxxx/xx/xx");
					nomPatient.setText("xxxx xxxx");
					agePatient.setText("xx ans");
					traitement.setText("");
					traitement.setEditable(false);
					conjeCombo.setSelectedIndex(0);
					conjeCombo.setEnabled(false);
					modifier.setEnabled(false);
					save.setEnabled(false);
					annuler.setEnabled(false);
					DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
					idCombo.setModel(emptyModel);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		///////////////////// les ids de nom chercher ////////////////////
		idCombo = new JComboBox<String>();
		idCombo.setBackground(Color.white);
		idCombo.setToolTipText("Afficher tous les ID des patients de ces mot et prenom taper, "
				+ "plus le num tel, et les id des ordonnances pour preciser un patient.");
		idCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String donne[] = idCombo.getSelectedItem().toString().split(" ");
				idp = Integer.parseInt(donne[1]);
				ido = Integer.parseInt(donne[4]);
				// aff les info rdv choisir
				afficherInfoPatient(Integer.parseInt(donne[1]));
				afficherInfoOrd(Integer.parseInt(donne[4]));
			}
		});
		
		////////////////////////// existance patient /////////////////////
		exist = new JLabel("exist dans la base des donnes");
		exist.setForeground(new Color(11,102,35));
		exist.setVisible(false);
		
		////////////////////// noms des champs ///////////////////////////
		dateOrd = new JLabel("Date : xxxx/xx/xx");
		nomPatient = new JLabel("xxxx xxxx");
		agePatient = new JLabel("xx ans");
		
		traitement = new JTextArea(10, 30);
		traitement.setBackground(new Color(203,22,47));
		traitement.setForeground(Color.white);
		traitement.setEditable(false);
		scp = new JScrollPane();
		scp.setViewportView(traitement);
		
		conje = new JLabel("Conje maladie");
		conjeCombo = new JComboBox<String>(); 
		conjeCombo.setBackground(Color.white);
		conjeCombo.setEnabled(false);
		conjeModel = new DefaultComboBoxModel<String>();
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
		
		////////////////////////////// button annuler //////////////////////////
		annuler = new JButton();
		annuler.setBorderPainted(false);
		annuler.setIcon(new ImageIcon(FicheUser.class.getResource("/ressourcesHomeManipulation/annuler_parametre_2.png")));
		annuler.setContentAreaFilled(false);
		annuler.setToolTipText("Annuler les chanegement de cette ordonnance.");
		annuler.setEnabled(false);
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				traitement.setText(traitementBDD);
				traitement.setEditable(false);
				
				conjeCombo.setSelectedIndex(indexConje);
				conjeCombo.setEnabled(false);
				
				save.setEnabled(false);
				annuler.setEnabled(false);
				
				if(idModel.getSize() != 0)
					modifier.setEnabled(true);
			}
		});
		
		/////////////////////////////////// button modifier //////////////////////////
		modifier = new JButton();
		modifier.setIcon(new ImageIcon(FicheUser.class.getResource("/ressourcesHomeManipulation/modifier_parametre.png")));
		modifier.setBounds(20,580,100,40);
		modifier.setBorderPainted(false);
		modifier.setContentAreaFilled(false);
		modifier.setToolTipText("Click pour actvier les champs de saisir pour pouvez faire les chanegement de cette ordonnance.");
		modifier.setEnabled(false);
		modifier.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			traitement.setEditable(true);
			conjeCombo.setEnabled(true);
			save.setEnabled(true);
			annuler.setEnabled(true);
		}
		});
		
	
		
		///////////////////// button sauvegarder ///////////////////////////////
		save = new JButton();
		save.setIcon(new ImageIcon(FicheUser.class.getResource("/ressourcesHomeManipulation/save_parametre.png")));
		save.setBorderPainted(false);
		save.setContentAreaFilled(false);
		save.setToolTipText("Sauvegarder les chanegement de cette ordonnance.");
		save.setEnabled(false);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String traitementTxt = traitement.getText().toString().trim();
				String conje = conjeCombo.getSelectedItem().toString().substring(0,3).trim();

				Ordonnance o = new Ordonnance(ido, dateBDD, Integer.parseInt(conje), traitementTxt);
				o.setId_patient(idp);
				FormLogIn.userNow.modifierOrdonnance(o);
				
				if(idModel.getSize() != 0)
					modifier.setEnabled(true);
				save.setEnabled(false);
				annuler.setEnabled(false);
				
				traitement.setText("");
				traitement.setEditable(false);
				conjeCombo.setSelectedIndex(0);
				conjeCombo.setEnabled(false);
				
				dateOrd.setText("Date : xxxx/xx/xx");
				nomPatient.setText("xxxx xxxx");
				agePatient.setText("xx ans");
			}
		});
		
		content2 = new JPanel();
		content2.setLayout(new BoxLayout(content2, BoxLayout.LINE_AXIS));
		content2.setBackground(Color.white);
		content2.add(Box.createRigidArea(new Dimension(250, 0)));
		content2.add(modifier);
		content2.add(Box.createRigidArea(new Dimension(70, 0)));
		content2.add(save);
		content2.add(Box.createRigidArea(new Dimension(70, 0)));
		content2.add(annuler);

		
		infoVisiteGui();
		// desactiver les champs jusqua trouver un patient 
		prenezAjtGui();
		// cacher cette window
		desactiverWindow();
	}
	
	///////////////////////////// info d'un patient /////////////////////////

    /////////////////////// manager d'afficher window ////////////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	/////////////////////////////////// exceptions ///////////////////////////
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

	
	//////////////////// afficher info d'un patient //////////////////////////
	public void afficherInfoPatient(int idp) {
		Patient p = FormLogIn.userNow.chercherPatient(idp);
		String gender = "Mr ";
		if(p.getSexe().equals(Sexe.Femme))
			gender = "Mme ";
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		String da = ft.format(p.getDate_naiss());
		Date date = new Date();
		int anneCurrent = Integer.parseInt(ft.format(date));
		int ageCalculer = anneCurrent - Integer.parseInt(da);
		
		nomPatient.setText(gender.concat(p.getNom()).concat(" ").concat(p.getPrenom()));
		agePatient.setText(String.valueOf(ageCalculer).concat(" ans"));
    }
	public void afficherInfoOrd(int ido) {
		for (int i = 0; i < FormLogIn.userNow.getOrdonnances().size(); i++) {
			if(FormLogIn.userNow.getOrdonnances().get(i).getId_ordonnance() == ido) {
				conjeBDD = FormLogIn.userNow.getOrdonnances().get(i).getConjeMaladie();
				traitementBDD = FormLogIn.userNow.getOrdonnances().get(i).getTraitement();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
				dateBDD = FormLogIn.userNow.getOrdonnances().get(i).getDate_ordonnance();
				String dateb = ft.format(dateBDD);
				
				dateOrd.setText("Date : " + dateb);
				traitement.setText(traitementBDD);
				for (int j = 0; j < conjeModel.getSize(); j++) {
					if(Integer.parseInt(conjeModel.getElementAt(j).toString().substring(0,3).trim()) == conjeBDD) {
						conjeCombo.setSelectedIndex(j);
						indexConje = j;
						break;
					}
				}
			}
		}
    }
	
	////////////////////////// design gui window ////////////////////////////
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
	    
    	//////////////////////////1 raw //////////////////////////
	    c.insets = new Insets(10, 10, 10, 10);
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.2; 
	    c.weighty = 0.1;
	    content1.add(dateOrd, c);

	    //////////////////////////2 raw  ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    content1.add(nomPatient, c);

	    ////////////////////////// 3 raw ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    content1.add(agePatient, c);
	    
	    c.gridx = 0;
	    c.gridy++;
	    content1.add(conje, c);
	    
	    c.gridx = 1;
	    content1.add(conjeCombo, c);
	    
	    //////////////////////////4 raw ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.weightx = 1;
	    c.gridheight=5;
	    c.weighty = 12;
	    content1.add(scp, c);
	}
}
	
