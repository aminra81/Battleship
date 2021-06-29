package ir.sharif.aminra.models;

import ir.sharif.aminra.util.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

public class ID {
    private static final File idFile = Config.getConfig("db").getProperty(File.class, "IDFile");
    int idNum;

    public ID(boolean toBeGen) {
        Scanner myReader;
        int lastID = 0;
        try {
            myReader = new Scanner(idFile);
            lastID = myReader.nextInt() + 1;
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PrintStream myPrinter;
        try {
            myPrinter = new PrintStream(idFile);
            myPrinter.print(lastID);
            myPrinter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.idNum = lastID;
    }

    public ID(int idNum) {
        this.idNum = idNum;
    }

    @Override
    public String toString() {
        return String.valueOf(idNum);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ID id = (ID) o;
        return id.idNum == idNum;
    }

    @Override
    public int hashCode() { return Objects.hash(idNum); }
}
