package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import category.Groupage;
import category.Sexe;
import controllor.ConnexionMySql;

public class Patient extends Personne{
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private int id_patient;
	private Date date_naiss;
	private Groupage grpSang;
	private String malad_chronique;
	private Mesure mesure;
	private String consultations; 
	private String analyse;
	private Vector<Facture> factures;
	private Vector<Ordonnance> ordonnances;
	private Vector<Rdv> rdvs;
	private boolean inscrit = true;
	
	// constructeur
	public Patient(int id_patient,String nom, String prenom, String tel, String adr,String email, Sexe sexe,Date date_inscp,
			Date date_naiss, double poid, double taille, double tension, Groupage grpSang,
			String malad_chronique) {
		super(nom, prenom, tel, adr,email, sexe,date_inscp);
		this.date_naiss = date_naiss;
		this.id_patient = id_patient;
		this.mesure = new Mesure(tension, poid, taille);
		this.grpSang = grpSang;
		this.malad_chronique = malad_chronique;
		this.consultations = "";
		this.analyse = "";
		this.factures = new Vector<Facture>();
		listeFactureBD();
		this.ordonnances = new Vector<Ordonnance>();
		listeOrdonnanceBD();
		this.rdvs = new Vector<Rdv>();
		listeRdvBD();
	}
	public Patient(int id_patient,String nom, String prenom, String tel, String adr,String email, Sexe sexe,Date date_inscp,
			Date date_naiss, double poid, double taille, double tension, Groupage grpSang,
			String malad_chronique,String consultation, String analyse) {
		this(id_patient,nom,prenom,tel, adr,email, sexe,date_inscp,
				date_naiss, poid, taille, tension, grpSang,
				malad_chronique);
		this.consultations = consultation;
		this.analyse = analyse;
		this.factures = new Vector<Facture>();
		listeFactureBD();
		this.ordonnances = new Vector<Ordonnance>();
		listeOrdonnanceBD();
		this.rdvs = new Vector<Rdv>();
		listeRdvBD();
	}
	public Patient(int id_patient,String nom, String prenom, String tel, String email,Sexe sexe) {
		super(nom, prenom, tel, "",email, sexe,new Date());
		this.id_patient = id_patient;
		this.factures = new Vector<Facture>();
		this.ordonnances = new Vector<Ordonnance>();
		this.rdvs = new Vector<Rdv>();
		this.mesure = new Mesure(0, 0, 0);
	}
	
	// setters et getters
	public int getId_patient() {
		return id_patient;
	}
	public Vector<Rdv> getRdvs() {
		return rdvs;
	}
	public void setRdvs(Vector<Rdv> rdvs) {
		this.rdvs = rdvs;
	}
	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}
	public Date getDate_naiss() {
		return date_naiss;
	}
	public void setDate_naiss(Date date_naiss) {
		this.date_naiss = date_naiss;
	}
	public Groupage getGrpSang() {
		return grpSang;
	}
	public void setGrpSang(Groupage grpSang) {
		this.grpSang = grpSang;
	}
	public String getMalad_chronique() {
		return malad_chronique;
	}
	public void setMalad_chronique(String malad_chronique) {
		this.malad_chronique = malad_chronique;
	}
	public Mesure getMesure() {
		return mesure;
	}
	public void setMesure(Mesure mesure) {
		this.mesure = mesure;
	}
	public String getConsultations() {
		return consultations;
	}
	public void setConsultations(String consultations) {
		this.consultations = consultations;
	}
	public String getAnalyse() {
		return analyse;
	}
	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
	public Vector<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Vector<Facture> factures) {
		this.factures = factures;
	}
	public Vector<Ordonnance> getOrdonnances() {
		return ordonnances;
	}
	public void setOrdonnances(Vector<Ordonnance> ordonnances) {
		this.ordonnances = ordonnances;
	}
	public boolean isInscrit() {
		return inscrit;
	}
	public void setInscrit(boolean inscrit) {
		this.inscrit = inscrit;
	}
	
	
	//////////////////////////////////////////// rdv //////////////////////////
	public Rdv rechercherRdv(int id) {
		for (int i = 0; i < getRdvs().size(); i++) {
			if(getRdvs().get(i).getId_rdv() == id)
				return getRdvs().get(i);
		}
		return null;
	}
	public void ajouterRdv(Rdv r) {
		getRdvs().add(r);
	}
	public void modifierRdv(Rdv r) {
		for (int i = 0; i < getRdvs().size(); i++) {
			if(getRdvs().get(i).getId_rdv() == r.getId_rdv()) {
				getRdvs().remove(i);
				break;
			}
		}
		ajouterRdv(r);
	}
	public void suppRdv(int id) {
		for (int i = 0; i < getRdvs().size(); i++) {
			if(getRdvs().get(i).getId_rdv() == id) {
				getRdvs().remove(i);
				break;
			}
		}
	}

	
	///////////////////////////// ord ///////////////////////////////////////
	public void modifierOrdonnance(Ordonnance o) {
		for (int i = 0; i < getOrdonnances().size(); i++) {
			if(getOrdonnances().get(i).getId_ordonnance() == o.getId_ordonnance()) {
				getOrdonnances().remove(i);
				break;
			}
		}
		getOrdonnances().add(o);
	}
	// ajouter une ordonnance
	public void ajouterOrdonnance(Ordonnance o) {
		getOrdonnances().add(o);
	}
	
	
	//////////////////////////// facture ////////////////////////////////////
	public void modifierFacture(Facture f) {
		for (int i = 0; i < getFactures().size(); i++) {
			if(getFactures().get(i).getId_facture() == f.getId_facture()) {
				getFactures().remove(i);
				break;
			}
		}
		getFactures().add(f);
	}
	// ajouter une facture
	public void ajouterFacture(Facture f) {
		getFactures().add(f);
	}
	
	
	//////////////////////////////// recupere a partir BD /////////////////////////////
	// recuperer tous les ordonnances de cette patient qui est dans la base des donnes
	private void listeOrdonnanceBD() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from ordonnance";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				int id = resultat.getInt("id_ordonnance");
				int jour = resultat.getInt("conjeMaladie");
				String traitement = resultat.getString("traitement");
				int idp = resultat.getInt("id_patient");
				
				// convertir la date type String vers la date type Date
				String d = resultat.getString("date_ord");
				Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(d);
				
				Ordonnance o = new Ordonnance(id, date1, jour, traitement);
				o.setId_patient(idp);
				if(getId_patient() == idp)
					getOrdonnances().add(o);
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// recuperer tous les facture de cette patient qui est dans la base des donnes
	private void listeFactureBD() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from facture";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				int id = resultat.getInt("id_facture");
				int idp = resultat.getInt("id_patient");
				double montant = resultat.getDouble("montant");
				
				// convertir la date type String vers la date type Date
				String d = resultat.getString("date_facture");
				Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(d);
				
				Facture f = new Facture(montant, date1);
				f.setId_facture(id);
				f.setId_patient(idp);
				if(getId_patient() == idp)
					getFactures().add(f);
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
		}
	// recuperer tous les rdvs de cette patient qui est dans la base des donnes
	private void listeRdvBD() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from rdv";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				int id = resultat.getInt("id_rdv");
				String heureBD = resultat.getString("heure");
				String medecinBD = resultat.getString("nomMedecin");
				int idp = resultat.getInt("id_patient");
				String dateBD = resultat.getString("date");
				Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(dateBD);
				
				if(getId_patient() == idp)
					rdvs.add(new Rdv(id, date1, heureBD, medecinBD, idp));
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	
	
	////////////////////////// autre methodes /////////////////////////////////////
	// copier les info d'un patient
	public void copierPatient(Patient p) {
		p.setNom(getNom());
		p.setPrenom(getPrenom());
		p.setDate_naiss(getDate_naiss());
		p.setSexe(getSexe());
		p.setTel(getTel());
		p.setMesure(getMesure());
		p.setConsultations(getConsultations());
		p.setAnalyse(getAnalyse());
	}
	// calculer total montant des factures
	public double totalMontant() {
		double t = 0;
		for (int i = 0; i < factures.size(); i++) {
			t+=factures.get(i).getMontant();
		}
		return t;
	}
	// prendre nouveaux mesures et ajouter a cette consultation
	public void prendreMesureMaladeChro(double tension, double poids, double taille,String maladeChro) {
		getMesure().setPoids(poids);
		getMesure().setTaille(taille);
		getMesure().setTension(tension);
		setMalad_chronique(getMalad_chronique().concat("\n").concat(maladeChro));
	}
	// copier les information d'un patient vers autre
	public void copierInfoPatient(Patient p) {
		this.setNom(p.getNom());
		this.setPrenom(p.getPrenom());
		this.setDate_naiss(p.getDate_naiss());
		this.setSexe(p.getSexe());
		this.setTel(p.getTel());
		this.setEmail(p.getEmail());
		this.setAdr(p.getAdr());
		this.setGrpSang(p.getGrpSang());
		this.getMesure().setPoids(p.getMesure().getPoids());
		this.getMesure().setTaille(p.getMesure().getTaille());
		this.getMesure().setTension(p.getMesure().getTension());
		this.setMalad_chronique(p.getMalad_chronique());
		this.setConsultations(p.getConsultations());
		this.setAnalyse(p.getAnalyse());
	}
	
	
	///////////////////////// methodes pour afficher les info ////////////////////////////
	@Override
	public String toString() {
		return "Patient [id_patient=" + id_patient + ", date_naiss=" + date_naiss + ", grpSang=" + grpSang
				+ ", malad_chronique=" + malad_chronique + ", mesure=" + mesure.toString()
				+ "]";
	}
	public void afficher() {
		System.out.println(this + " ," + super.toString());
	}
}