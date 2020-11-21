package gui_home_statique;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import category.Sexe;
import gui_login.FormLogIn;
import managerEntreFrame.ManagerWindow;

public class StatiqueWindow  extends JPanel implements ManagerWindow{
	private static final long serialVersionUID = 1L;
	
	
	private Container content1,content2,content3,content4,content5;
	DefaultComboBoxModel<String> idModel;
	
	private JLabel titleGeneralStat;
	private JLabel total,plus5,rdv,inscrit,noInscrit,ord,facture,rdvEff;
	private JLabel totalVal,plus5Val,rdvVal,inscritVal,noInscritVal,ordVal,factureVal,rdvEffVal;
	private JLabel img,nom,email,iduser;
	private JLabel medecin,medecinSp,secretaire,titleUser;
	private JLabel medecinVal,medecinSpVal,secretaireVal,medecinHomFem,medecinSpHomFem,secretaireHomFem;
	private JLabel titleDiagLine;
	private JLabel titleDiagCir;
	private JButton actualiser;
	ChartPanel panelDiag,panelCir;
	DefaultPieDataset diffPatient;
	DefaultCategoryDataset nouPatient;
	int c1=0,c2=0,c3=0,c4=0;
	int age[];
	
	public StatiqueWindow() {
		///////////////////////// general stat ///////////////////////////////
		titleGeneralStat = new JLabel("Generale Statique");
		titleGeneralStat.setFont(new Font("sans serif",Font.BOLD,14));
		titleGeneralStat.setForeground(new Color(203,22,47));
		
		total = new JLabel("Total patient");
		total.setForeground(new Color(134,136,138));
		totalVal = new JLabel("(vide)");
		
		plus5 = new JLabel("Patient inscrit plus de 5 ans");
		plus5.setForeground(new Color(134,136,138));
		plus5Val = new JLabel("(vide)");
		
		rdv = new JLabel("Nbr rendez-vous");
		rdv.setForeground(new Color(134,136,138));
		rdvVal = new JLabel("(vide)");
		
		inscrit = new JLabel("Patient inscrit");
		inscrit.setForeground(new Color(134,136,138));
		inscritVal = new JLabel("(vide)");
		
		ord = new JLabel("Nbr ordonnance");
		ord.setForeground(new Color(134,136,138));
		ordVal = new JLabel("(vide)");
		
		facture = new JLabel("Nbr facture");
		facture.setForeground(new Color(134,136,138));
		factureVal = new JLabel("(vide)");
		
		noInscrit = new JLabel("Patient non inscrit");
		noInscrit.setForeground(new Color(134,136,138));
		noInscritVal = new JLabel("(vide)");
		
		rdvEff = new JLabel("Nbr rdv effectue");
		rdvEff.setForeground(new Color(134,136,138));
		rdvEffVal = new JLabel("(vide)");
		
		actualiser = new JButton();
		actualiser.setIcon(new ImageIcon(StatiqueWindow.class.getResource("/ressourcesHomeManipulation/actualiser.png")));
		actualiser.setBorderPainted(false);
		actualiser.setContentAreaFilled(false);
		actualiser.setToolTipText("Click pour recalculer les statiques.");
		actualiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chargerStat();
			}
		});
		
		
		///////////////////////// user info ///////////////////////////////
		img = new JLabel("",SwingConstants.CENTER);
		img.setIcon(new ImageIcon(StatiqueWindow.class.getResource("/resources/userLoginIcon.png")));
		String s = "Mr.";
		if(FormLogIn.userNow.getSexe().equals(Sexe.Femme))
			s = "Mme.";
		String nomTxt = s.concat(FormLogIn.userNow.getNom()).concat(" ").concat(FormLogIn.userNow.getPrenom());
		nom = new JLabel(nomTxt,SwingConstants.CENTER);
		nom.setForeground(new Color(134,136,138));
		email = new JLabel(FormLogIn.userNow.getEmail(),SwingConstants.CENTER);
		iduser = new JLabel("ID " + FormLogIn.userNow.getId_Med(),SwingConstants.CENTER);
		
		///////////////////////// user info ///////////////////////////////
		titleUser = new JLabel("Utilisateurs Statique");
		titleUser.setFont(new Font("sans serif",Font.BOLD,14));
		titleUser.setForeground(new Color(203,22,47));
		
		medecin = new JLabel("Medecin generaliste");
		medecin.setForeground(new Color(134,136,138));
		medecinVal = new JLabel("(vide)");
		medecinHomFem = new JLabel("(vide) homme / (vide) femme");
		
		medecinSp = new JLabel("Medecin specialite");
		medecinSp.setForeground(new Color(134,136,138));
		medecinSpVal = new JLabel("(vide)");
		medecinSpHomFem = new JLabel("(vide) homme / (vide) femme");
		
		secretaire = new JLabel("Secretaire");
		secretaire.setForeground(new Color(134,136,138));
		secretaireVal = new JLabel("(vide)");
		secretaireHomFem = new JLabel("(vide) homme / (vide) femme");
		
		///////////////////////// diagramme de ligne d'age des patients///////////////////////////////
		titleDiagLine = new JLabel("Diagrammes des categories d'age des patiens");
		titleDiagLine.setFont(new Font("sans serif",Font.BOLD,14));
		titleDiagLine.setForeground(new Color(203,22,47));
		
		nouPatient = new DefaultCategoryDataset();
		nouPatient.setValue(80, "ages ", "10");
		nouPatient.setValue(50, "ages ", "20");
		nouPatient.setValue(75, "ages ", "30");
		nouPatient.setValue(90, "ages ", "40");
		nouPatient.setValue(90, "ages ", "50");
		nouPatient.setValue(10, "ages ", "60");
		nouPatient.setValue(80, "ages ", "70");
		nouPatient.setValue(50, "ages ", "80");
		nouPatient.setValue(75, "ages ", "90");
		nouPatient.setValue(90, "ages ", "100");
		JFreeChart diag = ChartFactory.createLineChart(null,"ages categories", "Nbr patient",nouPatient
				,PlotOrientation.VERTICAL,false,true,false);
		diag.setBackgroundPaint(Color.white);
		
		CategoryPlot po = diag.getCategoryPlot();
		po.setRangeGridlinePaint(Color.black);
		panelDiag = new ChartPanel(diag);
		
		////////////// diagramme circulaire des patiens ont maladies chronique ou non/////////////
		titleDiagCir = new JLabel("Diagrammes des differents patients");
		titleDiagCir.setFont(new Font("sans serif",Font.BOLD,14));
		titleDiagCir.setForeground(new Color(203,22,47));
		
		diffPatient = new DefaultPieDataset();
		diffPatient.setValue("Homme pas des maladies Chroniques",new Integer(10));
		diffPatient.setValue("Femme pas des maladies Chroniques",new Integer(20));
		diffPatient.setValue("Homme aux maladies Chroniques",new Integer(30));
		diffPatient.setValue("Femme aux maladies Chroniques",new Integer(40));
		
		JFreeChart diag2 = ChartFactory.createPieChart(null, diffPatient, true,true,true);

		panelCir = new ChartPanel(diag2);
		
			
		genralStatGui();
		userGui();
		allUserGui();
		diagLineGui();
		diagCirGui();

		statGui();
		desactiverWindow();
		
		
	}
	///////////////// charger statistique (utiliser les fonctions de class medecin)/////////////////
	public void chargerStat() {
		totalVal.setText(String.valueOf(FormLogIn.userNow.totalPatient()));
		inscritVal.setText(String.valueOf(FormLogIn.userNow.patientInscrit()));
		noInscritVal.setText(String.valueOf(FormLogIn.userNow.patientNoInscrit()));
		ordVal.setText(String.valueOf(FormLogIn.userNow.nbrOrdonnance()));
		plus5Val.setText(String.valueOf(FormLogIn.userNow.nbrPatient5Plus()));
		factureVal.setText(String.valueOf(FormLogIn.userNow.nbrFacture()));
		rdvVal.setText(String.valueOf(FormLogIn.userNow.nbrRdv()));
		rdvEffVal.setText(String.valueOf(FormLogIn.userNow.nbrRdvEff()));
		
		medecinVal.setText(String.valueOf(FormLogIn.userNow.medecinStat()[0]));
		medecinHomFem.setText(String.valueOf(FormLogIn.userNow.medecinStat()[1] + " Homme / " + FormLogIn.userNow.medecinStat()[2] + " Femme"));
		medecinSpVal.setText(String.valueOf(FormLogIn.userNow.medecinSpStat()[0]));
		medecinSpHomFem.setText(String.valueOf(FormLogIn.userNow.medecinSpStat()[1] + " Homme / " + FormLogIn.userNow.medecinSpStat()[2] + " Femme"));
		secretaireVal.setText(String.valueOf(FormLogIn.userNow.secretaireStat()[0]));
		secretaireHomFem.setText(String.valueOf(FormLogIn.userNow.secretaireStat()[1] + " Homme / " + FormLogIn.userNow.secretaireStat()[2] + " Femme"));
		
		c1 = (int)FormLogIn.userNow.diagCirStat()[0]; 
		c2 =  (int)FormLogIn.userNow.diagCirStat()[1]; 
		c3 =  (int)FormLogIn.userNow.diagCirStat()[2]; 
		c4 =  (int)FormLogIn.userNow.diagCirStat()[3]; 
		
		diffPatient.setValue("Homme pas des maladies Chroniques",new Integer(c1));
		diffPatient.setValue("Femme pas des maladies Chroniques",new Integer(c2));
		diffPatient.setValue("Homme aux maladies Chroniques",new Integer(c3));
		diffPatient.setValue("Femme aux maladies Chroniques",new Integer(c4));
		
		age = FormLogIn.userNow.diagBarStat();
		
		nouPatient.setValue(age[0], "ages ", "10");
		nouPatient.setValue(age[1], "ages ", "20");
		nouPatient.setValue(age[2], "ages ", "30");
		nouPatient.setValue(age[3], "ages ", "40");
		nouPatient.setValue(age[4], "ages ", "50");
		nouPatient.setValue(age[5], "ages ", "60");
		nouPatient.setValue(age[6], "ages ", "70");
		nouPatient.setValue(age[7], "ages ", "80");
		nouPatient.setValue(age[8], "ages ", "90");
		nouPatient.setValue(age[9], "ages", "100");
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
	
	////////////////////////////// design gui window ///////////////////////////////
	public void statGui() {
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
	    c.insets = new Insets(10, 10, 10, 10);
	    
	    c.gridy = 4;
	    c.gridx = 0;
	    c.gridwidth = 2;
	    c.gridheight = 7;
	    c.weightx = 0.25;
	    c.weighty = 0;
	    //this.add(content2, c);
	    
	    c.gridy = 0;
	    c.gridx = 0;
	    c.gridwidth = 4;
	    c.gridheight = 7;
	    c.weightx = 0.75;
	    this.add(content1, c);
	    
	    c.gridy = 0;
	    c.gridx = 4;
	    c.gridwidth = 2;
	    c.gridheight = 7;
	    c.weightx = 0;
	    this.add(content2, c);
	    
	    c.gridy = 7;
	    c.gridx = 0;
	    c.gridwidth = 2;
	    c.gridheight = 4;
	    c.weightx = 0.5;
	    c.weighty = 0;
	    this.add(content3, c);
	    
	    c.gridy = 11;
	    c.gridx = 0;
	    c.gridwidth = 2;
	    c.gridheight = 4;
	    c.weightx = 0.5;
	    c.weighty = 1;
	    this.add(content4, c);
	    
	    c.gridy = 7;
	    c.gridx = 4;
	    c.gridwidth = 2;
	    c.gridheight = 8;
	    c.weightx = 0.5;
	    c.weighty = 1;
	    this.add(content5, c);
	  
	}
    public void genralStatGui() {
		content1= new JPanel();
		content1.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; 
		content1.setBackground(Color.white);
		c.insets = new Insets(5, 5, 5, 5);
	    
		//////////////////////////1 raw : nom et prenom ////////////////////////
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1; 
		c.weighty = 0;
		content1.add(titleGeneralStat, c);
		
    	//////////////////////////1 raw : nom et prenom ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content1.add(total, c);
	    
	    c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(plus5, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content1.add(rdv, c);

	    //////////////////////////2 raw : dateNaiss et sexe /////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content1.add(totalVal, c);
	    
	    c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(plus5Val, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content1.add(rdvVal, c);

	    //////////////////////////4 raw : email et tel ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content1.add(inscrit, c);
	    
	    c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(ord, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content1.add(facture, c);
	    
	    //////////////////////////5 raw : adr et groupage ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content1.add(inscritVal, c);
	    
	    c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(ordVal, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content1.add(factureVal, c);
	    
		//////////////////////////8 raw : poid et taille et tension ////////////////////////
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		content1.add(noInscrit, c);
		
	    c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(rdvEff, c);
		
		/////////////////////////// last raw ////////////////////////
		c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content1.add(noInscritVal, c);
	    
	    c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content1.add(rdvEffVal, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content1.add(actualiser, c);
	}
    public void userGui() {
		content2= new JPanel();
		content2.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; 
		content2.setBackground(Color.white);
		c.insets = new Insets(5, 5, 5, 5);
	    
		//////////////////////////1 raw : nom et prenom ////////////////////////
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1; 
		c.weighty = 0;
		content2.add(img, c);
		
    	//////////////////////////1 raw : nom et prenom ////////////////////////
	    c.gridx = 0;
	    c.gridy+=5;
	    c.gridwidth = 2;
	    content2.add(nom, c);
	    
	    //////////////////////////2 raw : dateNaiss et sexe /////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content2.add(email, c);

	    //////////////////////////4 raw : email et tel ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content2.add(iduser, c);
	}
    public void allUserGui() {
		content3= new JPanel();
		content3.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; 
		content3.setBackground(Color.white);
		c.insets = new Insets(5, 5, 5, 5);
	    
		//////////////////////////1 raw : nom et prenom ////////////////////////
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1; 
		c.weighty = 0;
		content3.add(titleUser, c);
		
    	//////////////////////////1 raw : nom et prenom ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content3.add(medecin, c);

		c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content3.add(medecinVal, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content3.add(medecinHomFem, c);
	    
	    //////////////////////////2 raw : dateNaiss et sexe /////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content3.add(medecinSp, c);

		c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content3.add(medecinSpVal, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content3.add(medecinSpHomFem, c);
	    
	    //////////////////////////2 raw : dateNaiss et sexe /////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 2;
	    content3.add(secretaire, c);

		c.gridx = 2;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    content3.add(secretaireVal, c);
	    
	    c.gridx = 4;
	    c.gridwidth = 2;
	    content3.add(secretaireHomFem, c);
	}
    public void diagLineGui() {
    	content4= new JPanel();
		content4.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; 
		content4.setBackground(Color.white);
		c.insets = new Insets(5, 5, 5, 5);
	    
		//////////////////////////1 raw : nom et prenom ////////////////////////
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1; 
		c.weighty = 0;
		content4.add(titleDiagLine, c);
		
    	//////////////////////////1 raw : nom et prenom ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 6;
	    c.weighty = 1;
	    content4.add(panelDiag, c);
    }
    public void diagCirGui() {
    	content5= new JPanel();
		content5.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH; 
		content5.setBackground(Color.white);
		c.insets = new Insets(5, 5, 5, 5);
	    
		//////////////////////////1 raw : nom et prenom ////////////////////////
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1; 
		c.weighty = 0;
		content5.add(titleDiagCir, c);
		
    	//////////////////////////1 raw : nom et prenom ////////////////////////
	    c.gridx = 0;
	    c.gridy++;
	    c.gridwidth = 6;
	    c.weighty = 1;
	    content5.add(panelCir, c);
    }
}
	
