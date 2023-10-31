package komplexaufgabe.core.components;

public class LED {
    private final String color = "red";

    public void flash() {
        System.out.println("Driver was flashed by " + color + " flashlight");
    }
}
