/*
  UDPEchoClient.java
  A simple echo client with no error handling
*/

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoClient extends networkingLayer {
   // public static final int BUFSIZE= 1024;
   //public static final int MYPORT= 0;
    private DatagramSocket socket;
	private DatagramPacket sendPacket;
	private DatagramPacket receivePacket;
    public static final String MSG= "An Echo Message!An Echo Message!An Echo Message!An Echo Message!An Echo Message!1234";
    int numberOfSentMsgs;
    public UDPEchoClient(String[] args) {
        super(args);
    }
    @Override
    public void run() {

        for (numberOfSentMsgs = 0; numberOfSentMsgs < super.transferRate; numberOfSentMsgs++) {
            //let the socket send and receive packets
            try {
                socket.send(sendPacket);
                socket.receive(receivePacket);
            } catch (Exception e) {
                return;
            }
            Delay();
        	/* Compare sent and received message */
      String receivedString=
                new String(receivePacket.getData(),
                        receivePacket.getOffset(),
                        receivePacket.getLength());
        if (receivedString.compareTo(MSG) == 0) {
            System.out.printf(+MSG.length() +" bytes sent and "+ receivedString.length() +" received\n (The Sent Message is = " + MSG+ "] \n");

        }  else
            System.out.printf(+MSG.length() +" bytes sent and "+ receivedString.length() +" received.... Sent and received msg not equal!\n");

        }
    }

    @Override
    public void ClientTransmit() {
        // We start by checking if the length of the message is good to fit in the UDP packet
        if (MSG.length() > 65507) {
            System.out.println("Error: Check the size of the message");
            System.exit(1);
        }

        try {
            socket = new DatagramSocket(null);
        } catch (SocketException e) {
            e.printStackTrace();
        }


	/* Create datagram packet for sending message */
        sendPacket = new DatagramPacket(MSG.toString().getBytes(),
                MSG.length(),
                remoteBindPoint);

	/* Create datagram packet for receiving echoed message */
        receivePacket = new DatagramPacket(super.buf, super.buf.length);
        //Run the thread
        try {
            super.RunTheThread(new Thread(this));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("the Time is : " + super.time  +" second");
        System.out.println(+numberOfSentMsgs+ " Messages sent successfully");
        socket.close();

    }






}