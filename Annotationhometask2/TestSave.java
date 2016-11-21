package Annotationhometask2;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/*
* Write code that is serialized and deserializes into / from a file all the values of a class of fields that
noted annotation @Save.
*/
public class TestSave {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        TextContainer tc = new TextContainer("String", 200, true, "Not Saved");
        MySerializer myS = new MySerializer(tc);
        myS.savetoFile("c:\\saveobj.txt");
        TextContainer tcfromfile = new TextContainer("Test sipmle string", 10, false, "Not Saved field");
        myS.loadfromFile("c:\\saveobj.txt", tcfromfile);
        System.out.println(tcfromfile.getsField());
        System.out.println(tcfromfile.getiField());
        System.out.println(tcfromfile.isbField());
        System.out.println(tcfromfile.getnotsField());
    }
}
