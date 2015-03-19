package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
    public static void main(String[] args) throws IOException {
        new TestClient().doit("localhost", 25565);
    }
    public void doit(String hostName, int portNumber){
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
                20.0
        );

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
        ) {
            String userInput = pd.toString();
            out.println(userInput);
            BufferedOutputStream  out2= new BufferedOutputStream(echoSocket.getOutputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(pd.getImage(), "jpg", baos);
            byte[] bytes = baos.toByteArray();
            System.out.println(bytes.length);
            out2.write(bytes);
            out2.close();
            out.close();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
