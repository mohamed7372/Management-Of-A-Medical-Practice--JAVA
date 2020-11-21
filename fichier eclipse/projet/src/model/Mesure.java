package model;

public class Mesure {
	private double tension;
	private double poids;
	private double taille;
	
	// constructeur
	public Mesure(double tension, double poids, double taille) {
		this.tension = tension;
		this.poids = poids;
		this.taille = taille;
	}
	public Mesure() {
		this(0,0,0);
	}
	
	// setters et getters
	public double getTension() {
		return tension;
	}
	public void setTension(double tension) {
		this.tension = tension;
	}
	public double getPoids() {
		return poids;
	}
	public void setPoids(double poids) {
		this.poids = poids;
	}
	public double getTaille() {
		return taille;
	}
	public void setTaille(double taille) {
		this.taille = taille;
	}
	
	// methodes pour afficher les info
	@Override
	public String toString() {
		return "Mesure [tension=" + tension + ", poids=" + poids + ", taille=" + taille + "]";
	}	
	public void afficher() {
		System.out.println(this);
	}
}
