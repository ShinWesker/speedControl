package komplexaufgabe.core;

import komplexaufgabe.entities.car.Car;
import komplexaufgabe.interfaces.stoppingtools.IStoppingTools;
import komplexaufgabe.core.components.Camera;
import komplexaufgabe.core.components.CentralUnit;
import komplexaufgabe.core.components.LED;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.components.engines.FineEngine;

import java.util.Date;
import java.util.Stack;
import java.util.UUID;

public class SpeedCamera {
    private final UUID serialNumber;
    private final Date manufacturingDate;
    private Camera camera;
    private IStoppingTools stoppingTool;
    private  CentralUnit centralUnit;
    private LED led;
    private LaserScanner laserScanner;
    private FineEngine fineEngine;
    private boolean isShutDown = true;

    private SpeedCamera(CameraBuilder cameraBuilder) {

        if (cameraBuilder.bSections.peek() instanceof CentralUnit) {
            centralUnit = (CentralUnit) cameraBuilder.bSections.peek();
            cameraBuilder.bSections.pop();
        }

        if (cameraBuilder.bSections.peek() instanceof LaserScanner) {
            laserScanner = (LaserScanner) cameraBuilder.bSections.peek();
            cameraBuilder.bSections.pop();
        }

        if (cameraBuilder.bSections.peek() instanceof LED) {
            led = (LED) cameraBuilder.bSections.peek();
            cameraBuilder.bSections.pop();
        }

        serialNumber = cameraBuilder.bSerialNumber;
        manufacturingDate = cameraBuilder.bManufacturingDate;
        camera = cameraBuilder.bCamera;
        stoppingTool = cameraBuilder.bStoppingTool;

    }

    public void activate() {
        this.isShutDown = false;
    }

    public void deactivate() {
        this.isShutDown = true;
    }

    public void controlCar(Car car) {

    }

    public void setFineEngine(FineEngine fineEngine) {
        this.fineEngine = fineEngine;
    }

    public boolean isShutDown() {
        return isShutDown;
    }

    public Camera getCamera() {
        return camera;
    }

    public IStoppingTools getStoppingTool() {
        return stoppingTool;
    }

    public CentralUnit getCentralUnit() {
        return centralUnit;
    }

    public LED getLed() {
        return led;
    }

    public LaserScanner getLaserScanner() {
        return laserScanner;
    }

    public FineEngine getFineEngine() {
        return fineEngine;
    }

    public static class CameraBuilder{
        private final Stack<Object> bSections;
        private final UUID bSerialNumber;
        private final Date bManufacturingDate;
        private final Camera bCamera;
        private final IStoppingTools bStoppingTool;

        public CameraBuilder(Stack<Object> pSections, UUID pSerialNumber, Date pManufacturingDate,
                             Camera pCamera, IStoppingTools pStoppingTools) {
            bSections = pSections;
            bSerialNumber = pSerialNumber;
            bManufacturingDate = pManufacturingDate;
            bCamera = pCamera;
            bStoppingTool = pStoppingTools;

        }
        public SpeedCamera build() {
            return new SpeedCamera(this);
        }
    }


}



