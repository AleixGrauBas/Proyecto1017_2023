package es.uji.al394752.lectura;

import es.uji.al394752.clases.Row;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVUnlabeledFileReader extends ReaderTemplate{

    public CSVUnlabeledFileReader(String source) {
        super(source);
    }

    @Override
    void processData(String data) {
        List<Double> auxRow = new ArrayList<>();
        String [] datos = data.split(",");
        for (String s : datos){
            Double r = Double.parseDouble(s);
            auxRow.add(r);
        }
        Row row = new Row(auxRow);
        table.addRow(row);
    }

}
