import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Experiment04_TcpClientServer {

    public static final int DEFAULT_PORT = 5000;
    public static final String DEFAULT_HOST = "localhost";

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
            System.out.println("   EXPERIMENT 04: TCP CLIENT-SERVER APPLICATION");
            System.out.println("==================================================");
            System.out.println("Choose role to run:");
            System.out.println("  1. Start TCP Server (Listens on port " + DEFAULT_PORT + ")");
            System.out.println("  2. Start TCP Client (Connects to localhost:" + DEFAULT_PORT + ")");
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
        System.out.println("\n[SERVER] Starting TCP Server on port " + DEFAULT_PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            System.out.println("[SERVER] Server is listening. Awaiting client connection...");

            try (Socket socket = serverSocket.accept()) {
                System.out.println("[SERVER] Client connected: " + socket.getRemoteSocketAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

                out.println("Connected to TCP Server! Type messages, or 'bye' to disconnect.");

                String clientMsg;
                while ((clientMsg = in.readLine()) != null) {
                    System.out.println("[CLIENT]: " + clientMsg);

                    if (clientMsg.equalsIgnoreCase("bye") || clientMsg.equalsIgnoreCase("exit")) {
                        out.println("Goodbye from Server!");
                        System.out.println("[SERVER] Client disconnected.");
                        break;
                    }

                    System.out.print("[SERVER ENTER REPLY]: ");
                    String serverReply = console.readLine();
                    if (serverReply == null || serverReply.trim().isEmpty()) {
                        serverReply = "ACK: " + clientMsg.toUpperCase();
                    }
                    out.println(serverReply);

                    if (serverReply.equalsIgnoreCase("bye") || serverReply.equalsIgnoreCase("exit")) {
                        System.out.println("[SERVER] Server initiated shutdown.");
                        break;
                    }
                }
            }
            System.out.println("[SERVER] Session closed.");

        } catch (IOException e) {
            System.err.println("[SERVER ERROR] " + e.getMessage());
        }
    }

    public static void runClient() {
        System.out.println("\n[CLIENT] Connecting to TCP Server at " + DEFAULT_HOST + ":" + DEFAULT_PORT + "...");

        try (Socket socket = new Socket(DEFAULT_HOST, DEFAULT_PORT)) {
            System.out.println("[CLIENT] Successfully connected to Server!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            String welcomeMsg = in.readLine();
            System.out.println("[SERVER]: " + welcomeMsg);

            while (true) {
                System.out.print("[CLIENT ENTER MESSAGE]: ");
                String userInput = console.readLine();

                if (userInput == null) break;

                out.println(userInput);

                if (userInput.equalsIgnoreCase("bye") || userInput.equalsIgnoreCase("exit")) {
                    System.out.println("[CLIENT] Disconnecting...");
                    break;
                }

                String response = in.readLine();
                if (response == null) {
                    System.out.println("[CLIENT] Server closed connection.");
                    break;
                }
                System.out.println("[SERVER]: " + response);

                if (response.equalsIgnoreCase("bye") || response.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            System.out.println("[CLIENT] Connection closed cleanly.");

        } catch (UnknownHostException e) {
            System.err.println("[CLIENT ERROR] Unknown host: " + DEFAULT_HOST);
        } catch (IOException e) {
            System.err.println("[CLIENT ERROR] Could not connect to Server: " + e.getMessage());
            System.err.println("Ensure the Server is started first!");
        }
    }
}
