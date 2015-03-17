package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Pattern;

public class PuzzleData {
    private double size;
    private PieceShape shapeType;
    private PuzzleShape shape;
    private PuzzleSkill skill;
    private PuzzleType type;
    private BufferedImage image;
    private BufferedImage origImage;

    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }
    public PieceShape getShapeType() {
        return shapeType;
    }
    public void setShapeType(PieceShape shapeType) {
        this.shapeType = shapeType;
    }
    public PuzzleShape getShape() {
        return shape;
    }
    public void setShape(PuzzleShape shape) {
        this.shape = shape;
    }
    public PuzzleSkill getSkill() {
        return skill;
    }
    public void setSkill(PuzzleSkill skill) {
        this.skill = skill;
    }
    public PuzzleType getType() {
        return type;
    }
    public void setType(PuzzleType type) {
        this.type = type;
    }
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public BufferedImage getOrigImage() {
        return origImage;
    }
    public void setOrigImage(BufferedImage origImage) {
        this.origImage = origImage;
    }

    public PuzzleData(PieceShape shapeType, PuzzleShape shape, PuzzleSkill skill, PuzzleType type, BufferedImage image, double size) {
        this.shapeType = shapeType;
        this.shape = shape;
        this.skill = skill;
        this.type = type;
        this.image = image;
        this.origImage = image;
        this.size = size;
    }
    public PuzzleData(String data, BufferedImage img) {
        this.image = img;
        this.origImage = img;
        double[] x = fromString(data);
        this.size = x[0];
        this.shapeType = shapeType.valueOf((int)x[1]);
        this.shape = shape.valueOf((int)x[2]);
        this.skill = skill.valueOf((int)x[3]);
        this.type = type.valueOf((int)x[4]);


    }
    @Override
    public String toString() {
        return "PuzzleData{" +
                "" + size +
                "," + shapeType.getVal() +
                "," + shape.getVal() +
                "," + skill.getVal() +
                "," + type.getVal() +
                '}';
    }

    public double[] fromString(String d) {
        String data = Pattern.compile("PuzzleData").matcher(d).replaceAll("");
        data = data.replace("{","");
        data = data.replace("}","");
        String[] parts = data.split(",");
        double[] ret ={} ;
        for (int x = 0; x < parts.length; x++) {
            System.out.println(parts[x]);
            ret[x] = Double.parseDouble(parts[x]);
        }
        return ret;
    }

    public static void main(String[] sa) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File("res/puzzle.jpg"));
        } catch (Exception e) {

        }
        PuzzleData pd = new PuzzleData(PieceShape.SQUARE, PuzzleShape.SQUARE, PuzzleSkill.CHILD, PuzzleType.ONESIDED, null, 20);
        String newPD = pd.toString();
        PuzzleData pd2 = new PuzzleData(newPD, null);
        System.out.println(pd2.toString());
    }
}
