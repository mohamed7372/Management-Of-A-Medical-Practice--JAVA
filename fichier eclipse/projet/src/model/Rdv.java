package model;

import java.util.Date;

public class Rdv {
	private int id_rdv;
	private Date date;
	private String heure;
	private String nomMedecin;
	private boolean inscrit;
	private int id_patient;
	
	// constructeur
	public Rdv(int id_rdv, Date date, String heure,String nomMedecin, int id_patient,boolean inscrit) {
		this.id_rdv = id_rdv;
		this.date = date;
		this.heure = heure;
		this.nomMedecin = nomMedecin;
		this.id_patient = id_patient;
		this.inscrit = inscrit;
	}
	public Rdv(int id_rdv, Date date, String heure,String nomMedecin, int id_patient) {
		this.id_rdv = id_rdv;
		this.date = date;
		this.heure = heure;
		this.nomMedecin = nomMedecin;
		this.id_patient = id_patient;
	}

	// setters et getters
	public int getId_rdv() {
		return id_rdv;
	}
	public boolean isInscrit() {
		return inscrit;
	}
	public void setInscrit(boolean inscrit) {
		this.inscrit = inscrit;
	}

	public void setId_rdv(int id_rdv) {
		this.id_rdv = id_rdv;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getHeure() {
		return heure;
	}
	public void setHeure(String heure) {
		this.heure = heure;
	}
	public String getNomMedecin() {
		return nomMedecin;
	}
	public void setNomMedecin(String nomMedecin) {
		this.nomMedecin = nomMedecin;
	}
	public int getId_patient() {
		return id_patient;
	}
	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}

	// methodes d'afficher info
	@Override
	public String toString() {
		return "Rdv [id_rdv=" + id_rdv + ", date=" + date + ", heure=" + heure + ", nomMedecin=" + nomMedecin
				+ ", inscrit=" + inscrit + ", id_patient=" + id_patient + "]";
	}
	public void afficher() {
		System.out.println(this);
	}
}
