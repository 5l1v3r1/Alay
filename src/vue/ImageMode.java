package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

import utils.IconRenderer;

@SuppressWarnings("serial")
public class ImageMode extends JPanel implements Runnable {
	
	
	private JTable table;
	private JLabel lblNewLabel;
	private JButton btnLoad;
	private JButton btnClear;
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnExporter;
	private JScrollPane scrollPane;

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JButton getBtnLoad() {
		return btnLoad;
	}

	public void setBtnLoad(JButton btnLoad) {
		this.btnLoad = btnLoad;
	}

	public JButton getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(JButton btnClear) {
		this.btnClear = btnClear;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public void setBtnStart(JButton btnStart) {
		this.btnStart = btnStart;
	}

	public JButton getBtnStop() {
		return btnStop;
	}

	public void setBtnStop(JButton btnStop) {
		this.btnStop = btnStop;
	}

	public JButton getBtnNewButton() {
		return btnExporter;
	}

	public void setBtnNewButton(JButton btnNewButton) {
		this.btnExporter = btnNewButton;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	/**
	 * Create the panel.
	 */
	public ImageMode() {
		
		setBackground(new Color(105, 105, 105));
		setBounds(100, 100, 893, 562);
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(105, 105, 105));
		add(panel, BorderLayout.WEST);

		btnLoad = new JButton("Importer");
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		panel.add(btnLoad);

		btnClear = new JButton("Vider");
		panel.add(btnClear);

		btnStart = new JButton("Commencer");
		panel.add(btnStart);

		btnStop = new JButton("Arrêter");
		panel.add(btnStop);

		btnExporter = new JButton("Exporter");
		panel.add(btnExporter);

		JLabel label_1 = new JLabel("");
		panel.add(label_1);

		JLabel label_2 = new JLabel("");
		panel.add(label_2);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(105, 105, 105));

		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(0, 0, 0));
		lblNewLabel.setIcon(new ImageIcon(new BufferedImage(436, 320, BufferedImage.TYPE_INT_RGB)));
		panel_1.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(105, 105, 105));
		scrollPane.setOpaque(true);
		scrollPane.getViewport().setBackground(new Color(105, 105, 105));
		add(scrollPane, BorderLayout.CENTER);
		String[] columnsName = { "Nom de fichier", "Resultat", "Image", "Temps", "Acces autorisé" };
		Object[][] objets = new Object[100][5];

		table = new JTable(objets, columnsName);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumn colonneImage = table.getColumnModel().getColumn(2);
		IconRenderer ico = new IconRenderer();
		ico.setHorizontalAlignment(JLabel.CENTER);
		colonneImage.setCellRenderer(ico);
		// table.setValueAt("/Users/s-man/Desktop/a.png", 2, 2);
		table.setBackground(new Color(105, 105, 105));
		table.setOpaque(true);
		scrollPane.setViewportView(table);
		// // table.setValueAt("test", 1, 1);
		// table.setValueAt("/Users/s-man/Desktop/tryWithJava/outputs/resultat265.png",
		// 2, 2);
		// table.setValueAt("/Users/s-man/Desktop/voitures/20170222_120930.jpg",
		// 3, 2);

	}

	public JButton getBtnExporter() {
		return btnExporter;
	}

	public void setBtnExporter(JButton btnExporter) {
		this.btnExporter = btnExporter;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true)
			;
	}

}
