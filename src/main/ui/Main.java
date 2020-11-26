package ui;

import exception.InvalidEmailException;

import java.io.FileNotFoundException;

// CITATION: JSon Serialization Demo

public class Main {
    public static void main(String[] args) {
        try {
            new YogaApp();
        } catch (FileNotFoundException | InvalidEmailException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
