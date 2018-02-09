/**
 * Created by zack on 2018-02-06.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class TCPEchoServer {

        final static int PORT = 50000;
        static int userNumber = 0;

        public static void main(String[] args) throws IOException {
            // Create server socket
            ServerSocket serverSocket = new ServerSocket(PORT); // the port match the one in the client class
            System.out.println("Waiting for the client connect...");
            // do this forever
            while (true) {
                // Wait for connection
                Socket socket = serverSocket.accept();

                // Once connected, create client thread
                RunTheThread serverThread = new RunTheThread(socket, ++userNumber, 1024);

                // execute the thread
                serverThread.start();
            }
        }
    }

    class RunTheThread extends Thread {

        private  int BUFSIZE ;
        private Socket socket;
        private InputStream in;
        private OutputStream out;
        private  int userNumber;
        private byte [] buf;
        public RunTheThread(Socket socket, int userNumber, int BUFSIZE) {

            this.socket = socket;
            this.userNumber = userNumber;    // this integer can be incremented to track the users number
            this.BUFSIZE=BUFSIZE;            // this filed so the we can change the buffer size from the main method

            }
        public int getUserNumber(){
            return userNumber;
        }
        @Override
        public void run() {
            System.out.println("            Server is ready to receive data");
            try {
                // create the input and the output streams to handle the messages
                in = this.socket.getInputStream();
                out = this.socket.getOutputStream();

                // create message string
                String receivedMessage = "";

               do {
                   buf = new byte[BUFSIZE];
                   in.read(buf); // read the message into the buffer
                   receivedMessage = new String(buf).trim();  // get the message into a string

                   if (!receivedMessage.isEmpty()) {
                       out.write(receivedMessage.getBytes()); // send the message
                       System.out.println("User with IP address " + socket.getInetAddress() + " Sent a message with a size " + receivedMessage.length() + " by the port " + socket.getPort());
                   }
               }while (!receivedMessage.isEmpty());  // repeat the progress until the messages is received

                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {

            }
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            System.out.println("            Server with the user "+ socket.getInetAddress()+ " is now closed");

        }


    }

