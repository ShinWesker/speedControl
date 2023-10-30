package komplexaufgabe.core.entities;

import java.util.Date;

public class CameraData {
    private final String cameraPhoto;
    private final long timeStamp;

    public CameraData(String cameraPhoto, long timeStamp) {
        this.cameraPhoto = cameraPhoto;

        this.timeStamp = timeStamp;
    }

    public String getCameraPhoto() {
        return String.valueOf(cameraPhoto);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return getCameraPhoto() + "\n" + new Date(timeStamp);
    }
}
