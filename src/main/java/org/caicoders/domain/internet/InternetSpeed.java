package org.caicoders.domain.internet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InternetSpeed implements IInternetSpeed {

    private String internetSpeedStr;  // Variable para almacenar la información de velocidad de Internet
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public InternetSpeed() {
        scheduler.scheduleAtFixedRate(this::collectAndSendInternetSpeed, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void collectAndSendInternetSpeed() {
        try {
            // URL de prueba
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            long startTime = System.currentTimeMillis();

            // Lee datos de la conexión para medir la velocidad
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
            // Calcula la velocidad en megabits por segundo (Mbps)
            long totalTime = endTime - startTime;
            double speedMbps = (totalBytesRead * 8.0 / 1024.0 / 1024.0) / (totalTime / 1000.0);

            // Construir el mensaje final
            internetSpeedStr = String.format("%.3f", speedMbps).replace(',', '.') + " Mbps";
            System.out.flush();  // Forzar la liberación del búfer

        } catch (IOException e) {
            // Manejar el error y almacenar un mensaje en la variable
            internetSpeedStr = "N.D";
            System.out.println(internetSpeedStr);
        }
    }

    @Override
    public String getInternetSpeedStr() {
        return internetSpeedStr;
    }
}
