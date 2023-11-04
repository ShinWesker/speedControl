package komplexaufgabe.core.entities;


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
