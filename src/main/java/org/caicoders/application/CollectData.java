package org.caicoders.application;

import org.caicoders.domain.cpu.ICpuUsage;
import org.caicoders.domain.internet.IInternetSpeed;
import org.caicoders.domain.ram.IRamUsage;
import org.caicoders.domain.storage.IStorage;
import oshi.util.Util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CollectData implements ICollectData {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ICpuUsage usageCPU;
    private IRamUsage ramUsage;
    private IStorage UsbLector;
    private IInternetSpeed internetSpeed;

    private StringBuilder message = new StringBuilder();

    public CollectData(ICpuUsage usageCPU, IRamUsage ramUsage, IStorage UsbLector, IInternetSpeed internetSpeed) {
        this.usageCPU = usageCPU;
        this.ramUsage = ramUsage;
        this.UsbLector = UsbLector;
        this.internetSpeed = internetSpeed;
    }

    @Override
    public void startMonitoring() {
        Util.sleep(7000);
        scheduler.scheduleAtFixedRate(this::buildMessage, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void buildMessage() {
        message.setLength(0);
        message.append(usageCPU.getCpuUsageAverageStr()).append(", ");
        message.append(ramUsage.getRamUsageStr()).append(", ");
        message.append(internetSpeed.getInternetSpeedStr()).append(", ");
        message.append(UsbLector.getStorage()).append(", ");
        message.append(usageCPU.getCpuName());
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return message.toString();
    }
}
