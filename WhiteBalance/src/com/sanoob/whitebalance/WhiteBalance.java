package com.sanoob.whitebalance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sanoob.whitebalance.gui.ImageFrame;
import com.sanoob.whitebalance.imageop.ImageOperations;

/*
 * @author sanoob
 */
public class WhiteBalance {

	public static int startX = 0, startY = 0, endX = 0, endY = 0;
	public static int oldW = 0;
	public static int oldH = 0;
	public static boolean rectangleMade = false;
	public static Point start;

	public static void main(String[] args) throws Exception {

		String filePath = "/home/sanoob/Desktop/sample_images/image1withoutwhitebalance.png";
		ImageFrame imageFrame = new ImageFrame();
		BufferedImage image = ImageIO.read(new File(filePath));
		BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		imageFrame.setImage(image);
		// Rectangle selection;
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				start = e.getPoint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {

				Point end = e.getPoint();
				rectangleMade = true;
				Rectangle selection = new Rectangle(start, new Dimension((end.x - start.x), (end.y - start.y)));
				imageFrame.setImage(ImageOperations.paintImage(image, copy, selection));

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Point end = e.getPoint();
				BufferedImage crop = null;
				if (rectangleMade) {
					if (Math.abs(end.x - start.x) > 0)
						crop = ImageOperations.cropImage(image, copy, start.x, start.y, Math.abs(end.x - start.x),
								Math.abs(end.y - start.y));
				}
				if(crop != null){
					JLabel label = new JLabel(new ImageIcon(crop));
					label.setLayout(new BorderLayout());
					JPanel panel = new JPanel();
							panel.setPreferredSize(new Dimension(crop.getWidth(), crop.getHeight()));
					panel.add(label, BorderLayout.CENTER);
					panel.repaint();
					
					JOptionPane.showConfirmDialog(null,
	                        panel,
	                        "Selected part ",
	                        JOptionPane.OK_CANCEL_OPTION,
	                        JOptionPane.PLAIN_MESSAGE);
					
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (rectangleMade) {
					imageFrame.setImage(ImageOperations.paintImage(image, copy, new Rectangle(0, 0, 0, 0)));
					rectangleMade = false;
				}
			}
		};

		imageFrame.getPanel().addMouseListener(mouseAdapter);
		imageFrame.getPanel().addMouseMotionListener(mouseAdapter);

	}
}
