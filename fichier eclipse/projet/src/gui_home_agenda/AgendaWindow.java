package gui_home_agenda;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;

public class AgendaWindow extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	private JTable tab;
	private JScrollPane sc;
	private JButton actualiser;
	
	public AgendaWindow() {
		////////////////////////// tableau information ////////////////////////
		tab = new JTable();
		tab.getTableHeader().setFont(new Font("Sans serif",Font.BOLD,15));
		tab.getTableHeader().setBackground(new Color(255,255,255));
		tab.getTableHeader().setForeground(new Color(203,22,47));
		tab.setRowHeight(50);
		tab.setFont(new Font("Sans serif",Font.BOLD,12));
		tab.setSelectionBackground(new Color(203,22,47));
		tab.setSelectionForeground(new Color(255,255,255));
		
		// les jours des semaines
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("");
		model.addColumn("Sam");
		model.addColumn("Dim");
		model.addColumn("Lun");
		model.addColumn("Mar");
		model.addColumn("Mer");
		model.addColumn("Jeu");
		
		// les horaires
		model.addRow(new Object[] {"      08.00 - 08.15 h","","","","","",""});
		model.addRow(new Object[] {"      08.15 - 08.30 h","","","","","",""});
		model.addRow(new Object[] {"      08.30 - 08.45 h","","","","","",""});
		model.addRow(new Object[] {"      08.45 - 09.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      09.00 - 09.15 h","","","","","",""});
		model.addRow(new Object[] {"      09.15 - 09.30 h","","","","","",""});
		model.addRow(new Object[] {"      09.30 - 09.45 h","","","","","",""});
		model.addRow(new Object[] {"      09.45 - 10.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      10.00 - 10.15 h","","","","","",""});
		model.addRow(new Object[] {"      10.15 - 10.30 h","","","","","",""});
		model.addRow(new Object[] {"      10.30 - 10.45 h","","","","","",""});
		model.addRow(new Object[] {"      10.45 - 11.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      11.00 - 11.15 h","","","","","",""});
		model.addRow(new Object[] {"      11.15 - 11.30 h","","","","","",""});
		model.addRow(new Object[] {"      11.30 - 11.45 h","","","","","",""});    
		model.addRow(new Object[] {"      11.45 - 12.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      12.00 - 12.15 h","","","","","",""});
		model.addRow(new Object[] {"      12.15 - 12.30 h","","","","","",""});    
		model.addRow(new Object[] {"      12.30 - 12.45 h","","","","","",""});    
		model.addRow(new Object[] {"      12.45 - 13.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      13.00 - 13.15 h","","","","","",""});
		model.addRow(new Object[] {"      13.15 - 13.30 h","","","","","",""});    
		model.addRow(new Object[] {"      13.30 - 13.45 h","","","","","",""});    
		model.addRow(new Object[] {"      13.45 - 14.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      14.00 - 14.15 h","","","","","",""});
		model.addRow(new Object[] {"      14.15 - 14.30 h","","","","","",""});    
		model.addRow(new Object[] {"      14.30 - 14.45 h","","","","","",""});
		model.addRow(new Object[] {"      14.45 - 15.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      15.00 - 15.15 h","","","","","",""});    
		model.addRow(new Object[] {"      15.15 - 15.30 h","","","","","",""});
		model.addRow(new Object[] {"      15.30 - 15.45 h","","","","","",""});
		model.addRow(new Object[] {"      15.45 - 16.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      16.00 - 16.15 h","","","","","",""});
		model.addRow(new Object[] {"      16.15 - 16.30 h","","","","","",""});
		model.addRow(new Object[] {"      16.30 - 16.45 h","","","","","",""});    
		model.addRow(new Object[] {"      16.45 - 17.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      17.00 - 17.15 h","","","","","",""});
		model.addRow(new Object[] {"      17.15 - 17.30 h","","","","","",""});    
		model.addRow(new Object[] {"      17.30 - 17.45 h","","","","","",""});
		model.addRow(new Object[] {"      17.45 - 18.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      18.00 - 18.15 h","","","","","",""});    
		model.addRow(new Object[] {"      18.15 - 18.30 h","","","","","",""});    
		model.addRow(new Object[] {"      18.30 - 18.45 h","","","","","",""});
		model.addRow(new Object[] {"      18.45 - 19.00 h","","","","","",""});
		
		model.addRow(new Object[] {"      19.00 - 19.15 h","","","","","",""});    
		model.addRow(new Object[] {"      19.15 - 19.30 h","","","","","",""});
		model.addRow(new Object[] {"      19.30 - 19.45 h","","","","","",""});
		model.addRow(new Object[] {"      19.45 - 20.00 h","","","","","",""});    
		
		
		tab.setModel(model);

		sc = new JScrollPane();
		sc.setViewportView(tab);

		///////////////button actualiser pour voir les nouveaux rendez vous/////////////
		actualiser = new JButton();
		actualiser.setIcon(new ImageIcon(AgendaWindow.class.getResource("/ressourcesHomeManipulation/actualiser.png")));
		actualiser.setBorderPainted(false);
		actualiser.setContentAreaFilled(false);
		actualiser.setToolTipText("Click pour actualiser la table agenda ( chaque semaine commence pour le samedi et charger "
				+ "automatiquement les heures des rendez-vous ).");
		actualiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				viderTab();
				chargerRdv();
			}
		});

		chargerRdv();
		homeGui();
		desactiverWindow();
	}
	
	/////////////////////////////// charger les rdvs /////////////////////////
	public void chargerRdv() {
		int row=0,col=0,jour;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd/E");
		String dateCurrent[] = ft.format(new Date()).split("/");
		// remplir le tableau
		for (int i = 0; i < FormLogIn.userNow.getRdvs().size(); i++) {
			// prendre un par un rdv et classer dans le tableau
			String date2[] = ft.format(FormLogIn.userNow.getRdvs().get(i).getDate()).split("/");
			// verifier l'anne de rdv si elle meme d'annee current
			if(Integer.parseInt(date2[0]) == Integer.parseInt(dateCurrent[0]) && 
					Integer.parseInt(date2[1]) == Integer.parseInt(dateCurrent[1])) {
				//le nbr de 1er jour de cette semaine 
				jour = recuperJourDebutSemaine(Integer.parseInt(dateCurrent[2]), dateCurrent[3]);
				// classer a partir le jour 
				switch (date2[3]) {
				case "Sat":
					col =1;
					if(jour == Integer.parseInt(date2[2])) {
						row = recuperEmplacementHeure(FormLogIn.userNow.getRdvs().get(i).getHeure().trim());
						if(row != -1) {
							String txt = FormLogIn.userNow.recupereInfoPatientRdv(FormLogIn.userNow.getRdvs().get(i).getId_patient());
							tab.setValueAt(txt, row, col);
						}
					}
					break;
				case "Sun":
					col =2;
					jour+=1;
					if(jour == Integer.parseInt(date2[2])) {
						row = recuperEmplacementHeure(FormLogIn.userNow.getRdvs().get(i).getHeure().trim());
						if(row != -1) {
							String txt = FormLogIn.userNow.recupereInfoPatientRdv(FormLogIn.userNow.getRdvs().get(i).getId_patient());
							tab.setValueAt(txt, row, col);
						}
					}
					break;
				case "Mon":
					col =3;
					jour+=2;
					if(jour == Integer.parseInt(date2[2])) {
						row = recuperEmplacementHeure(FormLogIn.userNow.getRdvs().get(i).getHeure().trim());
						if(row != -1) {
							String txt = FormLogIn.userNow.recupereInfoPatientRdv(FormLogIn.userNow.getRdvs().get(i).getId_patient());
							tab.setValueAt(txt, row, col);
						}
					}
					break;
				case "Tue":
					col =4;
					jour+=3;
					if(jour == Integer.parseInt(date2[2])) {
						row = recuperEmplacementHeure(FormLogIn.userNow.getRdvs().get(i).getHeure().trim());
						if(row != -1) {
							String txt = FormLogIn.userNow.recupereInfoPatientRdv(FormLogIn.userNow.getRdvs().get(i).getId_patient());
							tab.setValueAt(txt, row, col);
						}
					}
					break;
				case "Wed":
					col =5;
					jour+=4;
					if(jour == Integer.parseInt(date2[2])) {
						row = recuperEmplacementHeure(FormLogIn.userNow.getRdvs().get(i).getHeure().trim());
						if(row != -1) {
							String txt = FormLogIn.userNow.recupereInfoPatientRdv(FormLogIn.userNow.getRdvs().get(i).getId_patient());
							tab.setValueAt(txt, row, col);
						}
					}
					break;
				case "Thu":
					col =6;
					jour+=5;
					if(jour == Integer.parseInt(date2[2])) {
						row = recuperEmplacementHeure(FormLogIn.userNow.getRdvs().get(i).getHeure().trim());
						if(row != -1) {
							String txt = FormLogIn.userNow.recupereInfoPatientRdv(FormLogIn.userNow.getRdvs().get(i).getId_patient());
							tab.setValueAt(txt, row, col);
						}
					}
					break;
				default:
					break;
				}
			}
		}
	}
	// reucpere le jour de debut semaine
	private int recuperJourDebutSemaine(int dd,String nameDay) {
		if(nameDay.equalsIgnoreCase("Fri")) {
			dd+=1;
			return dd;
		}
		else {
			switch (nameDay) {
			case "Sat":
				return dd;
			case "Sun":
				return dd-1;
			case "Mon":
				return dd-2;
			case "Tue":
				return dd-3;
			case "Wed":
				return dd-4;
			case "Thu":
				return dd-5;
			default:
				return dd-6;
			}
		}
	}
	// recupere la case d'heure dans le tableau 
	private int recuperEmplacementHeure(String h) {
		switch (Integer.parseInt(h.substring(0,2))) {
		case 8:
			return 0+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 9:
			return 4+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 10:
			return 8+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 11:
			return 12+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 12:
			return 16+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 13:
			return 20+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 14:
			return 24+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 15:
			return 28+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 16:
			return 32+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 17:
			return 36+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 18:
			return 40+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		case 19:
			return 44+ determinerRowMinute(Integer.parseInt(h.substring(3,5)));
		default:
			return -1;
		}
	}
	// recupere la case de minute correct dans le tableau
	private int determinerRowMinute(int m) {
		switch (m) {
		case 0:
			return 0;
		case 15:
			return 1;
		case 30:
			return 2;
		default:
			return 3;
		}
	}
	// vider tableau 
	private void viderTab() {
		for (int i = 0; i < tab.getRowCount(); i++) {
			for (int j = 1; j < 7; j++) {
				tab.setValueAt("", i, j);
			}
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

	//////////////////////////////// design gui //////////////////////////////
	public void homeGui() {
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
	    c.insets = new Insets(10, 600, 10, 10);

	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 9;
	    c.gridheight = 2;
	    c.insets = new Insets(10, 10, 10, 10);
	    this.add(actualiser, c);
	    
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