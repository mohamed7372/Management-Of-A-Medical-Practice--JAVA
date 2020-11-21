package gui_home_prenez;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
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
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import category.Sexe;
import controllor.ConnexionMySql;
import controllor.HintTextField;
import controllor.MyException;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;
import model.Patient;
import model.Rdv;

public class PrenezWindowAjt extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private int idp;
	private JTextField search;
	private JComboBox<String> idCombo;
	private JLabel exist,titre;
	private Container content1,content2;
	private JLabel nom,prenom,tel,dateNaiss,date,heure,medecin,sexe;
	private JTextField nomField,prenomField,telField;
	private JDateChooser dateChooser,dateNaissChooser;
	private JComboBox<String> medecinCombo;
	private JButton ajouter,annuler;
	DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
	private JComboBox<String> heureCombo,minuteCombo,sexeCombo;
	
	public PrenezWindowAjt() {
		titre = new JLabel("Ajouter un Rendez-vous");
		titre.setFont(new Font("sans serif",Font.BOLD,30));
		titre.setForeground(new Color(203,22,47));
		
		///////////////////// ici pour la rechercher ////////////
		search = new HintTextField("rechercher (nom prenom)");
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
							desactiverChamps();
						}
						else {
							exist.setVisible(false);
							activerChamps();
							nomField.setText("");
							prenomField.setText("");
							telField.setText("");
							dateNaissChooser.setDate(new Date());
							sexeCombo.setSelectedIndex(0);
							
						}
						idCombo.setModel(idModel);
					}
				} catch (MyException e) {
					viderChamps();
					sexeCombo.setSelectedIndex(0);
					activerChamps();
					exist.setVisible(false);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// si trouve deux patient ont meme nom ///////////////
		idCombo = new JComboBox<String>();
		idCombo.setBackground(Color.white);
		idCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(idModel.getSize()>0) {
					String donne[] = idCombo.getSelectedItem().toString().split(" ");
					idp = Integer.parseInt(donne[1]);
					infoPatient(idp);
				}
			}
		});
		
		exist = new JLabel("exist dans la base des donnes");
		exist = new JLabel("cette patient exist dans la base des donnees");
		exist.setForeground(new Color(11,102,35));
		exist.setVisible(false);
		
		////////////////////// info ////////////////////
		nom = new JLabel("Nom *");
		prenom = new JLabel("Prenom *");
		tel = new JLabel("Tel *");
		dateNaiss = new JLabel("Date Naissance");
		sexe = new JLabel("Sexe *");
		date = new JLabel("Date *");
		heure = new JLabel("Heure *");
		medecin = new JLabel("Nom Medecin");
		
		nomField = new HintTextField("votre nom");
		nomField.setBackground(Color.white);
		prenomField = new HintTextField("votre prenom");
		prenomField.setBackground(Color.white);
		telField = new HintTextField("Tel: (0X) XX XXX XXX");
		telField.setBackground(Color.white);
		dateNaissChooser = new JDateChooser();
		dateNaissChooser.setBackground(Color.white);
		dateNaissChooser.setDate(new Date());
		
		sexeCombo = new JComboBox<String>();
		sexeCombo.setBackground(Color.white);
		DefaultComboBoxModel<String> sexeModel = new DefaultComboBoxModel<String>();
		sexeModel.addElement("Homme");
		sexeModel.addElement("Femme");
		sexeCombo.setModel(sexeModel);
		
		dateChooser = new JDateChooser();
		dateChooser.setDate(new Date());
		date.setBackground(Color.white);

		heureCombo = new JComboBox<String>();
		heureCombo.setBackground(Color.white);
		DefaultComboBoxModel<String> heureModel = new DefaultComboBoxModel<String>();
		heureModel.addElement("08");
		heureModel.addElement("09");
		heureModel.addElement("10");
		heureModel.addElement("11");
		heureModel.addElement("12");
		heureModel.addElement("13");
		heureModel.addElement("14");
		heureModel.addElement("15");
		heureModel.addElement("16");
		heureModel.addElement("17");
		heureModel.addElement("18");
		heureModel.addElement("19");
		heureCombo.setModel(heureModel);
		
		minuteCombo = new JComboBox<String>();
		minuteCombo.setBackground(Color.white);
		DefaultComboBoxModel<String> minuteModel = new DefaultComboBoxModel<String>();
		minuteModel.addElement("00");
		minuteModel.addElement("15");
		minuteModel.addElement("30");
		minuteModel.addElement("45");
		minuteCombo.setModel(minuteModel);
		
		medecinCombo = new JComboBox<String>();
		medecinCombo.setBackground(Color.white);
		medecinCombo.setModel(nomMedecins());
		
		
		infoVisiteGui();
		
		///////////////////// buttons /////////////////////////////
		ajouter = new JButton();
		ajouter.setIcon(new ImageIcon(PrenezWindowAjt.class.getResource("/ressourcesHomeManipulation/Ajouter_parmetre.png")));
		ajouter.setBorderPainted(false);
		ajouter.setContentAreaFilled(false);
		ajouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date dateTxt = dateChooser.getDate();
				String heureTxt = heureCombo.getSelectedItem().toString().concat(":")
						.concat(minuteCombo.getSelectedItem().toString());
				String medecinTxt = medecinCombo.getSelectedItem().toString();
				
				try {
					SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
					String date1 = ft.format(dateTxt);
					Date date2=new SimpleDateFormat("yyyy/MM/dd").parse(date1);  
					verifierRdvDateLibre(heureTxt, date2);
					
					int idrdv;
					if(FormLogIn.userNow.getRdvs().size() == 0) 
						idrdv = 0;
					else
						idrdv = FormLogIn.userNow.getRdvs().get(FormLogIn.userNow.getRdvs().size()-1).getId_rdv() + 1;
					boolean inscritTxt = idModel.getSize()==0;
					
					Rdv r = new Rdv(idrdv, date2, heureTxt, medecinTxt, idp,!inscritTxt);
					
					
					if(!inscritTxt) {
						FormLogIn.userNow.ajouterRdv(r);
						viderChamps();
						exist.setVisible(false);
					}
					
					else {
						try {
							champSaisir(nomField.getText().toString().trim(), prenomField.getText().toString().trim(),
									telField.getText().toString().trim(), dateNaissChooser.getDate());
							
							int idpatient;
							if(FormLogIn.userNow.getPatients().size() == 0)
								idpatient = 0;
							else
								idpatient = FormLogIn.userNow.getPatients().get(FormLogIn.userNow.getPatients().size()-1).getId_patient() + 1;
							
							Sexe s = Sexe.Homme;
							if(sexeCombo.getSelectedItem().toString().trim().equalsIgnoreCase("Femme"))
								s = Sexe.Femme; 
							
							Patient p = new Patient(idpatient, nomField.getText().toString().trim(), prenomField.getText().toString().trim(),
									telField.getText().toString().trim(), "",s);
							r.setId_patient(idpatient);
							p.setInscrit(false);
							p.setDate_naiss(dateNaissChooser.getDate());
							
							boolean tr = false;
							for (int i = 0; i < FormLogIn.userNow.getPatients().size(); i++) {
								String s1 = FormLogIn.userNow.getPatients().get(i).getNom();
								String s2 = FormLogIn.userNow.getPatients().get(i).getPrenom();
								String s3 = FormLogIn.userNow.getPatients().get(i).getTel();
								if(nomField.getText().trim().equalsIgnoreCase(s1) && prenomField.getText().trim().equalsIgnoreCase(s2) &&
										telField.getText().trim().equalsIgnoreCase(s3)) {
									JOptionPane.showMessageDialog(null, "cette patient exist deja (id = " + FormLogIn.userNow.getPatients().get(i).getId_patient() +" )");
									tr = true;
									break;
								}
							}
							if(!tr) {
								FormLogIn.userNow.ajouterRdvNoInscirt(r, p);
								System.out.println();
								System.out.println(r.toString());
								viderChamps();
								exist.setVisible(false);
							}
						} catch (MyException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (MyException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, this.getClass() +" " + e1.getMessage());
				}
			}
		});
		
		
		annuler = new JButton();
		annuler.setIcon(new ImageIcon(PrenezWindowAjt.class.getResource("/ressourcesHomeManipulation/Annuler_parmetre.png")));
		annuler.setBorderPainted(false);
		annuler.setContentAreaFilled(false);
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				viderChamps();
				exist.setVisible(false);
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
		// cacher cette window
		desactiverWindow();
	}
	
	///////////////////////recuperer les info d'un patient/////////////////////////
	public void infoPatient(int idp) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from patient where id_patient = " + idp;
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			if(resultat.next()) {
				String nomBD = resultat.getString("nom");
				String prenomBD = resultat.getString("prenom");
				String telBD = resultat.getString("tel");
				String dateBD = resultat.getString("date_naiss");
				
				nomField.setText(nomBD);
				prenomField.setText(prenomBD);
				telField.setText(telBD);
				Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(dateBD);
				dateNaissChooser.setDate(date1);
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
	}
	
	///////////////// recuperer tous les nom des tous les medecins/////////////////
	public DefaultComboBoxModel<String> nomMedecins(){
		DefaultComboBoxModel<String> medecinModel = new DefaultComboBoxModel<String>();
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select nom,prenom,specialite from utilisateur where medecin = 1";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String nomBD = resultat.getString("nom");
				String prenomBD = resultat.getString("prenom");
				String specialiteBD = resultat.getString("specialite");
				
				medecinModel.addElement(nomBD + " " + prenomBD + " " + specialiteBD.toUpperCase());
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return medecinModel;
	}
	
	//////////////////// desactiver les champs de saise et activer///////////////////
	public void desactiverChamps() {
		nomField.setEditable(false);
		prenomField.setEditable(false);
		telField.setEditable(false);
		dateNaissChooser.setEnabled(false);
		sexeCombo.setEnabled(false);
	}
	public void activerChamps() {
		nomField.setEditable(true);
		prenomField.setEditable(true);
		telField.setEditable(true);
		dateNaissChooser.setEnabled(true);
		sexeCombo.setEnabled(true);
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
	public void champSaisir(String nom,String prenom,String tel,Date date)throws MyException {
		Date d = new Date();
		if(nom.equalsIgnoreCase("") || prenom.equalsIgnoreCase("") || tel.equalsIgnoreCase("")) {
			throw new MyException("Il faut remplir les champs possede une etoile '*'");
		}
		else if(nom.trim().split(" ").length>1 || nom.trim().split(" ").length>1) {
			throw new MyException("S'il vous plait ne laisse pas les espaces entre les mots,\n"
					+ "si vous avez par exp 2 prenom spare par un \"-\" \n"
					+ "exp : mohamed amine ==> faux\n"
					+ "mohamed-amine ==> vrai.");
		}
		else if(tel.length()<10 || tel.length()>15) {
			throw new MyException("Votre num telephone est depasse la taille (entre 10 et 15 chiffres)\n "
					+ "saisir un num telephone dans cette intervalle");
		}
		else if(date.compareTo(d)>0) {
			throw new MyException("Votre date naissance est faux \n "
					+ "donnez date inf a la date actuelle");
		}
	}
	public void verifierRdvDateLibre(String heure,Date date)throws MyException {
		for (int i = 0; i < FormLogIn.userNow.getRdvs().size(); i++) {
			Date d = FormLogIn.userNow.getRdvs().get(i).getDate();
			String h = FormLogIn.userNow.getRdvs().get(i).getHeure();
			if(date.equals(d)) {
				if(heure.equals(h)) {
					throw new MyException("Cette heure est occuper par un autre patient.\n"
						+ "verifier Agenda pour choisir une heure libre");
				}
			}
			else if(1<2) {
				SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd/E/HH/mm");
				String dateCurrent[] = ft.format(new Date()).split("/");
				String dateChoisir[] = ft.format(date).split("/");
				
				if(dateChoisir[3].equalsIgnoreCase("Fri")) {
					throw new MyException("vous etes choisir le jour Vendredi");
				}
				else {
					boolean res = false;
					if(Integer.parseInt(dateChoisir[0]) < Integer.parseInt(dateCurrent[0])) {
						throw new MyException("vous etes choisir une date faux\nvous devez choisir date supp a la date current");
					}
					else if(Integer.parseInt(dateChoisir[0]) == Integer.parseInt(dateCurrent[0])){
						if(Integer.parseInt(dateChoisir[1]) < Integer.parseInt(dateCurrent[1])) {
							throw new MyException("vous etes choisir un mois faux\nvous devez choisir mois supp a le mois current");
						}
						else if(Integer.parseInt(dateChoisir[1]) == Integer.parseInt(dateCurrent[1])) {
							if(Integer.parseInt(dateChoisir[2]) < Integer.parseInt(dateCurrent[2])) {
								throw new MyException("vous etes choisir un jour faux\nvous devez choisir jour supp a le jour current");
							}
							else if(Integer.parseInt(dateChoisir[2]) == Integer.parseInt(dateCurrent[2])) {
								if(Integer.parseInt(heure.substring(0,2)) < Integer.parseInt(dateCurrent[4])) {
									throw new MyException("vous etes choisir une heure faux\nvous devez choisir heure supp a l'heure current");
								}
								else if(Integer.parseInt(heure.substring(0,2)) == Integer.parseInt(dateCurrent[4])) {
									if(Integer.parseInt(heure.substring(3,5)) <= Integer.parseInt(dateCurrent[5]) && !res) {
										res = true;
										throw new MyException("vous etes choisir une minute faux\nvous devez choisir minute supp minute current");
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	////////////////////////////// vides les champs ///////////////////////////////
	public void viderChamps() {
		activerChamps();
		nomField.setText("");
		prenomField.setText("");
		telField.setText("");
		dateNaissChooser.setDate(new Date());
		search.setText("");
		DefaultComboBoxModel<String> empty = new DefaultComboBoxModel<String>();
		idCombo.setModel(empty);
		dateChooser.setDate(new Date());
		heureCombo.setSelectedIndex(0);
		minuteCombo.setSelectedIndex(0);
		sexeCombo.setSelectedIndex(0);
	}
	
    //////////////////////////manager d'afficher window////////////////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	//////////////////// design des parties de cette window ////////////////////////
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
	    
    	//////////////////////////1 raw : nom ////////////////////////
	    c.insets = new Insets(10, 10, 10, 10);
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5; 
	    c.weighty = 0.5;
	    content1.add(nom, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0;
	    content1.add(nomField, c);

	    //////////////////////////2 raw : prenom ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(prenom, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 7;
	    content1.add(prenomField, c);
	    
	    //////////////////////////3 raw : tel ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(tel, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 7;
	    content1.add(telField, c);

	    //////////////////////////4 raw : email ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(dateNaiss, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 7;
	    content1.add(dateNaissChooser, c);
	    
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(sexe, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 7;
	    content1.add(sexeCombo, c);
	    
	    //////////////////////////5 raw : date ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(date, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0;
	    content1.add(dateChooser, c);
	    
	    //////////////////////////6 raw : heure ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(heure, c);
	    
	    c.gridx++;
	    c.gridwidth = 1;
	    content1.add(heureCombo, c);
	    
	    c.gridx++;
	    c.gridwidth = 1;
	    content1.add(minuteCombo, c);
	    
	    //////////////////////////7 raw : nom de medecin ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(medecin, c);
	    
	    c.gridx = 1;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0;
	    content1.add(medecinCombo, c);
	}

	
}
	
