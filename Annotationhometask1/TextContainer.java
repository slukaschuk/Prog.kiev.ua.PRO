package Annotationhometask1;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@interface SaveToFile {
    String value() default "c:\\savefile.txt";
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Saver {
}

@SaveToFile
public class TextContainer {
    private String teststring;

    TextContainer(String str) {
        this.teststring = str;
    }

    public void setTextString(String str) {
        this.teststring = str;
    }

    public String getTextString() {
        return teststring;
    }

    @Saver
    public void save() {
        Class<?> cls = this.getClass();
        if (cls.isAnnotationPresent(SaveToFile.class)) {
            SaveToFile stf = cls.getAnnotation(SaveToFile.class);
            String filename = stf.value();
            try (DataOutputStream fs = new DataOutputStream(new FileOutputStream(filename))) {
                fs.writeUTF(teststring);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
