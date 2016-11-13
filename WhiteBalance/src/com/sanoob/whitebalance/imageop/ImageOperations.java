package com.sanoob.whitebalance.imageop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.sanoob.whitebalance.constants.Constants;

/*
 * @author sanoob
 */

public class ImageOperations {

	private static int imageBalanceParam = 0;
	private static Color white = new Color(255, 255, 255);
	private static int diffR = 0, diffG = 0, diffB = 0;
	private static Color normColor;

	public static BufferedImage paintImage(BufferedImage image, BufferedImage copy, Rectangle captureRect) {

		Graphics2D g = copy.createGraphics();
		g.drawImage(image, 0, 0, null);
		if (captureRect != null) {
			g.setColor(Color.BLUE);
			g.draw(captureRect);
			g.setColor(new Color(255, 255, 255, 100));
			g.fill(captureRect);
		}
		g.dispose();
		return copy;

	}

	public static BufferedImage cropImage(BufferedImage image, BufferedImage copy, int x, int y, int width,
			int height) {
		return copy.getSubimage(x + 1, y + 1, width - 1, height);
	}

	public BufferedImage doWhiteBalance(BufferedImage image, BufferedImage selection) {

		getDifference(getParameterFromSelection(selection));
		
		return normaliseImage(image);

	}

	private Color getParameterFromSelection(BufferedImage selection) {

		int height = selection.getHeight();
		int width = selection.getWidth();
		int sumR = 0, sumG = 0, sumB = 0;
		int count = 0;
		Color color;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				try {
					color = new Color(selection.getRGB(j, i));
					sumR = sumR + color.getRed();
					sumG = sumG + color.getGreen();
					sumB = sumB + color.getBlue();

					count++;
				} catch (Exception e) {
					System.out.println("Error " + e.getMessage());
					System.out.println("i: " + i + " j: " + j);
				}
			}
		}

		Color finalColor = new Color((sumR / count), (sumG / count), (sumB / count));
		//System.out.println(" col avg " + (sumR / count) + " " + (sumG / count) + " " + (sumB / count));
		return finalColor;

	}

	private void getDifference(Color color) {

		diffR = 255 - color.getRed();
		diffG = 255 - color.getGreen();
		diffB = 255 - color.getBlue();
		//System.out.println(diffR + " " + diffG + " " + diffB);
	}

	private Color getDifferenceColor(Color color) {

		diffR = 255 - color.getRed();
		diffG = 255 - color.getGreen();
		diffB = 255 - color.getBlue();
		// System.out.println(diffR+" "+diffG+" "+diffB);
		return new Color(diffR, diffG, diffB);
	}

	private BufferedImage normaliseImage(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();
		Color color, balColor;
		int r = 0, g = 0, b = 0, a = 0, dr = 0, dg = 0, db = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				color = new Color(image.getRGB(j, i));
				dr = (color.getRed() + diffR) - Constants.adjustmentFactor;
				r = (dr < 256) ? dr : 255;
				dg = (color.getGreen() + diffG) - Constants.adjustmentFactor;
				g = (dg < 256) ? dg : 255;
				db = (color.getBlue() + diffB) - Constants.adjustmentFactor;
				b = (db < 256) ? db : 255;
				a = color.getAlpha();

				balColor = new Color(r, g, b, a);
				image.setRGB(j, i, balColor.getRGB());
				
			}
		}
		return image;
	}

	private Color calculateColorDifference(Color color) {

		int difference = white.getRGB() - color.getRGB();
		return new Color(difference);

	}
}
