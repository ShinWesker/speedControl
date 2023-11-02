package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.entities.Record;
import komplexaufgabe.core.interfaces.components.ICentralUnit;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.io.IFileParser;
import komplexaufgabe.io.IFileWriter;
import komplexaufgabe.io.TextFileWriter;

import java.util.*;
import java.util.stream.Collectors;

public class CentralUnit implements ICentralUnit {
    private final TreeMap<Integer, Officer> registeredOfficer;
    private final ArrayList<Record> fineRecords;
    private final IFileWriter fileWriter;
    private final String logPath = "/report.log";
    private final String exportPath = "/export.csv";
    private final IEncryption aes = new AES();
    private final String exportPathEncrypted = "/export.enc";

    public CentralUnit() {
        registeredOfficer = new TreeMap<>();
        fineRecords = new ArrayList<>();
        Officer off1 = new Officer.OfficerBuilder("Joe", new Date(System.currentTimeMillis()), "FACEAFACEAFACEA", 0, new IDCard()).build();
        off1.getIdCard().store(1234);
        registeredOfficer.put(0, off1);

        Officer off2 = new Officer.OfficerBuilder("Peter", new Date(System.currentTimeMillis()), "FACEBFACEBFACEB", 1, new NextGenIDCard()).build();
        off2.getIdCard().store(2345);
        registeredOfficer.put(1, off2);
        fileWriter = new TextFileWriter();
    }

    public boolean validateOfficer(Integer officerID, Integer pin) {
        Officer officer = registeredOfficer.get(officerID);
        if (officer == null) {
            return false;
        }

        return officer.getIdCard().isAuthorized(pin);
    }

    public void addRecord(Record record) {
        fineRecords.add(record);
    }

    public void createReportLog() {
        String content = "";

        content = content + "Counted fined cars by manufacturer:\n--------------------------------\n";

        content = content + fineRecords.stream().collect(Collectors.groupingBy(Record::getManufacturer, Collectors.counting())) + "\n";
        content = content + "\nRecord overview (timestamp asc):\n--------------------------------\n";
        content = content + fineRecords.stream().sorted(Comparator.comparingLong(Record::getTimestamp)).toList() + "\n";
        content = content + "\nRecord overview (measured speed desc):\n--------------------------------\n";
        content = content + fineRecords.stream().sorted(Comparator.comparingInt(Record::getMeasuredSpeed).reversed()).toList() + "\n";
        content = content + "\nRecord overview (to fast speed average, min, max):\n--------------------------------\n";
        content = content + "average: " + String.format("%1.2f", (fineRecords.stream().collect(Collectors.averagingInt(Record::getMeasuredSpeedAfterDeductingTolerance)) - 50)) + "\n";
        content = content + "min: " + (fineRecords.stream().mapToInt(Record::getMeasuredSpeedAfterDeductingTolerance).min().getAsInt() - 50) + "\n";
        content = content + "max: " + (fineRecords.stream().mapToInt(Record::getMeasuredSpeedAfterDeductingTolerance).max().getAsInt() - 50) + "\n";

        fileWriter.writeFile(logPath, content);

    }

    public void export() {
        StringBuilder content = new StringBuilder();
        List<Record> sortedList = fineRecords.stream().sorted(Comparator.comparingLong(Record::getTimestamp)).toList();
        for (Record rec : sortedList) {
            content.append(rec.toCSV());
        }

        fileWriter.writeFile(exportPath, content.toString());
    }

    public void encryptExport() {
        IFileParser csvParser = new CSVParser();
        List<String[]> in = csvParser.parse(exportPath);


        StringBuilder content = new StringBuilder();
        for (String[] arr : in) {
            for (String s : arr) {
                content.append(s).append(";");
            }
            content.append("\n");
        }
        fileWriter.writeFile(exportPathEncrypted, aes.encrypt(content.toString()));
        System.out.println("Encrypted export file.");
    }
}
