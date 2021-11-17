package com.makkras.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.makkras.task5.entity.Port.containersAmountInPortStorage;
import static com.makkras.task5.entity.Port.MAX_PORT_STORAGE_CAPACITY;

public class Dock {
    private final Integer dockId;
    private static Logger logger = LogManager.getLogger();
    public Dock(Integer dockId){
        this.dockId = dockId;
    }
    public void doShipService(Ship ship){
        try {
            String threadName = Thread.currentThread().getName();
            int threadIndex =Character.getNumericValue(threadName.charAt(threadName.length()-1));
            TimeUnit.SECONDS.sleep(threadIndex);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        ThreadLocalRandom threadLocalRandom =ThreadLocalRandom.current();
        ship.goToNextState();
        if(ship.isUploadingContainersOnItself()){
            int amountOfContainersToUploadOnShip = ship.getShipContainerMaxCapacity() - ship.getContainerAmount();
            while(amountOfContainersToUploadOnShip>=0){
                if(containersAmountInPortStorage - amountOfContainersToUploadOnShip >= 0){
                    ship.setContainerAmount(ship.getContainerAmount()+ amountOfContainersToUploadOnShip);
                    containersAmountInPortStorage-=amountOfContainersToUploadOnShip;
                    break;
                }
                amountOfContainersToUploadOnShip--;
            }
            logger.info("Ship: " + ship.getShipName()+" uploaded "+ amountOfContainersToUploadOnShip+
                    " containers on itself and left the port." + " Port Storage: " + containersAmountInPortStorage +
                    ". Service done by dock: " + dockId);
        }else {
            int maxAmountOfContainersOnShipReadyToUnLoad = ship.getContainerAmount();
            while(maxAmountOfContainersOnShipReadyToUnLoad>=0){
                if(containersAmountInPortStorage + maxAmountOfContainersOnShipReadyToUnLoad <= MAX_PORT_STORAGE_CAPACITY){
                    ship.setContainerAmount(ship.getContainerAmount() - maxAmountOfContainersOnShipReadyToUnLoad);
                    containersAmountInPortStorage+=maxAmountOfContainersOnShipReadyToUnLoad;
                    break;
                }
                maxAmountOfContainersOnShipReadyToUnLoad--;
            }
            logger.info("Ship: " + ship.getShipName()+" unloaded "+ maxAmountOfContainersOnShipReadyToUnLoad+
                    " containers from itself into port storage and left the port." + " Port Storage: " + containersAmountInPortStorage +
                    ". Service done by dock: " + dockId);
        }
        ship.goToNextState();
    }

    public Integer getDockId() {
        return dockId;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dock{");
        sb.append("dockId=").append(dockId);
        sb.append('}');
        return sb.toString();
    }
}
