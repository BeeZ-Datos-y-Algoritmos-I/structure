package data;

import util.JVMUtil;

import java.util.Date;

public class Metric {

    private long memory;
    private long time;

    public Metric() {

        memory = JVMUtil.getMemoryUsed();
        time = new Date().getTime();

    }

    public Metric consume() {

        memory = JVMUtil.getMemoryUsed() - memory;
        time = new Date().getTime() - time;

        return this;

    }

    public long getMemoryUsage() {
        return memory;
    }

    public long getTimeUsage() {
        return time;
    }

    @Override
    public String toString() {
        return "[Tiempo: " + time + " ms, Memoria: " + memory + " bytes]";
    }

}
