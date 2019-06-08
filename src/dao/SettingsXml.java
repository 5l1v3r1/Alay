package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class SettingsXml {
	public static String fileName = "includes/settings.xml";
	private Element racine;

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		SettingsXml.fileName = fileName;
	}

	public Element getRacine() {
		return racine;
	}

	public void setRacine(Element racine) {
		this.racine = racine;
	}

	public SettingsXml() {
		Document document = null;
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			document = saxBuilder.build(new File(fileName));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (document == null) {
			System.err.println("fichier settings introuvable");
		} else {
			racine = document.getRootElement();
		}

	}

	public List<VehiculeSettingsPojo> getAll() {
		List<VehiculeSettingsPojo> resultat = new ArrayList<VehiculeSettingsPojo>();
		if (racine == null) {
			System.err.println("fichier settings introuvable");
			return resultat;
		} else if (racine.getChildren().size() == 0) {
			return resultat;
		} else {
			for (Element e : racine.getChildren()) {
				VehiculeSettingsPojo vps = new VehiculeSettingsPojo();
				vps.setNumPlate(e.getChildText("numeroDePlaque"));
				vps.setMarque(e.getChildText("marque"));
				vps.setType(e.getChildText("type"));
				vps.setCouleur(e.getChildText("couleur"));
				vps.setNomProprietaire(e.getChildText("nomProprietaire"));
				vps.setCinProprietaire(e.getChildText("cinProprietaire"));
				vps.setDateAjout(e.getChildText("dateAjout"));
				vps.setNbrPassages(Integer.parseInt(e.getChildText("nombrePassage")));
				resultat.add(vps);
			}
		}
		return resultat;

	}

	public VehiculeSettingsPojo getByNumPlate(String numPlate) {

		VehiculeSettingsPojo resultat = new VehiculeSettingsPojo();
		resultat.setNumPlate("null");
		if (racine == null) {
			System.err.println("fichier settings introuvable");
			return resultat;
		} else if (racine.getChildren().size() == 0) {
			return resultat;
		} else {
			for (Element e : racine.getChildren()) {
				if (e.getChildText("numeroDePlaque").equals(numPlate)) {
					VehiculeSettingsPojo vps = new VehiculeSettingsPojo();
					vps.setNumPlate(e.getChildText("numeroDePlaque"));
					vps.setMarque(e.getChildText("marque"));
					vps.setType(e.getChildText("type"));
					vps.setCouleur(e.getChildText("couleur"));
					vps.setNomProprietaire(e.getChildText("nomProprietaire"));
					vps.setCinProprietaire(e.getChildText("cinProprietaire"));
					vps.setDateAjout(e.getChildText("dateAjout"));
					vps.setNbrPassages(Integer.parseInt(e.getChildText("nombrePassage")));
					return vps;
				}
			}
		}
		return resultat;
	}

	public boolean edit(VehiculeSettingsPojo vps) {
		boolean resultat = false;
		if (racine == null) {
			System.err.println("fichier settings introuvable");
			return false;
		} else if (racine.getChildren().size() == 0) {
			System.out.println("aucun element");
			return false;
		} else {
			for (Element e : racine.getChildren()) {
				if (e.getChildText("numeroDePlaque").equals(vps.getNumPlate())) {
					e.getChild("marque").setText(vps.getMarque());
					e.getChild("type").setText(vps.getType());
					e.getChild("couleur").setText(vps.getCouleur());
					e.getChild("nomProprietaire").setText(vps.getNomProprietaire());
					e.getChild("cinProprietaire").setText(vps.getCinProprietaire());
					resultat = true;
				}
			}
		}
		if (resultat == false) {
			System.out.println("aucun resultat trouvé");
			return false;

		} else {
			XMLOutputter xo = new XMLOutputter(Format.getPrettyFormat());

			try {
				xo.output(racine, new FileOutputStream(fileName));
			} catch (FileNotFoundException e) {
				return false;

			} catch (IOException e) {
				return false;
			}
			return true;
		}

	}

	public boolean add(VehiculeSettingsPojo vps) {
		System.out.println(vps.getNumPlate());
		System.out.println("9bal matekteb "+this.getByNumPlate(vps.getNumPlate()).getCinProprietaire());
		if (this.getByNumPlate(vps.getNumPlate()).getNumPlate().equals("null")) {
			System.out.println("ekteb");
			Element vehicule = new Element("vehicule");
			vehicule.addContent(new Element("numeroDePlaque").setText(vps.getNumPlate()));
			vehicule.addContent(new Element("marque").setText(vps.getMarque()));
			vehicule.addContent(new Element("type").setText(vps.getType()));
			vehicule.addContent(new Element("couleur").setText(vps.getCouleur()));
			vehicule.addContent(new Element("nomProprietaire").setText(vps.getNomProprietaire()));
			vehicule.addContent(new Element("cinProprietaire").setText(vps.getCinProprietaire()));
			vehicule.addContent(new Element("dateAjout").setText(vps.getDateAjout()));
			vehicule.addContent(new Element("nombrePassage").setText(String.valueOf(vps.getNbrPassages())));
			racine.addContent(vehicule);
			XMLOutputter xo = new XMLOutputter(Format.getPrettyFormat());

			try {
				xo.output(racine, new FileOutputStream(fileName));
			} catch (FileNotFoundException e) {
				return false;

			} catch (IOException e) {
				return false;

			}
			return true;
		}
		return false;
	}

	public boolean delete(String numPlate) {

		boolean resultat = false;
		for (Element e : racine.getChildren()) {
			if (e.getChildText("numeroDePlaque").equals(numPlate)) {
				racine.removeContent(e);
				resultat = true;
				break;
			}
		}
		if (resultat == false) {
			return false;
		}
		XMLOutputter xo = new XMLOutputter(Format.getPrettyFormat());

		try {
			xo.output(racine, new FileOutputStream(fileName));
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return true;

	}

	
	public boolean incNbrPassage(String numPlate) {
		boolean resultat = false;
		if (racine == null) {
			System.err.println("fichier settings introuvable");
			return false;
		} else if (racine.getChildren().size() == 0) {
			System.out.println("aucun element");
			return false;
		} else {
			for (Element e : racine.getChildren()) {
				if (e.getChildText("numeroDePlaque").equals(numPlate)) {
					
					e.getChild("nombrePassage").setText(String.valueOf(Integer.parseInt(e.getChildText("nombrePassage"))+1));
					resultat = true;
				}
			}
		}
		if (resultat == false) {
			System.out.println("aucun resultat trouvé");
			return false;

		} else {
			XMLOutputter xo = new XMLOutputter(Format.getPrettyFormat());

			try {
				xo.output(racine, new FileOutputStream(fileName));
			} catch (FileNotFoundException e) {
				return false;

			} catch (IOException e) {
				return false;
			}
			return true;
		}

	}

	
	
	public static void main(String[] args) {
		SettingsXml sx = new SettingsXml();
		System.out.println(sx.getAll().size());
		VehiculeSettingsPojo vps = new VehiculeSettingsPojo("1111", "111", "111", "11", "11", "11");
		boolean x = sx.add(vps);
		System.out.println(x);
		x = sx.delete("1111");
		System.out.println(x);
		vps.setNumPlate("111");
		vps.setCouleur("modifie");
		x = sx.edit(vps);
		System.out.println(x);
		System.out.println(sx.getByNumPlate("169TN2995").getCinProprietaire());
		sx.incNbrPassage("169TN2995");
		System.err.println("digne de confiance");
	//	System.out.println(new String("169TN2995").contains(""));

	}
	
}
