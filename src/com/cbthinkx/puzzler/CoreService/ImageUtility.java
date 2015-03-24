package com.cbthinkx.puzzler.CoreService;


import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtility {
    public BufferedImage newImage (double size, PuzzleShape pShape, BufferedImage img) {
        img = cropImage(img, pShape);
        double[] dim = getDimensionsFromDiagnal(size, img.getHeight(), img.getWidth());
        BufferedImage newImg = new BufferedImage((int)dim[0], (int)dim[1], img.getType());
        AffineTransform at = new AffineTransform();
        at.scale(dim[2], dim[2]);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage image = scaleOp.filter(img, newImg);
        try {
            ImageIO.write(image, "jpg", new File("test3.jpg"));
        } catch (Exception e){

        }
        return image;
    }

    private double[] getDimensionsFromDiagnal (double dia, double height, double width) {
        double[] dim = new double[3];
        double dia2 = Math.sqrt((height * height) + (width * width));
        double k = dia/dia2;
        dim[0] = width * k;
        dim[1] = height * k;
        dim[2] = k;
        return dim;
    }
    private BufferedImage cropImage(BufferedImage img, PuzzleShape pShape) {
    	switch (pShape) {
    		case SQUARE: {
    			return img;
    		}
    		case ELLIPSE: {
    			return ellipseCrop(img);
    		}
    		case HEART: {
    			return heartCrop(img);
    		}
    		case NGON: {
    			return ngonCrop(img);
    		}
    		default: {
    			break;
    		}
    	}
        return img;
    }
    
    private BufferedImage ellipseCrop (BufferedImage img) {
    	int h = img.getHeight();
    	int w = img.getWidth();
    	Ellipse2D.Double ellipse = new Ellipse2D.Double(0, 0, w, h);
        Graphics2D g2d = img.createGraphics();
        g2d.setClip(ellipse);
        g2d.drawImage(img, null, 0, 0);
        Rectangle2D rect = new Rectangle2D.Double();
        rect.setRect(0, 0, w, h);
        g2d.clip(rect);
        try {
			ImageIO.write(img, "jpg", new File ("cropped.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return img;    	
    }
    private BufferedImage heartCrop (BufferedImage img) {
    	return img;
    }
    private BufferedImage ngonCrop (BufferedImage img) {
    	return img;
    }

}
