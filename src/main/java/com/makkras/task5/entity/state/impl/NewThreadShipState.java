package com.makkras.task5.entity.state.impl;

import com.makkras.task5.entity.Ship;
import com.makkras.task5.entity.state.CustomShipState;

public class NewThreadShipState implements CustomShipState {
    @Override
    public void next(Ship ship) {
        ship.setShipState(new ProcessingThreadShipState());
    }

    @Override
    public void prev(Ship ship) {
        throw new UnsupportedOperationException("This is first shipThreadState.");
    }
    @Override
    public String getStatus() {
        return "Thread is new.";
    }
}
