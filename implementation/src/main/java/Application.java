import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.Camera;
import komplexaufgabe.core.components.CentralUnit;
import komplexaufgabe.core.components.LED;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.components.engines.FineEngine;
import komplexaufgabe.entities.car.Car;
import komplexaufgabe.entities.car.LicensePlate;
import komplexaufgabe.entities.human.Owner;
import komplexaufgabe.entities.human.officer.idcard.NextGenIDCard;
import komplexaufgabe.entities.human.officer.idcard.OldIDCard;
import komplexaufgabe.entities.smartphone.SmartPhone;
import komplexaufgabe.interfaces.encryptionhash.MD5;
import komplexaufgabe.interfaces.encryptionhash.SHA256;
import komplexaufgabe.core.components.mobile.MobileCentralUnit;
import komplexaufgabe.interfaces.policy.GermanPolicy;
import komplexaufgabe.interfaces.stoppingtools.TrafficSpikes;

import java.util.Date;
import java.util.Stack;
import java.util.UUID;

public class Application {
    public static void main(String... args) {
        Car carHenry = new Car.CarBuilder(
                "BMW",
                "KJEHDKJHE",
                45.34,
                new LicensePlate("EPOIJED")
        ).build();

        Owner henry = new Owner.OwnerBuilder(
                "Hans Jürgen", new Date(), "DEFLKJHEIUHFEFUH",
                new SmartPhone(324092834),
                carHenry
        ).build();


        System.out.println("Car Parameter:");
        System.out.println(carHenry.getManufacturer());

        System.out.println("Owner Parameter:");
        System.out.println(henry.getName());


        System.out.println("Encryption Test:");
        SHA256 tool = new SHA256();
        System.out.println("SHA: " + tool.encrypt("Hallo"));
        MD5 toolMd5 = new MD5();
        System.out.println("MD5: " + toolMd5.encrypt("Hallo"));


        System.out.println("IDCard Test:");
        NextGenIDCard nextGen = new NextGenIDCard();
        OldIDCard oldGen = new OldIDCard();
        oldGen.savePIN(2388);
        nextGen.savePIN(2388);
        nextGen.saveFingerPrint("Finga");

        // HashMap
        MobileCentralUnit.addOwner(1, new SmartPhone(23498));

        //Camera test der Hölle

        Stack<Object> componentsStack = new Stack<>();

        LED led = new LED();
        CentralUnit centralUnit = new CentralUnit();
        LaserScanner laserScanner = new LaserScanner();

        // Push the objects into the stack
        componentsStack.push(led);
        componentsStack.push(laserScanner);
        componentsStack.push(centralUnit);

        SpeedCamera speedCamera = new SpeedCamera.CameraBuilder(
                componentsStack,
                new UUID(4,1),
                new Date(),new Camera(),
                new TrafficSpikes()).build();

        speedCamera.activate();
        speedCamera.getCamera().takePhoto(carHenry);
        speedCamera.getLaserScanner().detectSpeed(carHenry);
        speedCamera.setFineEngine(new FineEngine.FineEngineBuilder(new GermanPolicy()).build());
    }
}