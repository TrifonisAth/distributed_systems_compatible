package worker;

import com.example.tracker.model.Chunk;
import com.example.tracker.model.IntermediateResult;
import com.example.tracker.model.Waypoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


public class ChunkProcessor extends Thread {
    private final Socket socket;

    public ChunkProcessor(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
    }


    private void processChunk() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        Chunk chunk = (Chunk) in.readObject();
        // Radius of the earth in km.
        int R = 6371;
        List<Waypoint> ls = chunk.getWaypoints();
        double elevationGain = 0;
        double distance = 0;
        LocalDateTime end = ls.get(ls.size() - 1).getTime();
        LocalDateTime start = ls.get(0).getTime();
        // Duration in seconds between first and last waypoint.
        int duration = (int) (end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
        // Calculate the distance and elevationGain between each pair of waypoints and add it to the total.
        for (int i = 1; i < ls.size(); i++) {
            double latA = ls.get(i - 1).getLatitude();
            double latB = ls.get(i).getLatitude();
            double lonA = ls.get(i - 1).getLongitude();
            double lonB = ls.get(i).getLongitude();
            double latDistance = Math.toRadians(latB - latA);
            double lonDistance = Math.toRadians(lonB - lonA);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            // b is the angle in radians between the two points of a sphere.
            double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double eleB = ls.get(i).getElevation();
            double eleA = ls.get(i - 1).getElevation();
            // If eleB < eleA, then we don't add anything to elevationGain.
            elevationGain += Math.max(eleB - eleA, 0);
            // b is multiplied by the radius of the earth to get the distance in km.
            distance += R * b;
        }
        IntermediateResult res = new IntermediateResult(chunk.getFileName(), chunk.getIndex(), distance, duration, elevationGain);
        out.writeObject(res);
        out.flush();
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            processChunk();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
