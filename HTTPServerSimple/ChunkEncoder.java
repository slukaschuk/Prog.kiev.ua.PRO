package HTTPServerSimple;


import java.io.*;


public class ChunkEncoder extends OutputStream {
    private int chunkSize;
    private OutputStream out;


    public ChunkEncoder(OutputStream out, int chunkSize) {
        if (out == null) {
            throw new NullPointerException();
        } else if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunk size <= 0");
        }
        this.out = out;
        this.chunkSize = chunkSize;
    }

    public ChunkEncoder(OutputStream out){
        if (out == null) {
            throw new NullPointerException();}
        this.out = out;
        this.chunkSize = 10;
    }

    @Override
    public void write(int b) throws IOException {
        String head = Integer.toHexString(1) + "\r\n";
        out.write(head.getBytes());
        out.write(b);
        out.write("\r\n".getBytes());
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        int n = (len - off) / chunkSize;
        int tail = (len - off) % chunkSize;
        int offset = 0;
        String head = Integer.toHexString(chunkSize) + "\r\n";

        for (int i = 0; i < n; i++) {
            out.write(head.getBytes());
            out.write(b, offset, chunkSize);
            out.write("\r\n".getBytes());
            offset += chunkSize;
        }
        if (tail > 0) {
            head = Integer.toHexString(tail) + "\r\n";
            out.write(head.getBytes());
            out.write(b, offset, tail);
            out.write("\r\n".getBytes());
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b,0,b.length);
    }

    @Override
    public void flush() throws IOException {
        out.write("0\r\n\r\n".getBytes());
        out.flush();
    }
    @Override
    public void close() throws IOException {
        out.close();
    }


    public static void main(String[] args) throws IOException {
        String text = "Java is a great language, simple of Chunking'a, awdkjh" +
                "awdkhawdkjhawkdhawkdhawkdjhkjwadhjkawdhjkawdhkwdhjwkadka" +
                "wdkwadhkawdhjawawkldkldwakdlkjaldkjawldjlawdkjlawkdjlawkdjlwadkjlwd" +
                "awdwlakdlkwadjkldjadddddddddddddddddddddddddddddddddddddddddddddddd" +
                "awdjawdkjhwdkjhwdkahdkahwdkajwdkajhdwkjadkw" +
                "awdakjwdhkwjdhkwahdkhawkdjhkawdhkwadh kwhdkawdhhwa kwhdkhwda" +
                "dakjdwkawjdkawhdkhawd wad wadkjhdkahwdk  kwjdd";
        byte[] byteArr = text.getBytes();
        File file = new File("c:/test.txt");
        FileOutputStream out = new FileOutputStream(file);
        int chunkSize = 100;
        ChunkEncoder chunkcoder = new ChunkEncoder(out, chunkSize);
        chunkcoder.write(byteArr);
        chunkcoder.flush();
    }
}
