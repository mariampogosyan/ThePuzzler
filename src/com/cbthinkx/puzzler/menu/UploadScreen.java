package com.cbthinkx.puzzler.menu;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFileChooser;

public class UploadScreen extends Background{
	private static final long serialVersionUID = 1L;
	private JTextField size;
	private BufferedImage image = null;
	public UploadScreen(PFrame pf) {
		super(pf);
		JButton upld = new JButton("Chose the file");
		upld.addActionListener(upload);

		upld.setSize(100, 40);
        upld.setLocation(
                pf.getWidth() / 2 - upld.getWidth() / 2,
                (int)(pf.getHeight() * 0.25)
        );
		add(upld);
		size = new JTextField();
		size.setSize(100, 40);
        size.setLocation(
                pf.getWidth() / 2 - upld.getWidth() / 2,
                (int)(pf.getHeight() * 0.45)
        );
		add(size);
		JButton next = new JButton("next");
		next.addActionListener(nxt);
		next.setSize(100, 40);
        next.setLocation(
                pf.getWidth() / 2 - next.getWidth() / 2 ,
                (int)(pf.getHeight() * 0.65)
        );
		add(next);
	}
	private ActionListener upload = ae -> {
		JFileChooser fileChooser = new JFileChooser("C:\\");
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {	     
			System.out.println("yay");
			fileChooser.setMultiSelectionEnabled(false);
		}
	};

	private ActionListener nxt = ae -> {
		frame.setView(PFrame.Puzzle_settings);	
		System.out.println(size.getText().toString());
		System.out.println(Double.parseDouble(size.getText().toString()));
		double sizeD = Double.parseDouble(size.getText().toString());
		frame.getData().setSize(sizeD);
		frame.getData().setImage(image);
		frame.getData().setOrigImage(image);
	};
}
