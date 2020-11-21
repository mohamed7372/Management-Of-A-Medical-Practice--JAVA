package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import category.Sexe;
import controllor.ConnexionMySql;
import gui_login.FormLogIn;

public class Utilisateur extends Personne {
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	private String nomUtl, mdp;
	private Vector<Facture> factures;
	private Vector<Rdv> rdvs;
	
	// constructeur
	public Utilisateur(String nom, String prenom, String tel, String adr,String email
			, Sexe sexe,Date date_inscp, String nomUtl, String mdp) {
		super(nom, prenom, tel, adr,email, sexe,date_inscp);
		this.nomUtl = nomUtl;
		this.mdp = mdp;
		
		// collection pour tous les factures
		factures = new Vector<Facture>();
		// recuperer les factures dans la base des donnes 
		listeFactureBD();
		// collection pour tous les rdvs
		rdvs = new Vector<Rdv>();
		// recuperer les rdvs dans la base des donnes 
		listeRdvBD();
	}
	public Utilisateur(String nom, String prenom, String tel, String adr,String email, Sexe sexe,String mdp) {
		super(nom, prenom, tel, adr,email, sexe);
		this.mdp = mdp;
		
		// collection pour tous les factures
		factures = new Vector<Facture>();
		// recuperer les factures dans la base des donnes
		listeFactureBD();
		// collection pour tous les rdvs
		rdvs = new Vector<Rdv>();
		// recuperer les rdvs dans la base des donnes 
		listeRdvBD();
	}

	// setters et getters
	public String getNomUtl() {
		return nomUtl;
	}
	public void setNomUtl(String nomUtl) {
		this.nomUtl = nomUtl;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public Vector<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Vector<Facture> factures) {
		this.factures = factures;
	}
	public Vector<Rdv> getRdvs() {
		return rdvs;
	}
	public void setRdvs(Vector<Rdv> rdvs) {
		this.rdvs = rdvs;
	}
	
	
	////////////////////////////////// methodes rendez vous //////////////////////////////////
	// recuperer tous les rdvs d'un patient rechercher
	public DefaultComboBoxModel<String> chercherPatientRdv(String []nomSearch) {
		DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
		for (int i = 0 ; i < FormLogIn.userNow.getPatients().size();i++) {
			if(nomSearch[0].equalsIgnoreCase(FormLogIn.userNow.getPatients().get(i).getNom()) && 
					nomSearch[1].equalsIgnoreCase(FormLogIn.userNow.getPatients().get(i).getPrenom())){
				for (int j = 0; j < FormLogIn.userNow.getPatients().get(i).getRdvs().size() ; j++) {
					String elt = "id_patient "
							.concat(String.valueOf(FormLogIn.userNow.getPatients().get(i).getId_patient()))
							.concat(" / id_rdv ")
							.concat(String.valueOf(FormLogIn.userNow.getPatients().get(i).getRdvs().get(j).getId_rdv()))
							.concat(" / tel : ")
							.concat(FormLogIn.userNow.getPatients().get(i).getTel());
					idModel.addElement(elt);
				}
			}
		}
	    return idModel;
	}
	// chercher un rdv dans la liste des rdv de user
	public Rdv rechercherRdv(Rdv r) {
		for (int i = 0; i < getRdvs().size(); i++) {
			if(getRdvs().get(i).getId_rdv() == r.getId_rdv())
				return getRdvs().get(i);
		}
		return null;
	}
	// ajouter un rdv 
	public void ajouterRdv(Rdv r) {
		Patient p = FormLogIn.userNow.chercherPatient(r.getId_patient());
		if(p!=null) {
			// ajouter dans la base de donnes
			ajouterRdvBD(r);
			// ajouter dans la liste des rdvs d'utilisateur
			getRdvs().add(r);
			// ajouter dans la liste des patients
			p.ajouterRdv(r);
		}
	}	
	// supp un rdv
	public void suppRdv(int idp,int idr) {
		// supp dans la base de donnes
		suppRdvBD(idr);
		Patient p = FormLogIn.userNow.chercherPatient(idp);
		if(p!=null) {
			for (int i = 0; i < getRdvs().size(); i++) {
				if(getRdvs().get(i).getId_rdv() == idr)
					getRdvs().remove(i);
			}
			// supp dans la liste des patients
			p.suppRdv(idr);
		}
	}
	// rechercher
	public DefaultComboBoxModel<String> rechercherRdv(String []nomSearch) {
		DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
		
		for (int i = 0; i < FormLogIn.userNow.getPatients().size(); i++) {
			String nom = FormLogIn.userNow.getPatients().get(i).getNom();
			String prenom = FormLogIn.userNow.getPatients().get(i).getPrenom();
			if(nomSearch[0].equalsIgnoreCase(nom) && nomSearch[1].equalsIgnoreCase(prenom)) {
					String elt = "ID ".concat(String.valueOf(FormLogIn.userNow.getPatients().get(i).getId_patient()))
							.concat(" / tel : ").concat(FormLogIn.userNow.getPatients().get(i).getTel());
					idModel.addElement(elt);
			}
		}
		return idModel;
	}
	
	
	////////////////////////////////// methodes patient no inscrit ///////////////////////////////
	// remplir idcombo par tous les id des ordonnanes lie a patient rechercher
	public DefaultComboBoxModel<String> chercherPatientRdvInscritNoInscrit(String []nomSearch) {
		DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
		for (int i = 0 ; i < FormLogIn.userNow.getPatients().size();i++) {
			if(nomSearch[0].equalsIgnoreCase(FormLogIn.userNow.getPatients().get(i).getNom()) && 
					nomSearch[1].equalsIgnoreCase(FormLogIn.userNow.getPatients().get(i).getPrenom())){
				for (int j = 0; j < FormLogIn.userNow.getPatients().get(i).getRdvs().size() ; j++) {
					String elt = "id_patient "
							.concat(String.valueOf(FormLogIn.userNow.getPatients().get(i).getId_patient()))
							.concat(" / id_rdv ")
							.concat(String.valueOf(FormLogIn.userNow.getPatients().get(i).getRdvs().get(j).getId_rdv()))
							.concat(" / tel : ")
							.concat(FormLogIn.userNow.getPatients().get(i).getTel());
					idModel.addElement(elt);
				}
			}
		}
		return idModel;
	}
	// ajouter un rdv patient n'a pas inscrit
	public boolean ajouterRdvNoInscirt(Rdv r,Patient p) {
		if(FormLogIn.userNow.rechercherPatientBD(p.getNom(),p.getPrenom(),p.getTel())){
			// ajouter dans la liste des rdvs d'utilisateur
			getRdvs().add(r);
			// ajouter dans la liste des patients
			p.ajouterRdv(r);
			// ajouter patinet a la liste des medecin 
			FormLogIn.userNow.getPatients().add(p);
			
			Medecin.patientNoInscrit++;
			JOptionPane.showMessageDialog(null, "ajouter un redez-vous");
			return true;
		}
		return false;
	}	
	// supp rdv
	public void suppRdvInscritNoInscrit(int idp,int idr) {
		boolean inscrit = false;
		for (int i = 0; i < getRdvs().size(); i++) {
			if(getRdvs().get(i).getId_rdv() == idr) {
				inscrit = getRdvs().get(i).isInscrit();
				getRdvs().remove(i);
				break;
			}
		}
		System.err.println(inscrit);
		if(!inscrit) {
			for (int i = 0; i < FormLogIn.userNow.getPatients().size(); i++) {
				if(FormLogIn.userNow.getPatients().get(i).getId_patient() == idp)
					FormLogIn.userNow.getPatients().remove(i);
				Medecin.patientNoInscrit--;
			}
		}
		else if(inscrit)
			suppRdv(idp, idr);
	}
	
	
	/////////////////////////////////// recuper info patient rdv /////////////////////
	public String recupereInfoPatientRdv(int idp) {
		String ch="";
		for (int i = 0; i < FormLogIn.userNow.getPatients().size(); i++) {
			if(idp == FormLogIn.userNow.getPatients().get(i).getId_patient()) {
				if(FormLogIn.userNow.getPatients().get(i).getSexe().equals(Sexe.Homme))
					ch = "Mr.";
				else
					ch = "Mme.";
				ch = ch.concat(FormLogIn.userNow.getPatients().get(i).getNom()).concat(" ").concat(FormLogIn.userNow.getPatients().get(i).getPrenom());
				break;
			}
		}
		return ch;
	}
	
	
	/////////////////////////////////// rdv inscrit nonInscrit/////////////////////////////////////////
	public void modifierRdvInscritNoInscrit(Rdv r) {
		Patient p = FormLogIn.userNow.chercherPatient(r.getId_patient());
		if(p!=null) {
			// modifier dans la liste des rdvs d'utilisateur
			if(rechercherRdv(r) != null) { 
				for (int i = 0; i < getRdvs().size(); i++) {
					if(getRdvs().get(i).getId_rdv() == r.getId_rdv()) {
						getRdvs().remove(i);
						break;
					}
				}
				getRdvs().add(r);
				p.modifierRdv(r);
				
				if(r.isInscrit())
					modifierRdvBD(r);
			}
		}
	}	
	
	
	////////////////////////////////// methodes base des donnes ///////////////////////////////////
	// ajouter un rdv dans la base des donnes
	private void ajouterRdvBD(Rdv r) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "insert into rdv values(?,?,?,?,?,?)";
		try {
			prepared = cnx.prepareStatement(sql);
			
			prepared.setInt(1, 0);
			prepared.setString(2, ft.format(r.getDate()));
			prepared.setString(3, r.getHeure());
			prepared.setString(4, r.getNomMedecin());
			prepared.setBoolean(5, r.isInscrit());
			prepared.setInt(6, r.getId_patient());
			prepared.execute();
			
			JOptionPane.showMessageDialog(null, "Ajouter rendez-vous");
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// modifier un rdv dans la base des donnes
	private void modifierRdvBD(Rdv r) {
		cnx = ConnexionMySql.ConnexionDb();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		String sql = "update rdv set date = ?, heure = ?, nomMedecin = ? where id_patient = " + r.getId_patient();
		try {
			prepared = cnx.prepareStatement(sql);
			prepared.setString(1, ft.format(r.getDate()));
			prepared.setString(2, r.getHeure());
			prepared.setString(3, r.getNomMedecin());
			prepared.execute();
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// supprimer un rdv danse la base des donne
	private void suppRdvBD(int id) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "delete from rdv where id_rdv = " + id;
		try {
			prepared = cnx.prepareStatement(sql);
			prepared.execute();
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
	}

	
	////////////////////////////////// methodes factures BD ////////////////////////
	// ajouter un facture dans la base des donnes
	private void ajouterFactureBD(Facture f) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "insert into facture values(?,?,?,?)";
		try {
			prepared = cnx.prepareStatement(sql);
			
			prepared.setInt(1, 0);
			prepared.setString(2, ft.format(f.getDate_facture()));
			prepared.setDouble(3, f.getMontant());
			prepared.setInt(4, f.getId_patient());
			prepared.execute();
			
			JOptionPane.showMessageDialog(null, "Ajouter une consultation,facture");
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// modifier un facture dans la base des donnes
	private void modifierFactureBD(Facture f) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "update facture set montant = ? where id_facture = " + f.getId_facture();
		try {
			prepared = cnx.prepareStatement(sql);
			prepared.setDouble(1, f.getMontant());
			prepared.execute();
			
			JOptionPane.showMessageDialog(null, "facture modifier");
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// recuperer id de patient a partir id de facture 
	private int idpatinetFactureBD(int id) {
		int idp = 0;
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_patient from facture where id_facture = " + id;
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			if(resultat.next())
				idp = resultat.getInt("id_patient");
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
		return idp;
	}
	
	
	////////////////////////////// methodes facture ///////////////////////////////////////////
	// chercher facture
	public Facture rechercherFacture(int id) {
		for (int i = 0; i < getFactures().size(); i++) {
			if(getFactures().get(i).getId_facture() == id) {
				return getFactures().get(i);
			}
		}
		return null;
	}	
	// ajouter une facture
	public void ajouterFacture(Facture f) {
		getFactures().add(f);
		ajouterFactureBD(f);
	}
	// modifier un patient
	public void modifierFacture(int id,double montant) {
		int idp = idpatinetFactureBD(id);
		Patient p = FormLogIn.userNow.chercherPatient(idp);
		Facture f = rechercherFacture(id);
		String option[] = {"oui","no"};
		int ret = JOptionPane.showOptionDialog(null, "vous etes sure ?", "modifier cette facture",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, option, option[0]);
		if(ret == 0) {
			for (int i = 0; i < getFactures().size(); i++) {
				if(getFactures().get(i).getId_facture() == f.getId_facture())
					getFactures().remove(i);
			}
			f.setMontant(montant);
			getFactures().add(f);
			p.modifierFacture(f);
			if(p.isInscrit())
				modifierFactureBD(f);
		}
	}
	
	
	///////////////////////////////// recupere les info dans la BD //////////////////////////////
	// recuperer tous les rdvs dans la base des donnes
	public void listeRdvBD() {
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
				boolean ins = resultat.getBoolean("inscrit");
				
				rdvs.add(new Rdv(id, date1, heureBD, medecinBD, idp,ins));
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// recuperer tous les factures dans la base des donnes
	public void listeFactureBD() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from facture";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				int id = resultat.getInt("id_facture");
				double montant = resultat.getDouble("montant");
				int id_patient = resultat.getInt("id_patient");
				String date = resultat.getString("date_facture");
				Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(date);
				Facture f = new Facture(montant, date1);
				f.setId_facture(id);
				f.setId_patient(id_patient);
				factures.add(f);
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	
	
	///////////////////////// methodes pour afficher les info ////////////////////////////
	@Override
	public String toString() {
		return "Utilisateur [nomUtl=" + nomUtl + ", mdp=" + mdp + "]";
	}	
	public void afficher() {
		System.out.println(this + " ," +super.toString());
	}
}
