package org.caicoders.infrastructure;

import org.caicoders.application.ICollectData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Connection implements IConnection{

    private ICollectData data;
    private final int PORT = 6789;

    public Connection(ICollectData data) {
        this.data = data;
    }

    @Override
    public void sendData() {
        try{
            ServerSocket welcomeSocket = new ServerSocket(PORT);

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();

                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                task(connectionSocket, outToClient);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void task(Socket connectionSocket, DataOutputStream outToClient) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!connectionSocket.isClosed()) {
                        String message = data.getMessage() + "\n";
                        outToClient.writeBytes(message);
                    } else {
                        this.cancel();
                    }
                } catch (IOException e) {
                    this.cancel(); // Cancel the task if there's an IOException
                    System.out.println(e.getMessage());
                }
            }
        };

        timer.schedule(task, 0, 1000);
    }
}
