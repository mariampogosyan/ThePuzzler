package com.cbthinkx.puzzler.menu;

import javax.swing.JButton;
import javax.swing.JTextField;

public class UploadScreen extends Background{
	private static final long serialVersionUID = 1L;
	public UploadScreen(PFrame pframe) {
		super();
		JButton upld = new JButton("Upload");
		upld.setSize(100, 40);
        upld.setLocation(
                pframe.getWidth() / 2 - upld.getWidth() / 2,
                (int)(pframe.getHeight() * 0.25)
        );
		add(upld);
		JTextField size = new JTextField();
		size.setSize(100, 40);
        size.setLocation(
                pframe.getWidth() / 2 - upld.getWidth() / 2,
                (int)(pframe.getHeight() * 0.45)
        );
		add(size);
		JButton next = new JButton("next");
		next.setSize(100, 40);
        next.setLocation(
                pframe.getWidth() / 2 - next.getWidth() / 2 ,
                (int)(pframe.getHeight() * 0.65)
        );
		add(next);
	}
}
