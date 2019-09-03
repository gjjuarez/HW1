import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Consumer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) throws IOException {
        Consumer server=new Consumer();
        server.start(6666);
        server.stop();
    }

    public void start(int port) throws IOException {
        System.out.println("Listening on port 6666");
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null){
            if (inputLine.equals("exit")){
                out.println("good bye");
                System.out.println("Good bye");
                stop();
                break;
            }
            System.out.println(inputLine);
            out.println(inputLine);
        }

    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}
