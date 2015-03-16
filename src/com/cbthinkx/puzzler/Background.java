package com.cbthinkx.puzzler;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel {
	private static final long serialVersionUID = 1L;
	Image background;
	public Background() {
		super();
		setLayout(null);
		this.setDoubleBuffered(true);
		try {
			this.background = ImageIO.read(new File("C:/Users/Masha/Documents/GitHub/ThePuzzler/puzzle.jpg"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(background, 0, 0, this);
	}
}
