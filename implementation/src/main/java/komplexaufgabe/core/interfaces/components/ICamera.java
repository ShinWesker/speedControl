package komplexaufgabe.core.interfaces.components;

import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.entities.Car;

public interface ICamera {
    CameraData takePhoto(Car car);
}
