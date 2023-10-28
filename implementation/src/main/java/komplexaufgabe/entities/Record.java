package komplexaufgabe.entities;

import komplexaufgabe.entities.car.LicensePlate;

import java.util.Date;

public class Record {
    private final int sequenzID;
    private final Date timestamp;
    private final String picture;
    private final LicensePlate licensePlate;
    private final String name;
    private final Date birthDate;
    private final int phoneNumber;
    private final double allowedSpeed;
    private final double measuredSpeed;
    private final double measuredSpeedAfterDeductingTolerance;

    public Record(int sequenzID, Date timestamp, String picture, LicensePlate licensePlate, String name, Date birthDate, int phoneNumber, double allowedSpeed, double measuredSpeed, double measuredSpeedAfterDeductingTolerance) {
        this.sequenzID = sequenzID;
        this.timestamp = timestamp;
        this.picture = picture;
        this.licensePlate = licensePlate;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.allowedSpeed = allowedSpeed;
        this.measuredSpeed = measuredSpeed;
        this.measuredSpeedAfterDeductingTolerance = measuredSpeedAfterDeductingTolerance;
    }

    @Override
    public String toString() {
        return "Record{" +
                "sequenzID=" + sequenzID +
                ", timestamp=" + timestamp +
                ", picture='" + picture + '\'' +
                ", licensePlate=" + licensePlate +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber=" + phoneNumber +
                ", allowedSpeed=" + allowedSpeed +
                ", measuredSpeed=" + measuredSpeed +
                ", measuredSpeedAfterDeductingTolerance=" + measuredSpeedAfterDeductingTolerance +
                '}';
    }
}
