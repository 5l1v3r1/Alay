package utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import main.Main;
import dao.SettingsXml;

public class ThreadVideo implements Runnable {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private JTable tableSource;
	private JTable tableResultat;
	private HashMap<Integer, String> hashmapVideoCapture;
	private HashMap<Integer, String> hashmapVideo;
	private String videoPath;
	private JLabel label;
	private HashMap<Integer, String> hashmapVideoCaptureImg;
	private HashMap<Integer, String> hashmapVideoCapturePlt;
	public static int nbr = 0;

	public JTable getTableSource() {
		return tableSource;
	}

	public void setTableSource(JTable tableSource) {
		this.tableSource = tableSource;
	}

	public JTable getTableResultat() {
		return tableResultat;
	}

	public void setTableResultat(JTable tableResultat) {
		this.tableResultat = tableResultat;
	}

	public HashMap<Integer, String> getHashmapVideoCapture() {
		return hashmapVideoCapture;
	}

	public void setHashmapVideoCapture(HashMap<Integer, String> hashmapVideoCapture) {
		this.hashmapVideoCapture = hashmapVideoCapture;
	}

	public HashMap<Integer, String> getHashmapVideo() {
		return hashmapVideo;
	}

	public void setHashmapVideo(HashMap<Integer, String> hashmapVideo) {
		this.hashmapVideo = hashmapVideo;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public ThreadVideo(JLabel label, JTable src, JTable res, HashMap<Integer, String> hashmapVideoCapture,
			HashMap<Integer, String> hashmapVideo, int nbr, HashMap<Integer, String> hashmapVideoCaptureImg,
			HashMap<Integer, String> hashmapVideoCapturePlt) {
		this.tableResultat = res;
		this.tableSource = src;
		this.hashmapVideo = hashmapVideo;
		this.hashmapVideoCapture = hashmapVideoCapture;
		this.label = label;
		this.hashmapVideoCaptureImg = hashmapVideoCaptureImg;
		this.hashmapVideoCapturePlt = hashmapVideoCapturePlt;

	}

	@Override
	public void run() {
		if (tableSource.getValueAt(0, 0) != null && !tableSource.getValueAt(0, 0).equals("")) {
			tableSource.setValueAt("En cours", 0, 2);
			VideoCapture cap = new VideoCapture((String) tableSource.getValueAt(0, 1));
			tableSource.setValueAt("En cours", 0, 2);
			Mat image = new Mat();
			Mat aux;
			SettingsXml sx = new SettingsXml();
			while (cap.read(image)) {
				aux = image.clone();
				label.setIcon(Main.matToLabel(image.clone()));
				Mat imageHSV = new Mat(image.size(), CvType.CV_8UC4);
				Mat imageBlurr = new Mat(image.size(), CvType.CV_8UC4);
				Mat imageA = new Mat(image.size(), CvType.CV_32F);
				Imgproc.cvtColor(image, imageHSV, Imgproc.COLOR_BGR2GRAY);
				Imgproc.GaussianBlur(imageHSV, imageBlurr, new Size(5, 5), 2);
				Imgproc.adaptiveThreshold(imageBlurr, imageA, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY, 7, 5);
				List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
				Imgproc.findContours(imageA, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
				for (int i = 0; i < contours.size(); i++) {
					if (Imgproc.contourArea(contours.get(i)) > 50) {
						Rect rect = Imgproc.boundingRect(contours.get(i));
						if ((float) (rect.width / rect.height) >= 3 && (float) (rect.width / rect.height) <= 5
								&& rect.height > image.rows() / 50 && rect.height < image.rows() / 4

						) {
							Imgproc.rectangle(image, new Point(rect.x, rect.y),
									new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255));
							@SuppressWarnings("unused")
							double m = (rect.y + rect.y + rect.height) / 2;

							if (rect.contains(new Point(rect.x, image.rows() / 1.5))) {
								System.out.println("voiture");
								String res = "includes/tryWithJava/inputs/video"
										+ (int) (Math.random() * 10000) + ".jpg";
								Mat a = new Mat();
								Imgproc.resize(aux.submat(rect), a, new Size(500, 109));
								Imgcodecs.imwrite(res, a);

								PythonResult pr = Main.extract(res);
								if (pr != null) { // matricule trouvé traitement
													// filtre doublons a
													// appliquer plus tard
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
									String d = dateFormat.format(date);
									label.setIcon(Main.matToLabel(image.clone()));
									String res1 = "includes/tryWithJava/inputs/video"
											+ (int) (Math.random() * 10000) + ".jpg";
									Imgcodecs.imwrite(res1, aux);
									hashmapVideo.put(nbr, res);/////////////////////////////////////////////////////
									System.out.println("nbr=" + nbr);
									System.out.println("res=" + res);
									hashmapVideoCapture.put(nbr, res1);
									hashmapVideoCaptureImg.put(nbr, pr.getImgSuccesful());
									hashmapVideoCapturePlt.put(nbr, pr.getImgPlate());
									sx.incNbrPassage(pr.getNumPlate());
									tableSource.setValueAt(
											String.valueOf(Integer.parseInt((String) tableSource.getValueAt(0, 3)) + 1),
											0, 3);
									tableResultat.setValueAt(d, nbr, 0);
									tableResultat.setValueAt(tableSource.getValueAt(0, 0), nbr, 1);
									tableResultat.setValueAt(pr.getNumPlate(), nbr, 2);
									tableResultat.setValueAt(Main.ResizeToFillInCell(image.submat(rect)), nbr, 3);
									if (sx.getByNumPlate(pr.getNumPlate()).getNumPlate().equals("null")) {
										tableResultat.setValueAt("non", nbr, 4);
									} else {
										tableResultat.setValueAt("oui", nbr, 4);
									}
									nbr++;
									System.out.println(pr.getNumPlate());

								} else {
									File file = new File(res);
									file.delete();
								}
								for (int ii = 0; ii < 15; ii++) { // bypass
																	// doubles
									cap.read(image);
									label.setIcon(Main.matToLabel(image));
								}

							}
						}
					}
				}

			}
			tableSource.setValueAt("Terminé", 0, 2);
		}
	}
}
