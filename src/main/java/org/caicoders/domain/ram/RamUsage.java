package org.caicoders.domain.ram;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RamUsage implements IRamUsage {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String ramUsageStr = "N.D.";  // Variable para almacenar la información de uso de RAM

    public RamUsage() {
        // Schedules the task to run every second
        scheduler.scheduleAtFixedRate(this::collectRamUsage, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void collectRamUsage() {
        // Collects information from CPU and RAM
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware().getMemory();

        // Calculate the used memory in MB
        long usedMemory = memory.getTotal() - memory.getAvailable();
        double usedMemoryMb = usedMemory / (1024.0 * 1024.0);

        // Build the string with the RAM usage information
        ramUsageStr = String.format("%.2f", usedMemoryMb).replace(',', '.') + " MB";
    }

    @Override
    public String getRamUsageStr() {
        return ramUsageStr;
    }
}
