package com.cbthinkx.puzzler;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel puzzler;
	public PFrame() {
		super("The Puzzler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(550, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		puzzler = new JPanel();
		puzzler.setLayout(new CardLayout());
		puzzler.add(new UploadScreen());
		add(puzzler);
	}	
	public static void main(String[] sa) {
		EventQueue.invokeLater(
				() -> new PFrame()
		);
	}
	public int getHeight(){
		return getHeight();
	}
	public int getWidth(){
		return getWidth();
	}
}


