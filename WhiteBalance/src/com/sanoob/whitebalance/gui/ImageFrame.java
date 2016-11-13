package com.sanoob.whitebalance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sanoob.whitebalance.util.Util;
/*
 * @author sanoob
 */

public class ImageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel imagePanel;
	private static JLabel imageLabel;
	private MouseListener mouseListener;
	

	public ImageFrame() {

		imagePanel = new JPanel(new BorderLayout());
		imageLabel = new JLabel("", JLabel.CENTER);
		imagePanel.addMouseListener(mouseListener);
		this.setLocationRelativeTo(null);
		this.setTitle("Image White Balance");

	}
	
	public ImageFrame(int width, int height) {

		imagePanel = new JPanel(new BorderLayout());
		imageLabel = new JLabel("", JLabel.CENTER);
		imagePanel.addMouseListener(mouseListener);
		this.setLocationRelativeTo(null);
		this.setPreferredSize(new Dimension(width+20, height+30));
		this.setTitle("Selected Part");

	}

	public JLabel getLabel() {
		return imageLabel;
	}

	public JPanel getPanel() {
		return imagePanel;
	}

	public void setImage(BufferedImage image) {
		
		ImageIcon image_ = new ImageIcon(image);
		imageLabel.setLayout(new BorderLayout());
		imageLabel.setIcon(image_);
		imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		imagePanel.repaint();
		int w, h;
		Dimension screen = Util.getScreenSize();
		if (image.getWidth() > screen.getWidth()) {
			w = screen.width;
		} else {
			w = image.getWidth() + 10;
		}
		if (image.getHeight() > screen.getHeight()) {
			h = screen.height;
		} else {
			h = image.getHeight() + 10;
		}
		this.setSize(w, h);
		this.add(imagePanel);
		this.repaint();
		showFrame(true);
	}

	public void showFrame(boolean value) {

		this.setVisible(value);
	}
	
	public MouseListener getPanelMouseListener(){
		return mouseListener;
	}
	 
	public void setFrame(){
		this.setLocationRelativeTo(null);
	}
	
	

}
