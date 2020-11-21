package managerEntreFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui_home_agenda.AgendaWindow;
import gui_home_consultation.ConsultationWindow;
import gui_home_facture.FactureWindow;
import gui_home_facture.FactureWindowMod;
import gui_home_facture.FactureWindowRech;
import gui_home_home.HomeWindow;
import gui_home_ordonnance.OrdonnanceWindow;
import gui_home_ordonnance.OrdonnanceWindowMod;
import gui_home_ordonnance.OrdonnanceWindowRech;
import gui_home_parametre.ParametreWindow;
import gui_home_patient.PatientWindow;
import gui_home_patient.PatientWindowAjt;
import gui_home_patient.PatientWindowMod;
import gui_home_patient.PatientWindowRech;
import gui_home_patient.PatientWindowSupp;
import gui_home_prenez.PrenezWindow;
import gui_home_prenez.PrenezWindowAjt;
import gui_home_prenez.PrenezWindowAnn;
import gui_home_prenez.PrenezWindowMod;
import gui_home_prenez.PrenezWindowRech;
import gui_home_statique.StatiqueWindow;
import gui_menuHome.FormMenuHome;

public class HomeFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static FormMenuHome menu= new FormMenuHome();;
	
	private ParametreWindow p;
	private HomeWindow h ;
	private PatientWindow pat;
	private PatientWindowRech paRech ;
	private PatientWindowAjt paAjt ;
	private PatientWindowMod paMod ;
	private PatientWindowSupp paSupp ;
	private FactureWindow fac;
	private FactureWindowRech fRech ;
	private FactureWindowMod fMod;
	private PrenezWindowRech prRech ;
	private PrenezWindow pre;
	private PrenezWindowAjt prAjt ;
	private PrenezWindowMod prMod ;
	private PrenezWindowAnn prAnn ;
	private OrdonnanceWindow ord;
	private OrdonnanceWindowRech oRech ;
	private OrdonnanceWindowMod oMod;
	private ConsultationWindow c ;
	private StatiqueWindow s;
	private AgendaWindow a;

	public HomeFrame() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(HomeFrame.class.getResource("/resources/logoApp.png")));
		
		
		p = new ParametreWindow();
		
		h = new HomeWindow();
		
		pat = new PatientWindow();
		paRech = new PatientWindowRech();
		paAjt = new PatientWindowAjt();
		paMod = new PatientWindowMod();
		paSupp = new PatientWindowSupp();
		
		fac = new FactureWindow();
		fRech = new FactureWindowRech();
		fMod = new FactureWindowMod();
		
		pre = new PrenezWindow();
		prRech = new PrenezWindowRech();
		prAjt = new PrenezWindowAjt();
		prMod = new PrenezWindowMod();
		prAnn = new PrenezWindowAnn();
		
		ord = new OrdonnanceWindow();
		oRech = new OrdonnanceWindowRech();
		oMod = new OrdonnanceWindowMod();
		
		c = new ConsultationWindow();
		s = new StatiqueWindow();
		a = new AgendaWindow();

		setTitle("Gestion d'un cabinet Medical - Tableau de bord");

		// manipuler et afficher les diff windows a partir le nom de button 
		menu.setFormListenerWindowAfficher(new FormListenerWindowAfficher() {
			@Override
			public void afficherWindow(String name) {
				// cacher tous les windows avant click sur un button
				desactiverAllWindow();
				
				/* dans chaque button choisir elle retouner leur nom pour determiner
				 * la window qui doit afficher
				 * donc en activer cette window a partir le nom de button apres
				 * ajoute a la Frame Home apres
				 * changer le titre qui afficher dans la bar de l'application
				 */
				switch (name) {
				case "parametre":
					p.activerWindow();
					getContentPane().add(p,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Parametre");
					break;
				case "home":
					h.activerWindow();
					getContentPane().add(h,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Tableau de bord");
					break;
				case "patient":
					pat.activerWindow();
					getContentPane().add(pat,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Patient");
					break;					
				case "pati_rechercher":
					paRech.activerWindow();
					getContentPane().add(paRech,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Patient - Rechercher");
					break;
				case "pati_ajouter":
					paAjt.activerWindow();
					getContentPane().add(paAjt,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Patient - Ajouter");
					break;
				case "pati_modifier":
					paMod.activerWindow();
					getContentPane().add(paMod,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Patient - Modifier");
					break;
				case "pati_supprimer":
					paSupp.activerWindow();
					getContentPane().add(paSupp,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Patient - Supprimer");
					break;
				case "facture":
					fac.activerWindow();
					getContentPane().add(fac,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Facture");
					break;
				case "fact_rechercher":
					fRech.activerWindow();
					getContentPane().add(fRech,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Facture - Rechercher");
					break;
				case "fact_modifier":
					fMod.activerWindow();
					getContentPane().add(fMod,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Facture - modifier");
					break;
				case "prenez":
					pre.activerWindow();
					getContentPane().add(pre,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Rendez-vous & Visite");
					break;
				case "pren_rechercher":
					prRech.activerWindow();
					getContentPane().add(prRech,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Rendez-vous & Visite - Rechercher");
					break;
				case "pren_ajouter":
					prAjt.activerWindow();
					getContentPane().add(prAjt,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Rendez-vous & Visite - Ajouter");
					break;
				case "pren_modifier":
					prMod.activerWindow();
					getContentPane().add(prMod,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Rendez-vous & Visite - Modifier");
					break;
				case "pren_annuler":
					prAnn.activerWindow();
					getContentPane().add(prAnn,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Rendez-vous & Visite - Annuler");
					break;
				case "ordonnance":
					ord.activerWindow();
					getContentPane().add(ord,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Ordonnance");
					break;
				case "ordo_rechercher":
					oRech.activerWindow();
					getContentPane().add(oRech,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Ordonnance - Rechercher");
					break;
				case "ordo_modifier":
					oMod.activerWindow();
					getContentPane().add(oMod,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Ordonnance - Modifier");
					break;
				case "consultation":
					c.activerWindow();
					getContentPane().add(c,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Consultation");
					break;
				case "statique":
					s.activerWindow();
					getContentPane().add(s,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Statique");
					break;
				case "agenda":
					a.activerWindow();
					getContentPane().add(a,BorderLayout.CENTER);
					setTitle("Gestion d'un cabinet Medical - Agenda");
					break;
				default:
					setTitle("Gestion d'un cabinet Medical ");
					String option[] = {"oui","no"};
					int ret = JOptionPane.showOptionDialog(null, "vous etes sure ?", "Quitter l'application",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, option, option[0]);
					if(ret == 0) {
						System.exit(0);
						setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					}
					else {
						h.activerWindow();
						getContentPane().add(h,BorderLayout.CENTER);
						setTitle("Gestion d'un cabinet Medical - Tableau de bord");
						HomeFrame.getHomeFrameVar().selectButton("home");
					}
					break;
				}
			}
		});
		// la forme de HomeFrame
		homeFrameGui();
		setVisible(false);
	}
	
	// cacher tous les windows
	public void desactiverAllWindow() {  
		p.desactiverWindow();
		h.desactiverWindow();
		pat.desactiverWindow();
		paRech.desactiverWindow();
		paRech.desactiverWindow();
		paAjt.desactiverWindow();
		paMod.desactiverWindow();
		paSupp.desactiverWindow();
		fac.desactiverWindow();
		fRech.desactiverWindow();
		fMod.desactiverWindow();
		pre.desactiverWindow();
		prRech.desactiverWindow();
		prAjt.desactiverWindow();
		prMod.desactiverWindow();
		prAnn.desactiverWindow();
		oRech.desactiverWindow();
		oMod.desactiverWindow();
		ord.desactiverWindow();
		c.desactiverWindow();
		s.desactiverWindow();
		a.desactiverWindow();
	}
	
	// activer window choisir
	public void activerPatientWindow() {
		desactiverAllWindow();
		paRech.activerWindow();
		getContentPane().add(paRech,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Patient - Rechercher");
	}
	public void activerConsultationWindow() {
		desactiverAllWindow();
		c.activerWindow();
		getContentPane().add(c,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Consultation");
	}
	public void activerOrdonnanceWindow() {
		desactiverAllWindow();
		oRech.activerWindow();
		getContentPane().add(oRech,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Ordonnance - Rechercher");
	}
	public void activerFactureWindow() {
		desactiverAllWindow();
		fRech.activerWindow();
		getContentPane().add(fRech,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Facture - Rechercher");
	}
	public void activerParametreWindow() {
		desactiverAllWindow();
		p.activerWindow();
		getContentPane().add(p,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Parametre");
	}
	public void activerPrenewWindow() {
		desactiverAllWindow();
		prRech.activerWindow();
		getContentPane().add(prRech,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Rendez-vous & Visite - Rechercher");
	}
	public void activerStatiqueWindow() {
		desactiverAllWindow();
		s.activerWindow();
		getContentPane().add(s,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Statique");
	}
	public void activerAgendaWindow() {
		desactiverAllWindow();
		a.activerWindow();
		getContentPane().add(a,BorderLayout.CENTER);
		setTitle("Gestion d'un cabinet Medical - Agenda");
	}
	
	// design de home frame window
	public void homeFrameGui() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(menu,BorderLayout.LINE_START);
		getContentPane().add(h,BorderLayout.CENTER);
		
		setSize(1200,600);
		setMinimumSize(new Dimension(1200,600));
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static FormMenuHome getHomeFrameVar() {
		return menu;
	}
	public void activerHomeFrame() {
		setVisible(true);
	}
}
