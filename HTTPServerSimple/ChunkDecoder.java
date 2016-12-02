package HTTPServerSimple;


import java.io.*;
import java.util.Arrays;



public class ChunkDecoder extends InputStream {
    InputStream in;
    protected byte buf[];
    private static int DEFAULT_BUFFER_SIZE = 1024;
    private boolean ischuksize = false;
    private int chunksize = 0;
    private int chunklength = 0;
    private ByteArrayOutputStream arr;
    private byte[] b1;
    protected int count;
    protected int pos;

    public ChunkDecoder(InputStream in) {
        this(in, DEFAULT_BUFFER_SIZE);
    }

    public ChunkDecoder(InputStream in, int size) {
        this.in = in;
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        buf = new byte[size];
    }

    private byte[] getBufIfOpen() throws IOException {
        byte[] buffer = buf;
        if (buffer == null)
            throw new IOException("Stream closed");
        return buffer;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (in.available() == 0) return -1;
        //byte[] buf = new byte[in.available()];
        byte[] buf = new byte[b.length];
        if (in.read(buf) == -1) return -1;
        int n = b.length;
      //  boolean ischuksize = false;
        while (n > 0) {
            if (chunksize == 0) {
                readChunkSize(buf);
                ischuksize = true;
            }
            //если длина чанка не хранилась в буфере
            if ((chunksize > 0) && (ischuksize)) {
                //здесь получаем информацию исключая техническую
                //если длина чанка меньше вместимости буфера
                if (n > chunksize ) {
                    //проверяем а не присутствует ли тут же еще и длина чанка, может она была считана в прошлый раз
                    if (!ischuksize) {
                        System.arraycopy(buf, chunklength + "\r\n".length(), b, 0, n);
                        n -= chunksize;
                        chunksize = 0;
                        ischuksize = false;
                    } else//длина чанка была считана прошлый раз, считываем остаток
                    {
                        System.arraycopy(buf, chunklength + "\r\n".length(), b, 0, chunksize);
                        n -= chunksize;
                        chunksize = 0;
                        ischuksize = false;
                    }
                }
                //если длина чанка больше вместимости буфера
                if (n < chunksize) {
                    System.arraycopy(buf, chunklength + "\r\n".length(), b, 0, n-(chunklength + "\r\n".length()));
                    chunksize -= n;
                    n=0;
                }
            } else if ((chunksize > 0) && (!ischuksize)) {

            }

            //возврат -1 однозначно когда нулевой чанк
            else {
                ischuksize = false;
                return -1;
            }
        }

        return buf.length;

    }

    private int readChunkSize(byte[] buf) throws IOException {
        if (chunksize == 0)
            arr = new ByteArrayOutputStream();
        {
            //int i = 0;
            int cur;
           /* do {
                cur = buf[i];
                if (cur == 13) {
                    cur = buf[i + 1];//не нравится мне этот момент
                    if (cur == 10) {
                        if (arr.size() == 0) continue;
                        break;
                    }
                }
                i++;
                arr.write(cur);
            } while (true);*/
            for(int i = 0;i<=buf.length;i++){
                cur = buf[i];
                if (cur == 13) {
                    continue;
                }
                if (cur == 10) {
                    break;
                }
                arr.write(cur);
            }
            chunklength = arr.size();
            String str = new String(arr.toByteArray());
            chunksize = Integer.parseInt(str, 16);
        }
        return chunksize;
    }

    private int readChunkSize() throws IOException {
        if (chunksize == 0)
            arr = new ByteArrayOutputStream();
        {
            int i = 0;
            int cur;
            do {
                if((cur = in.read())==-1) return -1;
                if ((byte) cur == 13) {
                    if((cur = in.read())==-1) return -1;
                    if ((byte) cur == 10) {
                        if (arr.size() == 0) continue;
                        break;
                    }
                }

                arr.write(cur);
            } while (true);

            String str = new String(arr.toByteArray());
            chunksize = Integer.parseInt(str, 16);
        }
        return chunksize;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }


    @Override
    public int read() throws IOException {
        if (chunksize == 0) {
            readChunkSize();
        }
        if (chunksize > 0) {
            chunksize--;
            return in.read();
        } else {
            return -1;
        }
    }


    public static void main(String[] args) throws IOException {
        //FileInputStream fis = new FileInputStream(new File("c:\\test.txt"));
        FileInputStream fis = new FileInputStream(new File("c:\\my.txt"));
        FileOutputStream fos = new FileOutputStream(new File("c:\\decod.txt"));
        ChunkDecoder cd = new ChunkDecoder(fis);
        int count;
        byte[] buffer = new byte[20];
        {
            do {
                //тест побайтового декодирования
               count = cd.read();
                //тест декодирования буфера
               //count = cd.read(buffer);
                if (count > 0) {
                    //записать побайтово
                    fos.write(count);
                    //запись из буфера
                    //fos.write(buffer);
                }
            } while (count != -1);

        }
    }
}
