package com.cbthinkx.puzzler.menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.cbthinkx.puzzler.CoreService.PuzzleData;

public class ThePuzzler extends JFrame {
	private static final long serialVersionUID = 1L;
    private JPanel puzzler;
    private int height = 750;
    private int width = 750;
    private CardLayout layout;
    private PuzzleData data = new PuzzleData();
    public ThePuzzler() {
    	super("The Puzzler");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setSize(width, height);
    	setResizable(false);
    	setLocationRelativeTo(null);
		setVisible(true);
		setUpIcon();
//		puzzler.setBackground(Color.WHITE);
		puzzler = new JPanel();
		layout = new CardLayout();
		puzzler.setLayout(layout);
		puzzler.add(new PuzzlePanel(this), "PuzzlePanel");
		add(puzzler);
    }
	public void setUpIcon() {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf("mac") >= 0){
			com.apple.eawt.Application application = com.apple.eawt.Application.getApplication();
			Image image = Toolkit.getDefaultToolkit().getImage("res/puzzle.png");
			application.setDockIconImage(image);
			System.setProperty("apple.laf.useScreenMenuBar", "false");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "The Puzzler");
		}
		else if(os.indexOf("win") >= 0){
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("res/puzzle.png"));
			} catch (Exception e ) {
				e.printStackTrace();
			}
			this.setIconImage(img);
		}
	}
    public PuzzleData getData() {
    	return this.data;
    }
	public static void main(String[] sa) {
		EventQueue.invokeLater(
				() -> new ThePuzzler()
		);
	};
	
}
