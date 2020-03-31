package twizytest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class utils {
	public static Mat LectureImage(String fichier) {
		File f = new File (fichier);
		Mat m = Highgui.imread(f.getAbsolutePath());
		return m;

	}

	public static void ImShow (String title, Mat img) {
		MatOfByte matOfByte = new MatOfByte();
		Highgui.imencode(".png", img, matOfByte);
		byte [] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			JFrame frame = new JFrame();
			frame.setTitle(title);
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void exo01() {
		Mat mat = LectureImage("ref30.jpg");
		Vector<Mat> channels = new Vector <Mat>();
		Core.split(mat, channels);
		ImShow("", mat);
		Vector<Mat> chans = new Vector <Mat>();
		Mat dst = Mat.zeros(mat.size(), mat.type());
		Mat empty = Mat.zeros(mat.size(), CvType.CV_8UC1);
		for (int i=0; i<channels.size(); i++) {
			//ImShow(Integer.toString(i), channels.get(i));
			chans.removeAllElements();
			for (int j=0; j<channels.size(); j++) {
				if (j != i) chans.add(empty);
				else chans.add(channels.get(i));
			}

			Core.merge(chans, dst);
			ImShow(Integer.toString(i), dst);
			//System.out.println( "mat = " + mat.dump() );
		}

	}

	public static void exo02() {
		Mat m = LectureImage("ref30.jpg");
		Mat output = Mat.zeros(m.size(), m.type());
		Imgproc.cvtColor(m, output, Imgproc.COLOR_BGR2HSV);
		ImShow("HSV",output);
		Vector <Mat> channels = new Vector<Mat>();
		Core.split(output, channels);
		double hsv_values[][] = {{1, 255, 255},{179, 1, 255},{179,0,1}};
		for (int i=0; i<3; i++) {
			ImShow(Integer.toString(i), channels.get(i));
			Mat chans [] = new Mat[3];
			for (int j=0; j<3; j++) {
				Mat empty = Mat.zeros(m.size(), CvType.CV_8UC1);
				Mat comp = Mat.zeros(m.size(), CvType.CV_8UC1);
				Scalar v =new Scalar(hsv_values[i][j]);
				Core.multiply(empty, v, comp);
				chans[j] = comp;
			}
			chans[i] = channels.get(i);
			Mat dst = Mat.zeros(output.size(), output.type());
			Mat res = Mat.ones(dst.size(), dst.type());
			Core.merge(Arrays.asList(chans), dst);
			Imgproc.cvtColor(dst, res, Imgproc.COLOR_HSV2BGR);
			ImShow(Integer.toString(i), res);
		}

	}
	
	public static void exo03() { //seuillage
		Mat m = LectureImage("ref30.jpg");
		ImShow("", m);
		Mat hsv_image = Mat.zeros(m.size(), m.type());
		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
		Mat threshold = new Mat();
		Mat threshold_1 = new Mat();
		Mat threshold_2 = new Mat();
		Mat threshold_3 = new Mat();
		Mat threshold_4 = new Mat();
		Mat threshold_5 = new Mat();
		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(20,255,255), threshold_1); 
		Core.inRange(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255), threshold_2);
		Core.inRange(hsv_image, new Scalar(107,100,100), new Scalar(118,255,255), threshold_3);
		Core.inRange(hsv_image, new Scalar(30,100,100), new Scalar(50,255,255), threshold_4);
		Core.inRange(hsv_image, new Scalar(0,0,0), new Scalar(0,100,100), threshold_5);
		
		Core.bitwise_or(threshold_1, threshold_2, threshold);
		Core.bitwise_or(threshold, threshold_3, threshold);
		Core.bitwise_or(threshold, threshold_4, threshold);
		Core.bitwise_or(threshold, threshold_5, threshold);
		Imgproc.GaussianBlur(threshold, threshold, new Size(9,9), 2,2);
		
		ImShow("2", threshold);
	}
	
	public static void exo04() { // detection des contours
		Mat m = LectureImage("ref30.jpg");
		ImShow("1", m);
		Mat hsv_image = Mat.zeros(m.size(), m.type());
		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
		int thresh = 100;
		Mat canny_output = new Mat();


		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		MatOfInt4 hierarchy = new MatOfInt4();
		Imgproc.Canny(m, canny_output, thresh, thresh*2);
		Imgproc.findContours(canny_output, contours, hierarchy,Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat drawing = Mat.zeros(canny_output.size(), CvType.CV_8UC3);
		Random rand = new Random();
		for (int i =0; i<contours.size(); i++) {
			Scalar color  = new Scalar(rand.nextInt(255 - 0 + 1),rand.nextInt(255 - 0 + 1),rand.nextInt(255 - 0 + 1));
			Imgproc.drawContours(drawing, contours, i, color, 1,8,hierarchy, 0, new Point());
		}
	
		ImShow("3", drawing);
	
	}
	
	public static void exo05() {
		Mat m = LectureImage("ref30.jpg");
		ImShow("", m);
		Mat hsv_image = Mat.zeros(m.size(), m.type());
		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
		Mat threshold = new Mat();
		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255), threshold);
		
		Imgproc.GaussianBlur(threshold, threshold, new Size(9,9), 2,2);
		ImShow("", threshold);
		
		int thresh = 100;
		Mat canny_output = new Mat();


		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		MatOfInt4 hierarchy = new MatOfInt4();
		Imgproc.Canny(threshold, canny_output, thresh, thresh*2);
		Imgproc.findContours(canny_output, contours, hierarchy,Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat drawing = Mat.zeros(canny_output.size(), CvType.CV_8UC3);
		Random rand = new Random();
		for (int i =0; i<contours.size(); i++) {
			Scalar color  = new Scalar(rand.nextInt(255 - 0 + 1),rand.nextInt(255 - 0 + 1),rand.nextInt(255 - 0 + 1));
			Imgproc.drawContours(drawing, contours, i, color, 1,8,hierarchy, 0, new Point());
		}
	
		ImShow("3", drawing);
	}

	public static void exo06() {
		Mat m = LectureImage("circles_rectangles.jpg");
		ImShow("1", m);
		Mat hsv_image = Mat.zeros(m.size(), m.type());
		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
		ImShow("2", hsv_image);
	}
	
}
