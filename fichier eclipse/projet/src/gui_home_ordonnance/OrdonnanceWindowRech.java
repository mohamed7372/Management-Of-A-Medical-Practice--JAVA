package gui_home_ordonnance;

import java.awt.Color;
import java.awt.Container;
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

import javax.swing.DefaultComboBoxModel;
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
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;
import model.Patient;

public class OrdonnanceWindowRech extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	private JTextField search;
	private JComboBox<String> idCombo;
	private JLabel exist,titre;
	private Container content1;
	private JLabel nomDocteur,telCabinet,adrCabient ,nomPatient,agePatient,dateOrd;
	private JTextArea traitement;
	private JScrollPane scp;
	DefaultComboBoxModel<String> idModel;
	
	public OrdonnanceWindowRech() {
		////////////////////////////// title /////////////////////////////
		titre = new JLabel("Rechercher une Ordonnance");
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
						//idModel = FormLogIn.userNow.chercherPatient(nomSearch);
						idModel = FormLogIn.userNow.rechercherOrd(nomSearch);
						if(idModel.getSize()>0) { 
							idCombo.setModel(idModel);
							exist.setVisible(true);
							idCombo.setSelectedIndex(0);
						}
						else {
							exist.setVisible(false);
							dateOrd.setText("Date : xxxx/xx/xx");
							nomPatient.setText("xxxx xxxx");
							agePatient.setText("xx ans");
							traitement.setText("");
						}
					}
				} catch (MyException e) {
					exist.setVisible(false);
					search.setText("");
					dateOrd.setText("Date : xxxx/xx/xx");
					nomPatient.setText("xxxx xxxx");
					agePatient.setText("xx ans");
					traitement.setText("");
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
		nomDocteur = new JLabel("Docteur " + FormLogIn.userNow.getNom() + " " + FormLogIn.userNow.getPrenom());
		adrCabient = new JLabel("5 rue de saint paul Miliana");
		telCabinet = new JLabel("027 65 35 41");

		dateOrd = new JLabel("Date : xxxx/xx/xx");
		nomPatient = new JLabel("xxxx xxxx");
		agePatient = new JLabel("xx ans");
		
		traitement = new JTextArea(10, 30);
		traitement.setBackground(new Color(203,22,47));
		traitement.setForeground(Color.white);
		traitement.setEnabled(false);
		scp = new JScrollPane();
		scp.setViewportView(traitement);
		
		
		infoVisiteGui();
		// desactiver les champs jusqua trouver un patient 
		prenezAjtGui();
		// cacher cette window
		desactiverWindow();
	}

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
				int conjeBD = FormLogIn.userNow.getOrdonnances().get(i).getConjeMaladie();
				String traitementBD = FormLogIn.userNow.getOrdonnances().get(i).getTraitement();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
				String dateBD = ft.format(FormLogIn.userNow.getOrdonnances().get(i).getDate_ordonnance());
				
				dateOrd.setText("Date : " + dateBD);
				if(conjeBD == 0)
					traitement.setText(traitementBD);
				else if(conjeBD == 1)
					traitement.setText(traitementBD.concat("\n\nConje maladie : ")
							.concat(String.valueOf(conjeBD)).concat(" jour"));
				else
					traitement.setText(traitementBD.concat("\n\nConje maladie : ")
							.concat(String.valueOf(conjeBD)).concat(" jours"));
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
	    c.weighty = 0.1;
	    content1.add(nomDocteur, c);

	    //////////////////////////2 raw : dateNaiss et sexe /////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    content1.add(adrCabient, c);

	    //////////////////////////4 raw : email et tel ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    content1.add(telCabinet, c);
	    
	    //////////////////////////5 raw : adr et groupage ////////////////////////
	    c.gridy++;
	    c.gridx = 4;
	    c.gridwidth = 1;
	    content1.add(dateOrd, c);
	    
		//////////////////////////8 raw : poid et taille et tension ////////////////////////
	    c.gridy++;
		content1.add(nomPatient, c);
		
		/////////////////////////// last raw ////////////////////////
	    c.gridy++;
	    content1.add(agePatient, c);
	    
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.weightx = 1;
	    c.gridheight=5;
	    c.weighty = 12;
	    content1.add(scp, c);
	}
}
	
