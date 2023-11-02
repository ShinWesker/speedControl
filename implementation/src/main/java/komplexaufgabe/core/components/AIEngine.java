package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.CameraData;

public class AIEngine {

    public String[] extractData(CameraData cameraData) {
        String face = cameraData.getCameraPhoto().substring(26, 31) + cameraData.getCameraPhoto().substring(43, 48) + cameraData.getCameraPhoto().substring(60, 65);
        String licensePlate = cameraData.getCameraPhoto().substring(107, 113);
        return new String[]{face, licensePlate};
    }
}
