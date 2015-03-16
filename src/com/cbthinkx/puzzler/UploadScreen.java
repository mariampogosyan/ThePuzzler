package com.cbthinkx.puzzler;

import javax.swing.JButton;
import javax.swing.JTextField;

public class UploadScreen extends Background{
	private static final long serialVersionUID = 1L;
	PFrame frame;
	public UploadScreen(){
		super();
		JButton upld = new JButton("Upload");
		upld.setSize(650, 40);
		add(upld);
		JTextField size = new JTextField();
		size.setSize(650, 40);
		add(size);
		JButton next = new JButton("save and continue");
		next.setSize(650, 40);
		add(next);
		}
}
