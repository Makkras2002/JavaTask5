package com.makkras.task5.entity.state.impl;

import com.makkras.task5.entity.Ship;
import com.makkras.task5.entity.state.CustomShipState;

public class FinishedThreadShipState implements CustomShipState {
    @Override
    public void next(Ship ship) {
        throw new UnsupportedOperationException("This is last shipThreadState.");
    }

    @Override
    public void prev(Ship ship) {
        ship.setShipState(new ProcessingThreadShipState());
    }
    @Override
    public String getStatus() {
        return "Thread is finished.";
    }
}
