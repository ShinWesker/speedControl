package komplexaufgabe.core;

import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.interfaces.stoppingtools.IStoppingTools;
import komplexaufgabe.core.components.Camera;
import komplexaufgabe.core.components.CentralUnit;
import komplexaufgabe.core.components.LED;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.components.FineEngine;

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
        centralUnit = (CentralUnit) cameraBuilder.bSections.peek();
        cameraBuilder.bSections.pop();

        laserScanner = (LaserScanner) cameraBuilder.bSections.peek();
        cameraBuilder.bSections.pop();

        led = (LED) cameraBuilder.bSections.peek();
        cameraBuilder.bSections.pop();

        serialNumber = UUID.randomUUID();
        manufacturingDate = new Date();
        camera = new Camera();
        fineEngine = new FineEngine();
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
        private final IStoppingTools bStoppingTool;

        public CameraBuilder(Stack<Object> pSections, IStoppingTools pStoppingTools) {
            bSections = pSections;
            bStoppingTool = pStoppingTools;
        }
        public SpeedCamera build() {
            return new SpeedCamera(this);
        }
    }


}



