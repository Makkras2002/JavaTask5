package com.makkras.task5.entity.state.impl;

import com.makkras.task5.entity.Ship;
import com.makkras.task5.entity.state.CustomShipState;

public class ProcessingThreadShipState implements CustomShipState {
    @Override
    public void next(Ship ship) {
        ship.setShipState(new FinishedThreadShipState());
    }

    @Override
    public void prev(Ship ship) {
        ship.setShipState(new NewThreadShipState());
    }

    @Override
    public String getStatus() {
        return "Thread is processing.";
    }
}
