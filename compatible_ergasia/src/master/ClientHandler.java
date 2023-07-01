package master;

import com.example.tracker.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The clientHandler thread is responsible for the communication
 * between the server and the client.
 */
public class ClientHandler extends Thread {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private int filesIndex = 0;
    private String username;
    private static int chunkSize;
    private static int workerIndex = 0;
    private final List<List<IntermediateResult>> intermediateResultsList = new ArrayList<>();

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
    }

    public synchronized static int nextWorkerIndex() {
        if (workerIndex == Master.getWorkerAddresses().size()) {
            workerIndex = 0;
        }
        return workerIndex++;
    }

    public static void setChunkSize(int chunkSize) {
        ClientHandler.chunkSize = chunkSize;
    }

    private void receive() throws IOException, ClassNotFoundException {
        // The bundle contains the username and the list of routes.
        Bundle bundle = (Bundle) in.readObject();
        username = bundle.getUsername();
        Master.getUsersData().checkUser(username);
        for (Route file : bundle.getRoutes()) {
            assign(file);
            filesIndex++;
        }
    }

    private void send() throws InterruptedException, IOException {
        while (filesIndex > 0){
            Result res = null;
            synchronized (intermediateResultsList) {
                if (intermediateResultsList.size() == 0)
                    intermediateResultsList.wait();
                res = reduce(intermediateResultsList.remove(0));
            }
            out.writeObject(res);
            out.flush();
            filesIndex--;
        }
        // Get the user's statistics.
        Statistics statistics = Master.getUsersData().getStats(Master.getUsersData().getUser(username));
        out.writeObject(statistics);
        out.flush();
        in.close();
        out.close();
        socket.close();
    }

    private Result reduce(List<IntermediateResult> ls) {
        String name = ls.get(0).getFilename();
        double distance = 0;
        double avgSpeed = 0;
        double duration = 0;
        double elevationGain = 0;
        for (IntermediateResult r : ls) {
            elevationGain += r.getElevationGain();
            duration += r.getDuration();
            distance += r.getDistance();
        }
        if (duration != 0)
            avgSpeed = distance / (duration / 3600);
        Result res = new Result(username, name, distance, duration, elevationGain, avgSpeed);
        Master.getUsersData().addRoute(res);
        return res;
    }

    private void assign(Route file) {
        new FileProcessor(file, intermediateResultsList).start();
    }

    public static int getChunkSize() {
        return chunkSize;
    }

    @Override
    public void run() {
        try {
            receive();
            send();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
