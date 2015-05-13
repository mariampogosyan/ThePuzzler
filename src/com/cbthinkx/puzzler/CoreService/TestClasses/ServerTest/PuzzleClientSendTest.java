package com.cbthinkx.puzzler.CoreService.TestClasses.ServerTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import com.cbthinkx.puzzler.CoreService.PuzzleData;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PuzzleClientSendTest {
    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT_NUMBER = 25565;
    private PDDocument pdfDoc = null;
    private InputStream in;
    private OutputStream out;
    public PuzzleClientSendTest() {
    }
    public PDDocument PuzzleClientSendX(PuzzleData pd) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT_NUMBER);
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
            sendPuzzleData(pd);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfDoc;
    }
    public byte[] getSizeInBytes(String s) {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(s.getBytes().length).array();
    }
    public int getSizeFromBuf(byte[] buf) {
        return ByteBuffer.wrap(buf).order(ByteOrder.BIG_ENDIAN).getInt();
    }
    public void sendPuzzleData(PuzzleData pd) {
        try {
            byte[] toStringSize = getSizeInBytes(pd.toString());
            out.write(toStringSize);
            byte[] stringBuff = pd.toString().getBytes();
            out.write(stringBuff);
            ImageIO.write(pd.getImage(), pd.getImgTail(), out);
        	recievePDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void recievePDF() {
        try {
            byte[] pdfSize = new byte[4];
            in.read(pdfSize);
            byte[] pdfBuf = new byte[getSizeFromBuf(pdfSize)];
            System.out.println("*** " + in.read(pdfBuf));
            InputStream is = new ByteArrayInputStream(pdfBuf);
            PDDocument pdf = PDDocument.load(is);
            this.pdfDoc = pdf;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
