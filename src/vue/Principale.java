package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Principale implements Runnable {
	

	private JFrame frame;
	private Video videoTab;
	private ImageMode imageTab;
	private Settings settingsTab;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Video getVideoTab() {
		return videoTab;
	}

	public void setVideoTab(Video videoTab) {
		this.videoTab = videoTab;
	}

	public ImageMode getImageTab() {
		return imageTab;
	}

	public void setImageTab(ImageMode imageTab) {
		this.imageTab = imageTab;
	}

	public Settings getSettingsTab() {
		return settingsTab;
	}

	public void setSettingsTab(Settings settingsTab) {
		this.settingsTab = settingsTab;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principale window = new Principale();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		frame.getContentPane().setBackground(new Color(105, 105, 105));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		videoTab = new Video();
		imageTab = new ImageMode();
		settingsTab = new Settings();
		tabbedPane.add("Mode vidéo", videoTab);
		tabbedPane.add("Mode image", imageTab);
		tabbedPane.add("Configuration", settingsTab);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		frame.setBounds(100, 100, 893, 562);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		while (true)
			;
	}

}
