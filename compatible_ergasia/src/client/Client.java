package client;

import com.example.tracker.model.Result;
import com.example.tracker.model.Statistics;

import java.io.IOException;
import java.util.*;

/**
 * This class represents a Client which in our case is
 * an android device. The client is responsible for
 * sending the routes to the server and receiving the
 * results. The client is also responsible for storing
 * the results and the statistics of the user.
 */
public class Client {
    private Statistics statistics;
    private final String SERVER_IP;
    private final int SERVER_PORT;
    private final String username;
    private final List<String> filenames;
    private final Map<String, Result> results = new HashMap<>();

    public Client(String username, String address, int port, String[] filenames) {
        this.username = username;
        this.SERVER_IP = address;
        this.SERVER_PORT = port;
        this.filenames = new ArrayList<>();
        this.filenames.addAll(Arrays.asList(filenames));
    }

    /**
     * The ClientServerIOThread is calling this method to store
     * the result of a route inside the Client's main thread code.
     */
    public void store(Result result) {
        results.put(result.getFilename(), result);
    }

    /**
     * The ClientServerIOThread is calling this method to set
     * the statistics of the user inside the Client's main thread code.
     */
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    /**
     * This method is called by the Client's main thread code
     * to send the files to the server, by creating a new thread
     * which will handle the communication with the server.
     */
    private void sendFiles() throws IOException {
        new ClientServerIOThread(this).start();
    }

    public String getServerIp() {
        return SERVER_IP;
    }

    public Result getRouteResult(String filename) {
        return results.getOrDefault(filename, null);
    }

    public int getServerPort() {
        return SERVER_PORT;
    }

    public static void main(String[] args) throws IOException {
        /*
          Use the Master's IP address and port.
          Provide the paths of the routes to be sent.
         */
       Client client = new Client("user1", "127.0.0.1", 54321, new String[]{"src/client/routes/route1.gpx", "src/client/routes/route4.gpx", "src/client/routes/route6.gpx"});
       client.sendFiles();
    }
}
