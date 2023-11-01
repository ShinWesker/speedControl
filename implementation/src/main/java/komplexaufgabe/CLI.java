package komplexaufgabe;

import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.CentralUnit;
import komplexaufgabe.core.components.LED;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;


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
    private boolean loadedPolicy = false;

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
            case 0 -> System.out.println("""
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

            case 1 -> startup();

            case 2 -> {
                if (speedCamera.isShutDown()) {
                    System.out.println("Turn on speed camera first!");
                } else {
                    policyImport();
                }
            }
            case 3 -> {
                if (speedCamera.isShutDown()) {
                    System.out.println("Turn on speed camera first!");
                } else {
                    if (loadedPolicy) {
                        executeSimulation();
                    } else {
                        System.out.println("Please load policy first!");
                    }
                }
            }
            case 4 -> createReportLog();

            case 5 -> export();

            case 6 -> {
                if (speedCamera.isShutDown()) {
                    System.out.println("Speed camera is already turned off.");
                } else {
                    speedCamera.deactivate();
                    System.out.println("Deactivated speed camera.");
                }
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
        if (!speedCamera.isShutDown()) {
            System.out.println("Speed camera already running.");
        } else {
            speedCamera.activate();
            System.out.println("Camera started!");
        }
    }

    private void policyImport() {
        System.out.println("Enter officer Id:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());

        } catch (Exception e) {
            System.out.println("Please type in a number!");
            System.out.println("Closing login...");
            return;
        }
        System.out.println("Enter officer password:");
        int password;
        try {
            password = Integer.parseInt(scanner.nextLine());

        } catch (Exception e) {
            System.out.println("Please type in a number!");
            System.out.println("Closing login...");
            return;
        }

        if (speedCamera.getCentralUnit().validateOfficer(id, password)) {

            System.out.println("Do you want to import \"resources/fine_catalogue.json\" ? (y/n)");
            String in = scanner.nextLine();
            if (in.equals("y")) {
                speedCamera.getFineEngine().setPolicy(new GermanPolicy("./implementation/src/main/java/resources/fine_catalogue.json"));
                loadedPolicy = true;
            } else {
                switchState("");
                showMenu();
            }

        } else {
            System.out.println("credentials incorrect!");
            switchState("");
            showMenu();
        }

    }

    private void executeSimulation() {
        speedCamera.getSimulation().start();
    }


    private void createReportLog() {
        speedCamera.createReportLog();
        System.out.println("Created report log!");
    }

    private void export() {
        speedCamera.export();
        System.out.println("Exported data to csv!");
    }

}
