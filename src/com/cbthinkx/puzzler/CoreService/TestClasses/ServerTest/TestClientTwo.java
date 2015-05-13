package com.cbthinkx.puzzler.CoreService.TestClasses.ServerTest;

import com.cbthinkx.puzzler.CoreService.Enum.PieceShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleSkill;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleType;
import com.cbthinkx.puzzler.CoreService.PuzzleData;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestClientTwo {
    public static void main(String[] sa) {
        new TestClientTwo().doIt("127.0.0.1", 25565);
    }
    public void doIt(String address, int port) {
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File("res/colors3.png"));
        } catch (Exception e) {

        }
        PuzzleData pd = new PuzzleData(
                PieceShape.JIGSAW,
                PuzzleShape.SQUARE,
                PuzzleSkill.ADULT,
                PuzzleType.ONESIDED,
                orig,
                "png",
                5.0
        );
        PDDocument PDFDoc = new PuzzleClientSendTest().PuzzleClientSendX(pd);
        try {
            PDFDoc.save(new File("GOTAPDF.pdf"));
            Desktop.getDesktop().open(new File("GOTAPDF.pdf"));
            PDFDoc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
