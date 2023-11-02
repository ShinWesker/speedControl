package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.interfaces.components.ICamera;

public class Camera implements ICamera {

    public CameraData takePhoto(Car car){

        String photo =
                "+--------------+\n" +
                "|        "+car.getDriver().getFace().substring(0,5)+" |\n" +
                "|        "+car.getDriver().getFace().substring(5,10)+" |\n" +
                "|        "+car.getDriver().getFace().substring(10,15)+" |\n" +
                "|              |\n" +
                "+--------------+\n" +
                "+---["+ car.getLicensePlate().getLicensePlateID() +"]---+\n" +
                "+--------------+";


        return new CameraData(photo, System.currentTimeMillis());
    }
}
