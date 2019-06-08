package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import utils.IconRendererVideo;

@SuppressWarnings("serial")
public class Video extends JPanel implements Runnable {
	public void setTable(JTable table) {
		this.table = table;
	}

	public void setTable_1(JTable table_1) {
		this.table_1 = table_1;
	}

	public void setTable_2(JTable table_2) {
		this.table_2 = table_2;
	}

	public void setButtonAjouter(JButton buttonAjouter) {
		this.buttonAjouter = buttonAjouter;
	}

	public void setBtnSupprimer(JButton btnSupprimer) {
		this.btnSupprimer = btnSupprimer;
	}

	public void setBtnStart(JButton btnStart) {
		this.btnStart = btnStart;
	}

	public void setBtnStop(JButton btnStop) {
		this.btnStop = btnStop;
	}

	public void setLblAffichage(JLabel lblAffichage) {
		this.lblAffichage = lblAffichage;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void setBtnExporter(JButton btnExporter) {
		this.btnExporter = btnExporter;
	}

	public void setBtnVider(JButton btnVider) {
		this.btnVider = btnVider;
	}

	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JButton buttonAjouter;
	private JButton btnSupprimer;
	private JButton btnStart;
	private JButton btnStop;
	private JLabel lblAffichage;
	private JScrollPane scrollPane;
	private JButton btnExporter;
	private JButton btnVider;

	public JTable getTable() {
		return table;
	}

	public JTable getTable_1() {
		return table_1;
	}

	public JTable getTable_2() {
		return table_2;
	}

	public JButton getButtonAjouter() {
		return buttonAjouter;
	}

	public JButton getBtnSupprimer() {
		return btnSupprimer;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public JButton getBtnStop() {
		return btnStop;
	}

	public JLabel getLblAffichage() {
		return lblAffichage;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JButton getBtnExporter() {
		return btnExporter;
	}

	public JButton getBtnVider() {
		return btnVider;
	}

	/**
	 * Create the panel.
	 */
	public Video() {
		setBackground(new Color(105, 105, 105));
		setBounds(100, 100, 893, 562);
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(105, 105, 105));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(105, 105, 105));
		panel.add(panel_1);

		JLabel label = new JLabel("Source video : ");
		panel_1.add(label);

		buttonAjouter = new JButton("Ajouter");
		panel_1.add(buttonAjouter);

		btnSupprimer = new JButton("Supprimer");
		panel_1.add(btnSupprimer);

		JLabel label_1 = new JLabel(
				"***********************************************************************************************");
		label_1.setForeground(new Color(105, 105, 105));
		label_1.setBackground(new Color(105, 105, 105));
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("");
		panel_1.add(label_2);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(105, 105, 105));
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		String[] columnsName = { "Nom Fichier", "Chemin absolu", "êtat", "Nombre de vehicules" };
		Object[][] objets = new Object[1][4];
		objets[0][0] = "Nom Fichier";
		objets[0][1] = "Chemin absolu";
		objets[0][2] = "êtat";
		objets[0][3] = "Nombre de vehicules";

		table_1 = new JTable(objets, columnsName);
		table_1.setEnabled(false);
		panel_4.add(table_1);
		Object[][] objets1 = new Object[1][4];
		objets1[0][0] = "";
		objets1[0][1] = "";
		objets1[0][2] = "";
		objets1[0][3] = "";
		table = new JTable(objets1, columnsName);
		table.setBackground(new Color(105, 105, 105));
		panel_4.add(table);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(105, 105, 105));
		panel.add(panel_3);

		btnStart = new JButton("Commencer");
		panel_3.add(btnStart);

		btnStop = new JButton("Arrêter");
		panel_3.add(btnStop);

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(105, 105, 105));
		panel_2.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		lblAffichage = new JLabel("");
		lblAffichage.setIcon(new ImageIcon(new BufferedImage(436, 320, BufferedImage.TYPE_INT_RGB)));
		panel_5.add(lblAffichage);

		JLabel label_3 = new JLabel("New label");
		label_3.setForeground(new Color(105, 105, 105));
		panel_5.add(label_3, BorderLayout.NORTH);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(105, 105, 105));
		panel_2.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(105, 105, 105));
		panel_6.add(panel_7, BorderLayout.NORTH);

		btnExporter = new JButton("Exporter");
		btnExporter.setBackground(new Color(105, 105, 105));
		panel_7.add(btnExporter);

		btnVider = new JButton("Vider");
		btnVider.setBackground(new Color(105, 105, 105));
		panel_7.add(btnVider);

		scrollPane = new JScrollPane();
		panel_6.add(scrollPane, BorderLayout.CENTER);
		String[] columnsName3 = { "Date", "Source", "Texte", "Image", "Acces autorisé" };
		Object[][] objets3 = new Object[100][5];

		table_2 = new JTable(objets3, columnsName3);
		table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_2.setBackground(new Color(105, 105, 105));
		TableColumn colonneImage = table_2.getColumnModel().getColumn(3);
		IconRendererVideo ico = new IconRendererVideo();
		ico.setHorizontalAlignment(JLabel.CENTER);
		colonneImage.setCellRenderer(ico);
		table_2.getColumnModel().getColumn(0).setPreferredWidth(100);
		//
		scrollPane.setViewportView(table_2);
		scrollPane.getViewport().setBackground(new Color(105, 105, 105));

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
