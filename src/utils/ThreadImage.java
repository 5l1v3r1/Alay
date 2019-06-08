package utils;

import java.util.HashMap;

import javax.swing.JTable;

import dao.SettingsXml;
import main.Main;

public class ThreadImage implements Runnable {
	private JTable table;
	private HashMap<String, String> hashmap;
	private HashMap<String, String> hashmapTraite;
	private HashMap<String, String> hashmapRecadre;

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public HashMap<String, String> getHashmap() {
		return hashmap;
	}

	public void setHashmap(HashMap<String, String> hashmap) {
		this.hashmap = hashmap;
	}

	public HashMap<String, String> getHashmapTraite() {
		return hashmapTraite;
	}

	public void setHashmapTraite(HashMap<String, String> hashmapTraite) {
		this.hashmapTraite = hashmapTraite;
	}

	public HashMap<String, String> getHashmapRecadre() {
		return hashmapRecadre;
	}

	public void setHashmapRecadre(HashMap<String, String> hashmapRecadre) {
		this.hashmapRecadre = hashmapRecadre;
	}

	public ThreadImage(JTable t, HashMap<String, String> hashmap, HashMap<String, String> hashmapTraite,HashMap<String, String> hashmapRecadre) {
		this.table = t;
		this.hashmap = hashmap;
		this.hashmapTraite = hashmapTraite;
		this.hashmapRecadre=hashmapRecadre;
	}

	@Override
	public void run() {
		SettingsXml sx=new SettingsXml();
		PythonResult pr;
		for (int i = 0; i < hashmap.size(); i++) {
			table.setRowSelectionInterval(i, i);
			pr=Main.extract(hashmap.get(table.getValueAt(i, 0)));
			if(pr!=null){
			
			table.setValueAt(pr.getNumPlate(), i, 1);
			hashmapTraite.put((String)table.getValueAt(i, 0), pr.getImgSuccesful());
			hashmapRecadre.put((String)table.getValueAt(i, 0), pr.getImgPlate());
			table.setValueAt(pr.getImgPlate(), i, 2);
			table.setValueAt(pr.getExectime()+" ms", i, 3);
			if(sx.getByNumPlate(pr.getNumPlate()).getNumPlate().equals("null"))
			table.setValueAt("Non", i, 4);
			else 
			table.setValueAt("Oui", i, 4);	
			}
		}


	}

}
