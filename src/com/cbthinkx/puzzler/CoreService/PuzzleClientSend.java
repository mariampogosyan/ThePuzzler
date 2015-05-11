package com.cbthinkx.puzzler.CoreService;

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PuzzleClientSend extends Thread {
    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT_NUMBER = 25565;
    private PDDocument pdfDoc = null;

    public PuzzleClientSend() {
    }
    public PDDocument PuzzleClientSend(PuzzleData pd) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT_NUMBER);
        ) {
            Thread sendPDThread = new Thread(
                    () -> sendPuzzleData(socket, pd)
            );
            sendPDThread.start();
            while (sendPDThread.isAlive());
            System.out.println("done sending");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfDoc;
    }
    public void run() {

    }
    public byte[] getSizeInBytes(String s) {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(s.getBytes().length).array();
    }
    public int getSizeFromBuf(byte[] buf) {
        return ByteBuffer.wrap(buf).order(ByteOrder.BIG_ENDIAN).getInt();
    }
    public void sendPuzzleData(Socket ss,PuzzleData pd) {
        try (
            OutputStream out = ss.getOutputStream()
        ) {
            //gets the length of the strings bytes
            byte[] toStringSize = getSizeInBytes(pd.toString());
            //writes that to the OutputStream
            out.write(toStringSize);
            //gets the string and puts it in a ByteBuffer
            byte[] stringBuff = pd.toString().getBytes();
            //writes the stringBuff to the OutputStream
            out.write(stringBuff);
            //writes the image to the OutputStream
            ImageIO.write(pd.getImage(), pd.getImgTail(), out);
            out.flush();
            ss.shutdownOutput();
            recievePDF(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void recievePDF(Socket ss) {
        try (
            InputStream in = ss.getInputStream();
        ) {
            System.out.println("recievePDF");
            //size of the pdfDoc
            byte[] pdfSize = new byte[4];
            //gets the size of the pdf
            in.read(pdfSize);
            System.out.println("pdfSize: " + pdfSize.toString());
            System.out.println("pdfSizeInt: " + getSizeFromBuf(pdfSize));
            //creates buffer to store the pdf
            byte[] pdfBuf = new byte[getSizeFromBuf(pdfSize)];
            //recieve the pdf
            in.read(pdfBuf);
            System.out.println("PDFBufSize: " + pdfBuf.length);
            //put the pdfBuf into an InputStream
            InputStream is = new ByteArrayInputStream(pdfBuf);
            //creates the pdfDocument from the is stream
            PDDocument pdf = PDDocument.load(is);
            //now i can do shit with my pdf hahahahahaha
            this.pdfDoc = pdf;
            ss.shutdownInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
