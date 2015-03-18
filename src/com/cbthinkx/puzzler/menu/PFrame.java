package com.cbthinkx.puzzler.menu;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cbthinkx.puzzler.CoreService.PuzzleData;

public class PFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String Upload_Screen = "upload";
	public static final String Puzzle_settings = "puzzle";

	private JPanel puzzler;
    private int height = 550;
    private int width = 550;
    private CardLayout layout;
    private PuzzleData data = new PuzzleData();
	public PFrame() {
		super("The Puzzler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		puzzler = new JPanel();
		layout = new CardLayout();
		puzzler.setLayout(layout);
		puzzler.add(new UploadScreen(this), Upload_Screen);
		puzzler.add(new PuzzleSettings(this), Puzzle_settings);
		add(puzzler);
	}	
	public void setView(String v) {
		layout.show(puzzler, v); 
	}
	public PuzzleData getData(){
		 return this.data;
	}
	public static void main(String[] sa) {
		EventQueue.invokeLater(
				() -> new PFrame()
		);
	};
}


