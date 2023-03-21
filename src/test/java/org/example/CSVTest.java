package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {
    @org.junit.jupiter.api.Test
    void readTable() {
        CSV csv=new CSV();
        String separator = System.getProperty( "file.separator" );
        assertNotNull(csv.readTable("src"+ separator + "miles_dollars.csv"));
        //Comprobamos que lee el numero de filas correcto
        Table tabla = csv.readTable( "src" + separator + "miles_dollars.csv");
        int filasEsperadas = 25;
        int filasObtenidas = tabla.rows.size();
        assertEquals(filasObtenidas, filasEsperadas);
        //Comprobamos numero de columnas
        int columnasEsperadas = 2;
        int columnasObtenidas = tabla.headers.size();
        assertEquals(columnasObtenidas,columnasEsperadas);
        //Comprobamos que las headers son las correspondientes
        List<String> headersEsperadas = new ArrayList<>();
        headersEsperadas.add("Miles");
        headersEsperadas.add("Dollars");
        List<String> headersObtenidas = tabla.headers;
        assertEquals(headersObtenidas.get(0), headersEsperadas.get(0));
        assertEquals(headersObtenidas.get(1), headersEsperadas.get(1));
        //Comprobamos el numero que se le asgina a cada fila es correcto
        List<Double> rowEsperada = new ArrayList<>();
        rowEsperada.add(1211.0); rowEsperada.add(1802.0);
        Row rowAComparar = new Row(rowEsperada);
        assertEquals(rowAComparar.getData(),tabla.getRowAt(0).getData());
        rowEsperada.clear();
        rowEsperada.add(3852.0); rowEsperada.add(4801.0);
        rowAComparar = new Row(rowEsperada);
        assertEquals(rowAComparar.getData(),tabla.getRowAt(16).getData());
    }
    @org.junit.jupiter.api.Test
    void readTableWithLabels(){
        CSV csv=new CSV();
        String separator = System.getProperty( "file.separator" );
        assertNotNull(csv.readTableWithLabels("src"+ separator + "iris.csv"));
        //Comprobamos que lee el numero de filas correcto
        TableWithLabels tabla = csv.readTableWithLabels( "src" + separator + "iris.csv");
        int filasEsperadas = 150;
        int filasObtenidas = tabla.rows.size();
        assertEquals(filasObtenidas, filasEsperadas);
        //Comprobamos numero de columnas
        int columnasEsperadas = 5;
        int columnasObtenidas = tabla.headers.size();
        assertEquals(columnasObtenidas,columnasEsperadas);
        //Comprobamos que las headers son las correspondientes
        List<String> headersEsperadas = new ArrayList<>();
        headersEsperadas.add("sepal length");
        headersEsperadas.add("sepal width");
        headersEsperadas.add("petal length");
        headersEsperadas.add("petal width");
        headersEsperadas.add("class");
        List<String> headersObtenidas = tabla.headers;
        assertEquals(headersObtenidas.get(0), headersEsperadas.get(0));
        assertEquals(headersObtenidas.get(1), headersEsperadas.get(1));
        assertEquals(headersObtenidas.get(2), headersEsperadas.get(2));
        assertEquals(headersObtenidas.get(3), headersEsperadas.get(3));
        assertEquals(headersObtenidas.get(4), headersEsperadas.get(4));
        //Comprobamos el numero que se le asgina a cada fila es correcto
        List<Double> rowEsperada = new ArrayList<>();
        rowEsperada.add(5.1); rowEsperada.add(3.5);rowEsperada.add(1.4);rowEsperada.add(0.2);
        Row rowAComparar = new Row(rowEsperada);
        assertEquals(rowAComparar.getData(),tabla.getRowAt(0).getData());
        rowEsperada.clear();
        rowEsperada.add(5.4); rowEsperada.add(3.9);rowEsperada.add(1.3);rowEsperada.add(0.4);
        rowAComparar = new Row(rowEsperada);
        assertEquals(rowAComparar.getData(),tabla.getRowAt(16).getData());
    }

}