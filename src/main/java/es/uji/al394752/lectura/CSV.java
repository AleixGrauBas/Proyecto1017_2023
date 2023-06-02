package es.uji.al394752.lectura;

import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.RowWithLabel;
import es.uji.al394752.clases.Table;
import es.uji.al394752.clases.TableWithLabels;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {
    private BufferedReader br = null;
    private String line = null;
    private String [] datos = null;
    private void abrir(String tabla) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(tabla));
    }
    private List<Double> devuelveRow(){
        List<Double> auxRow = new ArrayList<>();
        datos = line.split(",");
        for (String s : datos){
            Double r = Double.parseDouble(s);
            auxRow.add(r);
        }
        return auxRow;
    }
    private List<Double> devuelveRowWithLabel(){
        List<Double> auxRow = new ArrayList<>();
        datos = line.split(",");
        for (int i = 0; i < datos.length - 1; i++){
            Double r = Double.parseDouble(datos[i]);
            auxRow.add(r);
        }
        return auxRow;
    }
    private List<String> anyadirCabeceras() throws IOException {
        String line = br.readLine();
        String [] headers = line.split(",");
        List<String> auxH = new ArrayList<>();
        auxH.addAll(Arrays.asList(headers));
        return auxH;
    }

    public Table readTable(String tabla){
        Table table = new Table();
        try {
            abrir(tabla);
            //Leemos la primera linea que es diferente al resto
            table.addHeader(anyadirCabeceras());
            //Leemos el resto de lineas que son row
            while ((line = br.readLine()) != null){
                table.addRow(new Row(devuelveRow()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
    public TableWithLabels readTableWithLabels(String tabla){
        TableWithLabels table = new TableWithLabels();
        try {
            abrir(tabla);
            //Leemos la primera linea que es diferente al resto
            table.addHeader(anyadirCabeceras());
            //Leemos el resto de líneas que son row
            while ((line = br.readLine()) != null){
                RowWithLabel row = new RowWithLabel(devuelveRowWithLabel(), table.getLabel(datos[datos.length - 1]));
                table.addRow(row);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
}