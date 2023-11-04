package komplexaufgabe.core.interfaces.components;

import komplexaufgabe.core.entities.Record;

public interface ICentralUnit {
        boolean validateOfficer(Integer officerID, Integer pin);

        void addRecord(Record record);

        void createReportLog();

        void export();

       void encryptExport();
    }

