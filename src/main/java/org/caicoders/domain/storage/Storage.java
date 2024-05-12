package org.caicoders.domain.storage;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.stream.StreamSupport;

public class Storage implements IStorage{
    @Override
    public String getStorage() {
        StringBuilder storageInfo = new StringBuilder();

        try {
            Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
            int i = 0;
            int totalFileStores = (int) StreamSupport.stream(fileStores.spliterator(), false).count();

            for (FileStore fileStore : fileStores) {
                try {
                    //Nombre del disco
                    storageInfo.append(fileStore).append("-");
                    //Tipo de disco
                    storageInfo.append(fileStore.type()).append("-");
                    //Porcentaje de uso de disco
                    double usagePercentage = usagePercentage(fileStore);
                    storageInfo.append(String.format("%.2f", usagePercentage).replace(',', '.')).append("%");

                    // Agrega el separador si no es el último FileStore
                    if (i < totalFileStores - 1) {
                        storageInfo.append("_");
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return storageInfo.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener la información del almacenamiento.";
        }
    }

    @Override
    public double usagePercentage(FileStore fileStore) {
        try {
            // Obtiene el espacio total y el espacio utilizable para el FileStore.
            long totalSpace = fileStore.getTotalSpace();
            long usedSpace = totalSpace - fileStore.getUsableSpace();

            // Calcula y devuelve el porcentaje de uso de almacenamiento.
            if (totalSpace > 0) {
                return (usedSpace / (double) totalSpace) * 100.0;
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción si se produce una y devuelve 0.0.
            e.printStackTrace();
            return 0.0;
        }
    }
}
