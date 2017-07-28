import java.io.*;
import java.net.Socket;

public class Scrabble {
    private final int SERVERPORT = 2468;
    private final String SERVERIP = "127.0.0.1";
    private static Scrabble instance;
    public static void main(String[] args) throws IOException{
        instance = new Scrabble();
        Socket socket = new Socket(instance.getServerIP(), instance.getPort());
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        MessageReceiver receiver = new MessageReceiver(input, output, new UserInputGetter(new BufferedReader(new InputStreamReader(System.in))));
        receiver.start();
    }
    public int getPort(){
        return SERVERPORT;
    }
    public String getServerIP(){
        return SERVERIP;
    }
}