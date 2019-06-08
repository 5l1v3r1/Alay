package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.State;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import dao.SettingsXml;
import dao.VehiculeSettingsPojo;
import utils.PythonResult;
import utils.ThreadImage;
import utils.ThreadVideo;
import vue.AddVehiculeFrame;
import vue.EditVehiculeFrame;
import vue.Principale;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static Thread getThreadImage() {
		return threadImage;
	}

	public static void setThreadImage(Thread threadImage) {
		Main.threadImage = threadImage;
	}

	public static Thread getThreadVideo() {
		return threadVideo;
	}

	public static void setThreadVideo(Thread threadVideo) {
		Main.threadVideo = threadVideo;
	}

	public static int getNbrVideo() {
		return nbrVideo;
	}

	public static void setNbrVideo(int nbrVideo) {
		Main.nbrVideo = nbrVideo;
	}

	private static Thread threadImage;
	private static Thread threadVideo;
	private static int nbrVideo = 0;

	public static String traiter(String src) {
		src = src.replaceAll(" ", "");

		src = src.replaceAll("TT", "T");
		src = src.replaceAll("T", "TN");

		src = src.replaceAll("RR", "R");
		src = src.replaceAll("R", "RS");

		if (src.indexOf("TN") == -1 && src.indexOf("RS") == -1) {

			if (src.length() == 6) {

				if (Integer.parseInt(src.substring(0, 2)) < 50) {
					return src + "RS";
				} else {
					return (src.substring(0, 2) + "TN" + src.substring(2, 5));
				}
			} else if (src.length() == 5) {
				return src + "RS";
			} else if (src.length() == 7) {
				return (src.substring(0, 3) + "TN" + src.substring(3, 7));
			}

		}
		return src;
	}

	public static PythonResult extract(String filename) {
		Process pyEx = null;
		long debut = System.currentTimeMillis();
		try {
			pyEx = Runtime.getRuntime().exec("python includes/tryWithJava/Main.pyc " + filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long fin = System.currentTimeMillis();
		try {
			pyEx.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(pyEx.getInputStream());
		String resultat = scanner.nextLine();

		if (resultat.contains("license")) {
			PythonResult pr = new PythonResult();
			File image = new File("includes/tryWithJava/outputs/imgOriginalScene.png");
			int rnd = (int) (Math.random() * 10000);
			String newFile = image.getAbsolutePath().replace("imgOriginalScene", "resultat" + rnd);
			image.renameTo(new File(newFile));
			pr.setImgSuccesful(newFile);
			image = new File("includes/tryWithJava/outputs/imgPlate.png");
			rnd = (int) (Math.random() * 10000);
			newFile = image.getAbsolutePath().replace("imgPlate", "resultat" + rnd);
			image.renameTo(new File(newFile));
			pr.setImgPlate(newFile);
			pr.setNumPlate(resultat.substring(resultat.lastIndexOf("=") + 1, resultat.length()));
			pr.setNumPlate(Main.traiter(pr.getNumPlate()));
			pr.setExectime(fin - debut);

			return pr;
		}
		return null;
	}

	public static boolean exportVideo(JTable table, HashMap<Integer, String> hashmapVideo,
			HashMap<Integer, String> hashmapVideoCapture) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		String dateS = dateFormat.format(date);
		JFileChooser jf = new JFileChooser();
		jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jf.showOpenDialog(null);
		if (jf.getSelectedFile() == null) {
			System.out.println("il faut selectionner un fichier");
			return false;
		}
		File res = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS + ".html");
		File folder = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS);
		try {
			res.createNewFile();
			folder.mkdir();
		} catch (IOException e) {
			res.delete();
			folder.delete();
			System.out.println("creation des fichier impossible");
			return false;
		}

		String html = "<html><head><title>Historique " + dateS + "</title></head><body><p>Historique jusqu'au " + dateS
				+ "</p><table border=1><tr><td>Numero</td><td>Date de passage</td><td>Image</td><td>Plaque</td><td>Texte</td></tr>";

		@SuppressWarnings("unused")
		int x;
		for (int i = 0; i < ThreadVideo.nbr; i++) {
			x = i + 1;
			File img = new File(hashmapVideo.get(i));
			File dst = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS + "/" + img.getName());
			try {
				Files.copy(img.toPath(), dst.toPath());
			} catch (IOException e1) {
				res.delete();
				folder.delete();
				System.out.println("copie erreur");
				return false;
			}
			File imgR = new File(hashmapVideoCapture.get(i));
			File dstR = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS + "/" + imgR.getName());
			try {
				Files.copy(imgR.toPath(), dstR.toPath());
			} catch (IOException e1) {
				res.delete();
				folder.delete();
				System.out.println("copie recadré erreur");
				return false;
			}

			html = html + "<tr><td>1</td><td>" + table.getValueAt(i, 0) + "</td><td><img src='" + dateS + "/"
					+ imgR.getName() + "' height='400' width='400'></img></td><td><img src='" + dateS + "/"
					+ img.getName() + "' height='20' width='160'></td><td>" + table.getValueAt(i, 2) + "</td></tr>";

		}
		html = html + "</table></body></html>";
		FileWriter fos;
		try {
			fos = new FileWriter(res);
			fos.write(html);
			fos.close();
		} catch (IOException e) {
			System.out.println("ecriture impossible");
			return false;
		}
		return true;
	}

	public static ImageIcon ResizeToFillInLabel(String filename) {
		Mat ori = Imgcodecs.imread(filename);
		Mat m = new Mat();
		Imgproc.resize(ori, m, new Size(436, 320));
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b);
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);

		return new ImageIcon(image);

	}

	public static ImageIcon ResizeToFillInCell(Mat m) {
		Imgproc.resize(m, m, new Size(81, 14));
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b);
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);

		return new ImageIcon(image);
	}

	public static ImageIcon matToLabel(Mat m) {
		Imgproc.resize(m, m, new Size(436, 320));
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b);
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);

		return new ImageIcon(image);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SettingsXml sx = new SettingsXml();
		int sizeSettings = sx.getAll().size();
		HashMap<String, String> hashmap = new HashMap<String, String>();
		HashMap<String, String> hashmapTraite = new HashMap<String, String>();
		HashMap<String, String> hashmapRecadre = new HashMap<String, String>();
		HashMap<Integer, String> hashmapVideo = new HashMap<Integer, String>();
		HashMap<Integer, String> hashmapVideoCapture = new HashMap<Integer, String>();
		HashMap<Integer, String> hashmapVideoCaptureImg = new HashMap<Integer, String>();
		HashMap<Integer, String> hashmapVideoCapturePlt = new HashMap<Integer, String>();
		Principale principale = new Principale();
		Thread thradPrincipale = new Thread(principale);
		thradPrincipale.start();
		principale.getFrame().setVisible(true);
		ThreadImage ti = new ThreadImage(principale.getImageTab().getTable(), hashmap, hashmapTraite, hashmapRecadre);
		ThreadVideo tv = new ThreadVideo(principale.getVideoTab().getLblAffichage(),
				principale.getVideoTab().getTable(), principale.getVideoTab().getTable_2(), hashmapVideoCapture,
				hashmapVideo, Main.nbrVideo, hashmapVideoCaptureImg, hashmapVideoCapturePlt);
		// action du bouton ajouter
		principale.getImageTab().getBtnLoad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>Bouton Importer>>Tab Image Debut");

				// clear previous
				for (int i = 0; i < hashmap.size(); i++) {
					if (hashmapTraite.get(principale.getImageTab().getTable().getValueAt(i, 0)) != null) {
						File file = new File(hashmapTraite.get(principale.getImageTab().getTable().getValueAt(i, 0)));
						file.delete();
					}
					if (hashmapRecadre.get(principale.getImageTab().getTable().getValueAt(i, 0)) != null) {
						File file = new File(hashmapRecadre.get(principale.getImageTab().getTable().getValueAt(i, 0)));
						file.delete();
					}

					principale.getImageTab().getTable().setValueAt("", i, 0);
					principale.getImageTab().getTable().setValueAt("", i, 1);
					principale.getImageTab().getTable().setValueAt("", i, 2);
					principale.getImageTab().getTable().setValueAt("", i, 3);
					principale.getImageTab().getTable().setValueAt("", i, 4);
				}

				hashmap.clear();
				hashmapTraite.clear();
				hashmapRecadre.clear();
				principale.getImageTab().getLblNewLabel()
						.setIcon(new ImageIcon(new BufferedImage(436, 320, BufferedImage.TYPE_INT_RGB)));
				// end clear previous

				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jf.showOpenDialog(null);
				File folder = jf.getSelectedFile();
				if (folder != null) {

					File[] listOfFiles = folder.listFiles();

					int i = 0;
					for (File f : listOfFiles) {
						if (f.getName().contains(".JPG") || f.getName().contains(".jpg")
								|| f.getName().contains(".png")) {
							hashmap.put(f.getName(), f.getAbsolutePath());
							principale.getImageTab().getTable().setValueAt(f.getName(), i, 0);
							i++;
						}
					}
				}
				System.out.println("log>>Bouton Importer>>Tab Image Fin");
			}
		});
		// action du bouton clear
		principale.getImageTab().getBtnClear().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>Bouton Clear>>Tab Image Debut");

				for (int i = 0; i < hashmap.size(); i++) {
					if (hashmapTraite.get(principale.getImageTab().getTable().getValueAt(i, 0)) != null) {
						File file = new File(hashmapTraite.get(principale.getImageTab().getTable().getValueAt(i, 0)));
						file.delete();
					}
					if (hashmapRecadre.get(principale.getImageTab().getTable().getValueAt(i, 0)) != null) {
						File file = new File(hashmapRecadre.get(principale.getImageTab().getTable().getValueAt(i, 0)));
						file.delete();
					}

					principale.getImageTab().getTable().setValueAt("", i, 0);
					principale.getImageTab().getTable().setValueAt("", i, 1);
					principale.getImageTab().getTable().setValueAt("", i, 2);
					principale.getImageTab().getTable().setValueAt("", i, 3);
					principale.getImageTab().getTable().setValueAt("", i, 4);
				}

				hashmap.clear();
				hashmapTraite.clear();
				hashmapRecadre.clear();
				principale.getImageTab().getLblNewLabel()
						.setIcon(new ImageIcon(new BufferedImage(436, 320, BufferedImage.TYPE_INT_RGB)));
				System.out.println("log>>Bouton Clear>>Tab Image Fin");
			}
		});
		// action jtable select row
		principale.getImageTab().getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {

				JTable t = principale.getImageTab().getTable();
				System.out.println("log>>Select row table>>Tab Image Debut");
				if (t.getSelectedRow() > -1) {
					if (t.getValueAt(t.getSelectedRow(), 0) != null
							&& !t.getValueAt(t.getSelectedRow(), 0).equals("")) {
						if (hashmapTraite.get(t.getValueAt(t.getSelectedRow(), 0)) == null) {
							principale.getImageTab().getLblNewLabel().setIcon(
									Main.ResizeToFillInLabel(hashmap.get(t.getValueAt(t.getSelectedRow(), 0))));
						} else {
							principale.getImageTab().getLblNewLabel()
									.setIcon(new ImageIcon(hashmapTraite.get(t.getValueAt(t.getSelectedRow(), 0))));

						}
					} else {
						principale.getImageTab().getLblNewLabel()
								.setIcon(new ImageIcon(new BufferedImage(436, 320, BufferedImage.TYPE_INT_RGB)));

					}
				}
				System.out.println("log>>Select row table>>Tab Image fin");
			}
		});
		// action bouton start imagemode
		principale.getImageTab().getBtnStart().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>Start>>Tab Image debut");
				threadImage = new Thread(ti);
				threadImage.start();
				System.out.println("log>>Start>>Tab Image fin");
			}
		});
		// action bouton stop imagemode
		principale.getImageTab().getBtnStop().addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>stop>>Tab Image debut");
				if (threadImage != null) {
					threadImage.stop();

				}
				System.out.println("log>>stop>>Tab Image fin");

			}
		});
		// action bouton exporter imagemode
		principale.getImageTab().getBtnExporter().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>exporter>>Tab Image debut");
				System.out.println(Main.extractImage(hashmap, hashmapRecadre, principale.getImageTab().getTable()));
				System.out.println("log>>exporter>>Tab Image fin");

			}
		});
		// action bouton ajouter video
		principale.getVideoTab().getButtonAjouter().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>ajouter>>Tab Video debut");
				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jf.showOpenDialog(null);
				if (jf.getSelectedFile() != null && (jf.getSelectedFile().getName().contains(".mp4")
						|| jf.getSelectedFile().getName().contains(".mov"))) {
					principale.getVideoTab().getTable().setValueAt(jf.getSelectedFile().getName(), 0, 0);
					principale.getVideoTab().getTable().setValueAt(jf.getSelectedFile().getAbsolutePath(), 0, 1);
					principale.getVideoTab().getTable().setValueAt("Pret", 0, 2);
					principale.getVideoTab().getTable().setValueAt("0", 0, 3);
				} else {
					// alert dialog
				}
				System.out.println("log>>ajouter>>Tab Video fin");

			}
		});
		// action bouton supprimer video
		principale.getVideoTab().getBtnSupprimer().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>supprimer>>Tab Video debut");
				if (!principale.getVideoTab().getTable().getValueAt(0, 0).equals("")) {
					principale.getVideoTab().getTable().setValueAt("", 0, 0);
					principale.getVideoTab().getTable().setValueAt("", 0, 1);
					principale.getVideoTab().getTable().setValueAt("", 0, 2);
					principale.getVideoTab().getTable().setValueAt("", 0, 3);
				}
				principale.getVideoTab().getLblAffichage()
						.setIcon(new ImageIcon(new BufferedImage(436, 320, BufferedImage.TYPE_INT_RGB)));
				System.out.println("log>>supprimer>>Tab Video fin");

			}
		});
		// action bouton exporter video
		principale.getVideoTab().getBtnExporter().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>exporter>>Tab video debut");
				System.out.println(
						Main.exportVideo(principale.getVideoTab().getTable_2(), hashmapVideo, hashmapVideoCapture));
				System.out.println("log>>exporter>>Tab Video fin");

			}
		});
		// action bouton start video
		principale.getVideoTab().getBtnStart().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>Start>>Tab Video debut");

				if (threadVideo == null || threadVideo.getState().equals(State.TERMINATED)) {
					tv.setVideoPath((String) principale.getVideoTab().getTable().getValueAt(0, 0));

					threadVideo = new Thread(tv);
					threadVideo.start();
					if (threadVideo.getState().equals(State.NEW)) {
						principale.getVideoTab().getTable().setValueAt("0", 0, 4);
					}
				}

				System.out.println("log>>Start>>Tab Video fin");
			}
		});
		// action bouton stop video
		principale.getVideoTab().getBtnStop().addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>stop>>Tab video debut");
				if (threadVideo != null) {
					threadVideo.stop();
					if (principale.getVideoTab().getTable().getValueAt(0, 2) != null
							&& !principale.getVideoTab().getTable().getValueAt(0, 2).equals(""))
						principale.getVideoTab().getTable().setValueAt("arreté", 0, 2);
				}
				System.out.println("log>>stop>>Tab video fin");

			}
		});
		// btn vider video
		principale.getVideoTab().getBtnVider().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("log>>vider>>Tab video debut");
				int i = 0;
				while (principale.getVideoTab().getTable_2().getValueAt(i, 0) != null
						&& !principale.getVideoTab().getTable_2().getValueAt(i, 0).equals("")) {
					if (hashmapVideo.get(i) != null && !hashmapVideo.get(i).equals("")) {
						File file = new File(hashmapVideo.get(i));
						file.delete();
					}
					if (hashmapVideoCapture.get(i) != null && !hashmapVideoCapture.get(i).equals("")) {
						File file = new File(hashmapVideoCapture.get(i));
						file.delete();
					}
					if (hashmapVideoCaptureImg.get(i) != null && !hashmapVideoCaptureImg.get(i).equals("")) {
						File file = new File(hashmapVideoCaptureImg.get(i));
						file.delete();
					}
					if (hashmapVideoCapturePlt.get(i) != null && !hashmapVideoCapturePlt.get(i).equals("")) {
						File file = new File(hashmapVideoCapturePlt.get(i));
						file.delete();
					}

					principale.getVideoTab().getTable_2().setValueAt("", i, 0);
					principale.getVideoTab().getTable_2().setValueAt("", i, 1);
					principale.getVideoTab().getTable_2().setValueAt("", i, 2);
					principale.getVideoTab().getTable_2().setValueAt(null, i, 3);
					principale.getVideoTab().getTable_2().setValueAt("", i, 4);
					i++;
				}
				File file = new File("includes/tryWithJava/inputs");
				File[] files = file.listFiles();
				for (File f : files) {
					f.delete();
				}
				hashmapVideo.clear();
				hashmapVideoCapture.clear();
				hashmapVideoCaptureImg.clear();
				hashmapVideoCapturePlt.clear();
				Main.nbrVideo = 0;
				System.out.println("log>>vider>>Tab video fin");

			}
		});
		// action bouton ajouter settings
		principale.getSettingsTab().getBtnAjouter().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>ajouter>> tab settings debut");
				AddVehiculeFrame ajouter = new AddVehiculeFrame(principale.getSettingsTab().getBtnRaffraichir());

				System.out.println("log>>ajouter>>Tab settings fin");

			}
		});
		// label nombre
		principale.getSettingsTab().getLblX().setText(String.valueOf(sx.getAll().size()));
		// remplissage table settings
		for (int i = 0; i < sx.getAll().size(); i++) {
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getNumPlate(), i, 0);
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getMarque(), i, 1);
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getType(), i, 2);
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getCouleur(), i, 3);
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getNomProprietaire(), i, 4);
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getCinProprietaire(), i, 5);
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getDateAjout(), i, 6);
			principale.getSettingsTab().getTable().setValueAt(sx.getAll().get(i).getNbrPassages(), i, 7);
		}
		// action bouton raffraichir settings
		principale.getSettingsTab().getBtnRaffraichir().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>rafraichir>>Tab settings debut");
				principale.getSettingsTab().getTextField().setText("");
				SettingsXml sxa = new SettingsXml();
				// label nombre
				principale.getSettingsTab().getLblX().setText(String.valueOf(sxa.getAll().size()));
				// remplissage table settings
				int i;
				for (i = 0; i < sxa.getAll().size(); i++) {
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getNumPlate(), i, 0);
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getMarque(), i, 1);
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getType(), i, 2);
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getCouleur(), i, 3);
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getNomProprietaire(), i, 4);
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getCinProprietaire(), i, 5);
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getDateAjout(), i, 6);
					principale.getSettingsTab().getTable().setValueAt(sxa.getAll().get(i).getNbrPassages(), i, 7);
				}
				for (; i <= 99; i++) {
					principale.getSettingsTab().getTable().setValueAt("", i, 0);
					principale.getSettingsTab().getTable().setValueAt("", i, 1);
					principale.getSettingsTab().getTable().setValueAt("", i, 2);
					principale.getSettingsTab().getTable().setValueAt("", i, 3);
					principale.getSettingsTab().getTable().setValueAt("", i, 4);
					principale.getSettingsTab().getTable().setValueAt("", i, 5);
					principale.getSettingsTab().getTable().setValueAt("", i, 6);
					principale.getSettingsTab().getTable().setValueAt("", i, 7);
				}
				System.out.println("log>>rafraichir>>Tab settings fin");

			}
		});
		// action bouton supprimer settings
		principale.getSettingsTab().getBtnSupprimer().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>suprimer>>Tab settings debut");
				SettingsXml sxa = new SettingsXml();
				if (principale.getSettingsTab().getTable().getSelectedRow() == -1) {
					System.out.println("il faut selectionner la ligne a suprimer");
				} else {
					boolean xx = sxa.delete((String) principale.getSettingsTab().getTable()
							.getValueAt(principale.getSettingsTab().getTable().getSelectedRow(), 0));
					System.out.println(xx);
					principale.getSettingsTab().getBtnRaffraichir().doClick();
				}
				System.out.println("log>>suprimer>>Tab settings fin");

			}
		});
		// action bouton modifier settings
		principale.getSettingsTab().getBtnModifier().addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>modifier>>Tab settings debut");
				int selectedRow = principale.getSettingsTab().getTable().getSelectedRow();
				if (selectedRow == -1) {
					System.out.println("il faut selectionner la ligne a modifier");
				} else {
					if (principale.getSettingsTab().getTable().getValueAt(selectedRow, 0) == null
							|| principale.getSettingsTab().getTable().getValueAt(selectedRow, 0).equals("")) {
						System.out.println("ligne a modifier vide");
					} else {
						SettingsXml sxa = new SettingsXml();
						VehiculeSettingsPojo v = sxa.getByNumPlate(
								(String) principale.getSettingsTab().getTable().getValueAt(selectedRow, 0));
						System.out.println(v.getNumPlate());
						EditVehiculeFrame evf = new EditVehiculeFrame(v,
								principale.getSettingsTab().getBtnRaffraichir());

					}
				}
				System.out.println("log>>modifier>>Tab settings fin");

			}
		});
		// Action bouton rechercehr settings
		principale.getSettingsTab().getBtnRechercher().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("log>>rechercher>>Tab settings debut");
				SettingsXml sxa = new SettingsXml();
				for (int i = 0; i < sxa.getAll().size(); i++) {
					principale.getSettingsTab().getTable().setValueAt("", i, 0);
					principale.getSettingsTab().getTable().setValueAt("", i, 1);
					principale.getSettingsTab().getTable().setValueAt("", i, 2);
					principale.getSettingsTab().getTable().setValueAt("", i, 3);
					principale.getSettingsTab().getTable().setValueAt("", i, 4);
					principale.getSettingsTab().getTable().setValueAt("", i, 5);
					principale.getSettingsTab().getTable().setValueAt("", i, 6);
					principale.getSettingsTab().getTable().setValueAt("", i, 7);
				}
				int x = 0;
				for (VehiculeSettingsPojo vsp : sxa.getAll()) {
					if (vsp.getNumPlate().contains(principale.getSettingsTab().getTextField().getText())) {
						principale.getSettingsTab().getTable().setValueAt(vsp.getNumPlate(), x, 0);
						principale.getSettingsTab().getTable().setValueAt(vsp.getMarque(), x, 1);
						principale.getSettingsTab().getTable().setValueAt(vsp.getType(), x, 2);
						principale.getSettingsTab().getTable().setValueAt(vsp.getCouleur(), x, 3);
						principale.getSettingsTab().getTable().setValueAt(vsp.getNomProprietaire(), x, 4);
						principale.getSettingsTab().getTable().setValueAt(vsp.getCinProprietaire(), x, 5);
						principale.getSettingsTab().getTable().setValueAt(vsp.getDateAjout(), x, 6);
						principale.getSettingsTab().getTable().setValueAt(vsp.getNbrPassages(), x, 7);
						x++;
					}
				}
				principale.getSettingsTab().getLblX().setText(String.valueOf(x));
				System.out.println("log>>rechercher>>Tab settings fin");

			}
		});

	}

	public static boolean extractImage(HashMap<String, String> hashmap, HashMap<String, String> hashmapRecadre,
			JTable table) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		String dateS = dateFormat.format(date);
		JFileChooser jf = new JFileChooser();
		jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jf.showOpenDialog(null);
		if (jf.getSelectedFile() == null)
			return false;
		File res = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS + ".html");
		File folder = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS);
		try {
			res.createNewFile();
			folder.mkdir();
		} catch (IOException e) {
			res.delete();
			folder.delete();
			return false;
		}

		String html = "<html><head><title>Historique " + dateS + "</title></head><body><p>Historique jusqu'au " + dateS
				+ "</p><table border=1><tr><td>Numero</td><td>Image</td><td>Plaque</td><td>Texte</td></tr>";
		int i = 0;
		int x = 1;
		while (table.getValueAt(i, 0) != null && !table.getValueAt(i, 0).equals("")) {

			File img = new File(hashmap.get(table.getValueAt(i, 0)));
			File dst = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS + "/" + table.getValueAt(i, 0));
			try {
				Files.copy(img.toPath(), dst.toPath());
			} catch (IOException e1) {
				res.delete();
				folder.delete();
				return false;
			}
			if (hashmapRecadre.get(table.getValueAt(i, 0)) == null) {
				dst.delete();
				i++;
				continue;
			}
			File imgR = new File(hashmapRecadre.get(table.getValueAt(i, 0)));
			File dstR = new File(jf.getSelectedFile().getAbsolutePath() + "/" + dateS + "/" + imgR.getName());
			try {
				Files.copy(imgR.toPath(), dstR.toPath());
			} catch (IOException e1) {
				res.delete();
				folder.delete();
				return false;
			}
			html = html + "<tr><td>" + x + "</td><td><img src='" + dateS + "/" + table.getValueAt(i, 0)
					+ "' height='400' width='400'></img></td><td><img src='" + dateS + "/" + imgR.getName()
					+ "'></td><td>" + table.getValueAt(i, 1) + "</td></tr>";
			i++;
			x++;
		}
		html = html + "</table></body></html>";
		FileWriter fos;
		try {
			fos = new FileWriter(res);
			fos.write(html);
			fos.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
