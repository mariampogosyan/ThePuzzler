package com.cbthinkx.puzzler.menu;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel puzzler;
    private int height = 550;
    private int width = 550;
	public PFrame() {
		super("The Puzzler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		puzzler = new JPanel();
		puzzler.setLayout(new CardLayout());
		puzzler.add(new UploadScreen(this));
		add(puzzler);
	}	
	public static void main(String[] sa) {
		EventQueue.invokeLater(
				() -> new PFrame()
		);
	}
}


