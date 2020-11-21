package gui_home_patient;

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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controllor.HintTextField;
import controllor.MyException;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;

public class PatientWindowSupp extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	private JTextField search;
	private JComboBox<String> idCombo;
	private JLabel exist,titre;
	private Container content1;
	private JButton supp;
	DefaultComboBoxModel<String> idModel;
	DefaultTableModel model = new DefaultTableModel();
	
	public PatientWindowSupp() {
		///////////////////// title window ////////////////////////////////
		titre = new JLabel("Supprimer un Patient");
		titre.setFont(new Font("sans serif",Font.BOLD,30));
		titre.setForeground(new Color(203,22,47));
		
		///////////////////// bar de rechercher ///////////////////////////
		search = new HintTextField("rechercher (nom prenom)");
		search.setToolTipText("Saisir nom et prenom pour chercher un patient pour supprimer. ou saisez \"plus5\" pour"
				+ "pouvez supprimer les patients ont 5ans qui inscrit.");
		search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ev) {
				String nameComplet = search.getText().toString().trim();
				if(nameComplet.equalsIgnoreCase("plus5")) {
					idModel = FormLogIn.userNow.rechercherPatientPlus5BD();
					if(idModel.getSize()>0) { 
						idCombo.setModel(idModel);
						exist.setVisible(true);
						idCombo.setSelectedIndex(0);
						supp.setEnabled(true);
					}
					else {
						exist.setVisible(false);
						supp.setEnabled(false);
						DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
						idCombo.setModel(emptyModel);
					}
				}
				else {
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
								supp.setEnabled(true);
							}
							else {
								exist.setVisible(false);
								supp.setEnabled(false);
								DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
								idCombo.setModel(emptyModel);
							}
						}
					} catch (MyException e) {
						exist.setVisible(false);
						search.setText("");
						DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
						idCombo.setModel(emptyModel);
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		///////////// message pour afficher s il exite un rdv /////////////
		exist = new JLabel("exist dans la base des donnes");
		exist.setForeground(new Color(11,102,35));
		exist.setVisible(false);
		
		/////////////// afficher les rdv rechercher /////////////////////// 
		idCombo = new JComboBox<String>();
		idCombo.setBackground(Color.white);
		setToolTipText("Selectioner la ligne qui voulez supprimer.");
		
		//////////////////////// supprimer un patient /////////////////////
		supp = new JButton("Supprimer");
		supp.setForeground(Color.white);
		supp.setBackground(new Color(203,22,47));
		supp.setEnabled(false);
		supp.setToolTipText("Click pour supprimer patient selectioner dans la liste des ID.");
		supp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String []id = idCombo.getSelectedItem().toString().split(" ");
				FormLogIn.userNow.supprimerPatient(Integer.parseInt(id[1]));
				search.setText("");
				exist.setVisible(false);
				DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
				idCombo.setModel(idModel);
			}
		});
		
		/////////////////////////// les patient plus 5 ans ////////////////
		
		
		designGui();
		prenezAnnGui();
		desactiverWindow();
	}

	
	/////////////////////////////////// exceptions ///////////////////////
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
	
	//////////////////////// manager d'afficher window ///////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	//////////////////////// design de cette window //////////////////////
	public void prenezAnnGui() {
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
	    this.add(content1, c);
	}
	public void designGui() {
		content1= new JPanel();
		content1.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; 
		content1.setBackground(Color.white);
	    
    	//////////////////////////1 raw : search ////////////////////////
	    c.insets = new Insets(100, 20, 20, 20);
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5; 
	    c.weighty = 0.5;
	    content1.add(search, c);

	    //////////////////////////2 raw : exist ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    c.insets = new Insets(20, 20, 20, 20);
	    content1.add(exist, c);
	    
	    //////////////////////////3 raw : id ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(idCombo, c);
	    
	    //////////////////////////4 raw : button ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 5;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    content1.add(supp, c);
	    
	}
}
	
