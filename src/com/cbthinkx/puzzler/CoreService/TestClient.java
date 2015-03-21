package com.cbthinkx.puzzler.CoreService;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

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
                PuzzleShape.SQUARE,
                PuzzleSkill.BABY,
                PuzzleType.ONESIDED,
                orig,
                "jpg",
                20.0
        );

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                OutputStream outputStream = socket.getOutputStream();
                InputStream is = socket.getInputStream();
        ) {
            String userInput = pd.toString();
            out.println(userInput);
            ImageIO.write(pd.getImage(), pd.getImgTail(), outputStream);
            PDDocument document = new PDDocument();
            while ((document.load(is) != null)) {
                System.out.println("im herererererer");
            }
            document.save(new File("savedPDF.pdf"));
            document.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
    }
}
