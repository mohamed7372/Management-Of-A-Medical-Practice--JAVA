package model;

import java.util.Date;

public class Consultation {
	private String consultation;
	private Date date_consultation;

	// consturcteur
	public Consultation(String consultation,Date date_consultation) {
		this.consultation = consultation;
		this.date_consultation = date_consultation;
	}

	// setters et getters
	public String getConsultation() {
		return consultation;
	}
	public void setConsultation(String consultation) {
		this.consultation = consultation;
	}
	public Date getDate_consultation() {
		return date_consultation;
	}
	public void setDate_consultation(Date date_consultation) {
		this.date_consultation = date_consultation;
	}

	// methodes
	
	// methodes pour afficher les info
	@Override
	public String toString() {
		return "Consultation [consultation=" + consultation + ", date_consultation=" + date_consultation + "]";
	}
	public void afficher() {
		System.out.println(this);
	}
}
