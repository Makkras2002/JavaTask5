package com.makkras.task5.entity.state;

import com.makkras.task5.entity.Ship;

public interface CustomShipState {
    void next(Ship ship);
    void prev(Ship ship);
    String getStatus();
}
