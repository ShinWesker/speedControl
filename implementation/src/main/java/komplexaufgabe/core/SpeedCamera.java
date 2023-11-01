package komplexaufgabe.core;

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
    private final Camera camera;
    private final IStoppingTools stoppingTool;
    private final CentralUnit centralUnit;
    private final LED led;
    private final LaserScanner laserScanner;
    private final FineEngine fineEngine;
    private boolean isShutDown = true;

    private final MobileNetworkModule mobileNetworkModule;

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


        mobileNetworkModule = cameraBuilder.bMobileNetworkmodule;


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
            }

        }


    }

    public boolean isShutDown() {
        return isShutDown;
    }

    public CentralUnit getCentralUnit() {
        return centralUnit;
    }


    public FineEngine getFineEngine() {
        return fineEngine;
    }

    public static class CameraBuilder {
        private final Stack<Object> bSections;
        private final IStoppingTools bStoppingTool;
        private final MobileNetworkModule bMobileNetworkmodule;

        public CameraBuilder(Stack<Object> pSections, IStoppingTools pStoppingTools, MobileNetworkModule pMobileNetworkModule) {
            bSections = pSections;
            bStoppingTool = pStoppingTools;
            bMobileNetworkmodule = pMobileNetworkModule;
        }

        public SpeedCamera build() {
            return new SpeedCamera(this);
        }
    }

    public MobileNetworkModule getMobileNetworkModule() {
        return mobileNetworkModule;
    }
}



