package model;

import java.util.Date;

public class Facture {
	private int id_facture;
	private double montant;
	private Date date_facture;
	private int id_patient;
	
	// constructeur
	public Facture(double montant, Date date_facture) {
		this.montant = montant;
		this.date_facture = date_facture;
	}
	public Facture() {
		this(0,new Date());
	}

	// setters et getters
	public int getId_facture() {
		return id_facture;
	}
	public void setId_facture(int id_facture) {
		this.id_facture = id_facture;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Date getDate_facture() {
		return date_facture;
	}
	public void setDate_facture(Date date_facture) {
		this.date_facture = date_facture;
	}
	public int getId_patient() {
		return id_patient;
	}
	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}
	
	// methodes pour afficher les info
	@Override
	public String toString() {
		return "Facture [id_facture=" + id_facture + ", montant=" + montant + ", date_facture=" + date_facture
				+ ", id_patient=" + id_patient + "]";
	}
	public void afficher() {
		System.out.println(this);
	}
}
