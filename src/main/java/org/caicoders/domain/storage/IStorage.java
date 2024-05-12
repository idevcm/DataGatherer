package org.caicoders.domain.storage;

import java.nio.file.FileStore;

public interface IStorage {
    String getStorage();
    double usagePercentage(FileStore fileStore);
}
