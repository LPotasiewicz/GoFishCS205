package com.example.gofish;

import java.util.ArrayList;

public class FileIO {
    private ArrayList<String> gamePlayStrings = new ArrayList<String>();
    public FileIO(){}

    public void fileOutput(String output) {
        System.out.println(output);
        gamePlayStrings.add(output);
    }
    public void fileOutputDontPrint(String output) {
        gamePlayStrings.add(output);
    }

    public ArrayList<String> getArrayList() {
        return this.gamePlayStrings;
    }
}
