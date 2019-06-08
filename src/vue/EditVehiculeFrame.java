package vue;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.SettingsXml;
import dao.VehiculeSettingsPojo;

public class EditVehiculeFrame implements Runnable {

	public JFrame getFrmAjouterVehicule() {
		return frmAjouterVehicule;
	}

	public void setFrmAjouterVehicule(JFrame frmAjouterVehicule) {
		this.frmAjouterVehicule = frmAjouterVehicule;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JTextField getNumPlate() {
		return numPlate;
	}

	public void setNumPlate(JTextField numPlate) {
		this.numPlate = numPlate;
	}

	public JTextField getMarque() {
		return marque;
	}

	public void setMarque(JTextField marque) {
		this.marque = marque;
	}

	public JLabel getLblMarque() {
		return lblMarque;
	}

	public void setLblMarque(JLabel lblMarque) {
		this.lblMarque = lblMarque;
	}

	public JTextField getCouleur() {
		return couleur;
	}

	public void setCouleur(JTextField couleur) {
		this.couleur = couleur;
	}

	public JLabel getLblCouleur() {
		return lblCouleur;
	}

	public void setLblCouleur(JLabel lblCouleur) {
		this.lblCouleur = lblCouleur;
	}

	public JLabel getLblType() {
		return lblType;
	}

	public void setLblType(JLabel lblType) {
		this.lblType = lblType;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getType() {
		return type;
	}

	@SuppressWarnings("rawtypes")
	public void setType(JComboBox type) {
		this.type = type;
	}

	public JTextField getCinProp() {
		return cinProp;
	}

	public void setCinProp(JTextField cinProp) {
		this.cinProp = cinProp;
	}

	public JLabel getLblCinPropritetaire() {
		return lblCinPropritetaire;
	}

	public void setLblCinPropritetaire(JLabel lblCinPropritetaire) {
		this.lblCinPropritetaire = lblCinPropritetaire;
	}

	public JLabel getLblNomProprieraitre() {
		return lblNomProprieraitre;
	}

	public void setLblNomProprieraitre(JLabel lblNomProprieraitre) {
		this.lblNomProprieraitre = lblNomProprieraitre;
	}

	public JTextField getNomProp() {
		return nomProp;
	}

	public void setNomProp(JTextField nomProp) {
		this.nomProp = nomProp;
	}

	public VehiculeSettingsPojo getVsp() {
		return vsp;
	}

	public void setVsp(VehiculeSettingsPojo vsp) {
		this.vsp = vsp;
	}

	public JButton getRef() {
		return ref;
	}

	public void setRef(JButton ref) {
		this.ref = ref;
	}

	private JFrame frmAjouterVehicule;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JTextField numPlate;
	private JTextField marque;
	private JLabel lblMarque;
	private JTextField couleur;
	private JLabel lblCouleur;
	private JLabel lblType;
	@SuppressWarnings("rawtypes")
	private JComboBox type;
	private JTextField cinProp;
	private JLabel lblCinPropritetaire;
	private JLabel lblNomProprieraitre;
	private JTextField nomProp;
	private VehiculeSettingsPojo vsp;
	private JButton ref;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	EditVehiculeFrame window = new EditVehiculeFrame(new VehiculeSettingsPojo());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public EditVehiculeFrame(VehiculeSettingsPojo vps,JButton ref) {
		this.ref=ref;
		this.vsp = vps;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		System.out.println(vsp);
		frmAjouterVehicule = new JFrame();
		frmAjouterVehicule.getContentPane().setBackground(new Color(105, 105, 105));
		frmAjouterVehicule.setResizable(false);
		frmAjouterVehicule.setTitle("Modifier Vehicule");
		frmAjouterVehicule.setAlwaysOnTop(true);
		frmAjouterVehicule.setBounds(100, 100, 324, 343);
		frmAjouterVehicule.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAjouterVehicule.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		panel = new JPanel();
		panel.setBackground(new Color(105, 105, 105));
		frmAjouterVehicule.getContentPane().add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("Numero de Plaque :");
		lblNewLabel.setBounds(17, 22, 157, 16);
		panel.add(lblNewLabel);

		numPlate = new JTextField();
		numPlate.setText(vsp.getNumPlate());
		numPlate.setEditable(false);
		numPlate.setBounds(151, 17, 130, 26);
		panel.add(numPlate);
		numPlate.setColumns(10);

		marque = new JTextField();
		marque.setText(vsp.getMarque());
		marque.setColumns(10);
		marque.setBounds(151, 50, 130, 26);
		panel.add(marque);

		lblMarque = new JLabel("Marque :");
		lblMarque.setBounds(17, 55, 157, 16);
		panel.add(lblMarque);

		couleur = new JTextField();
		couleur.setText(vsp.getCouleur());
		couleur.setColumns(10);
		couleur.setBounds(151, 121, 130, 26);
		panel.add(couleur);

		lblCouleur = new JLabel("Couleur :");
		lblCouleur.setBounds(17, 126, 157, 16);
		panel.add(lblCouleur);

		lblType = new JLabel("Type :");
		lblType.setBounds(17, 93, 157, 16);
		panel.add(lblType);

		type = new JComboBox();
		type.setModel(new DefaultComboBoxModel(new String[] {"Citadine", "Berline", "Commerciale"}));
		if(vsp.getType().equals("Citadine")){
			type.setSelectedIndex(0);
		}
		else if(vsp.getType().equals("Berline")){
			type.setSelectedIndex(1);
		}
		else if(vsp.getType().equals("Commerciale")){
			type.setSelectedIndex(2);
		}
		type.setBounds(151, 88, 130, 26);
		panel.add(type);

		cinProp = new JTextField(vsp.getCinProprietaire());
		cinProp.setColumns(10);
		cinProp.setBounds(151, 192, 130, 26);
		panel.add(cinProp);

		lblCinPropritetaire = new JLabel("Cin propritetaire :");
		lblCinPropritetaire.setBounds(17, 197, 157, 16);
		panel.add(lblCinPropritetaire);

		lblNomProprieraitre = new JLabel("Nom proprieraitre :");
		lblNomProprieraitre.setBounds(17, 164, 157, 16);
		panel.add(lblNomProprieraitre);

		nomProp = new JTextField(vsp.getNomProprietaire());
		nomProp.setColumns(10);
		nomProp.setBounds(151, 159, 130, 26);
		panel.add(nomProp);

		JButton btnNewButton = new JButton("Modifier");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsXml sx=new SettingsXml();
				vsp.setCinProprietaire(cinProp.getText());
				vsp.setCouleur(couleur.getText());
				vsp.setMarque(marque.getText());
				vsp.setType(type.getSelectedItem().toString());
				vsp.setNomProprietaire(nomProp.getText());
				sx.edit(vsp);
				ref.doClick();
				frmAjouterVehicule.dispose();
			}
		});
		btnNewButton.setBounds(40, 245, 117, 29);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Annuler");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAjouterVehicule.dispose();
			}
		});
		btnNewButton_1.setBounds(164, 245, 117, 29);
		panel.add(btnNewButton_1);
		frmAjouterVehicule.setVisible(true);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
