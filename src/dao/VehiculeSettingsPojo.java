package dao;

import java.text.DateFormat;
import java.util.Date;

public class VehiculeSettingsPojo {
	private String numPlate;
	private String marque;
	private String type;
	private String couleur;
	private String nomProprietaire;
	private String cinProprietaire;
	private String dateAjout;
	private int nbrPassages;

	public String getNumPlate() {
		return numPlate;
	}

	public void setNumPlate(String numPlate) {
		this.numPlate = numPlate;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public String getNomProprietaire() {
		return nomProprietaire;
	}

	public void setNomProprietaire(String nomProprietaire) {
		this.nomProprietaire = nomProprietaire;
	}

	public String getCinProprietaire() {
		return cinProprietaire;
	}

	public void setCinProprietaire(String cinProprietaire) {
		this.cinProprietaire = cinProprietaire;
	}

	public String getDateAjout() {
		return dateAjout;
	}

	public void setDateAjout(String dateAjout) {
		this.dateAjout = dateAjout;
	}

	public int getNbrPassages() {
		return nbrPassages;
	}

	public void setNbrPassages(int nbrPassages) {
		this.nbrPassages = nbrPassages;
	}

	public VehiculeSettingsPojo(String numPlate, String marque, String type, String couleur, String nomProprietaire,
			String cinProprietaire) {
		super();
		this.numPlate = numPlate;
		this.marque = marque;
		this.type = type;
		this.couleur = couleur;
		this.nomProprietaire = nomProprietaire;
		this.cinProprietaire = cinProprietaire;
		this.nbrPassages = 0;
		Date date = new Date();	
		DateFormat dateFormat=DateFormat.getDateInstance();
		String d=dateFormat.format(date);
		this.dateAjout = d;
	}

	public VehiculeSettingsPojo() {
		super();
	}
}
