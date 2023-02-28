package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {
    public Table readTable(String tabla){
        BufferedReader br = null;
        Table table = new Table();
        try {
            br = new BufferedReader(new FileReader(tabla));
            //Leemos la primera linea que es diferente al resto
            String line = br.readLine();
            String [] headers = line.split(",");
            List<String> auxH = new ArrayList<>();
            auxH.addAll(Arrays.asList(headers));
            table.addHeader(auxH);
            //Leemos el resto de lineas que son row
            while ((line = br.readLine()) != null){
                List<Double> auxRow = new ArrayList<>();
                String [] datos = line.split(",");
                for (String s : datos){
                    Double r = Double.parseDouble(s);
                    auxRow.add(r);
                }
                Row row = new Row(auxRow);
                table.addRow(row);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
    public TableWithLabels readTableWithLabels(String tabla){
        BufferedReader br = null;
        TableWithLabels table = new TableWithLabels();
        try {
            br = new BufferedReader(new FileReader(tabla));
            //Leemos la primera linea que es diferente al resto
            String line = br.readLine();
            String [] headers = line.split(",");
            List<String> auxH = new ArrayList<>();
            auxH.addAll(Arrays.asList(headers));
            table.addHeader(auxH);
            //Leemos el resto de líneas que son row
            while ((line = br.readLine()) != null){
                List<Double> auxRow = new ArrayList<>();
                String [] datos = line.split(",");
                for (int i = 0; i < datos.length - 1; i++){
                    Double r = Double.parseDouble(datos[i]);
                    auxRow.add(r);
                }
                Row row = new Row(auxRow);
                table.addRow(row,datos[datos.length - 1]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
}
