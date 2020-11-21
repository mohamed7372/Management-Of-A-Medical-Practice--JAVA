package model;

import java.sql.Connection;
import java.util.Date;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import category.Groupage;
import category.Sexe;
import controllor.ConnexionMySql;

public class Medecin extends Utilisateur{
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	public static int patientNoInscrit = 0;
	private int id_Med;
	private String specialite;
	private Vector<Patient> patients;
	private Vector<Ordonnance> ordonnances;
	private boolean medecin = true;
	
	// constructeur
	public Medecin(String nom, String prenom, String tel, String adr,String email, Sexe sexe,Date date_inscp, String nomUtl,
			String mdp,String specialite) {
		
		super(nom, prenom, tel, adr,email, sexe,date_inscp,nomUtl,mdp);
		this.specialite = specialite;
		
		// collection pour tous les patients 
		patients = new Vector<Patient>();
		// recuperer les patients dans la base des donnes
		listePatientBD();
		// collection pour tous les ordonnanes
		ordonnances = new Vector<Ordonnance>();
		// recuperre les ordonnances dans la base des donnes
		listeOrdonnanceBD();
	}
	public Medecin(String nom, String prenom, String tel, String adr,String email, Sexe sexe,String mdp) {
		super(nom, prenom, tel, adr,email, sexe,mdp);
		
		patients = new Vector<Patient>();
		listePatientBD();
		// collection pour tous les ordonnanes
		ordonnances = new Vector<Ordonnance>();
		// recuperre les ordonnances dans la base des donnes
		listeOrdonnanceBD();
	}

	// setters et getters
	public int getId_Med() {
		return id_Med;
	}
	public void setId_Med(int id_Med) {
		this.id_Med = id_Med;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public Vector<Patient> getPatients() {
		return patients;
	}
	public void setPatients(Vector<Patient> patients) {
		this.patients = patients;
	}
	public Vector<Ordonnance> getOrdonnances() {
		return ordonnances;
	}
	public void setOrdonnances(Vector<Ordonnance> ordonnances) {
		this.ordonnances = ordonnances;
	}
	public boolean isMedecin() {
		return medecin;
	}
	public void setMedecin(boolean medecin) {
		this.medecin = medecin;
	}
	
	
	//////////////////////////////////// action consultation ////////////////////////////////////
	// faire une consultation a un patient et prendre les mesures et donnes une facture
	public void consulterPatient(int id,double tension,double poids,double taille,String maladeChro,String analyse,String consultation,double montant,
		int nbrConje,String traitement) {
		Patient p = chercherPatient(id);
		// verifier s il existe ce patient
		if(p != null) {
			if(!p.isInscrit()) {
				consulterPatientNoInscrit(id, tension, poids, taille,maladeChro, consultation, montant, nbrConje, traitement);
			}
			else {
				SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
				// chaque consultation a un nom de medcein et leur specialite qui fait cette consultation plus la date
				String titre = getNom().concat(" ,").concat(getSpecialite()).concat(" ,").concat(ft.format(new Date()));
				String finConsultation = p.getConsultations().concat(titre).concat("\n")
						.concat("------------------------------------------\n")
						.concat(consultation).concat("\n")
						.concat("****************************************************\n");
				
				// ajouter la facture a la liste des factures de utilisateur
				Facture f = new Facture(montant, new Date());
				int idfacture;
				if(getFactures().size() == 0) 
					idfacture = 0;
				else
					idfacture = getFactures().get(getFactures().size()-1).getId_facture() + 1;
	
				f.setId_facture(idfacture);
				f.setId_patient(id);
				super.ajouterFacture(f);
				// ajouter l'ordonnance a la liste des ordonnances de patient
				p.ajouterFacture(f);
				
				// ajouter cette consultation et mesure au patient
				p.setConsultations(finConsultation);
				p.prendreMesureMaladeChro(tension, poids, taille, maladeChro); 
				
				// save consultation et mesure dans la base des donnes
				miseAjourInfoPatientBD(id, finConsultation, poids, taille, tension, maladeChro, analyse);
				
				// si le patient est n'a pas malade donc ne donne pas un traitement en donne juste une facture
				if(!traitement.equals("")) {
					// ajouter l'ordonnance a la liste des ordonnances de medecin
					Ordonnance o = new Ordonnance(new Date(), nbrConje, traitement);
					int idord;
					if(getOrdonnances().size() == 0) 
						idord = 0;
					else
						idord = getOrdonnances().get(getOrdonnances().size()-1).getId_ordonnance() + 1;
	
					o.setId_ordonnance(idord);
					o.setId_patient(id);
					ajouterOrdonnance(o);
					// ajouter l'ordonnance a la liste des ordonnances de patient
					p.ajouterOrdonnance(o);
					// save ordonnance dans la base des donnes
					ajouterOrdonnanceBD(o);
				}
			}
		}
	}
	public void consulterPatientNoInscrit(int id,double tension,double poids,double taille,String maladeChro,String consultation,double montant,
			int nbrConje,String traitement) {
		Patient p = chercherPatient(id);
		// verifier s il existe ce patient
		if(p != null) {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
			// chaque consultation a un nom de medcein et leur specialite qui fait cette consultation plus la date
			String titre = getNom().concat(" ,").concat(getSpecialite()).concat(" ,").concat(ft.format(new Date()));
			String finConsultation = titre.concat("\n")
					.concat("------------------------------------------\n")
					.concat(consultation).concat("\n")
					.concat("****************************************************\n");
			
			// ajouter la facture a la liste des factures de utilisateur
			Facture f = new Facture(montant, new Date());
			int idfacture;
			if(getFactures().size() == 0) 
				idfacture = 0;
			else
				idfacture = getFactures().get(getFactures().size()-1).getId_facture() + 1;

			f.setId_facture(idfacture);
			f.setId_patient(id);
			super.getFactures().add(f);
			// ajouter l'ordonnance a la liste des ordonnances de patient
			p.ajouterFacture(f);
			
			// ajouter cette consultation et mesure au patient
			p.setConsultations(finConsultation);
			p.prendreMesureMaladeChro(tension, poids, taille, maladeChro); 
			
			// si le patient est n'a pas malade donc ne donne pas un traitement en donne juste une facture
			if(!traitement.equals("")) {
				// ajouter l'ordonnance a la liste des ordonnances de medecin
				Ordonnance o = new Ordonnance(new Date(), nbrConje, traitement);
				int idord;
				if(getOrdonnances().size() == 0) 
					idord = 0;
				else
					idord = getOrdonnances().get(getOrdonnances().size()-1).getId_ordonnance() + 1;

				o.setId_ordonnance(idord);
				o.setId_patient(id);
				ajouterOrdonnance(o);
				// ajouter l'ordonnance a la liste des ordonnances de patient
				p.ajouterOrdonnance(o);
			}
			
			JOptionPane.showMessageDialog(null, "Consultation,Traitement,facture ajouter");
		}
	}
	// modifier consultation et mesure de patient qui faire une consultation
	private void miseAjourInfoPatientBD(int id,String consultation,double poids, double taille, double tension, String maladeChro, String analyse) {
		cnx = ConnexionMySql.ConnexionDb();
		boolean existe = false;
		if(! maladeChro.trim().isEmpty()) {
			existe = true;
		}
		String sql = "update patient set consultation = ?,poids = ?, tension = ?, taille = ? ,"
				+ " malades = ?, analyse = ?, maladeChro = ? where id_patient = " + id;
		try {
			prepared = cnx.prepareStatement(sql);
			prepared.setString(1, consultation);
			prepared.setDouble(2, poids);
			prepared.setDouble(3, tension);
			prepared.setDouble(4, taille);
			prepared.setString(5, maladeChro);
			prepared.setString(6, analyse);
			prepared.setBoolean(7, existe);
			prepared.execute();
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// rechercher et remplir id combo
	public DefaultComboBoxModel<String> rechercherCons(String []nomSearch) {
		DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
		
		for (int i = 0; i < getPatients().size(); i++) {
			String nom = getPatients().get(i).getNom();
			String prenom = getPatients().get(i).getPrenom();
			if(nomSearch[0].equalsIgnoreCase(nom) && nomSearch[1].equalsIgnoreCase(prenom)) {
				String elt = "ID ".concat(String.valueOf(getPatients().get(i).getId_patient()))
						.concat(" / tel : ").concat(getPatients().get(i).getTel());
				idModel.addElement(elt);
			}
		}
		return idModel;
	}
	

	///////////////////////////////// recupere les info dans la BD //////////////////////////////
	// recuperer tous les patients dans la base des donnes qu on a lance l'application
	private void listePatientBD() {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from patient";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				int id = resultat.getInt("id_patient");
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				String tel = resultat.getString("tel");
				String adr = resultat.getString("adr");
				String email = resultat.getString("email");
				double poid = resultat.getDouble("poids");
				double taille = resultat.getDouble("taille");
				double tension = resultat.getDouble("tension");
				String malad_chronique = resultat.getString("malades");
				// convertir le sexe vers type Sexe enum
				String sexe = resultat.getString("sexe");
				Sexe sexeCat = Sexe.Homme;
				if(sexe.equalsIgnoreCase("F"))
					sexeCat = Sexe.Femme;
				// convertir le groupage sanguin vers type Groupage enum
				String grpSang = resultat.getString("groupage");
				Groupage grpSangCat = grpSang(grpSang);
				// convertir la date type String vers la date type Date
				String date_inscp = resultat.getString("date_inscp");
				Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(date_inscp);  
				String date_naiss = resultat.getString("date_naiss");
				Date date2=new SimpleDateFormat("yyyy/MM/dd").parse(date_naiss);  
				
				String consul = resultat.getString("consultation"); 
				String analy = resultat.getString("analyse");
				
				patients.add(new Patient(id,nom, prenom, tel, adr, email, sexeCat, date1, date2, poid, taille, 
						tension, grpSangCat, malad_chronique,consul,analy));
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// recuperer tous les ordonnance dans la base des donnes qu on a lance l'application
	public void listeOrdonnanceBD() {
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
				String date_o = resultat.getString("date_ord");
				Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(date_o);  

				Ordonnance o = new Ordonnance(id, date1, jour, traitement);
				o.setId_patient(idp);
				ordonnances.add(o);
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	
	
	//////////////////////////////////// methodes ordonnances ///////////////////////////////////
	// ajouter une ordonnance dans la liste des ordonnances de medecin
	public void ajouterOrdonnance(Ordonnance o) {
		ordonnances.add(o);
	}
	// modifier une ordonnance dans la liste de medecin et la liste de patient
	// modifier une ordonnance dans la Bd,liste de medecin,liste de patient
	public void modifierOrdonnance(Ordonnance o) {
		Patient pModifier = chercherPatient(o.getId_patient());
		// modfiier dans la liste de patient
		pModifier.modifierOrdonnance(o);
		// modifier dans la liste de medecin
		for (int i = 0; i < getOrdonnances().size(); i++) {
			if(getOrdonnances().get(i).getId_ordonnance() == o.getId_ordonnance()) {
				getOrdonnances().remove(i);
				break;
			}
		}
		getOrdonnances().add(o);
		if(pModifier.isInscrit()) {
			// modifier dans la BD
			modifierOrdonnanceBD(o);
		}
	}
	// remplir idcombo par tous les id des ordonnanes lie a patient rechercher
	public DefaultComboBoxModel<String> chercherPatient(String []nomSearch) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
		for (int i = 0 ; i < patients.size();i++) {
			if(nomSearch[0].equalsIgnoreCase(patients.get(i).getNom()) && 
					nomSearch[1].equalsIgnoreCase(patients.get(i).getPrenom())){
				for (int j = 0; j < patients.get(i).getOrdonnances().size() ; j++) {
					String elt = "id_patient "
							.concat(String.valueOf(patients.get(i).getId_patient()))
							.concat(" / id_ord ")
							.concat(String.valueOf(patients.get(i).getOrdonnances().get(j).getId_ordonnance()))
							.concat(" / date : ")
							.concat(ft.format(patients.get(i).getOrdonnances().get(j).getDate_ordonnance()))
							.concat(" / tel : ")
							.concat(patients.get(i).getTel());
					idModel.addElement(elt);
				}
			}
		}
		return idModel;
	}
	

	//////////////////////////////// methode patients //////////////////////////
	// recuperer un patient par votre id a partir la liste des patients de medecin
	public Patient chercherPatient(int id) {
		for (int i = 0 ; i < patients.size();i++)
			if(id == patients.get(i).getId_patient()){
				return ( patients.get(i));
			}
	    return null;
	}
	// ajouter un nouveau patient dans la liste de medecin (cette liste contient tous les patients)
	public void ajouterPatient(Patient p) {
		p.setId_patient(patients.get(patients.size()-1).getId_patient() + 1);
		boolean b = ajouterPatientBD(p);
		/* verifier ce patient s il n existe pas dans la base donne donc retourner vrai pour ajouter 
		*  a la collection de tous patients */
		if(b)
			patients.add(p);
	}
	// modifier un patient dans la liste de medecin 
	public void modifierPatient(Patient p) {
		Patient pModifier = chercherPatient(p.getId_patient());
		pModifier.copierInfoPatient(p);
		modifierPatientBD(p);
	}
	// supprimer un patient dans la liste de medecin 
	public void supprimerPatient(int id) {
		for (int i = 0; i < patients.size(); i++) {
			if(patients.get(i).getId_patient() == id) {
				patients.remove(i);
				suppPatientBD(id);
			}
		}
	}
	
	
	///////////////////////////// methodes de patient BD ///////////////////////////////
	// rechercher un patient s il existe dans la Base des donnes avec ces cordonnes nom,prenom,tel
	// si il existe n autrise pas pour ajouter autre fois
	boolean rechercherPatientBD(String nom,String prenom,String tel) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_patient from patient where nom = '" + nom + "' and prenom = '"+ prenom +
				"' and tel = '" + tel + "'";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			if(resultat.next()) {
				int id = resultat.getInt("id_patient");
				JOptionPane.showMessageDialog(null,"ce patient est deja inscrit (leur id = " + id + " )");
				return false;
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return true;
	}
	// rechercher patient plus 5 ans inscrit
	public DefaultComboBoxModel<String> rechercherPatientPlus5BD() {
		DefaultComboBoxModel<String> idmodel = new DefaultComboBoxModel<String>();
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select id_patient,tel,date_inscp from patient";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String idBD = String.valueOf(resultat.getInt("id_patient"));
				String tel = resultat.getString("tel");
				
				String []d = resultat.getString("date_inscp").split("/");
				Date date = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy");
				int anneCurrent = Integer.parseInt(ft.format(date));
				int diff = anneCurrent - Integer.parseInt(d[0]);
				if(diff>0) {
					String elt = "ID ".concat(idBD).concat(" / tel : ").concat(tel).concat(" (inscirt plus 5 ans)");
					idmodel.addElement(elt);
				}
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return idmodel;
	}
	// ajouter un patient dans la base des donnes
	public boolean ajouterPatientBD(Patient p) {
		if(rechercherPatientBD(p.getNom(), p.getPrenom(), p.getTel())) {
			cnx = ConnexionMySql.ConnexionDb();
			
			char s = 'F';
			if(p.getSexe().equals(Sexe.Homme))
				s = 'H';
			String groupeSang = grpSang(p.getGrpSang());
			SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
			boolean isMaladeChro = false;
			if(p.getMalad_chronique().trim().length() != 0)
				isMaladeChro = true; 
			
			String sql = "insert into patient values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				prepared = cnx.prepareStatement(sql);
				prepared.setInt(1, 0);
				prepared.setString(2, ft.format(p.getDate_naiss()));
				prepared.setString(3, groupeSang);
				prepared.setBoolean(4, isMaladeChro);
				prepared.setString(5, p.getMalad_chronique());
				prepared.setString(6, p.getNom());
				prepared.setString(7, p.getPrenom());
				prepared.setString(8, p.getTel());
				prepared.setString(9, p.getAdr());
				prepared.setString(10, String.valueOf(s));
				prepared.setString(11, ft.format(p.getDate_inscp()));
				prepared.setString(12, p.getEmail());
				prepared.setDouble(13, p.getMesure().getTaille());
				prepared.setDouble(14, p.getMesure().getPoids());
				prepared.setDouble(15, p.getMesure().getTension());
				prepared.setString(16, p.getConsultations());
				prepared.setString(17, p.getAnalyse());
				prepared.execute();
				
				JOptionPane.showMessageDialog(null, "patient ajouter");
				ConnexionMySql.disconnect(prepared, resultat, cnx);
				return true;
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
				return false;
			}
		}
		return false;
	}
	// modifier un patient dans la base des donnes
	private boolean modifierPatientBD(Patient p) {
		cnx = ConnexionMySql.ConnexionDb();
		
		char s = 'F';
		if(p.getSexe().equals(Sexe.Homme))
			s = 'H';
		String groupeSang = grpSang(p.getGrpSang());
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		boolean isMaladeChro = false;
		if(p.getMalad_chronique().trim().length() != 0)
			isMaladeChro = true; 
		
		String sql = "update patient set nom = ?, prenom = ?, date_naiss = ?, sexe = ?, tel = ?, "
				+ "email = ?, adr = ?, groupage = ?, poids = ?, taille = ?, tension = ? "
				+ ", maladeChro = ?, malades = ? where id_patient = " + p.getId_patient();
		try {
			prepared = cnx.prepareStatement(sql);
			prepared.setString(1, p.getNom());
			prepared.setString(2, p.getPrenom());
			prepared.setString(3, ft.format(p.getDate_naiss()));
			prepared.setString(4, String.valueOf(s));
			prepared.setString(5, p.getTel());
			prepared.setString(6, p.getEmail());
			prepared.setString(7, p.getAdr());
			prepared.setString(8, groupeSang);
			prepared.setDouble(9, p.getMesure().getPoids());
			prepared.setDouble(10, p.getMesure().getTaille());
			prepared.setDouble(11, p.getMesure().getTension());
			prepared.setBoolean(12, isMaladeChro);
			prepared.setString(13, p.getMalad_chronique());
			prepared.execute();
			
			JOptionPane.showMessageDialog(null, "patient modifier");
			ConnexionMySql.disconnect(prepared, resultat, cnx);
			return true;
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
		return false;
	}
	// supprimer un patient danse la base des donnes
	private boolean suppPatientBD(int id) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "delete from patient where id_patient = " + id;
		String option[] = {"oui","no"};
		try {
			int ret = JOptionPane.showOptionDialog(null, "vous etes sure ?", "supprimer cette patient",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, option, option[0]);
			if(ret == 0) {
				prepared = cnx.prepareStatement(sql);
				prepared.execute();
				return true;
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return false;
	}
	// recuperer tous les patients a meme nom et prenom
	// en diffirencier entre eux par le num de tel
	public DefaultComboBoxModel<String> rechercherPatientBD(String []nomSearch) {
		DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from patient where nom = '" + nomSearch[0] + "' and prenom = '"+ nomSearch[1] + "'";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				String idBD = String.valueOf(resultat.getInt("id_patient"));
				String tel = resultat.getString("tel");
				if(nomSearch[0].equalsIgnoreCase(nom) && nomSearch[1].equalsIgnoreCase(prenom)) {
					String elt = "ID ".concat(idBD).concat(" / tel : ").concat(tel);
					idModel.addElement(elt);
				}
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return idModel;
	}
	
	
	////////////////////////////// methodes d'ordonnance BD ///////////////////////////
	// ajouter une ordonnance dans la base des donnes
	public void ajouterOrdonnanceBD(Ordonnance o) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "insert into ordonnance values(?,?,?,?,?)";
		try {
			prepared = cnx.prepareStatement(sql);
			
			prepared.setInt(1, 0);
			prepared.setInt(2, o.getConjeMaladie());
			prepared.setString(3, o.getTraitement());
			prepared.setString(4, ft.format(o.getDate_ordonnance()));
			prepared.setInt(5, o.getId_patient());
			prepared.execute();
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
	}
	// modifier une ordonnance dans la base des donnes
	private boolean modifierOrdonnanceBD(Ordonnance o) {
		cnx = ConnexionMySql.ConnexionDb();
		
		String sql = "update ordonnance set conjeMaladie = ?, traitement = ? where id_ordonnance = " + o.getId_ordonnance();
		try {
			prepared = cnx.prepareStatement(sql);
			prepared.setInt(1, o.getConjeMaladie());
			prepared.setString(2, o.getTraitement());
			prepared.execute();
			
			JOptionPane.showMessageDialog(null, "ordonnance modifier");
			ConnexionMySql.disconnect(prepared, resultat, cnx);
			return true;
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
		return false;
	}
	// rechercher
	public DefaultComboBoxModel<String> rechercherOrd(String []nomSearch) {
		DefaultComboBoxModel<String> idModel = new DefaultComboBoxModel<String>();
		for (int i = 0; i < getPatients().size(); i++) {
			String nom = getPatients().get(i).getNom();
			String prenom = getPatients().get(i).getPrenom();
			if(nomSearch[0].equalsIgnoreCase(nom) && nomSearch[1].equalsIgnoreCase(prenom)) {
				for (int j = 0; j < getPatients().get(i).getOrdonnances().size(); j++) {
					String elt = "Id_patient ".concat(String.valueOf(getPatients().get(i).getId_patient()))
							.concat(" / id_ord ").concat(String.valueOf(getPatients().get(i).getOrdonnances().get(j).getId_ordonnance()))
							.concat(" / tel : ").concat(getPatients().get(i).getTel());
					idModel.addElement(elt);					
				}
			}
		}
		return idModel;
	}
	
	
	///////////////////////////// methodes des utilisateurs /////////////////////////////
	// ajouter
	public void ajouterUser(boolean medecin ,Medecin med){
		cnx = ConnexionMySql.ConnexionDb();

		char s = 'F';
		if(med.getSexe().equals(Sexe.Homme))
			s = 'H';
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/mm/dd");
		
		String sql = "insert into utilisateur values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			prepared = cnx.prepareStatement(sql);
			prepared.setInt(1, 0);
			prepared.setString(2, med.getNomUtl().trim());
			prepared.setString(3, med.getMdp().trim());
			prepared.setString(4, med.getNom().trim());
			prepared.setString(5, med.getPrenom().trim());
			prepared.setString(6, med.getTel().trim());
			prepared.setString(7, med.getAdr().trim());
			prepared.setString(8, String.valueOf(s).trim());
			prepared.setString(9, ft.format(getDate_inscp()).trim());
			prepared.setBoolean(10, medecin);
			prepared.setString(11, med.getEmail().trim());
			prepared.setString(12, med.getSpecialite().trim());
			prepared.execute();
			
			JOptionPane.showMessageDialog(null, "utilisateur ajouter");
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
	}
	// modifier
	public boolean modifierUser(String id, Medecin med) {
		cnx = ConnexionMySql.ConnexionDb();
		
		char s = 'F';
		if(med.getSexe().equals(Sexe.Homme))
			s = 'H';
		
		String sql = "update utilisateur set nom = ?, prenom = ?, sexe = ?, adr = ?, tel = ?, "
				+ "email = ?, nomUser = ?, mdp = ? where id_utilisateur = " + Integer.parseInt(id);
		try {
			if(!med.getNom().equals("") && !med.getPrenom().equals("") && !med.getTel().equals("") && 
					!med.getEmail().equals("") && !med.getMdp().equals("")) {
				prepared = cnx.prepareStatement(sql);
				prepared.setString(1, med.getNom());
				prepared.setString(2, med.getPrenom());
				prepared.setString(3, String.valueOf(s));
				prepared.setString(4, med.getAdr());
				prepared.setString(5, med.getTel());
				prepared.setString(6, med.getEmail());
				prepared.setString(7, med.getNom().concat(".").concat(med.getPrenom()));
				prepared.setString(8, med.getMdp());
				prepared.execute();
				
				JOptionPane.showMessageDialog(null, "utilisateur modifier");
				ConnexionMySql.disconnect(prepared, resultat, cnx);
				return true;
			}
			else
				JOptionPane.showMessageDialog(null, "il faut remplir les champs suivantes : nom, prenom, "
						+ "telephone, adresse email, mot de passe.");
		}
		catch(Exception ev) {
			JOptionPane.showMessageDialog(null, ev + " || " + this.getClass().getName());
		}
		return false;
	}
	// supprimer
	public boolean suppUser(String id) {
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "delete from utilisateur where id_utilisateur = " + Integer.parseInt(id);
		String option[] = {"oui","no"};
		try {
			int ret = JOptionPane.showOptionDialog(null, "vous etes sure ?", "supprimer un utilisateur",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, option, option[0]);
			if(ret == 0) {
				prepared = cnx.prepareStatement(sql);
				prepared.execute();
				return true;
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return false;
	}
	
	
	///////////////////////////////////// autre methodes //////////////////////////////
	// convertir groupe sanguin de type string vers type Groupage ou l'inverse
	public String grpSang(Groupage groupe) {
		switch (groupe) {
		case O_pos:
			return "O+";
		case O_neg:
			return "O-";
		case A_pos:
			return "A+";
		case A_neg:
			return "A-";
		case B_pos:
			return "B+";
		case B_neg:
			return "B-";
		case AB_pos:
			return "AB+";
		default :
			return "A-";
		}
	}
	public Groupage grpSang(String groupe) {
		switch (groupe) {
		case "O+":
			return Groupage.O_pos;
		case "O-":
			return Groupage.O_neg;
		case "A+":
			return Groupage.A_pos;
		case "A-":
			return Groupage.A_neg;
		case "B+":
			return Groupage.B_pos;
		case "B-":
			return Groupage.B_neg;
		case "AB+":
			return Groupage.AB_pos;
		default :
			return Groupage.AB_neg;
		}
	}
		
	
	////////////////////////// calculer stat ///////////////////////////////
	public int totalPatient() {
		return getPatients().size();
	}
	// index 0 pour les patient inscrit et 1 pour les patients non inscrit
	public int patientNoInscrit() {
		return patientNoInscrit;
	}
	public int patientInscrit() {
		return totalPatient() - patientNoInscrit();
	}
	//nbr ord
	public int nbrOrdonnance() {
		return getOrdonnances().size();
	}
	// nbr facture
	public int nbrFacture() {
		return getFactures().size();
	}
	// nbr rdv 
	public int nbrRdv() {
		return getRdvs().size();
	}
	// nbr rdv effectue
	public int nbrRdvEff() {
		int nbr=0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < getRdvs().size(); i++) {
			String date2[] = ft.format(new Date()).split("/");
			String date1[] = ft.format(getRdvs().get(i).getDate()).split("/");
			
			if(Integer.parseInt(date1[0]) <= Integer.parseInt(date2[0]) && Integer.parseInt(date1[1]) <= Integer.parseInt(date2[1]) &&
					Integer.parseInt(date1[2]) < Integer.parseInt(date2[2]))
				nbr++;
		}
		return nbr;
	}
	// patient plus 5 ans qu on a inscrit
	public int nbrPatient5Plus(){
		int nb = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		int currentAnnee = Integer.parseInt(ft.format(new Date()));
		for (int i = 0; i < getPatients().size(); i++) {
			String d = ft.format(getPatients().get(i).getDate_inscp());
			int diff = currentAnnee - Integer.parseInt(d);
			if(diff > 5)
				nb++;
		}
		return nb;
	}
	// index 0 pour tous les medecin generale 1 pour les hommes et 2 pour les femmes
	public int[] medecinStat() {
		int nb[] = {0,0,0};
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from utilisateur";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				boolean medecin = resultat.getBoolean("medecin"); 
				String sexeBD = resultat.getString("sexe");
				String spc = resultat.getString("specialite");
				if(medecin && spc.equalsIgnoreCase("")) {
					nb[0]++;
					if(sexeBD.equalsIgnoreCase("H"))
						nb[1]++;
					else
						nb[2]++;
				}
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return nb;
	}
	// index 0 pour tous les medecin specialite 1 pour les hommes et 2 pour les femmes
	public int[] medecinSpStat() {
		int nb[] = {0,0,0};
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from utilisateur";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				boolean medecin = resultat.getBoolean("medecin"); 
				String sexeBD = resultat.getString("sexe");
				String spc = resultat.getString("specialite");
				if(medecin && !spc.equalsIgnoreCase("")) {
					nb[0]++;
					if(sexeBD.equalsIgnoreCase("H"))
						nb[1]++;
					else
						nb[2]++;
				}
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return nb;
	}
	// index 0 pour tous les medecin generale 1 pour les hommes et 2 pour les femmes
	public int[] secretaireStat() {
		int nb[] = {0,0,0};
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from utilisateur";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				boolean medecin = resultat.getBoolean("medecin"); 
				String sexeBD = resultat.getString("sexe");
				if(!medecin) {
					nb[0]++;
					if(sexeBD.equalsIgnoreCase("H"))
						nb[1]++;
					else
						nb[2]++;
				}
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		return nb;
	}
	// stat pour diag circulaire
	// index 0 pour patient Homme bien sante index 1 patient homme malade chronique
	// index 2 pour patient femme bien sante index 3 patient femme malade chronique
	private double methodeTri(int val) {
		return (val*100)/totalPatient();
	}
	public double[] diagCirStat() {
		int pou[] = {0,0,0,0};
		cnx = ConnexionMySql.ConnexionDb();
		String sql = "select * from patient";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String sexe = resultat.getString("sexe");
				boolean maladesChro = resultat.getBoolean("maladeChro");
				if(sexe.equalsIgnoreCase("H")) {
					if(!maladesChro)
						pou[0]++;
					else
						pou[1]++;
				}
				else if(sexe.equalsIgnoreCase("F")) {
					if(!maladesChro)
						pou[2]++;
					else
						pou[3]++;
				}
			}
			ConnexionMySql.disconnect(prepared, resultat, cnx);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " || " + this.getClass().getName());
		}
		double p[] = {0.0,0.0,0.0,0.0};
		p[0] = methodeTri(pou[0]);
		p[1] = methodeTri(pou[1]);
		p[2] = methodeTri(pou[2]);
		p[3] = methodeTri(pou[3]);
		
		return p;
	}
	// diag de age category
	public int[] diagBarStat() {
		int nbr[] = {0,0,0,0,0,0,0,0,0,0};
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		int anneCurrent = Integer.parseInt(ft.format(new Date()));
		for (int i = 0; i < getPatients().size(); i++) {
			int dateNaiss = Integer.parseInt(ft.format(getPatients().get(i).getDate_naiss()));
			int diff = anneCurrent - dateNaiss;
			if(diff<=10)
				nbr[0]++;
			else if(diff<=20)
				nbr[1]++;
			else if(diff<=30)
				nbr[2]++;
			else if(diff<=40)
				nbr[3]++;
			else if(diff<=50)
				nbr[4]++;
			else if(diff<=60)
				nbr[5]++;
			else if(diff<=70)
				nbr[6]++;
			else if(diff<=80)
				nbr[7]++;
			else if(diff<=90)
				nbr[8]++;
			else 
				nbr[9]++;
		}
		return nbr;
	}
	
	
	///////////////////////// methodes pour afficher les info /////////////////////////
	@Override
	public String toString() {
		return "Medecin [id_Med=" + id_Med + ", specialite=" + specialite + "]";
	}	
	public void afficher() {
		System.out.println(this + " ," + super.toString());
	}
}