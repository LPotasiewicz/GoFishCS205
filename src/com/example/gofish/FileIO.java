package com.example.gofish;

import java.util.ArrayList;

public class FileIO {
    private ArrayList<String> gamePlayStrings = new ArrayList<String>();
    public FileIO(){}

    public void addToGamePlayOutput(String string) {
        gamePlayStrings.add(string);
    }

    public ArrayList getArrayList() {
        return this.gamePlayStrings;
    }
}
