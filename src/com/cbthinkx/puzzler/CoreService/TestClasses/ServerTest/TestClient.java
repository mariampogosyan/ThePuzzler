package com.cbthinkx.puzzler.CoreService.TestClasses.ServerTest;

import com.cbthinkx.puzzler.CoreService.Enum.PieceShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleSkill;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleType;
import com.cbthinkx.puzzler.CoreService.Jigsaw;
import com.cbthinkx.puzzler.CoreService.PuzzleData;

import javax.imageio.ImageIO;
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
        PuzzleClientSend puzzleClientSend = new PuzzleClientSend(pd);
    }
}
