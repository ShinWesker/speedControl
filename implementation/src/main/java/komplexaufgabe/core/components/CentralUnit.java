package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.Officer;
import komplexaufgabe.core.entities.OldIDCard;
import komplexaufgabe.core.entities.Record;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;
import komplexaufgabe.core.interfaces.encryptionhash.IEncryptionHash;
import komplexaufgabe.core.interfaces.encryptionhash.SHA256;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.io.IFileParser;
import komplexaufgabe.io.IFileWriter;
import komplexaufgabe.io.TextFileWriter;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;
import java.util.stream.Collectors;

public class CentralUnit {
    private final TreeMap<Integer, Officer> registeredOfficer;
    private final ArrayList<Record> fineRecords;
    private final IFileWriter fileWriter;
    private final String logPath = "./src/main/java/export/report.log";
    private final String exportPath = "./src/main/java/export/export.csv";
    private final IEncryption aes = new AES();
    private final String exportPathEncrypted = "./src/main/java/export/export.enc";

    public CentralUnit() {
        registeredOfficer = new TreeMap<>();
        fineRecords = new ArrayList<>();
        Officer off1 = new Officer.OfficerBuilder("Joe", new Date(System.currentTimeMillis()), "FACEAFACEAFACEA", 0, new OldIDCard()).build();
        off1.getIdCard().store(123);
        registeredOfficer.put(0, off1);

        Officer off2 = new Officer.OfficerBuilder("Peter", new Date(System.currentTimeMillis()), "FACEBFACEBFACEB", 1, new OldIDCard()).build();
        off2.getIdCard().store(234);
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

    public void createReportLog(MobileNetworkModule mobileNetworkModule) {
        String content = "";

        List<Car> finedCars = new ArrayList<>();
        for (Record rec : fineRecords) {
            finedCars.add(mobileNetworkModule.vraGetCar(rec.getLicensePlate().getLicensePlateID()));
        }
        content = content + "Counted fined cars by manufacturer:\n--------------------------------\n";

        content = content + finedCars.stream().collect(Collectors.groupingBy(Car::getManufacturer, Collectors.counting())) + "\n";
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
