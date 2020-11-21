package gui_menuHome;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ManagerSelectMenu {
	private JButton select;
	private String location;
	private String extension;
	private String nameButt;

	// constructeur
	public ManagerSelectMenu(JButton select, String location, String extension) {
		this.select = select;
		this.location = location;
		this.extension = extension;
		nameButt = select.getName();
	}
	
	// setters et getters
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNameButt() {
		return nameButt;
	}
	public void setNameButt(String nameButt) {
		this.nameButt = nameButt;
	}
	public JButton getSelect() {
		return select;
	}
	public void setSelect(JButton select) {
		this.select = select;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	// methodes
	public String nomEmplacement() {    // recuper le nom complet de l"emplacement image
		setNameButt(getNameButt());
		return getLocation().concat(getNameButt()).concat("Select").concat(getExtension());
	}
	public String nomEmplacementSousButton() {    // recuper le nom complet de l"emplacement image
		setNameButt(getNameButt().substring(5));
		return getLocation().concat(getNameButt()).concat("Select").concat(getExtension());
	}
	
	// selectioner le button click
	public void selectedButton() {
		getSelect().setIcon(new ImageIcon(FormMenuHome.class.getResource(nomEmplacement())));
	}
	
	// selectioner sous button click (c.a.d recupere la bonne image de button click)
	public void selectedPatientSB() {
		getSelect().setIcon(new ImageIcon(sousButtonsPatient.class.getResource(nomEmplacementSousButton())));
	}
	public void selectedPrenezSB() {
		getSelect().setIcon(new ImageIcon(sousButtonsPrenez.class.getResource(nomEmplacementSousButton())));
	}
	public void selectedOrdonnanceSB() {
		getSelect().setIcon(new ImageIcon(sousButtonOrdonnance.class.getResource(nomEmplacementSousButton())));
	}
	public void selectedFactureSB() {
		getSelect().setIcon(new ImageIcon(sousButtonFacture.class.getResource(nomEmplacementSousButton())));
	}
	
}
