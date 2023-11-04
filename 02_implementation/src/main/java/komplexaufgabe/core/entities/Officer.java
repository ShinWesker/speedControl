package komplexaufgabe.core.entities;

import java.util.Date;

public class Officer extends Human {
    private final int officerID;
    private final IDCard idCard;

    private Officer(OfficerBuilder officerBuilder) {
        super(officerBuilder.name, officerBuilder.birthdate, officerBuilder.face);
        officerID = officerBuilder.officerID;
        idCard = officerBuilder.idCard;
    }

    public static class OfficerBuilder {
        private final String name;
        private final String face;
        private final Date birthdate;
        private final int officerID;
        private final IDCard idCard;

        public OfficerBuilder(String pName, Date pBirthDate, String pFace, int pOfficerID, IDCard pIDCard) {
            name = pName;
            birthdate = pBirthDate;
            face = pFace;
            officerID = pOfficerID;
            idCard = pIDCard;
        }

        public Officer build() {
            return new Officer(this);
        }
    }

    public IDCard getIdCard() {
        return idCard;
    }
}
