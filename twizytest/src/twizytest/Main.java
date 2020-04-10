package twizytest;
import java.awt.image.BufferedImage;
import java.beans.FeatureDescriptor;
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
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Main {
	public static void main( String[] args )
	{

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

		List <Mat> imgref = new ArrayList<Mat>();
		imgref.add(Image.LectureImage("ref110.jpg"));
		imgref.add(Image.LectureImage("ref90.jpg"));
		imgref.add(Image.LectureImage("ref70.jpg"));
		imgref.add(Image.LectureImage("ref50.jpg"));
		imgref.add(Image.LectureImage("ref30.jpg"));
		imgref.add(Image.LectureImage("refdouble.jpg"));


		Mat m = Image.LectureImage("s_p2.jpg");
		Image.ImShow("", m);
		

		ArrayList<Mat> imgDetec = new ArrayList<Mat>(); 
		imgDetec = Image.DetecterPanneau(m);







		Mat panneauref = imgref.get(3);
		Mat object = imgDetec.get(0);
		Mat sObject = new Mat();
		Imgproc.resize(object, sObject, panneauref.size());

		Mat grayObjet = new Mat(sObject.rows(), sObject.cols(), sObject.type());
		Imgproc.cvtColor(sObject, grayObjet, Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(grayObjet, grayObjet, 0, 255, Core.NORM_MINMAX);


		Mat graySign = new Mat(panneauref.rows(), panneauref.cols(), panneauref.type());
		Imgproc.cvtColor(panneauref, graySign, Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(graySign, graySign, 0, 255, Core.NORM_MINMAX);

		Image.ImShow("", sObject);

		//Image.ImShow("", panneauref);

		int nombreNoirRef = 0;
		int nombreNoir = 0;

		for (int i=0;i<sObject.width();i++) {
			for (int j=0; j<sObject.height(); j++) {
				if ( Image.estnoir(sObject.get(i, j)) ) nombreNoir++;
				if ( Image.estnoir(panneauref.get(i, j)) ) nombreNoirRef++;

			}
		}


		System.out.println("nombreNoirRef = "+nombreNoirRef);
		System.out.println("nombreNoir = "+nombreNoir);

		//System.out.println(sObject.get(22, 22));



		//afficheImage("Panneau extrait de l'image",object);
		/*

		FeatureDetector orbDescriptor = FeatureDetector.create(FeatureDetector.ORB);
		DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

		MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
		orbDescriptor.detect(grayObject, objectKeyPoints);

		MatOfKeyPoint signKeyPoints = new MatOfKeyPoint();
		orbDescriptor.detect(graySign, signKeyPoints);

		Mat objectDescriptor = new Mat(imgDetec.get(0).rows(),imgDetec.get(0).cols(),imgDetec.get(0).type());
		orbExtractor.compute(grayObject, objectKeyPoints, objectDescriptor);

		Mat signDescriptor = new Mat(sroadSign.rows(),sroadSign.cols(),sroadSign.type());
		orbExtractor.compute(graySign, signKeyPoints, signDescriptor);


		// Faire le matching
		MatOfDMatch matchs = new MatOfDMatch();
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		matcher.match(objectDescriptor, signDescriptor, matchs);
		//System.out.println(matchs.dump());
		System.out.println();
		Mat marchedImage = new Mat(sroadSign.rows(), 2*sroadSign.cols(),sroadSign.type());
		Features2d.drawMatches(sObject, objectKeyPoints, sroadSign, signKeyPoints, matchs, marchedImage);
		Image.ImShow("", marchedImage);
		List myList = matchs.toList();
		/*

		for (int i=0;i<myList.size();i++) {
			System.out.println(myList.get(i));
		}





		Mat source=null;
        Mat template=null;
        //String filePath="C:\\Users\\mesutpiskin\\Desktop\\Object Detection\\Template Matching\\Sample Image\\";
        //Load image file
        source=Image.LectureImage("s_p10.jpg");
        template=Image.LectureImage("ref30.jpg");

        Mat outputImage=new Mat();    
        int machMethod=Imgproc.TM_CCOEFF;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);


        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        //Point matchLoc=mmr.maxLoc;

        Point matchLoc;
        if (machMethod == Imgproc.TM_SQDIFF || machMethod == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
            System.out.println(mmr.minLoc);
        } else {
            matchLoc = mmr.maxLoc;
            System.out.println(mmr.maxLoc);
        }
        //Draw rectangle on result image
       Core.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows()), new Scalar(0, 255, 0));
       Image.ImShow("", source);
		 */


	}


}
