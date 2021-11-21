package com.makkras.task5.parser.impl;

import com.makkras.task5.entity.Ship;
import com.makkras.task5.parser.CustomDataParser;

import java.util.ArrayList;
import java.util.List;

public class ShipDataParser implements CustomDataParser {
    private final static String SHIP_PARAMETERS_DELIMITER = "=";
    @Override
    public List<Ship> parseShipsFileData(List<String> unParsedData) {
        List<Ship> ships = new ArrayList<>();
        String[] subLine =null;
        for (String line : unParsedData){
            subLine  = line.split(SHIP_PARAMETERS_DELIMITER);
            ships.add(new Ship(subLine[0],Integer.parseInt(subLine[1]),Boolean.parseBoolean(subLine[2])));
        }
        return ships;
    }
}
