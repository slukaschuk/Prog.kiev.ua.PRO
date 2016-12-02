
package MyAnnotation;

import java.lang.annotation.*;
import java.lang.reflect.*;


/**
 Создать аннотацию, которая принимает параметры для метода. Написать код, который вызовет метод,
 помеченный этой аннотацией, и передаст параметры аннотации в вызываемый метод.
 @Test(a=2, b=5)
 public void test(int a, int b) {…}
 */


@Target(value= ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)

@interface MethodAnotation {
    int param1();
    int param2();
}

 class MTest {
    @MethodAnotation(param1 = 2, param2 = 5)
    public int mymethod(int i, int j) {
        return i * j;
    }

}
public class MainTest{
    public static void main(String[] args){
        Class<?> mt = MTest.class;
        Method[] methods = mt.getDeclaredMethods();
        for (Method m:methods){
            if(m.isAnnotationPresent(MethodAnotation.class)){
                System.out.println(m.getName());
                MethodAnotation an = m.getAnnotation(MethodAnotation.class);
                int res = 0;
                MTest mtest1 = new MTest();
                try {
                    res = (Integer) m.invoke(mtest1,an.param1(),an.param2());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                System.out.println(res);
            }
        }
    }
}

