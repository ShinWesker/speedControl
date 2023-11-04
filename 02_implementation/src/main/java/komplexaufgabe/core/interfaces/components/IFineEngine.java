package komplexaufgabe.core.interfaces.components;

import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.interfaces.policy.IPolicy;


public interface IFineEngine {

    void setPolicy(IPolicy policy);

    boolean isSpeeding(int speed);

    String processCase(CameraData cameraData, int carSpeed);
}
