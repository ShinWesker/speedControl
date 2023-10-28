package komplexaufgabe.entities.human.officer.idcard.magneticstrip;

public class NextGenMagneticStrip extends MagneticStrip {
    private String fingerPrint;

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        System.out.println(fingerPrint);
        this.fingerPrint = fingerPrint;
    }
}
