import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;

public class Producer {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Started connection on port 6666");
    }

    public String sendMessage() throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        String resp;
        Random random = new Random();
        int rand;
        System.out.println("Type \"exit\" to stop, \"mult\" to send random multiple messages all at once, or just type a single message and press enter to send.");
        while (true){
            System.out.println("Type a message to send to consumer.");
            String usrInput = input.nextLine();
            if (usrInput.equals("exit")){
                out.println(usrInput);
                System.out.println("Good bye");
                stopConnection();
                break;
            }else if(usrInput.equals("mult")){
                for(int i=0;i<6;i++){
                    rand = random.nextInt(10);
                    System.out.println(rand);
                    out.println(rand);
                }
                //out.println(usrInput);
            }else {
                out.println(usrInput);
                resp = in.readLine();
                if(resp.equals("Consumer is full")){
                    System.out.print("Producer stopped. Please wait until consumer is cleared.");
                    Thread.sleep(2000);
                    System.out.println("Type a message to send to consumer or exit to stop.");
                }
            }
        }
        stopConnection();
        return null;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Producer client = new Producer();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage();
        //System.out.println(response);
    }

}
