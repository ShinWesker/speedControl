package komplexaufgabe.core;

import io.cucumber.java.ro.Si;
import komplexaufgabe.core.components.*;
import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.core.interfaces.stoppingtools.IStoppingTools;
import komplexaufgabe.simulate.Simulation;

import java.util.Date;
import java.util.Objects;
import java.util.Stack;
import java.util.UUID;

public class SpeedCamera {
    private final UUID serialNumber;
    private final Date manufacturingDate;
    private Camera camera;
    private IStoppingTools stoppingTool;
    private CentralUnit centralUnit;
    private LED led;
    private LaserScanner laserScanner;
    private FineEngine fineEngine;
    private boolean isShutDown = true;

    private Simulation simulation;

    private MobileNetworkModule mobileNetworkModule;

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
        fineEngine = new FineEngine(this);
        stoppingTool = cameraBuilder.bStoppingTool;


        mobileNetworkModule = new MobileNetworkModule();
        simulation = new Simulation(this);


    }

    public void addWanted(Owner owner) {
        mobileNetworkModule.addWanted(owner);
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void activate() {
        this.isShutDown = false;
    }

    public void deactivate() {
        this.isShutDown = true;
    }

    public void controlCar(Car car) {
        int carSpeed = laserScanner.detectSpeed(car);
        if (fineEngine.isSpeeding(carSpeed)) {
            led.flash();
            CameraData cameraData = camera.takePhoto(car);

            String wantedDriverFace = fineEngine.processCase(cameraData, carSpeed);


            if (!Objects.equals(wantedDriverFace, "")) {
            stoppingTool.action();
            mobileNetworkModule.requestArrest(wantedDriverFace);
            mobileNetworkModule.requestCarConfiscation(car);
            simulation.removeCar(car);
            }

        }


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

    public static class CameraBuilder {
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

    public MobileNetworkModule getMobileNetworkModule() {
        return mobileNetworkModule;
    }

    public void createReportLog(){
        centralUnit.createReportLog(mobileNetworkModule);
    }

    public void export(){
        centralUnit.export();
    }
}



