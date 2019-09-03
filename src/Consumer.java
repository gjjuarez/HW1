import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Consumer {
    
    public static void main(String[] args) throws IOException {
        Consumer server=new Consumer();
        server.start(6666);
    }

    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        System.out.println("Listening on port 6666");
        serverSocket = new ServerSocket(port);
        while (true)
            new clientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class clientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public clientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("exit")) {
                        System.out.println("Good Bye");
                        //out.println("bye");
                        break;
                    }
                    System.out.println(inputLine);
                    //out.println(inputLine);
                }
                in.close();
                out.close();
                clientSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
