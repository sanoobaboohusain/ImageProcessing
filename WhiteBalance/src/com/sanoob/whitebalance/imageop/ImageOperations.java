package com.sanoob.whitebalance.imageop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImageOperations {
	int counti = 0, countj = 0;
	int rgb;

	public BufferedImage applyTintSeq(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.RED);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				try {

					image.setRGB(j, i, 0xffff0000);

				} catch (Exception e) {
					System.out.println("Error " + e.getMessage());
					System.out.println("i: " + i + " j: " + j);
				}
			}
		}

		return image;
	}

	public BufferedImage applyTintParallel(BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		BufferedImage image_ = image;
		Thread first = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < height / 2; i++) {
					for (int j = 0; j < width / 2; j++) {
						image_.setRGB(j, i, 0xffff0000);
					}
				}

			}
		});

		Thread second = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < height / 2; i++) {
					for (int j = width / 2; j < width; j++) {
						image_.setRGB(j, i, 0xff00ff00);
					}
				}

			}
		});

		Thread third = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = height / 2; i < height; i++) {
					for (int j = 0; j < width / 2; j++) {
						image_.setRGB(j, i, 0xff0000ff);
					}
				}

			}
		});

		Thread fourth = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = height / 2; i < height; i++) {
					for (int j = width / 2; j < width; j++) {
						image_.setRGB(j, i, 0xff000000);
					}
				}

			}
		});

		first.start();
		second.start();
		third.start();
		fourth.start();

		try {
			first.join();
			second.join();
			third.join();
			fourth.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		return image_;

	}

	public BufferedImage applyTint(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();

		Thread first = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width / 2; j++) {
						image.setRGB(j, i, 0xffff0000);
					}
				}
			}
		});

		Thread second = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < height; i++) {
					for (int j = width / 2; j < width; j++) {

						image.setRGB(j, i, 0xff00ff00);
					}
				}
			}
		});

		first.start();
		second.start();
		try {
			first.join();
			second.join();
		} catch (Exception e) {

		}

		return image;
	}

	public static BufferedImage drawRectangleOnImage(BufferedImage image, int x, int y, int width, int height) {
		BufferedImage image_ = image;
		Graphics2D g2 = image_.createGraphics();
		g2.setColor(Color.RED);

		// g2.drawLine(xClick, yClick, xDrag, yDrag);
		g2.drawRect(x, y, width, height);
		g2.dispose();

		return image_;

	}

	public static BufferedImage clearImage(BufferedImage image) {
		BufferedImage image_ = image;
		Graphics2D g2 = image_.createGraphics();
		g2.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		g2.clearRect(0, 0, image_.getWidth(), image_.getHeight());
		g2.dispose();
		return image_;
	}

	public static void drawSelection(JPanel panel, Rectangle rectangle) {
		Graphics2D g2d = (Graphics2D) panel.getGraphics();
		g2d.setColor(Color.BLUE);
		g2d.draw(rectangle);
		panel.repaint();
	}

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
		return copy.getSubimage(x+1, y+1, width -1, height);
	}

}
