package komplexaufgabe.core.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    private final int sequenzID;
    private final long timestamp;
    private final Date time;
    private final String picture;
    private final LicensePlate licensePlate;
    private final String name;
    private final Date birthDate;
    private final long phoneNumber;
    private final int allowedSpeed;
    private final int measuredSpeed;
    private final int measuredSpeedAfterDeductingTolerance;
    private  final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");


    private final double penalty;

    public Record(int sequenzID,long timestamp, Date time, String picture, LicensePlate licensePlate, String name, Date birthDate, long phoneNumber, int allowedSpeed, int measuredSpeed, int measuredSpeedAfterDeductingTolerance, double penalty) {
        this.sequenzID = sequenzID;
        this.timestamp = timestamp;
        this.time = time;
        this.picture = picture;
        this.licensePlate = licensePlate;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.allowedSpeed = allowedSpeed;
        this.measuredSpeed = measuredSpeed;
        this.measuredSpeedAfterDeductingTolerance = measuredSpeedAfterDeductingTolerance;
        this.penalty = penalty;
    }

    public int getSequenzID() {
        return sequenzID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Date getTime() {
        return time;
    }

    public String getPicture() {
        return picture;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public int getAllowedSpeed() {
        return allowedSpeed;
    }

    public int getMeasuredSpeed() {
        return measuredSpeed;
    }

    public int getMeasuredSpeedAfterDeductingTolerance() {
        return measuredSpeedAfterDeductingTolerance;
    }

    public double getPenalty() {
        return penalty;
    }

    @Override
    public String toString() {

        return "\n Record{\n" +
                "sequenzID=" + sequenzID +  ",\n" +
                "timestamp=" + timestamp +",\n" +
                "time=" + sdf.format(time) +",\n" +
                "picture=\n" + picture  +",\n" +
                "licensePlate=" + licensePlate.getLicensePlateID() +",\n" +
                "name='" + name + '\'' +",\n" +
                "birthDate=" + birthDate +",\n" +
                "phoneNumber=" + phoneNumber +",\n" +
                "allowedSpeed=" + allowedSpeed +",\n" +
                "measuredSpeed=" + measuredSpeed +",\n" +
                "measuredSpeedAfterDeductingTolerance=" + measuredSpeedAfterDeductingTolerance +",\n" +
                "penalty=" + penalty +"\n" +
                '}';
    }

    public String toCSV(){
        return "sequenzID=" + sequenzID +  ";" +
                "timestamp=" + timestamp +";" +
                "time=" + sdf.format(time) +";" +
                "picture=" + picture.replaceAll("\n","")  +";" +
                "licensePlate=" + licensePlate.getLicensePlateID() +";" +
                "name=" + name +";" +
                "birthDate=" + birthDate +";" +
                "phoneNumber=" + phoneNumber +";" +
                "allowedSpeed=" + allowedSpeed +";" +
                "measuredSpeed=" + measuredSpeed +";" +
                "measuredSpeedAfterDeductingTolerance=" + measuredSpeedAfterDeductingTolerance +";" +
                "penalty=" + penalty +"\n";
    }
}
