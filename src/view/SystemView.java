package view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SystemView extends JFrame{
	JPanel mainPanel;
	JRadioButton rb_CHmethod, rb_CHwPSmethod, rb_HRwCCmethod, rb_CHwCRmethod;
	JLabel lbl_methods, lbl_CHmethod, lbl_CHwPSmethod, lbl_HRwCCmethod, lbl_CHwCRmethod;
	JButton btn_retrieveImages;
	
	public SystemView(){
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 600, 600);
		
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
		
		lbl_methods.setBounds(5, 5, 80, 20);
		lbl_CHmethod.setBounds(25, 25, 250, 20);
		lbl_CHwPSmethod.setBounds(25, 45, 250, 20);
		lbl_HRwCCmethod.setBounds(25, 65, 270, 20);
		lbl_CHwCRmethod.setBounds(25, 85, 250, 20);
		
		mainPanel.add(lbl_methods);
		mainPanel.add(lbl_CHmethod);
		mainPanel.add(lbl_CHwPSmethod);
		mainPanel.add(lbl_HRwCCmethod);
		mainPanel.add(lbl_CHwCRmethod);
		
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
	
}
