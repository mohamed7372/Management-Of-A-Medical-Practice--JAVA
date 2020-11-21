package gui_home_facture;

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
import java.text.SimpleDateFormat;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controllor.ConnexionMySql;
import controllor.HintTextField;
import controllor.MyException;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;
import model.Patient;
import net.proteanit.sql.DbUtils;

public class FactureWindowMod extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private Container content1,content2,content1_1,content2_1;
	private JLabel titre,patient,nomComplet,adr,tel,exist;
	private JTextField total,search;
	private JComboBox<String> idCombo;
	private JTable tab;
	private JScrollPane sc;
	DefaultComboBoxModel<String> idModel;
	private JLabel changerMontant;
	private JTextField montantFiled;
	private JButton save;
	private String idSelecet,montantSelect;
	DefaultTableModel model;
	
	public FactureWindowMod() {
		titre = new JLabel("Facture Modifier");
		titre.setFont(new Font("sans serif",Font.BOLD,30));
		titre.setForeground(new Color(203,22,47));
		
		exist = new JLabel("cette patient exist dans la base des donnees");
		exist.setForeground(new Color(11,102,35));
		exist.setVisible(false);
		
		search = new HintTextField("rechercher (nom prenom)");
		search.setToolTipText("Saisir un nom et prenom pour rechercher les factures de cette patient.");
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
						idModel = FormLogIn.userNow.rechercherCons(nomSearch);
						if(idModel.getSize()>0) { 
							idCombo.setModel(idModel);
							exist.setVisible(true);
							idCombo.setSelectedIndex(0);
						}
						else {
							exist.setVisible(false);
							viderChamps();
							montantFiled.setText("Montant Total : 00.00 DA");
						}
						idCombo.setModel(idModel);
					}
				} catch (MyException e) {
					exist.setVisible(false);
					viderChamps();
					search.setText("");
					montantFiled.setText("Montant Total : 00.00 DA");
					changerMontant = new JLabel("selection la ligne de facture pour modifier montant");
					DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
					idCombo.setModel(emptyModel);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		idCombo = new JComboBox<String>();
		idCombo.setBackground(Color.white);
		idCombo.setToolTipText("Afficher tous les ID des patients de ces mot et prenom taper, "
				+ "plus le num tel pour preciser un patient, selectioner une ligne pour voir les facture de cette patient "
				+ "dans la table");
		idCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(idModel.getSize()>0) {
					String donne[] = idCombo.getSelectedItem().toString().split(" ");
					afficherInfoPatient(Integer.parseInt(donne[1]));
					toutfactures(Integer.parseInt(donne[1]));
					Patient p = FormLogIn.userNow.chercherPatient(Integer.parseInt(donne[1]));
					total.setText("Montant Total : " + String.valueOf(p.totalMontant()) + " DA");
				}
			}
		});
		
		//////////////////// patient info //////////////////:
		patient = new JLabel("Patient");
		patient.setFont(new Font("sans serif",Font.BOLD,16));
		
		nomComplet = new JLabel("xxxx xxxxx");
		
		adr = new JLabel("xxxxxxxxxxx");
		
		tel = new JLabel("Tel : xxxxxxxxxx");
		
		content1_1 = new JPanel();
		content1_1.setLayout(new BoxLayout(content1_1, BoxLayout.PAGE_AXIS));
		content1_1.setBackground(Color.white);
		content1_1.add(patient);
		content1_1.add(nomComplet);
		content1_1.add(adr);
		content1_1.add(tel);
		
		content1 = new JPanel();
		content1.setLayout(new BoxLayout(content1, BoxLayout.LINE_AXIS));
		content1.add(content1_1);
		
		///////////////////// table info ///////////////////////
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
				idSelecet = tab.getValueAt(index, 0).toString();
				montantSelect = tab.getValueAt(index, 1).toString();
				changerMontant.setText("modifier la montant de la facture n°= " + idSelecet + " :");
				montantFiled.setText(montantSelect);
			}
		});
		sc = new JScrollPane();
		sc.setViewportView(tab);
		
		//////////////////////// total de montant ////////////////
		total = new JTextField("Montant Total : 00.00 DA");
		total.setEditable(false);
		total.setForeground(Color.white);
		total.setBackground(new Color(203,22,47));
		
		///////////////////////// changer montant ///////////////
		changerMontant = new JLabel("selection la ligne de facture pour modifier montant");
		
		montantFiled = new HintTextField("montant (exp: 123.00 DA)");
		
		save = new JButton();
		save.setIcon(new ImageIcon(FactureWindowMod.class.getResource("/ressourcesHomeManipulation/save_parametre.png")));
		save.setBorderPainted(false);
		save.setContentAreaFilled(false);
		save.setToolTipText("selectioner une ligne dans la table pour peuvez modifier la montant et sauvegarder.");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tab.getSelectedRow()!=-1) {
					try {
						double montantTxt = Double.parseDouble(montantFiled.getText().toString().trim());
						montantSaisir(montantTxt);
						FormLogIn.userNow.modifierFacture(Integer.parseInt(idSelecet),montantTxt);
					} 
					catch (MyException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					catch (Exception ev) {
						JOptionPane.showMessageDialog(null, "le champ montant\n"
								+ "est de type decimale \nexp = 2.45",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				updateTable();
				montantFiled.setText("montant (exp: 123.00 DA)");
				total.setText("Montant Total : 0.00 DA");
				search.setText("");
				DefaultComboBoxModel<String> empty = new DefaultComboBoxModel<String>();
				idCombo.setModel(empty);
			}
		});
		
		content2_1 = new JPanel();
		content2_1.setLayout(new BoxLayout(content2_1, BoxLayout.LINE_AXIS));
		content2_1.add(Box.createRigidArea(new Dimension(100, 0)));
		content2_1.add(montantFiled);
		content2_1.add(Box.createRigidArea(new Dimension(50, 0)));
		content2_1.add(save);
		content2_1.setBackground(Color.white);
		
		content2 = new JPanel();
		content2.setLayout(new BoxLayout(content2, BoxLayout.PAGE_AXIS));
		content2.setBackground(Color.white);
		content2.add(Box.createRigidArea(new Dimension(0, 10)));
		content2.add(Box.createRigidArea(new Dimension(0, 10)));
		content2.add(sc);
		content2.add(Box.createRigidArea(new Dimension(0, 10)));
		content2.add(total);
		content2.add(Box.createRigidArea(new Dimension(0, 10)));
		content2.add(changerMontant);
		content2.add(Box.createRigidArea(new Dimension(0, 10)));
		content2.add(content2_1);
		content2.add(Box.createRigidArea(new Dimension(0, 100)));
		
		factureGui();
		desactiverWindow();
		viderChamps();
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
	public void montantSaisir(double montant)throws MyException {
		if(montant<0.0) {
			throw new MyException("Votre montant est negatif (0.0 < montant)\n saisir une montant positif");
		}
	}
	
	//////////////////////// recuperer info patient //////////////////
	public void afficherInfoPatient(int idp) {
		Patient p = FormLogIn.userNow.chercherPatient(idp);
		String nomBD = p.getNom();
		String prenomBD = p.getPrenom();
		String adrBD = p.getAdr();
		String telBD = p.getTel();
		
		nomComplet.setText(nomBD.concat(" ").concat(prenomBD));
		if(adrBD.equalsIgnoreCase(""))
			adr.setText("adr : (vide)");
		adr.setText("adr : " + adrBD);
		tel.setText("tel : " + telBD);
    }
	
	/////////// recupere tous les facture de cette patient////////////
	public void toutfactures(int idp) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		model = new DefaultTableModel();
		model.addColumn("id_facture");
		model.addColumn("montant");
		model.addColumn("date_facture");
		Patient p = FormLogIn.userNow.chercherPatient(idp);
		for (int i = 0; i < p.getFactures().size(); i++) {
			model.addRow(new Object[] {p.getFactures().get(i).getId_facture(),
					p.getFactures().get(i).getMontant(),
					ft.format(p.getFactures().get(i).getDate_facture())});
		}
		tab.setModel(model);
	}
	
	///////////////////////// vider les champs de saisir /////////////
	public void viderChamps() {
		nomComplet.setText("xxxx xxxxx");
		adr.setText("xxxxxxxxxxx");
		tel.setText("Tel : xxxxxxxxxx");
		total.setText("Montant Total : 00.00 DA");
		
		updateTable();
	}
	
	//////////////////////////// mise a jour la table ////////////////
	public void updateTable() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_facture,montant,date_facture from facture";
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
	
	/////////////////////////// manager window ///////////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	////////////////////////////// methodes gui //////////////////////
	public void factureGui() {
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
	    c.insets = new Insets(10, 10, 10, 10);
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    c.weighty = 0.4;
	    this.add(titre, c);
	    
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.weighty = 0.1;
	    this.add(search, c);

	    c.gridy = 2;
	    this.add(exist, c);
	    
	    c.gridy = 3;
	    this.add(idCombo, c);
	    
	    c.gridx = 2;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.weightx = 0.05;
	    this.add(content1, c);
	    
	    c.gridx = 0;
	    c.gridy = 4;
	    c.gridwidth = 3;
	    c.gridheight = 14;
	    c.weighty = 4;
	    this.add(content2, c);
	}
	
}
