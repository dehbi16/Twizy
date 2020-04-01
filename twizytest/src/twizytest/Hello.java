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
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Hello {
	public static void main( String[] args )
	   {
	     
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    //Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
		/*circles_rectangles.jpg  s_p10.jpg
		*/
		
		
		 
		
		Mat m = utils.LectureImage("s_p9.jpg");
		utils.ImShow("", m);
		Mat hsv_image = Mat.zeros(m.size(), m.type());
		
		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
		
		Mat threshold = new Mat();
		Mat threshold_1 = new Mat();
		Mat threshold_2 = new Mat();
		
		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(20,255,255), threshold_1); //rouge
		Core.inRange(hsv_image, new Scalar(160,100,100), new Scalar(180,255,255), threshold_2); //rouge
		
		
		Core.bitwise_or(threshold_1, threshold_2, threshold);
		
		Imgproc.GaussianBlur(threshold, threshold, new Size(9,9), 2,2);
		utils.ImShow("2", threshold);
			
		
		
		
		
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
	
		utils.ImShow("3", drawing);
		
		
		
		
		
		
		
		
		MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
		float[] radius = new float[1];
		Point center = new Point();
		for (int c=0; c<contours.size();c++) {
			MatOfPoint contour = contours.get(c);
			double contourArea = Imgproc.contourArea(contour);
			matOfPoint2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);
			
			if((contourArea/(Math.PI*radius[0]*radius[0])) >= 0.8) {
				System.out.println(contourArea+" ");
				Core.circle(m, center, (int) radius[0], new Scalar(0, 255, 0), 2);
			}
		}
		utils.ImShow("Détection des cercles rouges", m);
	
		 
	   }
	

}
