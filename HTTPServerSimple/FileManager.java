package HTTPServerSimple;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileManager {

    private String path;
    private static ConcurrentHashMap<String, Buffer> map = new ConcurrentHashMap<String, Buffer>();
    private final static int LIFETIME = 600;

    public FileManager(String path) {
        // "c:\folder\" --> "c:\folder"
        if (path.endsWith("/") || path.endsWith("\\"))
            path = path.substring(0, path.length() - 1);

        this.path = path;
    }

    public byte[] get(String url) {
        try {
            Buffer buf = map.get(url);
            if (buf != null) // in cache
            {
                if (!checkcash(buf.timeofcreation)) {
                    System.out.println("Нашли файл.");
                    return buf.file;
                } else {
                    System.out.println("Время жизни файла истекло.");
                    map.remove(url);
                }
            }

            // "c:\folder" + "/index.html" -> "c:/folder/index.html"
            String fullPath = path.replace('\\', '/') + url;

            RandomAccessFile f = new RandomAccessFile(fullPath, "r");
            try {
                buf = new Buffer(new byte[(int) f.length()], System.currentTimeMillis());
                f.read(buf.file, 0, buf.file.length);
            } finally {
                f.close();
            }
            //put to cache
            map.put(url, buf);
            return buf.file;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean checkcash(long filetime) {
        boolean answer;
        if ((System.currentTimeMillis() - filetime) > LIFETIME) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;

    }
}
