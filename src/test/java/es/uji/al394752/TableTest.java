package es.uji.al394752;

import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.Table;
import es.uji.al394752.lectura.CSV;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private CSV csv = new CSV();
    private String separator = System.getProperty( "file.separator" );
    private Table tabla = csv.readTable( "src" + separator + "miles_dollars.csv");
    @org.junit.jupiter.api.Test
    void getRowAt() {
        List<Double> esperado = new LinkedList<>();
        esperado.add(1211.0);
        esperado.add(1802.0);
        Row rowEsperada = new Row(esperado);
        Row resultado = tabla.getRowAt(0);
        assertEquals(rowEsperada.getData(),resultado.getData());
    }
    @org.junit.jupiter.api.Test
    void addRow(){

        List<Double> newRow = new ArrayList<>();
        newRow.add(0010.0);
        newRow.add(2055.0);
        Row esperada = new Row(newRow);
        tabla.addRow(esperada);
        assertEquals(esperada.getData(),tabla.getRowAt(tabla.getRowsSize()-1).getData());
    }
}