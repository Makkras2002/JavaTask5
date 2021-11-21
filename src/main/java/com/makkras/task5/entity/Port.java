package com.makkras.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Logger logger = LogManager.getLogger();
    public static final Integer MAX_NUMBER_OF_DOCKS = 7;
    public AtomicInteger containersAmountInPortStorage = new AtomicInteger(30);
    public static final Integer MAX_PORT_STORAGE_CAPACITY = 50;
    private Lock lock = new ReentrantLock();
    private Deque<Dock> docks = new ArrayDeque<>(MAX_NUMBER_OF_DOCKS);
    private Deque<Condition> waitingThreadsConditions = new ArrayDeque<>();

    private static class LoadSingletonPort{
        static final Port INSTANCE = new Port();
    }

    private Port(){
        int dockId =1;
        while (dockId<= MAX_NUMBER_OF_DOCKS){
            docks.add(new Dock(dockId));
            dockId++;
        }
    }
    public static Port getInstance(){
        return LoadSingletonPort.INSTANCE;
    }

    public Dock acquireDock(){
        Dock dock = new Dock(123);
        try {
            lock.lock();
            if(docks.size() ==0){
                Condition condition = lock.newCondition();
                waitingThreadsConditions.add(condition);
                condition.await();
            }
            dock = docks.pop();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            lock.unlock();
        }
        return dock;
    }
    public void getBackDock(Dock dock){
        try {
            lock.lock();
            docks.add(dock);
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
        return docks.size();
    }
}
