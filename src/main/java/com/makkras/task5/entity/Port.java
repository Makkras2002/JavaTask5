package com.makkras.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Logger logger = LogManager.getLogger();
    public static Integer numberOfFreeDocks = 5;
    public final static Integer maxNumberOfDocks = 5;
    public static Integer containersAmountInPortStorage = 30;
    public final static Integer MAX_PORT_STORAGE_CAPACITY = 50;
    private Lock lock = new ReentrantLock();
    private Deque<Dock> docks = new ArrayDeque<>(maxNumberOfDocks);

    private static class LoadSingletonPort{
        static final Port INSTANCE = new Port();
    }

    private Port(){
        int dockId =1;
        while (dockId<=maxNumberOfDocks){
            docks.add(new Dock(dockId));
            dockId++;
        }
    }
    public static Port getInstance(){
        return LoadSingletonPort.INSTANCE;
    }

    public Dock acquireDock(){
        lock.lock();
        try {
            while (true){
                if(numberOfFreeDocks ==0){
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }else {
                    break;
                }
            }
            numberOfFreeDocks--;
            return docks.pop();
        }finally {
            lock.unlock();
        }
    }
    public void getBackDock(Dock dock){
        docks.add(dock);
        numberOfFreeDocks++;
    }

    public Integer getNumberOfFreeDocks() {
        return numberOfFreeDocks;
    }
}
