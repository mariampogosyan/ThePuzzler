package com.cbthinkx.puzzler.CoreService;

import com.cbthinkx.puzzler.CoreService.Enum.PieceShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleSkill;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class TestClient extends Thread {
    public static void main(String[] args) throws IOException {
        new TestClient().doit("localhost", 25565);
    }
    public void doit(String hostName, int portNumber){
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File("res/colors.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
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
        try (
                Socket socket = new Socket(hostName, portNumber);
        ) {
            Thread outPutThread = new Thread(
                    () -> doOutput(socket, pd)
            );
            outPutThread.start();
            while (outPutThread.isAlive());
            System.out.println("done sending");
            System.out.println(socket.isConnected());
            doInput(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {

    }
    private void doOutput(Socket ss, PuzzleData pd) {
        try (
                OutputStream outputStream = ss.getOutputStream();
                PrintWriter out = new PrintWriter(ss.getOutputStream(), true);
        ) {
            String userInput = pd.toString();
            out.println(userInput);
            ImageIO.write(pd.getImage(), pd.getImgTail(), outputStream);
            ss.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void doInput(Socket ss) {
        System.out.println(ss.isConnected());
        System.out.println(ss.isInputShutdown());
        System.out.println(ss.isOutputShutdown());
        try {
            InputStream inputStream = ss.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println(input);
            }
            System.out.println(ss.isConnected());
//            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
