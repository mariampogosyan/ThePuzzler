package com.cbthinkx.puzzler.menu;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public abstract class Background extends JPanel {
	private static final long serialVersionUID = 1L;
	Image background;
	protected PFrame frame;
	public Background(PFrame pf) {
		super();
		setLayout(null);
		frame = pf;
		this.setDoubleBuffered(true);
		try {
			this.background = ImageIO.read(new File("res/puzzle.jpg"));
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
