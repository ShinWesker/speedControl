package komplexaufgabe.core.components.engines;

import komplexaufgabe.entities.CameraData;
import komplexaufgabe.interfaces.policy.IPolicy;
import komplexaufgabe.core.SpeedCamera;

public class FineEngine {
    private final IPolicy policy;
    private SpeedCamera speedCamera;
    private final AIEngine aiEngine;

    private FineEngine(FineEngineBuilder fineEngineBuilder){
        this.policy = fineEngineBuilder.bPolicy;
        this.aiEngine = fineEngineBuilder.bAIEngine;

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

    public static class FineEngineBuilder{
        private final IPolicy bPolicy;
         private final AIEngine bAIEngine;

        public FineEngineBuilder(IPolicy pPolicy, AIEngine pAIEngine) {
            bPolicy = pPolicy;
            bAIEngine = pAIEngine;
        }

        public FineEngine build() {
            return new FineEngine(this);
        }
    }
}


