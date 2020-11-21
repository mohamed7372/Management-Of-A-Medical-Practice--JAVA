package gui_home_patient;

import java.awt.BorderLayout;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controllor.ConnexionMySql;
import controllor.HintTextField;
import controllor.MyException;
import managerEntreFrame.ManagerWindow;
import net.proteanit.sql.DbUtils;

public class PatientWindowRech extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private Container content1,content2,content3,content4,content;
	private JLabel total,homme,femme,oldDossier;
	private JLabel nbrTotal,nbrHomme,nbrFemme,nbrOldDossier;
	private JButton reload;
	private JTextField search;
	private JTable tab;
	private JScrollPane sc;
	
	public PatientWindowRech() {
		///////////////////////// stat pour total //////////////////////////
		total = new JLabel("Total patient");
		total.setFont(new Font("sans serif",Font.BOLD,12));
		
		nbrTotal = new JLabel("0");
		nbrTotal.setFont(new Font("sans serif",Font.BOLD,20));
		nbrTotal.setForeground(new Color(203,22,47));
		
		content1 = new JPanel();
		content1.setLayout(new BorderLayout());
		content1.setBackground(Color.white);
		content1.add(nbrTotal,BorderLayout.CENTER);
		content1.add(total,BorderLayout.SOUTH);
		
		///////////////////////// stat pour homme //////////////////////////
		homme = new JLabel("Homme");
		homme.setFont(new Font("sans serif",Font.BOLD,12));
		
		nbrHomme = new JLabel("0");
		nbrHomme.setFont(new Font("sans serif",Font.BOLD,20));
		nbrHomme.setForeground(new Color(232,54,93));
		
		content2 = new JPanel();
		content2.setLayout(new BorderLayout());
		content2.setBackground(Color.white);
		content2.add(nbrHomme,BorderLayout.CENTER);
		content2.add(homme,BorderLayout.SOUTH);
		
		///////////////////////// stat pour femme //////////////////////////
		femme = new JLabel("Femme");
		femme.setFont(new Font("sans serif",Font.BOLD,12));
		
		nbrFemme = new JLabel("0");
		nbrFemme.setFont(new Font("sans serif",Font.BOLD,20));
		nbrFemme.setForeground(new Color(236,98,131));
		
		content3 = new JPanel();
		content3.setLayout(new BorderLayout());
		content3.setBackground(Color.white);
		content3.add(nbrFemme,BorderLayout.CENTER);
		content3.add(femme,BorderLayout.SOUTH);
		
		////////////////////// stat pour ancien dossier /////////////////////
		oldDossier = new JLabel("Plus 5 ans");
		oldDossier.setFont(new Font("sans serif",Font.BOLD,12));
		
		nbrOldDossier = new JLabel("0");
		nbrOldDossier.setFont(new Font("sans serif",Font.BOLD,20));
		nbrOldDossier.setToolTipText("Nombres des patients qui deja inscrit plus de 5 ans.");
		nbrOldDossier.setForeground(new Color(226,138,170));
		
		content4 = new JPanel();
		content4.setLayout(new BorderLayout());
		content4.setBackground(Color.white);
		content4.add(nbrOldDossier,BorderLayout.CENTER);
		content4.add(oldDossier,BorderLayout.SOUTH);
		
		//////////////// actualiser tableau de patient ///////////////////////
		reload = new JButton();
		reload.setIcon(new ImageIcon(PatientWindowRech.class.getResource("/ressourcesHomeManipulation/actualiser.png")));
		reload.setBorderPainted(false);
		reload.setContentAreaFilled(false);
		reload.setToolTipText("Actualiser les informations de tableau.");
		reload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateTable();
				statPatient();
			}
		});
		
		//////////////////////////// tous les stat ///////////////////////////
		content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.LINE_AXIS));
		content.setBackground(Color.white);
		content.add(content1);
		content.add(content2);
		content.add(content3);
		content.add(content4);
		content.add(reload);
		
		///////////////////////////// rechercher //////////////////////////////
		search = new HintTextField("Search");
		search.setToolTipText("Saisir nom et prenom pour chercher un patient et afficher dans le tableau.");
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
				} 
				catch (MyException e) {
					search.setText("");
					updateTable();
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		////////////////////////// tableau information ////////////////////////
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
				new FichePatient(id);
			}
		});
		sc = new JScrollPane();
		sc.setViewportView(tab);

		
		updateTable();
		homeGui();
		desactiverWindow();
		statPatient();
	}
	
	/////////////////////// mise a jout de table /////////////////////////////
	public void updateTable(String[] nomSearch) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_patient,nom,prenom,tel,sexe,date_inscp,maladeChro,groupage from patient where "
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
	public void updateTable() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_patient,nom,prenom,tel,sexe,date_inscp,maladeChro,groupage from patient";
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
	
	///////////////////////////// statique ///////////////////////////////////
	public void statPatient() {
		int totalPat = 0,nbrH=0,nbrF=0,nbrPlus5Ans=0;
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_patient,sexe,date_inscp from patient";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				totalPat++;
				String sexe = resultat.getString("sexe");
				if(sexe.equals("H")) 
					nbrH++;
				else
					nbrF++;
				String []d = resultat.getString("date_inscp").split("/");
				Date date = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy");
				int anneCurrent = Integer.parseInt(ft.format(date));
				int diff = anneCurrent - Integer.parseInt(d[0]);
				if(diff>0)
					nbrPlus5Ans++;
				
				nbrTotal.setText(String.valueOf(totalPat));
				nbrHomme.setText(String.valueOf(nbrH));
				nbrFemme.setText(String.valueOf(nbrF));
				nbrOldDossier.setText(String.valueOf(nbrPlus5Ans));
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
	}
	
	////////////////////////////// manager window ////////////////////////////
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

	//////////////////////////////// design gui //////////////////////////////
	public void homeGui() {
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
	    c.insets = new Insets(10, 600, 10, 10);

	    ///////////// serach ////////////////
	    c.gridx = 5;
	    c.gridy = 0;
	    c.gridwidth = 4;
	    c.gridheight = 1;
	    c.weighty = 0.1;
	    c.weightx =1;
	    this.add(search,c);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 9;
	    c.gridheight = 2;
	    c.insets = new Insets(10, 10, 10, 10);
	    this.add(content, c);
	    
	    c.gridx = 0;
	    c.gridy = 3;
	    c.gridwidth = 9;
	    c.gridheight = 5;
	    c.weighty = 1.0;
	    c.weightx =1.0;
	    c.insets = new Insets(10, 10, 10, 10);
	    this.add(sc,c);
	}
}