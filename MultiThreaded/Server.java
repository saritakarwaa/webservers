package MultiThreaded;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import java.io.IOException;
import java.io.PrintWriter;

public class Server {
    public Consumer<Socket> getConsumer(){
        return (clientSocket)->{
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from server");
                toClient.close();
                clientSocket.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        };
    }
    public static void main(String[] args) {
        Server server=new Server();
        int port=  8010;
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(100000000);
            System.out.println("Server started on port " + port);
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }    
}
