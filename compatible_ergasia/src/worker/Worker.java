package worker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  The Worker class represents a calculation unit that
 *  receives a chunk of a route and calculates
 * the distance, duration and elevation gain of the chunk.
 * The Worker then sends the intermediate result back to the master.
 * Each chunk is processed in a separate thread, so that the worker
 * can process multiple chunks in parallel.
 * The Worker is initializing a ServerSocket on a given port, and waits
 * for a calculation request from the master. When the Worker receives
 * a request, it creates a new ChunkProcessor thread to process the chunk.
 */
public class Worker {
    private final ServerSocket socket;

    public Worker(int port) throws Exception {
        // Initialize the ServerSocket on the given port.
        this.socket = new ServerSocket(port);
        System.out.println("Worker-> Initialized on port " + port);
    }

    public void startWorker() {
        while (true) {
            try {
                // Wait for a new request from the master, and accept the connection.
                Socket serverConnection = socket.accept();
                // Assign a new thread to process the chunk.
                new ChunkProcessor(serverConnection).start();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Worker(12345).startWorker();
    }
}