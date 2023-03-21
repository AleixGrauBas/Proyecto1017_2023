package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KmeansTest {

    @Test
    void estimate() {
        Kmeans kmeans = new Kmeans(3,10,100);
        Table datos = new Table();

        List<String> headers = new ArrayList<>();
        headers.add("uno");headers.add("dos");headers.add("tres");headers.add("cuatro");headers.add("cinco");

        List<Double> añadir = new ArrayList<>();
        Row rowAñadir;
        añadir.add(4.6);añadir.add(3.4);añadir.add(1.4);añadir.add(0.3);
        rowAñadir = new Row(añadir);
        datos.addRow(rowAñadir);
        añadir.add(6.0);añadir.add(3.0);añadir.add(4.8);añadir.add(1.8);
        rowAñadir = new Row(añadir);
        datos.addRow(rowAñadir);
        añadir.add(5.5);añadir.add(2.4);añadir.add(3.7);añadir.add(1.0);
        rowAñadir = new Row(añadir);
        datos.addRow(rowAñadir);

        kmeans.train(datos);
        List<Double> row2 = new ArrayList<>();
        row2.add(4.7);row2.add(3.2);row2.add(1.3);row2.add(0.2);
        Integer esperado = kmeans.estimate(row2);

        List<Double> row1 = new ArrayList<>();
        row1.add(4.6);row1.add(3.4);row1.add(1.4);row1.add(0.3);
        Integer resultado = kmeans.estimate(row1);

        assertEquals(esperado, resultado);
    }

    @Test
    void train() {
    }
}