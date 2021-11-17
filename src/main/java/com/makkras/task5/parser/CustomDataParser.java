package com.makkras.task5.parser;

import com.makkras.task5.entity.Ship;

import java.util.List;

public interface CustomDataParser {
    List<Ship> parseShipsFileData(List<String> unParsedData);
}
