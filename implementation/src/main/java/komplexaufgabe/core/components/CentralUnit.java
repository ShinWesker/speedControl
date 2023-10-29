package komplexaufgabe.core.components;

import komplexaufgabe.core.interfaces.encryption.IEncryption;

import java.util.ArrayList;
import java.util.TreeMap;

public class CentralUnit {
    private TreeMap<Integer, Integer> registeredOfficer;
    private ArrayList<Record> fineRecords;
    private IEncryption encryption;

    public CentralUnit() {
        registeredOfficer = new TreeMap<>();
        fineRecords = new ArrayList<>();
    }

    public boolean validateOfficer(Integer officerID, Integer password) {
        Integer storedPassword = registeredOfficer.get(officerID);
        if (storedPassword == null) {
            return false;
        }
        // TODO encryptionType to encrypt the entered password and then compare

        return storedPassword.equals(password);
    }
}
