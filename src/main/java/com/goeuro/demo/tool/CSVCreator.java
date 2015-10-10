package com.goeuro.demo.tool;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mehmet Burak Aktürk
 * @description Produce CSV list of given POJO object list
 */
public abstract class CSVCreator<T> {

    private String columnTitles = "";
    private String filePath;

    public CSVCreator(String filePath) {
        this.filePath = filePath;
    }

    public void setColumnTitles(String columnTitles) {
        this.columnTitles = columnTitles;
    }

    public void createCSVFile(T[] itemList) {
        try {
            FileWriter writer = new FileWriter(filePath);

            writer.append(columnTitles);

            for (T location : itemList) {
                writer.append(createRow(location));
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("An error occured please try again.");
        }

    }

    protected abstract StringBuilder createRow(T item);
}
