package com.makkras.task5.fileio;

import com.makkras.task5.exception.InteractionException;

import java.util.List;

public interface CustomFileReader {
    List<String> readDataFromFile(String filePath) throws InteractionException;
}
