import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

interface CalculatorService extends Remote {
    double addition(double a, double b) throws RemoteException;
    double subtraction(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
}

class CalculatorServiceImpl extends UnicastRemoteObject implements CalculatorService {
    private static final long serialVersionUID = 1L;

    public CalculatorServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double addition(double a, double b) throws RemoteException {
        System.out.printf("[RMI SERVER EXECUTING] addition(%.2f, %.2f)\n", a, b);
        return a + b;
    }

    @Override
    public double subtraction(double a, double b) throws RemoteException {
        System.out.printf("[RMI SERVER EXECUTING] subtraction(%.2f, %.2f)\n", a, b);
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        System.out.printf("[RMI SERVER EXECUTING] multiply(%.2f, %.2f)\n", a, b);
        return a * b;
    }
}

public class Experiment06_RmiCalculator {

    public static final int RMI_PORT = 1099;
    public static final String REGISTRY_NAME = "CalculatorService";

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("server")) {
                runServer();
                return;
            } else if (args[0].equalsIgnoreCase("client")) {
                runClient();
                return;
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("==================================================");
            System.out.println("   EXPERIMENT 06: RMI CALCULATOR (ADD, SUB, MUL)");
            System.out.println("==================================================");
            System.out.println("Choose role to run:");
            System.out.println("  1. Start RMI Server (Publishes remote service)");
            System.out.println("  2. Start RMI Client (Invokes remote methods)");
            System.out.print("Enter choice (1 or 2): ");

            String choice = br.readLine();
            if ("1".equals(choice)) {
                runServer();
            } else if ("2".equals(choice)) {
                runClient();
            } else {
                System.out.println("Invalid selection. Exiting.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runServer() {
        System.out.println("\n[RMI SERVER] Initializing server on port " + RMI_PORT + "...");
        try {
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(RMI_PORT);
                System.out.println("[RMI SERVER] RMI Registry created on port " + RMI_PORT);
            } catch (Exception ex) {
                registry = LocateRegistry.getRegistry(RMI_PORT);
                System.out.println("[RMI SERVER] Using existing RMI Registry on port " + RMI_PORT);
            }

            CalculatorService service = new CalculatorServiceImpl();
            registry.rebind(REGISTRY_NAME, service);

            System.out.println("[RMI SERVER] Service '" + REGISTRY_NAME + "' bound and ready.");
            System.out.println("[RMI SERVER] Awaiting remote client invocations...");

        } catch (Exception e) {
            System.err.println("[RMI SERVER ERROR] " + e.getMessage());
        }
    }

    public static void runClient() {
        System.out.println("\n[RMI CLIENT] Connecting to RMI Registry on localhost:" + RMI_PORT + "...");
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", RMI_PORT);
            CalculatorService service = (CalculatorService) registry.lookup(REGISTRY_NAME);
            System.out.println("[RMI CLIENT] Remote CalculatorService stub acquired successfully!\n");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("----------------------------------------------");
                System.out.println("Select Remote Operation:");
                System.out.println("  1. Addition    (a + b)");
                System.out.println("  2. Subtraction (a - b)");
                System.out.println("  3. Multiply    (a * b)");
                System.out.println("  4. Execute All Three Operations");
                System.out.println("  5. Exit Client");
                System.out.print("Enter choice (1-5): ");

                String choice = br.readLine().trim();
                if (choice.equals("5") || choice.equalsIgnoreCase("exit")) {
                    System.out.println("[RMI CLIENT] Exiting. Goodbye!");
                    break;
                }

                if (!choice.matches("[1-4]")) {
                    System.out.println("Invalid option. Choose 1 to 5.");
                    continue;
                }

                System.out.print("Enter number a: ");
                double a = Double.parseDouble(br.readLine().trim());
                System.out.print("Enter number b: ");
                double b = Double.parseDouble(br.readLine().trim());

                System.out.println("\n[INVOKING REMOTE METHOD VIA NETWORK...]");
                switch (choice) {
                    case "1":
                        double sum = service.addition(a, b);
                        System.out.printf(">>> REMOTE RESULT: %.2f + %.2f = %.2f\n", a, b, sum);
                        break;
                    case "2":
                        double diff = service.subtraction(a, b);
                        System.out.printf(">>> REMOTE RESULT: %.2f - %.2f = %.2f\n", a, b, diff);
                        break;
                    case "3":
                        double prod = service.multiply(a, b);
                        System.out.printf(">>> REMOTE RESULT: %.2f * %.2f = %.2f\n", a, b, prod);
                        break;
                    case "4":
                        System.out.println("=== COMPREHENSIVE REMOTE RESULTS ===");
                        System.out.printf("Addition       : %.2f + %.2f = %.2f\n", a, b, service.addition(a, b));
                        System.out.printf("Subtraction    : %.2f - %.2f = %.2f\n", a, b, service.subtraction(a, b));
                        System.out.printf("Multiplication : %.2f * %.2f = %.2f\n", a, b, service.multiply(a, b));
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("[RMI CLIENT ERROR] " + e.getMessage());
            System.err.println("Make sure RMI Server is started first!");
        }
    }
}
