package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import controller.Controller;

public class SystemView extends JFrame implements ActionListener{
	JPanel mainPanel, chosenImagePanel, resultsPanel;
	JRadioButton rb_CHmethod, rb_CHwPSmethod, rb_HRwCCmethod, rb_CHwCRmethod;
	JLabel lbl_methods, lbl_CHmethod, lbl_CHwPSmethod, lbl_HRwCCmethod, lbl_CHwCRmethod, lbl_chosenImage;
	JButton btn_retrieveImages, btn_chooseImage;
	JTextField tf_imagePath;
	JFileChooser fc_chooser;
	Controller controller;
	File file_chosenImage;
	JScrollPane resultsPanelScroller;
	
	public SystemView(){
		
		fc_chooser = new JFileChooser("C:\\Users\\xtiangabe\\Desktop");
		
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 600, 600);
		
		resultsPanel = new JPanel();
		resultsPanel.setBackground(Color.RED);
		resultsPanelScroller = new JScrollPane(resultsPanel);
		resultsPanelScroller.setBounds(5, 250, 550, 300);
		
		rb_CHmethod = new JRadioButton();
		rb_CHwPSmethod = new JRadioButton();
		rb_HRwCCmethod = new JRadioButton();
		rb_CHwCRmethod = new JRadioButton();
		
		
		ButtonGroup radio_btn_group = new ButtonGroup(); //forces only one radio btn can be selected at a time
		radio_btn_group.add(rb_CHmethod);
		radio_btn_group.add(rb_CHwPSmethod);
		radio_btn_group.add(rb_HRwCCmethod);
		radio_btn_group.add(rb_CHwCRmethod);
		
		rb_CHmethod.setBounds(5, 25, 20, 20);
		rb_CHwPSmethod.setBounds(5, 45, 20, 20);
		rb_HRwCCmethod.setBounds(5, 65, 20, 20);
		rb_CHwCRmethod.setBounds(5, 85, 20, 20);
		
		mainPanel.add(rb_CHmethod);
		mainPanel.add(rb_CHwPSmethod);
		mainPanel.add(rb_HRwCCmethod);
		mainPanel.add(rb_CHwCRmethod);
		
		lbl_methods = new JLabel("Methods:");
		lbl_CHmethod = new JLabel("Color Histogram");
		lbl_CHwPSmethod = new JLabel("CH with Perceptual Similarity incorporated");
		lbl_HRwCCmethod = new JLabel("Histogram Refinement with Color Coherence");
		lbl_CHwCRmethod = new JLabel("CH with Centering Refinement");
		lbl_chosenImage = new JLabel("Image:");
		
		tf_imagePath = new JTextField(20);
		tf_imagePath.setBounds(5, 135, 200, 25);
		
		
		lbl_chosenImage.setBounds(5,115, 80, 20);
		lbl_methods.setBounds(5, 5, 80, 20);
		lbl_CHmethod.setBounds(25, 25, 250, 20);
		lbl_CHwPSmethod.setBounds(25, 45, 250, 20);
		lbl_HRwCCmethod.setBounds(25, 65, 270, 20);
		lbl_CHwCRmethod.setBounds(25, 85, 250, 20);
		
		chosenImagePanel = new JPanel();
		chosenImagePanel.setBounds(360, 20, 200,200);
		
		btn_chooseImage = new JButton("Choose Image");
		btn_chooseImage.setBounds(210, 135, 120, 25);
		btn_chooseImage.addActionListener(this);
		btn_retrieveImages = new JButton("Retrieve Images");
		btn_retrieveImages.setBounds(5, 165, 250, 25);
		
		mainPanel.add(resultsPanelScroller);
		mainPanel.add(chosenImagePanel);
		mainPanel.add(btn_chooseImage);
		mainPanel.add(tf_imagePath);
		mainPanel.add(lbl_chosenImage);
		mainPanel.add(btn_retrieveImages);
		mainPanel.add(lbl_methods);
		mainPanel.add(lbl_CHmethod);
		mainPanel.add(lbl_CHwPSmethod);
		mainPanel.add(lbl_HRwCCmethod);
		mainPanel.add(lbl_CHwCRmethod);
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		chosenImagePanel.setBorder(raisedetched);
		chosenImagePanel.setLayout(null);
		tf_imagePath.setEnabled(false);
		
		mainPanel.setLayout(null);
		this.add(mainPanel);
		
		this.setLayout(null);
		this.setTitle("Image Retrieval");
		this.setSize(600,600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		SystemView sv = new SystemView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_chooseImage){
	        fc_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        int option = fc_chooser.showOpenDialog(null);
	        if (option == JFileChooser.APPROVE_OPTION) {
	        	file_chosenImage = fc_chooser.getSelectedFile();
	        	tf_imagePath.setText(fc_chooser.getSelectedFile().getAbsolutePath());
	        	BufferedImage bi = null;
	        	Image rescaled = null;
	        	try {
	        		FileInputStream in = new FileInputStream(file_chosenImage);

	        		// decodes the JPEG data stream into a BufferedImage
	        		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
	        		bi = decoder.decodeAsBufferedImage();
	        		rescaled = bi.getScaledInstance(195, 196, Image.SCALE_DEFAULT);
	        	} catch (Exception ex) {
	        		ex.printStackTrace();
	        	}
	        	JLabel picLabel = new JLabel(new ImageIcon(rescaled));
	    		picLabel.setBounds(3, 2, 195,196);
	    		chosenImagePanel.add(picLabel);
	    		this.repaint();
	        }
		}else if(e.getSource() == btn_retrieveImages){
			
		}
	}
//	try {
//	File file = new File(outputFileName);
//	FileInputStream in = new FileInputStream(file);
//
//	// decodes the JPEG data stream into a BufferedImage
//	JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
//	bi = decoder.decodeAsBufferedImage();
//} catch (Exception ex) {
//	ex.printStackTrace();
//}
	
}
