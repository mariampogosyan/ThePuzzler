package com.cbthinkx.puzzler.CoreService;

import com.cbthinkx.puzzler.CoreService.Enum.PieceShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleSkill;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleType;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Robert on 4/29/15.
 */
public class TestClient {
    public static void main(String[] sa) {
        new TestClient().doIt("127.0.0.1", 25565);
    }
    public void doIt(String address, int port) {
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File("res/colors.jpg"));
        } catch (Exception e) {

        }
        PuzzleData pd = new PuzzleData(
                PieceShape.JIGSAW,
                PuzzleShape.SQUARE,
                PuzzleSkill.ADULT,
                PuzzleType.ONESIDED,
                orig,
                "jpg",
                5.0
        );
        PDDocument PDFDoc = new PuzzleClientSend().PuzzleClientSend(pd);
        try {
            PDFDoc.save(new File("GOTAPDF.pdf"));
            Desktop.getDesktop().open(new File("GOTAPDF.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
