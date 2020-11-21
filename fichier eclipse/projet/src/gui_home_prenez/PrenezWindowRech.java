package gui_home_prenez;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controllor.HintTextField;
import controllor.MyException;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;

public class PrenezWindowRech extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	private int idp;
	private JTextField search;
	private JComboBox<String> idCombo;
	private JLabel exist,titre;
	private Container content2;
	private JLabel listeTxt;
	private JTable tab;
	private JScrollPane sc;
	DefaultComboBoxModel<String> idModel;
	DefaultTableModel model;
	
	public PrenezWindowRech() {
		////////////////////////// title window //////////////////////////
		titre = new JLabel("Rechercher un Rendez-vous");
		titre.setFont(new Font("sans serif",Font.BOLD,30));
		titre.setForeground(new Color(203,22,47));
		
		///////////////////////// bar de recherche ///////////////////////
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
						//idModel = FormLogIn.userNow.rechercherPatientBD(nomSearch);
						idModel = FormLogIn.userNow.rechercherRdv(nomSearch);
						if(idModel.getSize()>0) { 
							idCombo.setModel(idModel);
							exist.setVisible(true);
							idCombo.setSelectedIndex(0);
						}
						else {
							exist.setVisible(false);
							tousRdv();
						}
						idCombo.setModel(idModel);
					}
				} catch (MyException e) {
					exist.setVisible(false);
					search.setText("");
					tousRdv();
					DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
					idCombo.setModel(emptyModel);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		///////////// afficher les info de rdv selectioner ///////////////
		idCombo = new JComboBox<String>();
		idCombo.setBackground(Color.white);
		idCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String donne[] = idCombo.getSelectedItem().toString().split(" ");
				// aff les info rdv choisir
				idp = Integer.parseInt(donne[1]);
				if(idModel.getSize()>0) {
					tousRdv(idp);
				}
			}
		});
		
		///////////// message pour afficher s il exite un rdv ////////////
		exist = new JLabel("cette nom existe dans la base des donnes");
		exist.setForeground(new Color(11,102,35));
		exist.setVisible(false);
		
		//////////////////// afficher tous les rdv dans la table ///////////////////////
		listeTxt = new JLabel("Liste des Rendez-vous et Visites:");
		
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
				boolean inscrit = Boolean.parseBoolean(tab.getValueAt(index, 4).toString());
				if(!inscrit)
					new AjouterPatientNoInscrit(idp,Integer.parseInt(tab.getValueAt(index, 0).toString()));
			}
		});
		sc = new JScrollPane();
		sc.setViewportView(tab);
		
		content2 = new JPanel();
		content2.setLayout(new BoxLayout(content2, BoxLayout.PAGE_AXIS));
		content2.setBackground(Color.white);
		content2.add(listeTxt);
		content2.add(sc);
		
		tousRdv();
		// design de tous Window
		prenezRechGui();
	}
	///////////////////////// manager d'afficher window ///////////////////////
	@Override
	public void activerWindow() {
		setVisible(true);
	}
	@Override
	public void desactiverWindow() {
		setVisible(false);
	}
	
	
	// recupere tous les facture de cette patient
	public void tousRdv() {
		model = new DefaultTableModel();
		model.addColumn("id_rdv");
		model.addColumn("date");
		model.addColumn("heure");
		model.addColumn("nom medecin");
		model.addColumn("inscrit");
		
		for (int i = 0; i < FormLogIn.userNow.getRdvs().size(); i++) {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
			model.addRow(new Object[] {FormLogIn.userNow.getRdvs().get(i).getId_rdv(),
					ft.format(FormLogIn.userNow.getRdvs().get(i).getDate()),
					FormLogIn.userNow.getRdvs().get(i).getHeure(),
					FormLogIn.userNow.getRdvs().get(i).getNomMedecin(),
					FormLogIn.userNow.getRdvs().get(i).isInscrit()});
		}
		tab.setModel(model);
	}
	public void tousRdv(int id) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		model = new DefaultTableModel();
		model.addColumn("id_rdv");
		model.addColumn("date");
		model.addColumn("heure");
		model.addColumn("nom medecin");
		model.addColumn("inscrit");
		
		for (int i = 0; i < FormLogIn.userNow.getRdvs().size(); i++) {
			if(FormLogIn.userNow.getRdvs().get(i).getId_patient() == id) {
				model.addRow(new Object[] {FormLogIn.userNow.getRdvs().get(i).getId_rdv(),
						ft.format(FormLogIn.userNow.getRdvs().get(i).getDate()),
						FormLogIn.userNow.getRdvs().get(i).getHeure(),
						FormLogIn.userNow.getRdvs().get(i).getNomMedecin(),
						FormLogIn.userNow.getRdvs().get(i).isInscrit()});
			}
		}
		tab.setModel(model);
	}
	
	
	///////////////////////// fin manager d'afficher window ///////////////////
	// design des parties de cette window
	public void prenezRechGui() {
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
	    c.insets = new Insets(10, 10, 10, 10);
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx = 0.5;
	    c.weighty = 0;
	    this.add(titre, c);
	    
	    c.gridy = 1;
	    this.add(search, c);
	    
	    c.gridy = 2;
	    this.add(exist, c);
	    
	    c.gridx = 0;
	    c.gridy = 3;
	    this.add(idCombo, c);
	    
	    c.gridy = 4;
	    c.gridwidth = 4;
	    c.gridheight = 4;
	    c.weightx = 0.15;
	    c.weighty = 1;
	    this.add(content2, c);
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
}
