package komplexaufgabe.core.entities;

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
    private final double allowedSpeed;
    private final double measuredSpeed;
    private final double measuredSpeedAfterDeductingTolerance;

    private final double penalty;

    public Record(int sequenzID,long timestamp, Date time, String picture, LicensePlate licensePlate, String name, Date birthDate, long phoneNumber, double allowedSpeed, double measuredSpeed, double measuredSpeedAfterDeductingTolerance, double penalty) {
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

    @Override
    public String toString() {
        //TODO format time cause time.tostring is not right
        return "Record{" +
                "sequenzID=" + sequenzID +
                ", timestamp=" + timestamp +
                ", time=" + time.toString() +
                ", picture='" + picture + '\'' +
                ", licensePlate=" + licensePlate +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber=" + phoneNumber +
                ", allowedSpeed=" + allowedSpeed +
                ", measuredSpeed=" + measuredSpeed +
                ", measuredSpeedAfterDeductingTolerance=" + measuredSpeedAfterDeductingTolerance +
                ", penalty=" + penalty +
                '}';
    }
}
