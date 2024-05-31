package org.caicoders.domain.cpu;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CpuUsage implements ICpuUsage {
    private String cpuUsageAverageStr;
    private final SystemInfo SYSTEMINFO = new SystemInfo();
    private final HardwareAbstractionLayer HARDWARE = SYSTEMINFO.getHardware();
    private final CentralProcessor PROCESSOR = HARDWARE.getProcessor();
    private final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

    public CpuUsage() {
        SCHEDULER.scheduleAtFixedRate(this::getAverageCpuLoad, 0, 1, TimeUnit.SECONDS);
        SCHEDULER.scheduleAtFixedRate(this::getCpuName, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void getAverageCpuLoad() {
        long[] prevTicks = PROCESSOR.getSystemCpuLoadTicks();
        Util.sleep(1000);
        double cpuUsage = PROCESSOR.getSystemCpuLoadBetweenTicks(prevTicks) * 250 + 3;
        cpuUsageAverageStr = String.format("%.2f", cpuUsage).replace(",", ".");

        cpuUsageAverageStr += "%";
    }

    @Override
    public String getCpuName() {
        return PROCESSOR.getProcessorIdentifier().getName();
    }

    @Override
    public String getCpuUsageAverageStr() {
        return cpuUsageAverageStr;
    }
}
