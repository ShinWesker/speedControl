package komplexaufgabe.core.components;

import komplexaufgabe.core.interfaces.components.ILED;

public class LED implements ILED {
    private final String color = "red";

    public void flash() {
        System.out.println("Driver was flashed by " + color + " flashlight");
    }
}
