package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.interfaces.policy.IPolicy;
import komplexaufgabe.core.SpeedCamera;

public class FineEngine {
    private IPolicy policy;
    private SpeedCamera speedCamera;
    private final AIEngine aiEngine;

    public FineEngine(){

        aiEngine = new AIEngine();
    }
    public void setPolicy(IPolicy policy) {
        this.policy = policy;
    }

    public void setSpeedCamera(SpeedCamera speedCamera) {
        this.speedCamera = speedCamera;
    }

    public boolean isSpeeding(Double speed) {
        //TODO ermittlung der Strafe und die darauf folgenden Aktionen
        // return wert rpbly nutzen um Spikes auszulösen und blitzer auszulösen und so
        return true;
    }
    public void processPhoto(CameraData cameraData) {
        //TODO aufruf der AIEngine
    }

    private void contactPolice(String face) {
        //TODO aufruf über die speedCamera über das mobilenetwork module
    }

    private void identifyDriver(String licensePlate) {
        // TODO ermittelt das Auto durch die licensePlate über MobileNetowrkModule > VehicleRegistrationAuthoritys
    }

    private void fineWallet(Integer phoneNumber, Integer fine) {
        //TODO bestraft das Wallet des fahrers im Falle einer Strafte über das MobileNetowrkModule
    }
}


