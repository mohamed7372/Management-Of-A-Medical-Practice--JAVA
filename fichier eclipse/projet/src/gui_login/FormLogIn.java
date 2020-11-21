package gui_login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import category.Sexe;
import controllor.ConnexionMySql;
import managerEntreFrame.HomeFrame;
import managerEntreFrame.MainFrame;
import model.Medecin;


public class FormLogIn extends JPanel{
	private static final long serialVersionUID = 1L;

	public static Medecin userNow = new Medecin("", "", "", "", "", Sexe.Homme, new Date(), "", "", "");
	public static HomeFrame h;
	
	private JLabel imgLogin;
	private JLabel bonjour;
	private JPanel userPassGUI;
	private JLabel imgUser;
	private JLabel imgPassword;
	private JTextField userName;
	private JPasswordField password;
	private JButton buttConnecter;
	private JLabel forgetPassword;
	private JLabel errInfo;
	
	//public static HomeFrame h = new HomeFrame();
	
	private JButton exit;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	public FormLogIn() {
		/////////////// image login ////////////////////		
		imgLogin = new JLabel();
		imgLogin.setBounds(130,100,140,140);
		imgLogin.setIcon(new ImageIcon(FormLogIn.class.getResource("/resources/userLoginIcon.png")));
		
		////////////////// dit bonjour ou bonsoir a partir de l'heure currente //////////////////////
		bonjour = new JLabel("Bonjour !",SwingConstants.CENTER);
		bonjour.setBounds(130,230,140,60);
		Font font2 = new Font("SansSerif", Font.BOLD, 18);
		bonjour.setFont(font2);
		bonjour.setForeground(new Color(203,22,47));
		Date d = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("HH");
		
		if(Integer.parseInt(ft.format(d)) >= 12)
			bonjour.setText("Bonsoir !");
		
		
		////////////// user pass edit ///////////////////////
		userPassGUI = new JPanel(new BorderLayout());
		userPassGUI.setBounds(90,300,200,70);
		userPassGUI.setBackground(Color.white);
		
		// user filed
		Font font1 = new Font("SansSerif", Font.BOLD, 15);
		userName = new JTextField(20);
		userName.setFont(font1);
		userName.setForeground(Color.GRAY);
		userName.setBorder(null);
		userName.setToolTipText("Saisir votre nom d'utilisateur (exp : benrabah.mohamed). il faut saisir le point");
		userPassGUI.add(userName,BorderLayout.PAGE_START);
		
		// password filed
		password = new JPasswordField(20);
		password.setFont(font1);
		password.setForeground(Color.GRAY);
		password.setBorder(null);
		userPassGUI.add(password,BorderLayout.PAGE_END);
		
		//////////////// icone user name et password /////////////////
		imgUser = new JLabel();
		imgUser.setIcon(new ImageIcon(FormLogIn.class.getResource("/resources/editUser.png")));
		imgUser.setBounds(70,290,260,40);

		
		imgPassword = new JLabel();
		imgPassword.setIcon(new ImageIcon(FormLogIn.class.getResource("/resources/editPassword.png")));
		imgPassword.setBounds(70,340,260,40);
		
		/////////////////// button connecter ///////////
		buttConnecter = new JButton();
		buttConnecter.setBackground(Color.WHITE);
		buttConnecter.setIcon(new ImageIcon(FormLogIn.class.getResource("/resources/buttonConnecter.png")));
		buttConnecter.setBounds(70,400,260,40);
		buttConnecter.setBorderPainted(false);
		buttConnecter.setContentAreaFilled(false);
		
		buttConnecter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				cnx = ConnexionMySql.ConnexionDb();
				// recupere le nom et mdp dans champs saisir
				String name = userName.getText().toString().trim();
				@SuppressWarnings("deprecation")
				String pass = password.getText().toString().trim();
				String sql = "select * from utilisateur";
				
				try {
					prepared = cnx.prepareStatement(sql);
					resultat = prepared.executeQuery();   //hna recupere resultat ta3 requete
					// trier le cas de click connecter et les champs sont vides 
					if(name.equalsIgnoreCase("") || pass.equalsIgnoreCase(""))
						errInfo.setText("                      les champs sont vides !");
					// le cas normale
					else {
						int i = 0;
						// rechercher dans la base des donnes et recupere tous les donnes de cette utilasteur s il trouver
						while(resultat.next()) {
							int idu = resultat.getInt("id_utilisateur");
							String name1 = resultat.getString("nomUser");
							String pass1 = resultat.getString("mdp");
							
							String nom = resultat.getString("nom");
							String prenom = resultat.getString("prenom");
							String tel = resultat.getString("tel");
							String adr = resultat.getString("adr");
							String sexe = resultat.getString("sexe");
							String dateinscp = resultat.getString("date_inscrit");
							String sp = resultat.getString("specialite");
							
							String email = resultat.getString("email");
							Boolean medecin = resultat.getBoolean("medecin");
							
							if(name.equalsIgnoreCase(name1) && pass.equalsIgnoreCase(pass1)) {
								
								MainFrame.l.setVisible(false);
								JOptionPane.showMessageDialog(null,"connection ressite");
								i = 1;

								userNow.setNomUtl(name1);
								userNow.setMdp(pass1);
								userNow.setNom(nom);
								userNow.setPrenom(prenom);
								userNow.setTel(tel);
								userNow.setAdr(adr);
								if(sexe.equalsIgnoreCase("H"))
									userNow.setSexe(Sexe.Homme);
								else
									userNow.setSexe(Sexe.Femme);
								Date date1;
								try {
									date1 = new SimpleDateFormat("yyyy/MM/dd").parse(dateinscp);
									
									userNow.setDate_inscp(date1);
									userNow.setEmail(email);
									userNow.setSpecialite(sp);
									userNow.setMedecin(medecin);
									userNow.setId_Med(idu);
									
									h = new HomeFrame();
									h.activerHomeFrame();
									break;
								} 
								catch (ParseException e1) {
									JOptionPane.showMessageDialog(null, e1.getMessage() +" | here | " + this.getClass().getName() );
								}
							}
						}
						// le cas si ne trouve pas cette utilisateur dans ma base des donnes
						if(i == 0) 
							errInfo.setText("nom d'utilisateur ou mot de passe est incorrect !");
					}
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage() +" | here 2 | " + this.getClass().getName() );
				}
			}
		});
		
		///////////////// forget password //////////////
		forgetPassword = new JLabel("Mot de passe oublié?",SwingConstants.CENTER);
		forgetPassword.setBounds(70,445,260,40);
		forgetPassword.setForeground(Color.lightGray);
		forgetPassword.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  
		    {  
				new IndicateurPassword();
		    }  
		});  
		///////////////////// error information ////////////
		errInfo = new JLabel();
		errInfo.setBounds(70,480,280,40);
		errInfo.setForeground(new Color(203,22,47));
		
		///////////////// exit sign in window ///////////
		exit = new JButton(new ImageIcon(FormLogIn.class.getResource("/resources/exit_icon.png")));
		exit.setBounds(350,10,40,40);
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// forme de fenetre
		setBackground(Color.white);
		setLayout(null);
		setBounds(600,0,400,600);
		
		add(bonjour);
		add(imgLogin);
		add(imgUser);
		add(imgPassword);
		add(userPassGUI);
		add(buttConnecter);
		add(forgetPassword);
		add(errInfo);
		add(exit);
	}
}
