package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


public class Square {
	PuzzleData pd;
	BufferedImage image;
	BufferedImage nImage;
	ArrayList<BufferedImage> pieces = new ArrayList<>();
	int width;
	int height;
    public static void main(String[] sa) {
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File("res/puzzle.jpg"));
        } catch (Exception e) {

        }
        PuzzleData pd = new PuzzleData(
                PieceShape.SQUARE,
                PuzzleShape.ELLIPSE,
                PuzzleSkill.ADULT,
                PuzzleType.ONESIDED,
                orig,
                "",
                20.0
        );
        new Square(pd);
    }
	public Square (PuzzleData pd) {
		this.pd = pd;
//		nImage = new ImageUtility().newImage(this.pd.getSize(), this.pd.getShape(), pd.getImage());
        nImage = this.pd.getImage();
		width = nImage.getHeight();
		height = nImage.getWidth();	
		switch(pd.getSkill()) {
		case BABY: {
			baby();
			break;
		}
		case CHILD: {
			child();
			break;
		}
		case ADULT: {
			adult();
			break;
		}
		default:
			break;
		}
	}
	public void baby() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				image = nImage.getSubimage(j * width / 2, i * height / 2, width / 2, height / 2) ;                               
				pieces.add(image);
			}
       }
        try {
            new PDFGenerator(pieces);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void child() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				image = nImage.getSubimage(j * width / 5, i * height / 5, width / 5, height / 5) ;                               
				pieces.add(image);
			}
       }
        try {
            new PDFGenerator(pieces);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void adult() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				image = nImage.getSubimage(j * width / 7, i * height / 7, width / 7, height / 7) ;                               
				pieces.add(image);
			}
       }
        try {
            new PDFGenerator(pieces);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
