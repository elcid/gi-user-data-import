package de.springerprofessional.gi.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class CSVParseDemo {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java CSVParseDemo <csv_file>");
            return;
        }

        CSVParser parser = new CSVParser(new FileReader(args[0]), CSVFormat.EXCEL);
        List<CSVRecord> records = parser.getRecords();
        ListIterator<CSVRecord> it = records.listIterator(1);
        while (it.hasNext()) {
            printValues(parser.getLineNumber(), records);
            it.next();
        }
    }

    private static void printValues(long lineNumber, List<CSVRecord> as) {
        System.out.println("Line " + lineNumber + " has " + as.size() + " values:");
        for (CSVRecord s: as) {
            System.out.println("\t|" + s + "|");
        }
        System.out.println();
    }
}

