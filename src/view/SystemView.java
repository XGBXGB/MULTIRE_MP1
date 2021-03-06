package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import model.ResultImageData;

public class SystemView extends JFrame implements ActionListener
{
	JPanel mainPanel, chosenImagePanel, resultsPanel;
	JRadioButton rb_CHmethod, rb_CHwPSmethod, rb_HRwCCmethod, rb_CHwCRmethod;
	JLabel lbl_methods, lbl_CHmethod, lbl_CHwPSmethod, lbl_HRwCCmethod, lbl_CHwCRmethod, lbl_chosenImage;
	JButton btn_retrieveImages, btn_chooseImage;
	JTextField tf_imagePath;
	JFileChooser fc_chooser;
	Controller controller;
	File file_chosenImage;
	JScrollPane resultsPanelScroller;
	JComboBox selectCenterPercent;

	static String xgbRepo = "C:\\Users\\xtiangabe\\Desktop\\MP1\\images";
	static String winonaRepo = "D:\\College\\Multire\\MP1\\MP1\\images";
	static String kerrbieRepo = "C:\\Users\\Justin\\Documents\\Eclipse\\MULTIRE-MP1\\images";
	static String imagesRepository = xgbRepo;
	double significance = 0.005;
	double[][] similarityMatrix;
	
	public SystemView()
	{
		String[] percentages = {"50", "75"};
		selectCenterPercent = new JComboBox(percentages);
		selectCenterPercent.setBounds(200, 85, 100, 20);
		selectCenterPercent.setEnabled(false);
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		controller = new Controller();
//		fc_chooser = new JFileChooser("D:\\COLLEGE\\MULTIRE\\MP1\\MP1\\images");
		fc_chooser = new JFileChooser("C:\\Users\\xtiangabe\\Desktop");
		
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 600, 600);
		
		resultsPanel = new JPanel();
		resultsPanel.setLayout(null);
		
		resultsPanelScroller = new JScrollPane(resultsPanel);
		resultsPanelScroller.setBounds(5, 250, 570, 300);
		resultsPanelScroller.setBorder(raisedetched);
		
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
		rb_CHmethod.addActionListener(this);
		rb_CHwPSmethod.setBounds(5, 45, 20, 20);
		rb_CHwPSmethod.addActionListener(this);
		rb_HRwCCmethod.setBounds(5, 65, 20, 20);
		rb_HRwCCmethod.addActionListener(this);
		rb_CHwCRmethod.setBounds(5, 85, 20, 20);
		rb_CHwCRmethod.addActionListener(this);
		
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
		btn_retrieveImages.addActionListener(this);
		
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
		mainPanel.add(selectCenterPercent);
		
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
		
		similarityMatrix = controller.getLUVSimilarityMatrix();
	}
	
	public static void main(String args[])
	{
		SystemView sv = new SystemView();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource() == btn_chooseImage)
		{
			fc_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int option = fc_chooser.showOpenDialog(null);
			   
			if (option == JFileChooser.APPROVE_OPTION) 
			{
				if(!tf_imagePath.getText().isEmpty()){
					chosenImagePanel.removeAll();
				}
				
				file_chosenImage = fc_chooser.getSelectedFile();
				tf_imagePath.setText("");
				tf_imagePath.setText(fc_chooser.getSelectedFile().getAbsolutePath());
				BufferedImage bi = null;
				Image rescaled = null;
					
					try 
					{
						FileInputStream in = new FileInputStream(file_chosenImage);
					
						// decodes the JPEG data stream into a BufferedImage
						JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
						bi = decoder.decodeAsBufferedImage();
						rescaled = bi.getScaledInstance(195, 196, Image.SCALE_DEFAULT);
					} 
					catch (Exception ex) 
					{
						ex.printStackTrace();
					}
					
				JLabel picLabel = new JLabel(new ImageIcon(rescaled));
				picLabel.setBounds(3, 2, 195,196);
				chosenImagePanel.add(picLabel);
				this.repaint();
			}
		}
		else if(e.getSource() == btn_retrieveImages && rb_CHmethod.isSelected())
		{
//			ArrayList<ResultImageData> imageResult = controller.compare(0.05, file_chosenImage.getParent(), file_chosenImage.getName(), "D:\\College\\multire\\MP1\\MP1\\images");
			ArrayList<ResultImageData> imageResult = controller.compare(significance, file_chosenImage.getParent(), file_chosenImage.getName(), imagesRepository);
			int y = 5;
			int offset = 0;
			resultsPanel.removeAll();
			for(int i=0; i<1703; i++)
			{
				if(i%2==0 && i!=0)
				{
					y+=200;
					offset = 0;
				}
				
				Image sampleImage = getImageFromPathAndFile(imageResult.get(i).getFileName());
				JLabel samplePic = new JLabel(new ImageIcon(sampleImage));
				samplePic.setBounds(offset*200+5, y, 195,196);
				//System.out.println("value: "+imageResult.get(i).getValue());
				resultsPanel.add(samplePic);
				this.repaint();
				offset++;
			}
			resultsPanel.setPreferredSize(new Dimension(530, y+220));
			resultsPanel.repaint();
			resultsPanelScroller.repaint();
			resultsPanelScroller.setViewport(resultsPanelScroller.getViewport());
		}
		else if( e.getSource() == btn_retrieveImages && rb_CHwPSmethod.isSelected() )
		{
			ArrayList<ResultImageData> imageResult = controller.comparePerceptualSimilarity(significance, file_chosenImage.getParent(), file_chosenImage.getName(), imagesRepository, similarityMatrix);
//			ArrayList<ResultImageData> imageResultx = controller.compare(0.05, file_chosenImage.getParent(), file_chosenImage.getName(), "C:\\Users\\xtiangabe\\Desktop\\MP1\\images");
			int y = 5;
			int offset = 0;
			resultsPanel.removeAll();
			for(int i=0; i<1703; i++)
			{
				if(i%2==0 && i!=0)
				{
					y+=200;
					offset = 0;
				}
				
				Image sampleImage = getImageFromPathAndFile(imageResult.get(i).getFileName());
				JLabel samplePic = new JLabel(new ImageIcon(sampleImage));
				samplePic.setBounds(offset*200+5, y, 195,196);
				//System.out.println("value: "+imageResult.get(i).getValue());
				resultsPanel.add(samplePic);
				this.repaint();
				offset++;
			}
			resultsPanel.setPreferredSize(new Dimension(530, y+220));
			resultsPanel.repaint();
			resultsPanelScroller.repaint();
			resultsPanelScroller.setViewport(resultsPanelScroller.getViewport());
		}
		else if(e.getSource() == btn_retrieveImages && rb_HRwCCmethod.isSelected())
		{
			ArrayList<ResultImageData> imageResult = controller.compareCCV4(file_chosenImage.getParent(), file_chosenImage.getName(), imagesRepository, 6, 159);
			int y = 5;
			int offset = 0;
			resultsPanel.removeAll();
			for(int i=0; i<1703; i++)
			{
				if(i%2==0 && i!=0)
				{
					y+=200;
					offset = 0;
				}
				
				Image sampleImage = getImageFromPathAndFile(imageResult.get(i).getFileName());
				JLabel samplePic = new JLabel(new ImageIcon(sampleImage));
				samplePic.setBounds(offset*200+5, y, 195,196);
				//System.out.println("value: "+imageResult.get(i).getValue());
				resultsPanel.add(samplePic);
				this.repaint();
				offset++;
			}
			resultsPanel.setPreferredSize(new Dimension(530, y+220));
			resultsPanel.repaint();
			resultsPanelScroller.repaint();
			resultsPanelScroller.setViewport(resultsPanelScroller.getViewport());
		}
		else if(e.getSource().getClass() == rb_CHwCRmethod.getClass())
		{
			if(e.getSource() == rb_CHwCRmethod)
			{
				selectCenterPercent.setEnabled(true);
			}else
			{
				selectCenterPercent.setEnabled(false);
			}
		}		
		else if(e.getSource() == btn_retrieveImages && rb_CHwCRmethod.isSelected())
		{
//			ArrayList<ResultImageData> imageResult = controller.compareWithCR(Integer.parseInt((String)selectCenterPercent.getSelectedItem()), 
//																		file_chosenImage.getParent(), file_chosenImage.getName(), 
//																		"D:\\College\\multire\\MP1\\MP1\\images");
			ArrayList<ResultImageData> imageResult = controller.compareWithCR(Integer.parseInt((String)selectCenterPercent.getSelectedItem()), 
					file_chosenImage.getParent(), file_chosenImage.getName(), 
					imagesRepository);
			
			
			int y = 5;
			int offset = 0;
			resultsPanel.removeAll();
			for(int i=0; i<1703; i++)
			{
				if(i%2==0 && i!=0)
				{
					y+=200;
					offset = 0;
				}
				
				Image sampleImage = getImageFromPathAndFile(imageResult.get(i).getFileName());
				JLabel samplePic = new JLabel(new ImageIcon(sampleImage));
				samplePic.setBounds(offset*200+5, y, 195,196);
				//System.out.println("value: "+imageResult.get(i).getValue());
				resultsPanel.add(samplePic);
				this.repaint();
				offset++;
			}
			resultsPanel.setPreferredSize(new Dimension(530, y+220));
			resultsPanel.repaint();
			resultsPanelScroller.repaint();
			resultsPanelScroller.setViewport(resultsPanelScroller.getViewport());
		}
		
	}
	
	public static Image getImageFromPathAndFile(String fileName)
	{
		BufferedImage bi = null;
		Image rescaled = null;
//		String outputFileName = "D:\\College\\Multire\\MP1\\MP1\\images" + File.separatorChar + fileName;
		String outputFileName = imagesRepository + File.separatorChar + fileName;
    	
		try 
		{
	    		File file = new File(outputFileName);
	    		FileInputStream in = new FileInputStream(file);
	
	    		// decodes the JPEG data stream into a BufferedImage
	    		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
	    		bi = decoder.decodeAsBufferedImage();
	    		rescaled = bi.getScaledInstance(195, 196, Image.SCALE_DEFAULT);
	    	} 
		catch (Exception ex) 
		{
	    		ex.printStackTrace();
	    	}
	    	return rescaled;
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
