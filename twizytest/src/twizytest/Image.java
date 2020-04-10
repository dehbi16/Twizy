package twizytest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Image {
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

	

	public static ArrayList<Mat> DetecterPanneau (Mat m) {
		Mat hsv_image = Mat.zeros(m.size(), m.type());

		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);

		Mat threshold = new Mat();
		Mat threshold_1 = new Mat();
		Mat threshold_2 = new Mat();

		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(20,255,255), threshold_1); //rouge
		Core.inRange(hsv_image, new Scalar(160,100,100), new Scalar(180,255,255), threshold_2); //rouge

		Core.bitwise_or(threshold_1, threshold_2, threshold);

		Imgproc.GaussianBlur(threshold, threshold, new Size(9,9), 2,2);
		
		
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
		


		MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
		float[] radius = new float[1];
		Point center = new Point();
		ArrayList<Mat> imgDetec = new ArrayList<Mat>() ;


		for (int c=0; c<contours.size();c++) {		
			MatOfPoint contour = contours.get(c);
			Rect rect = Imgproc.boundingRect(contour);
			double contourArea = Imgproc.contourArea(contour);
			matOfPoint2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);

			if((contourArea/(Math.PI*radius[0]*radius[0])) >= 0.8) {
				//Core.circle(m, center, (int) radius[0], new Scalar(0, 255, 0), 2);
				//Core.rectangle(m, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height), new Scalar (0, 255, 0), 2);
				Mat tmp = m.submat(rect.y,rect.y+rect.height,rect.x,rect.x+rect.width);
				Mat sign = Mat.zeros(tmp.size(),tmp.type());
				tmp.copyTo(sign);
				imgDetec.add(sign);
			}
		}
		return imgDetec;
	}
	
	
	public static boolean estnoir(double[] ds) {
		if (ds[0]<100 && ds[1]<100 && ds[1]<100 ) return true;
		else return false;
		
	}
}
