package Annotationhometask1;

import java.lang.reflect.*;
/**
 * Write TextContainer class that contains a string. With annotation mechanism to specify
 1) in which the file should be preserved text
 2) method, which performs preservation. Write class Saver,
 which will keep the field TextContainer class to the specified file.
 */

public class TestSaver {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TextContainer tc = new TextContainer("test string");
        Class<?> cls = TextContainer.class;
        Method[] methods = cls.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Saver.class)) {
                m.invoke(tc, null);
                System.out.println("Our data was saved");
            }
        }
    }
}
