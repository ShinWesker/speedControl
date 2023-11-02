package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.entities.LicensePlate;
import komplexaufgabe.core.entities.Record;
import komplexaufgabe.core.interfaces.components.IAIEngine;
import komplexaufgabe.core.interfaces.components.IFineEngine;
import komplexaufgabe.core.interfaces.policy.IPolicy;
import komplexaufgabe.core.SpeedCamera;

import java.util.Date;

public class FineEngine implements IFineEngine {
    private IPolicy policy;
    private final SpeedCamera speedCamera;
    private final IAIEngine aiEngine;

    private int recordId = 1;

    public FineEngine(SpeedCamera speedCamera) {
        this.speedCamera = speedCamera;
        aiEngine = new AIEngine();
    }

    public void setPolicy(IPolicy policy) {
        this.policy = policy;
        System.out.println("Policy was set!");
    }

    public boolean isSpeeding(int speed) {
        return speed > 53;
    }

    private String[] processPhoto(CameraData cameraData) {
        return aiEngine.extractData(cameraData);
    }

    private boolean contactPolice(String face) {
        return speedCamera.getMobileNetworkModule().sendRequestToPolice(face);
    }

    private String[] identifyDriver(String licensePlate) {
        return speedCamera.getMobileNetworkModule().sendRequestToVRA(licensePlate);
    }

    private void fineWallet(long phoneNumber, double fine) {
        MobileCentralUnit.fineSmartphoneWallet(phoneNumber, fine);
    }

    public String processCase(CameraData cameraData, int carSpeed) {
        String[] carData = processPhoto(cameraData);
        boolean isWanted = contactPolice(carData[0]);
        String[] ownerData = identifyDriver(carData[1]);

        double penalty = policy.getFine(carSpeed);
        String manufacturer = speedCamera.getMobileNetworkModule().vraGetCar(carData[1]).getManufacturer();
        Record record = new Record(recordId++, System.nanoTime(), new Date(System.currentTimeMillis()), cameraData.getCameraPhoto(), new LicensePlate(carData[1]), manufacturer, ownerData[0], new Date(Long.parseLong(ownerData[1])), Long.parseLong(ownerData[2]), 50, carSpeed, carSpeed - 3, penalty);
        speedCamera.getCentralUnit().addRecord(record);

        fineWallet(Long.parseLong(ownerData[2]), penalty);
        if (isWanted) return carData[0];
        return "";
    }
}


