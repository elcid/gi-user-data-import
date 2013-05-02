package de.springerprofessional.gi.parser;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVStrategy;

import java.io.FileReader;
import java.io.IOException;

public class CSVParseDemo {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java CSVParseDemo <csv_file>");
            return;
        }

        CSVParser parser = new CSVParser(new FileReader(args[0]), CSVStrategy.EXCEL_STRATEGY);
        String[] values = parser.getLine();
        while (values != null) {
            printValues(parser.getLineNumber(), values);
            values = parser.getLine();
        }
    }

    private static void printValues(int lineNumber, String[] as) {
        System.out.println("Line " + lineNumber + " has " + as.length + " values:");
        for (String s: as) {
            System.out.println("\t|" + s + "|");
        }
        System.out.println();
    }
}

