package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;
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
        double[] vals = fromString(data);
        this.size = vals[0];
        this.shapeType = PieceShape.valueOf((int)(vals[1]));
        this.shape = PuzzleShape.valueOf((int)(vals[2]));
        this.skill = PuzzleSkill.valueOf((int)(vals[3]));
        this.type = PuzzleType.valueOf((int)(vals[4]));
    }
    public PuzzleData() {

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

    private double[] fromString(String d) {
        String data = Pattern.compile("PuzzleData").matcher(d).replaceAll("");
        data = data.replace("{","");
        data = data.replace("}","");
        String[] parts = data.split(",");
        double[] ret ={0,0,0,0,0};
        for (int x = 0; x < parts.length; x++) {
            ret[x] = Double.parseDouble(parts[x]);
        }
        return ret;
    }
}
