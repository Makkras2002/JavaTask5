package com.makkras.task5.fileio.impl;

import com.makkras.task5.exception.InteractionException;
import com.makkras.task5.fileio.CustomFileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShipDataReader implements CustomFileReader {
    @Override
    public List<String> readDataFromFile(String filePath) throws InteractionException {
        List<String> dataFromFile = new ArrayList<>();
        try(FileReader fileReader = new FileReader(filePath);BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            dataFromFile = bufferedReader.lines().collect(Collectors.toList());
        }catch (IOException e) {
            throw new InteractionException(e.getMessage());
        }
        return dataFromFile;
    }
}
