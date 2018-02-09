/**
 * Created by zack on 2018-02-02.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class networkingLayer implements Runnable {

    final int MYPORT = 0;
    protected int bufSize = 20;
    protected int transferRate;
    protected String IP;
    protected int port = 50000;
    protected byte[] buf;
    protected long start = 0;
    protected long time = 0;
    protected SocketAddress localBindPoint;
    protected SocketAddress remoteBindPoint;

    public networkingLayer(String[] args) {

        if (args.length != 3) {
            System.out.println("Error: You should give a right IP address and a number of messages you want to send");
            System.exit(1);
        }
        IP = args[0];
        transferRate = Integer.parseInt(args[1]);
        port = Integer.parseInt(args[2]);
        buf = new byte[bufSize];

        localBindPoint = new InetSocketAddress(MYPORT);
        remoteBindPoint = new InetSocketAddress(IP, port);
        ErrorHandler();


    }

    public static void IPValidity(String ip) {
        if (ip.split("\\.").length != 4) {
            System.out.println("Error: Invalid IP address");
            System.exit(1);
        }

        String[] parts = ip.split("\\.");
        for (String s : parts) {
            int i = Integer.parseInt(s);
            if ((i < 0) || (i > 255)) {
                System.out.println("Error: Invalid IP address");
                System.exit(1);
            }
        }
    }

    // This method will be implemented in Client class according to the needs.
    public abstract void ClientTransmit() throws IOException;

    protected void Delay() {
        try {
            Thread.sleep(1000 / transferRate);
        } catch (InterruptedException e) {
            return;
            // catch exception if time is out and thread is in the Delay
        }
    }

    protected void RunTheThread(Thread thread) throws InterruptedException {
        // create the thread and run it
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(thread);
        // calculating the time while running the thread
        start = System.currentTimeMillis();
        exec.awaitTermination(996, TimeUnit.MILLISECONDS);  // set the time out to 996 for getting 1 second
        time = System.currentTimeMillis() - start;
        // shut the thread down after the given runTime
        exec.shutdownNow();
    }

    private void ErrorHandler() {

        IPValidity(IP);


        // Check port
        if (port > 65535 || port < 1) {
            System.out.println("Error: Incorrect port number. Must be between (1-65535)");
            System.exit(1);
        }
        // check if transfer rate time is less than 1
        if (transferRate <= 0) {
            System.out.println("Error: Incorrect transfer rate time!!");
            System.exit(1);
        }
    }
}