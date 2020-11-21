package model;

import java.util.Date;

public class Ordonnance {
	private int id_ordonnance;
	private Date date_ordonnance;
	private int conjeMaladie;
	private String traitement;
	private int id_patient;
	
	// constructeur
	public Ordonnance(int id_ordonnance, Date date_ordonnance, int nbrJour_conje, String traitement) {
		this.id_ordonnance = id_ordonnance;
		this.date_ordonnance = date_ordonnance;
		this.conjeMaladie = nbrJour_conje;
		this.traitement = traitement;
		this.id_patient = 0;
	}
	public Ordonnance(Date date_ordonnance, int nbrJour_conje, String traitement) {
		this(0,date_ordonnance,nbrJour_conje,traitement);
		this.id_patient = 0;
	}

	// setters et getters
	public int getId_ordonnance() {
		return id_ordonnance;
	}
	public void setId_ordonnance(int id_ordonnance) {
		this.id_ordonnance = id_ordonnance;
	}
	public Date getDate_ordonnance() {
		return date_ordonnance;
	}
	public void setDate_ordonnance(Date date_ordonnance) {
		this.date_ordonnance = date_ordonnance;
	}
	public String getTraitement() {
		return traitement;
	}
	public void setTraitement(String traitement) {
		this.traitement = traitement;
	}
	public int getConjeMaladie() {
		return conjeMaladie;
	}
	public void setConjeMaladie(int conjeMaladie) {
		this.conjeMaladie = conjeMaladie;
	}
	public int getId_patient() {
		return id_patient;
	}
	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}


	///////////////////////// methodes pour afficher les info ////////////////////////////
	@Override
	public String toString() {
		return "Ordonnance [id_ordonnance=" + id_ordonnance + ", date_ordonnance=" + date_ordonnance
				+ ", nbrJour_conje=" + conjeMaladie + ", traitement=" + traitement + "]";
	}
	public void afficher() {
		System.out.println(this);
	}
}
