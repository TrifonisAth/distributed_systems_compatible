package master;

import com.example.tracker.model.UsersData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Master {
    private static final UsersData usersData = new UsersData();
    private static List<String> workerAddresses;
    private final ServerSocket clientSocket;
    private static int workerPort;

    public Master(List<String> workerAddresses, int workerPort, int clientPort, int chunkSize) throws IOException {
        Master.workerAddresses = new ArrayList<>(workerAddresses);
        Master.workerPort = workerPort;
        ClientHandler.setChunkSize(chunkSize);
        this.clientSocket = new ServerSocket(clientPort);
        System.out.println("Master-> Initialized on port " + clientPort);
    }

    private void openServer() {
        while (true) {
            try {
                Socket clientConnection = clientSocket.accept();
//                testConnection(clientConnection);   // TODO: Remove after testing.

                // TODO: Uncomment after testing.
                new ClientHandler(clientConnection).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void testConnection(Socket connection) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
        int request = in.readInt();
        System.out.println("Master-> Received request: " + request);
        out.writeInt(404);
        out.flush();
    }

    public static int getWorkerPort() {
        return workerPort;
    }

    public static UsersData getUsersData() {
        return usersData;
    }

    public static List<String> getWorkerAddresses() {
        return workerAddresses;
    }

    public static void main(String[] args) throws IOException {
        // Create list of worker addresses.
        List<String> workerAddresses = new ArrayList<>();
        /*
            Use add method to add the IP addresses of the workers.
            For example:
            workerAddresses.add("192.168.2.11");
            workerAddresses.add("192.168.2.12");
         */
        workerAddresses.add("127.0.0.1");   // set according to Worker's IP.
        // Create master.
        Master master = new Master(workerAddresses, 12345, 54321, 12);
        // Open server.
        master.openServer();
    }
}
