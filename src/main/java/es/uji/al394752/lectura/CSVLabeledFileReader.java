package es.uji.al394752.lectura;

import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.RowWithLabel;
import es.uji.al394752.clases.TableWithLabels;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVLabeledFileReader extends  ReaderTemplate{
    public CSVLabeledFileReader(String source) {
        super(source);
        table = new TableWithLabels();
    }


    @Override
    void processData(String data) {
        List<Double> auxRow = new ArrayList<>();
        String [] datos = data.split(",");
        for (int i = 0; i < datos.length - 1; i++){
            Double r = Double.parseDouble(datos[i]);
            auxRow.add(r);
        }
        int indice = table.getLabel(datos[datos.length - 1]);
        RowWithLabel row = new RowWithLabel(auxRow, indice);
        table.addRow(row);
    }

}
