package HTTPServerSimple;

public class Buffer {
    public byte[] file;
    public long timeofcreation;

    Buffer(byte[] file, long timeofcreation) {
        this.file = file;
        this.timeofcreation = timeofcreation;
    }

    public void setTimeofcreation(long timeofcreation) {
        this.timeofcreation = timeofcreation;
    }
}
