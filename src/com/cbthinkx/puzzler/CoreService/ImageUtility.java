package com.cbthinkx.puzzler.CoreService;


import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

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
        return img;
    }

}
