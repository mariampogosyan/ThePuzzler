package com.cbthinkx.puzzler.menu;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cbthinkx.puzzler.CoreService.PuzzleData;

public class ThePuzzler extends JFrame {
	private static final long serialVersionUID = 1L;
    private static final String hostName = "localhost";
    private static final int portNumber = 25565;
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
    	BufferedImage img = null;
    	try {
    		img = ImageIO.read(new File("res/puzzle.png"));
    	} catch (Exception e ) {
    		e.printStackTrace();
    	}
    	this.setIconImage(img);
		setVisible(true);
//		puzzler.setBackground(Color.WHITE);
		puzzler = new JPanel();
		layout = new CardLayout();
		puzzler.setLayout(layout);
		puzzler.add(new PuzzlePanel(this), "PuzzlePanel");
		add(puzzler);
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
