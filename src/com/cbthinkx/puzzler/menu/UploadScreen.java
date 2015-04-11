package com.cbthinkx.puzzler.menu;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cbthinkx.puzzler.CoreService.PuzzleType;

public class UploadScreen extends Background{
	private static final long serialVersionUID = 1L;
	private JTextField diag;
	private BufferedImage image = null;
	private double size;
	private File imageFile;
	private JFileChooser fc;
    private String imgTail;
	public UploadScreen(PFrame pf) {
		super(pf);
		JButton upld = new JButton("Choose a file");
		upld.addActionListener(upload);
		upld.setSize(120, 40);
        upld.setLocation(
                pf.getWidth() / 2 - upld.getWidth() / 2,
                (int)(pf.getHeight() * 0.25)
        );
		add(upld);
		diag = new JTextField();
		diag.setSize(100, 40);
        diag.setLocation(
                pf.getWidth() / 2 - diag.getWidth() / 2,
                (int)(pf.getHeight() * 0.45)
        );
		add(diag);
		JButton next = new JButton("Next");
		next.addActionListener(nxt);
		next.setSize(100, 40);
        next.setLocation(
                pf.getWidth() / 2 - next.getWidth() / 2 ,
                (int)(pf.getHeight() * 0.65)
        );
		add(next);
	}
	private ActionListener upload = ae -> {
		fc = new JFileChooser("C:\\");
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(
			new FileNameExtensionFilter(
			        "JPG & PNG Images", "jpg", "png")
		);
		int returnValue = fc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {	     
			fc.setMultiSelectionEnabled(false);
			imageFile = fc.getSelectedFile();
            imgTail = imageFile.getName().substring(imageFile.getName().length()-3, imageFile.getName().length());
			try {
	            image = ImageIO.read(imageFile);
	        } catch(IOException e) {
	            System.out.println("read error: " + e.getMessage());
	        }		
		}
	};
	private ActionListener nxt = ae -> {
		if (image == null) {
			JOptionPane.showMessageDialog(frame, "Please, upload the picture");
		} else {
			if (diag.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please, specify your size");	
			} else {
				try {
					size = Double.parseDouble(diag.getText().toString());
						if (size > 5) {
							frame.setView(PFrame.Puzzle_settings);	
							size = Double.parseDouble(diag.getText().toString());
							frame.getData().setSize(size);
							frame.getData().setImage(image);
							frame.getData().setOrigImage(image);
                            frame.getData().setImgTail(imgTail);
						} else {
							 JOptionPane.showMessageDialog(frame, "Please, enter a bigger number");
						}
				 	} catch (NumberFormatException e) {
				 		 JOptionPane.showMessageDialog(frame, "Please, enter a valid number");					   
				 	}	 
			}
		}
	};
}
