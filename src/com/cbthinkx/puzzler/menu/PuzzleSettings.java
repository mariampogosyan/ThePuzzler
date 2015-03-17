package com.cbthinkx.puzzler.menu;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class PuzzleSettings extends Background{
		private static final long serialVersionUID = 1L;
		final String[] puzzles = new String[] {"Puzzle type", "JigSaw", "Square"};
		final String[] skills = new String[] {"Level", "Baby", "Child", "Adult"};
		final String[] shapes = new String[] {"Shape", "Square", "Ellipse", "Heart"};
		public PuzzleSettings(PFrame pf) {
			super(pf);
			JComboBox<String> shape = new JComboBox<String>(shapes);
			shape.setSize(200, 40);
	        shape.setLocation(
	                pf.getWidth() / 2 - shape.getWidth() / 2,
	                (int)(pf.getHeight() * 0.15)
	        );
			add(shape);
			
			JComboBox<String> puzzle = new JComboBox<String>(puzzles);
			puzzle.setSize(200, 40);
	        puzzle.setLocation(
	                pf.getWidth() / 2 - puzzle.getWidth() / 2,
	                (int)(pf.getHeight() * 0.35)
	        );
			add(puzzle);
			JComboBox<String> skill = new JComboBox<String>(skills);
			skill.setSize(200, 40);
	        skill.setLocation(
	                pf.getWidth() / 2 - skill.getWidth() / 2,
	                (int)(pf.getHeight() * 0.55)
	        );
	        add(skill);
			JButton doit = new JButton("Puzzle it!");
			doit.setSize(200, 40);
	        doit.setLocation(
	                pf.getWidth() / 2 - doit.getWidth() / 2 ,
	                (int)(pf.getHeight() * 0.75)
	        );
			add(doit);
		}
	}

