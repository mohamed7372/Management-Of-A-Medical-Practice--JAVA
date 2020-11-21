package gui_login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controllor.ConnexionMySql;
import controllor.HintTextField;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class IndicateurPassword extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JTextField userField;
	private JTextField mdpField;
	private JLabel backgroundImg;
	private JButton fermer;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	public IndicateurPassword() {
		// titre de fenetre
		getContentPane().setBackground(Color.WHITE);
		setTitle("Recupere le mot de passe");
		setIconImage(Toolkit.getDefaultToolkit().getImage(IndicateurPassword.class.getResource("/resources/logoApp.png")));
		
		// image de background
		backgroundImg = new JLabel();
		backgroundImg.setIcon(new ImageIcon(IndicateurPassword.class.getResource("/ressourcesEmptyWindow/motPasseOublie.png")));
		backgroundImg.setBounds(30,40,450,200);
		getContentPane().add(backgroundImg);
		
		setBounds(0,0,490,240);
		setBackground(Color.red);
		
		getContentPane().setLayout(null);

		// champs de nom utilisateur
		userField = new HintTextField("nom d'utilisateur");
		userField.setBounds(62, 100, 138, 20);
		Font font1 = new Font("SansSerif", Font.BOLD, 15);
		userField.setFont(font1);
		userField.setForeground(Color.GRAY);
		userField.setBorder(null);
		getContentPane().add(userField);
		userField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ev) {
				cnx = ConnexionMySql.ConnexionDb();
				String username = userField.getText().toString();
				String sql = "select mdp from utilisateur where nomUser = ?";
				
				try {
					prepared = cnx.prepareStatement(sql);
					prepared.setString(1, username);
					resultat = prepared.executeQuery();
					// si trouve cette nom utilisateur on afficher juste les 3 premiers caractere et le dernier 
					if(resultat.next()) {
						String pass = resultat.getString("mdp");
						String pass1 = pass.substring(0,3);
						String etoile = "";
						for (int i = 3; i < pass.length()-1; i++) {
							etoile += "*";
						}
						etoile += pass.substring(pass.length()-1); 
						mdpField.setText(pass1 + etoile); 
					}
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e + " | here 3| " + this.getClass().getName());
				}
			}
		});
		
		// champs de mot de passe
		mdpField = new HintTextField("mot de passe");
		mdpField.setBounds(62, 167, 138, 20);
		mdpField.setFont(font1);
		mdpField.setBackground(new Color(203,22,47));
		mdpField.setForeground(Color.white);
		mdpField.setBorder(null);
		mdpField.setEditable(false);
		getContentPane().add(mdpField);
		
		// button fermer window
		fermer = new JButton();
		fermer.setIcon(new ImageIcon(IndicateurPassword.class.getResource("/resources/exit_icon.png")));
		fermer.setBounds(450,0,40,40);
		fermer.setBorderPainted(false);
		fermer.setContentAreaFilled(false);
		fermer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();   // fermer la fenetre current
			}
		});
		
		// forme de fenetre
		getContentPane().add(fermer);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getContentPane().setLayout(null);
		setVisible(true);
	}
}
