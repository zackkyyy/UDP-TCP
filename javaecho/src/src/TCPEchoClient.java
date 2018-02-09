import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by zack on 2018-02-06.
 */
public class TCPEchoClient extends networkingLayer {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private static final String MSG= "An Echo MessageAn Echo MessageAn Echo MessageAn Echo MessageAn Echo MessageAn Echo Message!";
    int numberOfSentMsgs;

    public TCPEchoClient(String[] args) {
        super(args);
    }



    @Override
    public void run()
    {

        for (numberOfSentMsgs = 0; numberOfSentMsgs < super.transferRate; numberOfSentMsgs++)
        {
            try
            {
                out.write(MSG.getBytes());  // Send the  string to the server
                out.flush();
                String receivedString ="";  // empty string to receive messages
                while(receivedString.length()<MSG.length()){ // ensure that the whole message is received
                    super.buf = new byte[super.bufSize];     // create a buffer
                    int bytesReceived = 0;                   // Total bytes received
                    int reader = in.read(super.buf);         //  the bytes that we received in last read
                    bytesReceived += reader;                 // the total byte received
                    receivedString += new String(super.buf, 0, bytesReceived);
                }
                /* Compare sent and received message */
                if (receivedString.compareTo(MSG) == 0) {
                    System.out.println(+MSG.length() + " byte sent and  " + receivedString.length() +" byte received and the buffer size is "+super.bufSize );

                }  else {
                    //receivedString +=  new String(super.buf, 0, bytesReceived);
                    System.out.println("the number of sent messages : " + receivedString.length() + " and the remaining is " + MSG.length());

                }

            } catch (Exception e)
                {

                return;
                 }
                 super.Delay();
        }
    }
    @Override
    public void ClientTransmit() throws IOException {
        // We start by checking if the message is empty
        if (MSG.isEmpty()) {
            System.out.println("Error: Message is empty!!");
            System.exit(1);
        }
        try {
            // creating the connection
            socket = new Socket();
            socket.bind(super.localBindPoint);
            socket.connect(new InetSocketAddress(super.IP, super.port) );

            in = socket.getInputStream();
            out = socket.getOutputStream();


        } catch (Exception e) {
            e.printStackTrace();
        }
        // Run the thread
        try {
            super.RunTheThread(new Thread(this));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("the Time is : " + super.time  +" ms");
       // close the streaming the and the socket
        in.close();
        out.close();
        socket.close();

    }

    }

