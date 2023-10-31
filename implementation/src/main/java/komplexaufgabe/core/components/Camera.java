package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.entities.Car;

public class Camera {

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
