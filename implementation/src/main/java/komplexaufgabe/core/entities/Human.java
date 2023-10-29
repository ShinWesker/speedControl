package komplexaufgabe.core.entities;

import java.util.Date;

public abstract class Human {

    protected final String name;
    protected final Date birthDate;
    protected final String face;

    protected Human(String pName, Date pBirthDate, String pFace){
        name = pName;
        birthDate = pBirthDate;
        face = pFace;

    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getFace() {
        return face;
    }
}
