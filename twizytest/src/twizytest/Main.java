package twizytest;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class Main {

	static {
		try {
			System.load("C:/Program Files/opencv/build/x64/vc14/bin/opencv_ffmpeg2413_64.dll");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	private static Mat m ;
	private static ArrayList<Mat> imgDetec = new ArrayList<Mat>(); 
	
	public static void main( String[] args ){


	
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

		int width = 1000;
		int height = 600;
		JFrame frame = new JFrame("Détécter les panneaux");
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		frame.getContentPane().setLayout(null);

		Panel panel = new Panel();
		panel.setBounds(200, 130, 607, 344);
		frame.getContentPane().add(panel);

		ImageIcon menu = new ImageIcon("Menu.png");
		JLabel jlabel = new JLabel(menu);
		//jlabel.setBounds(200, 130, 607, 344);
		panel.add(jlabel);

		JFileChooser image = new JFileChooser();
		JButton ButtonImage = new JButton("Ajouter une image");


		JTextField textField = new JTextField();
		textField.setBounds(60, 30, 200, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);


		ButtonImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retval = image.showOpenDialog(ButtonImage);
				if(retval==JFileChooser.APPROVE_OPTION) {
					File fimage=image.getSelectedFile();
					textField.setText(fimage.getName());
				}
			}
		});
		ButtonImage.setBounds(60, 60, 200, 22);
		frame.getContentPane().add(ButtonImage);

		JButton ButtonAfficher = new JButton("Afficher l'image");
		ButtonAfficher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m = Image.LectureImage(textField.getText());
				Image.ImShow(textField.getText(), m);
				textField.getText().endsWith("");
			}
		});
		ButtonAfficher.setBounds(340, 60, 200, 22);
		frame.getContentPane().add(ButtonAfficher);


		JButton ButtonDetecter = new JButton("Détecter les panneaux");
		ButtonDetecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgDetec = Image.DetecterPanneau(m);
				if (imgDetec.size()>0) {
					for (int i = 0; i<imgDetec.size(); i++) {
						Image.matching(imgDetec.get(i));
					}
				}


			}
		});
		ButtonDetecter.setBounds(600, 60, 200, 22);
		frame.getContentPane().add(ButtonDetecter);



		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);



		/*
		 //Create new MAT object
	    Mat frame = new Mat();

	    //Create new VideoCapture object
	    VideoCapture camera = new VideoCapture("video1.avi");
	    //Create new JFrame object	        
	    System.out.println(camera.isOpened());

	    JFrame jframe = new JFrame("Video Title");

	    //Inform jframe what to do in the event that you close the program
	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    //Create a new JLabel object vidpanel
	    JLabel vidPanel = new JLabel();

	    //assign vidPanel to jframe
	    jframe.setContentPane(vidPanel);

	    //set frame size
	    jframe.setSize(1000, 500);

	    //make jframe visible
	    jframe.setVisible(true);

	    while (true) {
	        //If next video frame is available
	        if (camera.read(frame)) {
	            //Create new image icon object and convert Mat to Buffered Image

	            ImageIcon image = new ImageIcon(Image.Mat2BufferedImage(frame));
	            //Update the image in the vidPanel
	            vidPanel.setIcon(image);
	            //Update the vidPanel in the JFrame
	            vidPanel.repaint();

	        }
	    }

		 */


	}
}
