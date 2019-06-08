package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Settings extends JPanel implements Runnable {
	public void setTable(JTable table) {
		this.table = table;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public void setBtnRechercher(JButton btnRechercher) {
		this.btnRechercher = btnRechercher;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void setBtnAjouter(JButton btnAjouter) {
		this.btnAjouter = btnAjouter;
	}

	public void setBtnModifier(JButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public void setBtnSupprimer(JButton btnSupprimer) {
		this.btnSupprimer = btnSupprimer;
	}

	public void setBtnRaffraichir(JButton btnRaffraichir) {
		this.btnRaffraichir = btnRaffraichir;
	}

	public void setLblX(JLabel lblX) {
		this.lblX = lblX;
	}

	private JTable table;
	private JTextField textField;
	private JButton btnRechercher;
	private JScrollPane scrollPane;
	private JButton btnAjouter;
	private JButton btnModifier;
	private JButton btnSupprimer;
	private JButton btnRaffraichir;
	private JLabel lblX;

	public JTable getTable() {
		return table;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JButton getBtnRechercher() {
		return btnRechercher;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JButton getBtnAjouter() {
		return btnAjouter;
	}

	public JButton getBtnModifier() {
		return btnModifier;
	}

	public JButton getBtnSupprimer() {
		return btnSupprimer;
	}

	public JButton getBtnRaffraichir() {
		return btnRaffraichir;
	}

	public JLabel getLblX() {
		return lblX;
	}

	/**
	 * Create the panel.
	 */
	public Settings() {
		setBackground(new Color(105, 105, 105));
		setBounds(100, 100, 893, 562);
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		String[] columnsName = { "Numéro de plaque", "Marque", "Type", "Couleur", "Nom proprietaire",
				"Cin proprietaire", "Date d'ajout", "Nombre de passages" };
		Object[][] objets = new Object[100][8];

		table = new JTable(objets, columnsName);
		table.setBackground(new Color(105, 105, 105));
		table.setOpaque(true);
		scrollPane.setBackground(new Color(105, 105, 105));
		scrollPane.setOpaque(true);
		scrollPane.getViewport().setBackground(new Color(105, 105, 105));
		scrollPane.setViewportView(table);

		JPanel panelRecherche = new JPanel();
		panelRecherche.setBackground(new Color(105, 105, 105));
		add(panelRecherche, BorderLayout.NORTH);
		panelRecherche.setLayout(new GridLayout(0, 4, 0, 0));

		JLabel lblNumeroDePlaque = new JLabel("Numéro de plaque :");
		panelRecherche.add(lblNumeroDePlaque);

		textField = new JTextField();
		panelRecherche.add(textField);
		textField.setColumns(10);

		btnRechercher = new JButton("Rechercher");
		panelRecherche.add(btnRechercher);

		JLabel label = new JLabel("");
		panelRecherche.add(label);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(105, 105, 105));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 7, 0, 0));

		btnAjouter = new JButton("Ajouter");
		panel.add(btnAjouter);

		btnModifier = new JButton("Modifier");
		panel.add(btnModifier);

		btnSupprimer = new JButton("Supprimer");
		panel.add(btnSupprimer);

		btnRaffraichir = new JButton("Raffraichir");
		panel.add(btnRaffraichir);

		JLabel lblNombreDeVehicules = new JLabel("Nombre =");
		panel.add(lblNombreDeVehicules);

		lblX = new JLabel("x");
		panel.add(lblX);

		JLabel label_1 = new JLabel("");
		panel.add(label_1);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
