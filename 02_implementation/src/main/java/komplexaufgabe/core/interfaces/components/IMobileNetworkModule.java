package komplexaufgabe.core.interfaces.components;

import komplexaufgabe.core.entities.Car;

public interface IMobileNetworkModule {

    boolean sendRequestToPolice(String data);

    String[] sendRequestToVRA(String licensePlate);

    void requestArrest(String face);

    void requestCarConfiscation(Car car);

    Car vraGetCar(String licensePlate);
}
