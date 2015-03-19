package com.cbthinkx.puzzler.menu;

import java.awt.event.ActionListener;



import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.cbthinkx.puzzler.CoreService.PieceShape;
import com.cbthinkx.puzzler.CoreService.PuzzleShape;
import com.cbthinkx.puzzler.CoreService.PuzzleSkill;
import com.cbthinkx.puzzler.CoreService.PuzzleType;

public class PuzzleSettings extends Background{
	private static final long serialVersionUID = 1L;
	final String[] puzzles = new String[] {"Puzzle type", "Square", "JigSaw"};
	final String[] skills = new String[] {"Level", "Baby", "Child", "Adult"};
	final String[] shapes = new String[] {"Shape", "Square", "Ellipse", "Heart",};
	final String[] sides = new String[] {"Sides", "One-Sided", "Two-Sided"};
	JComboBox<String> puzzle;
	JComboBox<String> shape;
	JComboBox<String> skill;
	JComboBox<String> side;
	public PuzzleSettings(PFrame pf) {
		super(pf);
		shape = new JComboBox<String>(shapes);
		shape.setSize(200, 40);
	    shape.setLocation(
	    	pf.getWidth() / 2 - shape.getWidth() / 2,
	    	(int)(pf.getHeight() * 0.1)
        );
		add(shape);
		puzzle = new JComboBox<String>(puzzles);
		puzzle.setSize(200, 40);
	    puzzle.setLocation(
	    	pf.getWidth() / 2 - puzzle.getWidth() / 2,
	        (int)(pf.getHeight() * 0.25)
	    );
		add(puzzle);
		skill = new JComboBox<String>(skills);
		skill.setSize(200, 40);
		skill.setLocation(
				pf.getWidth() / 2 - skill.getWidth() / 2,
	            (int)(pf.getHeight() * 0.4)
	    );
		add(skill);
		side = new JComboBox<String>(sides);
		side.setSize(200, 40);
		side.setLocation(
			pf.getWidth() / 2 - side.getWidth() / 2,
	        (int)(pf.getHeight() * 0.55)
	    );
	    add(side);
		JButton doit = new JButton("Puzzle it!");
		doit.setSize(200, 40);
	    doit.setLocation(
	    	pf.getWidth() / 2 - doit.getWidth() / 2 ,
	        (int)(pf.getHeight() * 0.7)
	    );
	    doit.addActionListener(pzzl);
		add(doit);
		}
	private ActionListener pzzl = ae -> {
		if((shape.getSelectedIndex()==0) || (skill.getSelectedIndex()==0) || (puzzle.getSelectedIndex()==0) || (side.getSelectedIndex()==0)) {
			JOptionPane.showMessageDialog(frame, "Please, specify all the parameters");
		} else {
			frame.getData().setShape(PuzzleShape.valueOf(shape.getSelectedIndex()));
			frame.getData().setSkill(PuzzleSkill.valueOf(skill.getSelectedIndex()));
			frame.getData().setShapeType(PieceShape.valueOf(puzzle.getSelectedIndex()));
			frame.getData().setType(PuzzleType.valueOf(side.getSelectedIndex()));
            frame.sendPuzzle();
			System.out.println(frame.getData().toString());
		}
	};
}

