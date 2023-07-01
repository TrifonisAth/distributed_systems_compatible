package client;

import com.example.tracker.model.Bundle;
import com.example.tracker.model.Route;
import com.example.tracker.model.Result;
import com.example.tracker.model.Statistics;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientServerIOThread extends Thread {
    private final Client client;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final List<Route> files = new ArrayList<>();

    public ClientServerIOThread(Client client) throws IOException {
        this.client = client;
        boolean connected = false;
        while (!connected) {
            try {
                socket = new Socket(client.getServerIp(), client.getServerPort());
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                connected = true;
            } catch (IOException ignored) {
            }
        }
    }

    private void upload() throws IOException, ClassNotFoundException {
        File file;
        byte[] buffer;
        BufferedInputStream stream;
        for (String filename : client.getFilenames()) {
            file = new File(filename);
            stream = new BufferedInputStream(new FileInputStream(file));
            buffer = new byte[(int) file.length()];
            stream.read(buffer, 0, buffer.length);
            files.add(new Route(filename, buffer));
        }
        Bundle bundle = new Bundle(files, client.getUsername());
        out.writeObject(bundle);
        out.flush();
        Result result;
        for (int i = 0; i < files.size(); i++) {
            result = (Result) in.readObject();
            System.out.println(result);
            client.store(new Result(result));
        }
        Statistics statistics = (Statistics) in.readObject();
        client.setStatistics(new Statistics(statistics));
        System.out.println(statistics);
        close();
    }

    private void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            upload();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
