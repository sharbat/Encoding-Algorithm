package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String textPath="C:/Users/Irene/IdeaProjects/InformationTh.txt";
        String theString = "";
        File file=new File(textPath);
        Scanner scanner = new Scanner(file);
        theString = scanner.nextLine();
        while (scanner.hasNextLine()) {
            theString = theString + "\n" + scanner.nextLine();
        }
        ShannonFanoAlgorithm sfc = new ShannonFanoAlgorithm(theString);
        System.out.println(sfc);
        System.out.println(sfc.toBitText());
    }

}
