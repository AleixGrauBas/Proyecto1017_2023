package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @org.junit.jupiter.api.Test
    void getRowAt() {
        List<Double> esperado = new LinkedList<>();
        esperado.add(1211.0);
        esperado.add(1802.0);
        Row rowEsperada = new Row(esperado);
        CSV csv = new CSV();
        String separator = System.getProperty( "file.separator" );
        Table table = csv.readTable("src" + separator + "miles_dollars.csv");
        Row resultado = table.getRowAt(0);
        assertEquals(rowEsperada.getData(),resultado.getData());
    }
}