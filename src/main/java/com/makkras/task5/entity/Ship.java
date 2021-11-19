package com.makkras.task5.entity;

import com.makkras.task5.entity.state.CustomShipState;
import com.makkras.task5.entity.state.impl.NewThreadShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class Ship extends Thread{
    private static Logger logger = LogManager.getLogger();
    private String shipName;
    private Integer containerAmount;
    private boolean isUploadingContainersOnItself;
    private CustomShipState shipState;
    private final Integer shipContainerMaxCapacity = 10;

    public Ship(){
        this.shipState = new NewThreadShipState();
    }
    public Ship(String shipName,Integer containerAmount,boolean isUploadingContainersOnItself){
        this.shipName = shipName;
        this.containerAmount = containerAmount;
        this.isUploadingContainersOnItself  =isUploadingContainersOnItself;
        this.shipState = new NewThreadShipState();
    }
    public void goToPreviousState(){
        shipState.prev(this);
    }
    public void goToNextState(){
        shipState.next(this);
    }

    @Override
    public void run() {
        Dock dock = Port.getInstance().acquireDock();
        dock.doShipService(this);
        Port.getInstance().getBackDock(dock);
    }

    public Integer getShipContainerMaxCapacity() {
        return shipContainerMaxCapacity;
    }

    public String getShipState(){
        return shipState.getClass().toString();
    }

    public void setShipState(CustomShipState shipState) {
        this.shipState = shipState;
    }

    public boolean isUploadingContainersOnItself() {
        return isUploadingContainersOnItself;
    }

    public void setUploadingContainersOnItself(boolean uploadingContainersOnItself) {
        isUploadingContainersOnItself = uploadingContainersOnItself;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Integer getContainerAmount() {
        return containerAmount;
    }

    public void setContainerAmount(Integer containerAmount) {
        this.containerAmount = containerAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (isUploadingContainersOnItself != ship.isUploadingContainersOnItself) return false;
        if (shipName != null ? !shipName.equals(ship.shipName) : ship.shipName != null) return false;
        if (containerAmount != null ? !containerAmount.equals(ship.containerAmount) : ship.containerAmount != null)
            return false;
        return shipContainerMaxCapacity != null ? shipContainerMaxCapacity.equals(ship.shipContainerMaxCapacity) : ship.shipContainerMaxCapacity == null;
    }

    @Override
    public int hashCode() {
        int result = shipName != null ? shipName.hashCode() : 0;
        result = 31 * result + (containerAmount != null ? containerAmount.hashCode() : 0);
        result = 31 * result + (isUploadingContainersOnItself ? 1 : 0);
        result = 31 * result + (shipContainerMaxCapacity != null ? shipContainerMaxCapacity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("shipName='").append(shipName).append('\'');
        sb.append(", containerAmount=").append(containerAmount);
        sb.append(", isUploadingContainersOnItself=").append(isUploadingContainersOnItself);
        sb.append(", shipState=").append(shipState.getStatus());
        sb.append('}');
        return sb.toString();
    }
}
