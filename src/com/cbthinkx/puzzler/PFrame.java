package com.cbthinkx.puzzler;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class PFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public PFrame() {
		super("The Puzzler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(550, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}	
	public static void main(String[] sa) {
		EventQueue.invokeLater(
				() -> new PFrame()
		);
	}
}


