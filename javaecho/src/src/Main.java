import java.io.IOException;

public class Main {

    public static void main(String[] args) {
         networkingLayer connection = new TCPEchoClient(args);  // uncomment this if you want to use TCP Connection
      // networkingLayer connection = new UDPEchoClient(args);  // uncomment this if you want to use UDP Connection
        try {
            connection.ClientTransmit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}