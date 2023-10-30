package komplexaufgabe;

import io.cucumber.java.sl.In;
import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.CentralUnit;
import komplexaufgabe.core.components.LED;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;

import java.util.Scanner;
import java.util.Stack;

public class CLI {

    /* state:
    0    main Menu:
    1    startup  - Startup
    2    import   - Import
    3    execute  - Execute Simulation
    4    report   - Report
    5    export   - Export
    6    shutdown - Shutdown
    7    exit     - Exit
    */
    private int state = 0;
    private Scanner scanner;
    private SpeedCamera speedCamera;

    public void start() {
        Stack<Object> componentsStack = new Stack<>();

        LED led = new LED();
        CentralUnit centralUnit = new CentralUnit();
        LaserScanner laserScanner = new LaserScanner();

        // Push the objects into the stack
        componentsStack.push(led);
        componentsStack.push(laserScanner);
        componentsStack.push(centralUnit);


        speedCamera = new SpeedCamera.CameraBuilder(
                componentsStack,
                new TrafficSpikes()).build();
        scanner = new Scanner(System.in);
        showMenu();
    }

    public void showMenu() {
        switch (state) {
            case 0 -> {
                System.out.println("""
                        Menu:
                        --------
                        startup  - Startup
                        import   - Import
                        execute  - Execute Simulation
                        report   - Report
                        export   - Export
                        shutdown - Shutdown
                        exit     - Exit
                        --------""");
            }
            case 1 -> {
                startup();
            }
            case 2 -> {
                if (speedCamera.isShutDown()) {
                    System.out.println("Turn on speedcamera first!");
                } else {
                    policyImport();
                }
            }
            case 3 -> {
                executeSimulation();
            }
            case 4 -> {
                System.out.println("case4");
            }
            case 5 -> {
                System.out.println("case5");
            }
            case 6 -> {
                System.out.println("case6");
            }
            case 7 -> {
                scanner.close();
                System.exit(0);
            }


        }
        switchState(scanner.nextLine());

    }

    private void switchState(String s) {
        state = switch (s) {
            case "startup" -> 1;
            case "import" -> 2;
            case "execute" -> 3;
            case "report" -> 4;
            case "export" -> 5;
            case "shutdown" -> 6;
            case "exit" -> 7;
            default -> 0;
        };
        showMenu();
    }

    private void startup() {
        speedCamera.activate();
        System.out.println("Camera started!");
    }

    private void policyImport() {
        System.out.println("Enter officer Id:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter officer password:");
        int password = Integer.parseInt(scanner.nextLine());

        if ( speedCamera.getCentralUnit().validateOfficer(id, password)){

            System.out.println("Do you want to import \"resources/fine_catalogue.json\" ? (y/n)");
            String in = scanner.nextLine();
            if (in.equals("y")){
                speedCamera.getFineEngine().setPolicy(new GermanPolicy("./src/main/java/resources/fine_catalogue.json"));
            }
            else {
                switchState("");
                showMenu();
            }

        }else {
            System.out.println("credentials incorrect!");
            switchState("");
            showMenu();
        }

    }

    private void executeSimulation(){
        speedCamera.getSimulation().start();
    }


}
