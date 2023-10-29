package komplexaufgabe.core.entities;

public class MagneticStrip {

    // TODO das hier muss ein String sein, wieso auch immer sobald wir ein MD5 als Integer converten sprengt es Gradle
    protected String pin;
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        System.out.println(pin);
        this.pin = pin;
    }
}
