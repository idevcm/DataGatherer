package org.caicoders.domain.internet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InternetSpeed implements IInternetSpeed {

    private String internetSpeedStr;  // Variable for storing Internet speed information
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public InternetSpeed() {
        scheduler.scheduleAtFixedRate(
                this::collectAndSendInternetSpeed,
                0,
                1,
                TimeUnit.SECONDS
        );
    }

    @Override
    public void collectAndSendInternetSpeed() {
        try {
            // Try to connect to Google to measure the speed
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            long startTime = System.currentTimeMillis();

            // Read the response to measure the speed
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            long totalBytesRead = 0;

            try (InputStream inputStream = connection.getInputStream()) {
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    totalBytesRead += bytesRead;
                }
            }
            long endTime = System.currentTimeMillis();
            // Calculate the speed in Mbps
            long totalTime = endTime - startTime;
            double speedMbps = (totalBytesRead * 8.0 / 1024.0 / 1024.0) / (totalTime / 1000.0);

            // Build the string with the Internet speed information
            internetSpeedStr = String.format("%.3f", speedMbps).replace(',', '.') + " Mbps";
            System.out.flush();  // Flush the output to avoid buffering

        } catch (IOException e) {
            // Manage the exception if there's an error
            internetSpeedStr = "N.D";
            System.out.println(internetSpeedStr);
        }
    }

    @Override
    public String getInternetSpeedStr() {
        return internetSpeedStr;
    }
}
