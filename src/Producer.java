import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Producer {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Started connection on port 6666");
        System.out.println("Type a message to send to consumer or exit to stop.");
    }

    public String sendMessage() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true){
            String usrInput = input.nextLine();
            if (usrInput.equals("exit")){
                out.println(usrInput);
                System.out.println("Good bye");
                stopConnection();
                break;
            }
            out.println(usrInput);
            String resp = in.readLine();
        }
        stopConnection();
        return null;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Producer client = new Producer();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage();
        //System.out.println(response);
    }

}
