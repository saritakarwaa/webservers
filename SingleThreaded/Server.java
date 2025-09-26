import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class Server {
    public void run() throws IOException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port); //  Create server socket once
        socket.setSoTimeout(10000);

        System.out.println("Server is listening on port " + port);

        while (true) {
            try {
                Socket acceptedSocket = socket.accept(); // Wait for a client
                System.out.println("Connection accepted from client " + acceptedSocket.getRemoteSocketAddress());

                PrintWriter toClient = new PrintWriter(acceptedSocket.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));

                // Send message to client
                toClient.println("Hello from server");

                // Example: read one line (optional)
                String line = fromClient.readLine();
                if (line != null) {
                    System.out.println("Client says: " + line);
                }

                // Close only client resources
                toClient.close();
                fromClient.close();
                acceptedSocket.close();

            } catch (SocketTimeoutException e) {
                System.out.println("Socket timed out");
            } catch (IOException e) {
                System.out.println("Error accepting connection");
                e.printStackTrace();
            }
        }
        // socket.close(); 
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (IOException e) {
            System.out.println("Error starting server");
            e.printStackTrace();
        }
    }
}
