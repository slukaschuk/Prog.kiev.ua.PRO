package Annotationhometask2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Save {
}

public class TextContainer {

    @Save
    private String sField;
    @Save
    private int iField;
    @Save
    public boolean bField;
    private String notsavedfield;

    public TextContainer() {
    }

    public TextContainer(String sField, int iField, boolean bField, String nsfield) {
        this.sField = sField;
        this.iField = iField;
        this.bField = bField;
        this.notsavedfield = nsfield;
    }

    public String getnotsField() {
        return notsavedfield;
    }

    public void setnotsField(String nsfield) {
        this.notsavedfield = nsfield;
    }

    public String getsField() {
        return sField;
    }

    public void setsField(String sField) {
        this.sField = sField;
    }

    public int getiField() {
        return iField;
    }

    public void setiField(int iField) {
        this.iField = iField;
    }

    public boolean isbField() {
        return bField;
    }

    public void setbField(boolean bField) {
        this.bField = bField;
    }
}
