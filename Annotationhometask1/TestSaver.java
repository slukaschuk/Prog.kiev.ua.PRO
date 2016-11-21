package Annotationhometask1;

import java.lang.reflect.*;

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
