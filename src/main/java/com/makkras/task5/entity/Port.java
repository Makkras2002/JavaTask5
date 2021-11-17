package com.makkras.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
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
    private Deque<Condition> waitingThreadsConditions = new ArrayDeque<>();

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
            if(numberOfFreeDocks ==0){
                Condition condition = lock.newCondition();
                waitingThreadsConditions.add(condition);
                condition.await();
            }
            numberOfFreeDocks--;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            lock.unlock();
        }
        return docks.pop();
    }
    public void getBackDock(Dock dock){
        lock.lock();
        try {
            docks.add(dock);
            numberOfFreeDocks++;
            if(!waitingThreadsConditions.isEmpty()){
                Condition condition = waitingThreadsConditions.poll();
                if(condition!=null){
                    condition.signal();
                }
            }
        }finally {
            lock.unlock();
        }
    }
    public Integer getNumberOfFreeDocks() {
        return numberOfFreeDocks;
    }
}
