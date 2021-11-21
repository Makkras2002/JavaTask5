package com.makkras.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

import static com.makkras.task5.entity.Port.MAX_PORT_STORAGE_CAPACITY;

public class Dock {
    private final Integer dockId;
    private static Logger logger = LogManager.getLogger();
    public Dock(Integer dockId){
        this.dockId = dockId;
    }
    public void doShipService(Ship ship) {
        try {
            String threadName = Thread.currentThread().getName();
            int threadIndex =Character.getNumericValue(threadName.charAt(threadName.length()-1));
            TimeUnit.SECONDS.sleep(threadIndex);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        ship.goToNextState();
        if(ship.isUploadingContainersOnItself()) {
            int amountOfContainersToUploadOnShip = ship.getShipContainerMaxCapacity() - ship.getContainerAmount();
            while(amountOfContainersToUploadOnShip>=0){
                if(Port.getInstance().containersAmountInPortStorage.intValue() - amountOfContainersToUploadOnShip >= 0){
                    ship.setContainerAmount(ship.getContainerAmount()+ amountOfContainersToUploadOnShip);
                    Port.getInstance().containersAmountInPortStorage.addAndGet(-amountOfContainersToUploadOnShip);
                    break;
                }
                amountOfContainersToUploadOnShip--;
            }
            logger.info("Ship: " + ship.getShipName()+" uploaded "+ amountOfContainersToUploadOnShip+
                    " containers on itself and left the port." + " Port Storage: " +
                    Port.getInstance().containersAmountInPortStorage +
                    ". Service done by dock: " + dockId);
        }else {
            int maxAmountOfContainersOnShipReadyToUnLoad = ship.getContainerAmount();
            while(maxAmountOfContainersOnShipReadyToUnLoad>=0) {
                if(Port.getInstance().containersAmountInPortStorage.intValue() + maxAmountOfContainersOnShipReadyToUnLoad <= MAX_PORT_STORAGE_CAPACITY){
                    ship.setContainerAmount(ship.getContainerAmount() - maxAmountOfContainersOnShipReadyToUnLoad);
                    Port.getInstance().containersAmountInPortStorage.addAndGet(+maxAmountOfContainersOnShipReadyToUnLoad);
                    break;
                }
                maxAmountOfContainersOnShipReadyToUnLoad--;
            }
            logger.info("Ship: " + ship.getShipName()+" unloaded "+ maxAmountOfContainersOnShipReadyToUnLoad+
                    " containers from itself into port storage and left the port." + " Port Storage: " +
                    Port.getInstance().containersAmountInPortStorage +
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
