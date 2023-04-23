package es.uji.al394752.Lectura;

import es.uji.al394752.Row;

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
    private Scanner sc = null;

    @Override
    void openSource(String source) {
        try {
            sc = new Scanner(new FileReader(source));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void processHeaders(String headers) {
        String[] cabeceras = headers.split(",");
        List<String> auxH = new ArrayList<>();
        auxH.addAll(Arrays.asList(cabeceras));
        table.addHeader(auxH);
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

    @Override
    void closeSource() {
        sc.close();
    }

    @Override
    boolean hasMoreData() {
        return sc.hasNext();
    }

    @Override
    String getNextData() {
        return sc.nextLine();
    }
}
