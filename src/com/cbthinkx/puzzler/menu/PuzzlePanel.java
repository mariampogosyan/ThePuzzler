package com.cbthinkx.puzzler.menu;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cbthinkx.puzzler.CoreService.Enum.PieceShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleSkill;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleType;
import com.cbthinkx.puzzler.CoreService.PuzzleClientSend;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PuzzlePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	final String[] puzzles = new String[] {"Puzzle type", "Square", "JigSaw"};
	final String[] skills = new String[] {"Level", "Baby", "Child", "Adult"};
	final String[] shapes = new String[] {"Shape", "Square", "Ellipse", "Heart",};
	final String[] sides = new String[] {"Sides", "One-Sided", "Two-Sided"};
	private JLabel type;
	private JTextField diag;
	private BufferedImage image = null;
	private double size;
	private File imageFile;
	private JFileChooser fc;
    private String imgTail;
    private ThePuzzler pf;
	JComboBox<String> puzzle;
	JComboBox<String> shape;
	JComboBox<String> skill;
	JComboBox<String> side;
	private BufferedImage img;
	public PuzzlePanel(ThePuzzler pf) {
		setLayout(null);
		this.pf = pf;
		setBackground(Color.WHITE);
		shape = new JComboBox<String>(shapes);
		shape.setBackground(Color.WHITE);
		shape.setSize(150, 40);
	    shape.setLocation(
				(int) (pf.getWidth()*0.95 - shape.getWidth()),
				(int) (pf.getHeight() * 0.07)
		);
		add(shape);
		puzzle = new JComboBox<String>(puzzles);
		puzzle.setBackground(Color.WHITE);
		puzzle.setSize(150, 40);
	    puzzle.setLocation(
				(int) (pf.getWidth()*0.95 - puzzle.getWidth()),
				(int) (pf.getHeight() * 0.26)
		);
		add(puzzle);
		skill = new JComboBox<String>(skills);
		skill.setBackground(Color.WHITE);
		skill.setSize(150, 40);
		skill.setLocation(
				(int) (pf.getWidth()*0.95 - skill.getWidth()),
				(int) (pf.getHeight() * 0.46)
		);
		add(skill);
		side = new JComboBox<String>(sides);
		side.setBackground(Color.WHITE);
		side.setSize(150, 40);
		side.setLocation(
				(int) (pf.getWidth()*0.95 - side.getWidth()),
				(int) (pf.getHeight() * 0.66)
		);
	    add(side);
		JButton doit = new JButton("Puzzle it!");
		doit.setBackground(Color.WHITE);
		doit.setSize(150, 40);
	    doit.setLocation(
				(int) (pf.getWidth()*0.95 - doit.getWidth()),
				(int) (pf.getHeight() * 0.85)
		);
	    doit.addActionListener(pzzl);
		add(doit);
		diag = new JTextField();
		diag.setBackground(Color.WHITE);
		diag.setSize(150, 40);
        diag.setLocation(
                pf.getWidth() / 2 - diag.getWidth() / 2,
                (int)(pf.getHeight() * 0.85)
        );
		add(diag);
		JButton upld = new JButton("Choose a file");
		upld.setBackground(Color.WHITE);
		upld.addActionListener(upload);
		upld.setSize(150, 40);
        upld.setLocation(
                (int) (pf.getWidth()*0.25 - upld.getWidth()),
                (int)(pf.getHeight() * 0.85
                		)
        );
		add(upld);
		try {
			this.img = ImageIO.read(new File("res/puzzle_logo.png"));
		} catch (IOException ex) {
				ex.printStackTrace();
		}
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, (int)(pf.getHeight()*0.07), this);
	}
	private ActionListener pzzl = ae -> {
		if (image == null) {
			JOptionPane.showMessageDialog(pf, "Please, upload the picture");
		} else {
			if (diag.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(pf, "Please, specify your size");
			} else {
				try {
					size = Double.parseDouble(diag.getText().toString());
					if (size > 5) {
						size = Double.parseDouble(diag.getText().toString());
						pf.getData().setSize(size);
						pf.getData().setImage(image);
						pf.getData().setOrigImage(image);
						pf.getData().setImgTail(imgTail);
					} else {
						JOptionPane.showMessageDialog(pf, "Please, enter a bigger number");
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(pf, "Please, enter a valid number");
				}
			}
		}
		if((shape.getSelectedIndex()==0) || (skill.getSelectedIndex()==0) || (puzzle.getSelectedIndex()==0) || (side.getSelectedIndex()==0)) {
			JOptionPane.showMessageDialog(pf, "Please, specify all the parameters");
		} else {
			pf.getData().setShape(PuzzleShape.valueOf(shape.getSelectedIndex()));
			pf.getData().setSkill(PuzzleSkill.valueOfIndex(skill.getSelectedIndex()));
			pf.getData().setShapeType(PieceShape.valueOf(puzzle.getSelectedIndex()));
			pf.getData().setType(PuzzleType.valueOf(side.getSelectedIndex()));
			PDDocument PDFDoc = new PuzzleClientSend().PuzzleClientSend(pf.getData());
			try {
				PDFDoc.save(new File("GOTAPDF.pdf"));
				Desktop.getDesktop().open(new File("GOTAPDF.pdf"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(pf.getData().toString());
		}
	};
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
}
