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
                    //Disk name
                    storageInfo.append(fileStore).append("-");
                    //Kind of disk
                    storageInfo.append(fileStore.type()).append("-");
                    //Percentage of disk used
                    double usagePercentage = usagePercentage(fileStore);
                    storageInfo.append(String.format("%.2f", usagePercentage).replace(',', '.')).append("%");

                    // Add an underscore if it's not the last FileStore.
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
            // Get the total space and used space of the file store.
            long totalSpace = fileStore.getTotalSpace();
            long usedSpace = totalSpace - fileStore.getUsableSpace();

            // Calculate the percentage of used space.
            if (totalSpace > 0) {
                return (usedSpace / (double) totalSpace) * 100.0;
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            // Print the exception and return 0.0 if there's an error.
            e.printStackTrace();
            return 0.0;
        }
    }
}
