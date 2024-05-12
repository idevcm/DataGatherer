package org.caicoders;

import org.caicoders.application.CollectData;
import org.caicoders.application.ICollectData;
import org.caicoders.domain.cpu.CpuUsage;
import org.caicoders.domain.cpu.ICpuUsage;
import org.caicoders.domain.internet.IInternetSpeed;
import org.caicoders.domain.internet.InternetSpeed;
import org.caicoders.domain.ram.IRamUsage;
import org.caicoders.domain.ram.RamUsage;
import org.caicoders.domain.storage.IStorage;
import org.caicoders.domain.storage.Storage;
import org.caicoders.infrastructure.Connection;
import org.caicoders.infrastructure.IConnection;

public class AppMain {
    public static void main(String[] args) {

        ICpuUsage cpuUsage = new CpuUsage();
        IInternetSpeed internetSpeed = new InternetSpeed();
        IRamUsage ramUsage = new RamUsage();
        IStorage storage = new Storage();

        ICollectData collectData = new CollectData(cpuUsage, ramUsage, storage, internetSpeed);
        collectData.startMonitoring();

        IConnection connection = new Connection(collectData);
        connection.sendData();
    }
}