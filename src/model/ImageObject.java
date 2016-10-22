package model;

/*
 * @(#)Image.java 1.0 03/07/08
 *
 * displays a JPEG image on the panel and  
 * gets the RGB value per pixel 
 */

import java.awt.*;
import java.awt.event.*;
import com.sun.image.codec.jpeg.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.*;
import javax.swing.*;
import java.awt.image.ColorModel;

class ImageObject extends JFrame {

	String path;
	String fileName;
	JTextField jTextArea1 = new JTextField();
	JTextField jTextArea2 = new JTextField();
	JPanel mainPanel;
	int[] histogram;
	BufferedImage imageObject;

	public void showJPEG() {
		BufferedImage bi = null;
		String outputFileName = path + File.separatorChar + fileName;
		System.out.println("OUTPUT: " + outputFileName);
		try {
			File file = new File(outputFileName);
			FileInputStream in = new FileInputStream(file);

			// decodes the JPEG data stream into a BufferedImage
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			bi = decoder.decodeAsBufferedImage();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (bi == null) {
			System.out.println("NULL");
			return;
		}

		JLabel picLabel = new JLabel(new ImageIcon(bi));
		picLabel.setBounds(0, 0, 500, 500);
		mainPanel.add(picLabel);
	}

	public ImageObject(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
		
		String outputFileName = path + File.separatorChar + fileName;
		System.out.println("OUTPUT: " + outputFileName);
		try {
			File file = new File(outputFileName);
			FileInputStream in = new FileInputStream(file);

			// decodes the JPEG data stream into a BufferedImage
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			imageObject = decoder.decodeAsBufferedImage();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (imageObject == null) {
			System.out.println("NULL");
			return;
		}
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		mainPanel.setBounds(0, 0, 500, 700);
		jTextArea1.setBounds(20, 570, 250, 50);
		jTextArea2.setBounds(20, 510, 250, 50);

		this.add(mainPanel);
		mainPanel.add(this.jTextArea1);
		mainPanel.add(this.jTextArea2);

		this.setSize(500, 700);
		this.setTitle("Image");
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void initializeHistogram() {
		histogram = new int[159];
		// gets the RGB and Luv value at x, y
		BufferedImage bi1 = null;
		int RGB1;
		int totalPixels;

		try {
			File file = new File(path, fileName);
			FileInputStream in = new FileInputStream(file);
			// decodes the JPEG data stream into a BufferedImage
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			bi1 = decoder.decodeAsBufferedImage();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (bi1 == null) {
			System.out.println("Null File");
			return;
		}
		int accu =0;
		for(int x=0; x<bi1.getWidth(); x++){
			for(int y=0; y<bi1.getHeight(); y++){
				ColorModel CM;
				CM = bi1.getColorModel();
				RGB1 = bi1.getRGB(x, y); // get the RGB value at x,y of the image

				double R, G, B;
				R = CM.getRed(RGB1); // get the 8-bit values of RGB (0-255)
				G = CM.getGreen(RGB1);
				B = CM.getBlue(RGB1);
				
				cieConvert colorCIE = new cieConvert();
				colorCIE.setValues(R / 255.0, G / 255.0, B / 255.0);
				histogram[colorCIE.IndexOf()] += 1;
				accu++;
			}
		}
		for(int i=0; i<159; i++){
			System.out.println("histogram["+i+"] = "+histogram[i]);
		}
		System.out.println("total: "+accu);
	}

	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

	      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	      final int width = image.getWidth();
	      final int height = image.getHeight();
	      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

	      int[][] result = new int[height][width];
	      if (hasAlphaChannel) {
	         final int pixelLength = 4;
	         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
	            int argb = 0;
	            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
	            argb += ((int) pixels[pixel + 1] & 0xff); // blue
	            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
	            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
	            result[row][col] = argb;
	            col++;
	            if (col == width) {
	               col = 0;
	               row++;
	            }
	         }
	      } else {
	         final int pixelLength = 3;
	         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
	            int argb = 0;
	            argb += -16777216; // 255 alpha
	            argb += ((int) pixels[pixel] & 0xff); // blue
	            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
	            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
	            result[row][col] = argb;
	            col++;
	            if (col == width) {
	               col = 0;
	               row++;
	            }
	         }
	      }

	      return result;
	   }
	
	public void getRGB(int x, int y) {
		// gets the RGB and Luv value at x, y
		BufferedImage bi1 = null;
		int RGB1;
		int i, j;
		int totalPixels;

		try {
			File file = new File(path, fileName);
			FileInputStream in = new FileInputStream(file);

			// decodes the JPEG data stream into a BufferedImage
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			bi1 = decoder.decodeAsBufferedImage();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (bi1 == null) {
			System.out.println("Null File");
			return;
		}

		totalPixels = bi1.getHeight() * bi1.getWidth();
		ColorModel CM;
		CM = bi1.getColorModel();
		RGB1 = bi1.getRGB(x, y); // get the RGB value at x,y of the image

		double R, G, B;
		R = CM.getRed(RGB1); // get the 8-bit values of RGB (0-255)
		G = CM.getGreen(RGB1);
		B = CM.getBlue(RGB1);

		cieConvert ColorCIE = new cieConvert();
		ColorCIE.setValues(R / 255.0, G / 255.0, B / 255.0);

		jTextArea2.setText("RGB:(" + Double.toString(R) + "," + Double.toString(G) + "," + Double.toString(B) + ") -> "
				+ Integer.toString(ColorCIE.IndexOf()));
		jTextArea1.setText("  = LUV:(\n" + Double.toString(ColorCIE.L) + "," + Double.toString(ColorCIE.u) + ","
				+ Double.toString(ColorCIE.v) + ")");
		this.repaint();
	}

	public static void main(String args[]) {
		
		System.out.println("Starting Image...");
		ImageObject mainFrame = new ImageObject("C:\\Users\\xtiangabe\\Desktop", "playlogo2.jpg");
		mainFrame.initializeHistogram();
		mainFrame.showJPEG();
		mainFrame.getRGB(50, 30);
	}
}
