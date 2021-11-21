package com.makkras.task5.main;

import com.makkras.task5.entity.Port;
import com.makkras.task5.entity.Ship;
import com.makkras.task5.exception.InteractionException;
import com.makkras.task5.fileio.CustomFileReader;
import com.makkras.task5.fileio.impl.ShipDataReader;
import com.makkras.task5.parser.CustomDataParser;
import com.makkras.task5.parser.impl.ShipDataParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        String filePath = "filesrc/shipdatafile.txt";
        CustomFileReader fileReader  =new ShipDataReader();
        CustomDataParser dataParser =new ShipDataParser();

        try {
            List<Ship> ships = dataParser.parseShipsFileData(fileReader.readDataFromFile(filePath));
            ExecutorService executorService = Executors.newFixedThreadPool(ships.size());
            ships.forEach(executorService::execute);
            boolean isFinished = true;
            boolean endingIndicator = true;
            while (endingIndicator) {
                isFinished = true;
                for(Ship ship: ships) {
                    if(!ship.getShipState().equals("class com.makkras.task5.entity.state.impl.FinishedThreadShipState")){
                        isFinished =false;
                    }
                }
                if(isFinished) {
                    executorService.shutdownNow();
                    logger.info(Port.getInstance().containersAmountInPortStorage);
                    endingIndicator =false;
                }
            }
        } catch (InteractionException e) {
            logger.error(e.getMessage());
        }
    }
}
