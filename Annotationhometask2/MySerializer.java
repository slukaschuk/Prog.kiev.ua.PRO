package Annotationhometask2;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MySerializer {
    TextContainer tc;

    MySerializer(TextContainer obj) {
        this.tc = obj;
    }

    public void savetoFile(String path) throws IllegalAccessException {
        Class<?> cls = tc.getClass();
        File f = new File(path);
        StringBuilder sb = new StringBuilder();
        try (FileWriter ds = new FileWriter(f)) {
            Field[] fld = cls.getDeclaredFields();
            for (Field field : fld) {
                Class<?> fieldType = field.getType();
                if (field.isAnnotationPresent(Save.class)) {
                    sb.append(field.getName() + "=");
                    int mods = field.getModifiers();
                    if (Modifier.isPrivate(mods)) {
                        field.setAccessible(true);
                    }

                    if (field.getType() == int.class) {
                        sb.append(field.getInt(tc));
                    } else if (field.getType() == boolean.class) {
                        sb.append(field.getBoolean(tc));
                    } else if (field.getType() == String.class) {
                        sb.append((String) field.get(tc));
                    }
                    sb.append(";");
                } else {
                    System.out.println("This field won't be saved.");
                    System.out.print("\tName: " + field.getName());
                    System.out.println("\tType: " + fieldType.getName());
                }
            }
            ds.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("All fields was saved.");

    }

    public void loadfromFile(String path, TextContainer tcfromfile) throws NoSuchFieldException, IllegalAccessException {
        Class<?> cls = tcfromfile.getClass();
        StringBuilder sb = new StringBuilder();
        String str = "";
        int r;
        try (FileReader dis = new FileReader(path)) {
            char[] buf = new char[100];
            do {
                r = dis.read(buf);
                if (r > 0) {
                    sb.append(buf, 0, r);
                }
            } while (r != -1);
            str = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (str != "") {
            String[] data = str.split(";");
            for (String s : data) {
                String[] fieldsdata = s.split("=");
                String fName = fieldsdata[0];
                String fvalue = fieldsdata[1];
                Field f = cls.getDeclaredField(fName);
                int mods = f.getModifiers();
                if (Modifier.isPrivate(mods)) {
                    f.setAccessible(true);
                }

                if (f.getType() == int.class) {
                    f.setInt(tcfromfile, Integer.parseInt(fvalue));
                } else if (f.getType() == boolean.class) {
                    f.setBoolean(tcfromfile, Boolean.parseBoolean(fvalue));
                } else if (f.getType() == String.class) {
                    f.set(tcfromfile, (String) fvalue);
                }
            }
        }
    }

}
