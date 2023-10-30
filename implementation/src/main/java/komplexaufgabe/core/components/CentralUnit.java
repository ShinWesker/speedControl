package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.Officer;
import komplexaufgabe.core.entities.OldIDCard;
import komplexaufgabe.core.entities.Record;
import komplexaufgabe.core.interfaces.encryptionhash.IEncryptionHash;
import komplexaufgabe.core.interfaces.encryptionhash.SHA256;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class CentralUnit {
    private final TreeMap<Integer, Officer> registeredOfficer;
    private ArrayList<Record> fineRecords;

    public CentralUnit() {
        registeredOfficer = new TreeMap<>();
        fineRecords = new ArrayList<>();
        IEncryptionHash encryptionHash = new SHA256();
        Officer off1 = new Officer.OfficerBuilder("Joe",new Date(System.currentTimeMillis()), "FACEAFACEAFACEA", 0, new OldIDCard()).build();
        off1.getIdCard().store(123);
        registeredOfficer.put(0,off1);

        Officer off2 = new Officer.OfficerBuilder("Peter",new Date(System.currentTimeMillis()), "FACEBFACEBFACEB", 1, new OldIDCard()).build();
        off2.getIdCard().store(234);
        registeredOfficer.put(1,off2);

    }

    public boolean validateOfficer(Integer officerID, Integer pin) {
        Officer officer = registeredOfficer.get(officerID);
        if (officer == null) {
            return false;
        }

        return officer.getIdCard().isAuthorized(pin);
    }

    public void addRecord(Record record){
        fineRecords.add(record);
    }
}
