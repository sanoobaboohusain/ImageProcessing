package com.sanoob.whitebalance.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
/*
 * @author sanoob
 */
public class Util {
	
	public static Dimension getScreenSize(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize;
	}

	public static  void drawOnImage(BufferedImage image,int xClick,int yClick,int xDrag,int yDrag){
		Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.RED);
        g2.drawLine(xClick, yClick, xDrag, yDrag);
        g2.dispose();
	}
	
	
}
