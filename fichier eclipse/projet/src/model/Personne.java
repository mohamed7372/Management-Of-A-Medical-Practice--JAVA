package model;

import java.util.Date;

import category.Sexe;

public class Personne {
	private String nom;
	private String prenom;
	private String tel;
	private String adr;
	private Sexe sexe;
	private String email;
	private Date date_inscp;
	
	// constructeur
	public Personne(String nom, String prenom, String tel, String adr,String email, Sexe sexe,Date date_inscp) {
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.adr = adr;
		this.email = email;
		this.sexe = sexe;
		this.date_inscp = date_inscp;
	}
	public Personne(String nom, String prenom, String tel, String adr,String email, Sexe sexe) {
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.adr = adr;
		this.email = email;
		this.sexe = sexe;
		this.date_inscp = new Date();
	}
	public Personne() {
		this("","","","","",Sexe.Homme,null);
	}
	
	// setters et getters
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Date getDate_inscp() {
		return date_inscp;
	}
	public void setDate_inscp(Date date_inscp) {
		this.date_inscp = date_inscp;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAdr() {
		return adr;
	}
	public void setAdr(String adr) {
		this.adr = adr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Sexe getSexe() {
		return sexe;
	}
	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}
	
	///////////////////////// methodes pour afficher les info ////////////////////////////
	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + ", adr=" + adr + ", sexe=" + sexe
				+ ", email=" + email + ", date_inscp=" + date_inscp + "]";
	}
	public void afficher() {
		System.out.println(this);
	}
}