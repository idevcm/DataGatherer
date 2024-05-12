package org.caicoders.domain.cpu;

public interface ICpuUsage {
    void getAverageCpuLoad();
    String getCpuName();
    String getCpuUsageAverageStr();
}
