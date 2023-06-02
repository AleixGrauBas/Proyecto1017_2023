package es.uji.al394752;

import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.RowWithLabel;
import es.uji.al394752.clases.Table;
import es.uji.al394752.lectura.CSVLabeledFileReader;
import es.uji.al394752.lectura.CSVUnlabeledFileReader;
import es.uji.al394752.lectura.ReaderTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTemplateTest {

    @Test
    void readTableFromSource() {
        String separator = System.getProperty( "file.separator" );
        ReaderTemplate readerTemplate = new CSVUnlabeledFileReader("src"+ separator + "miles_dollars.csv");
        Table tabla = readerTemplate.readTableFromSource();
        //Comprobamos que lee el numero de filas correcto
        int filasEsperadas = 25;
        int filasObtenidas = tabla.getSize();
        assertEquals(filasEsperadas, filasObtenidas);
        //Comprobamos numero de columnas
        int columnasEsperadas = 2;
        int columnasObtenidas = tabla.getSizeHeaders();
        assertEquals(columnasEsperadas,columnasObtenidas);
        //Comprobamos que las headers son las correspondientes
        List<String> headersEsperadas = new ArrayList<>();
        headersEsperadas.add("Miles");
        headersEsperadas.add("Dollars");
        List<String> headersObtenidas = tabla.getHeaders();
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

        //TEST CVLALEBELEDFILEREADER
        readerTemplate = new CSVLabeledFileReader("src"+ separator + "iris.csv");
        //Comprobamos que lee el numero de filas correcto
        tabla = readerTemplate.readTableFromSource();
        filasEsperadas = 150;
        filasObtenidas = tabla.getSize();
        assertEquals(filasEsperadas, filasObtenidas);
        //Comprobamos numero de columnas
        columnasEsperadas = 5;
        columnasObtenidas = tabla.getSizeHeaders();
        assertEquals(columnasObtenidas,columnasEsperadas);
        //Comprobamos que las headers son las correspondientes
        headersEsperadas = new ArrayList<>();
        headersEsperadas.add("sepal length");
        headersEsperadas.add("sepal width");
        headersEsperadas.add("petal length");
        headersEsperadas.add("petal width");
        headersEsperadas.add("class");
        headersObtenidas = tabla.getHeaders();
        assertEquals(headersObtenidas.get(0), headersEsperadas.get(0));
        assertEquals(headersObtenidas.get(1), headersEsperadas.get(1));
        assertEquals(headersObtenidas.get(2), headersEsperadas.get(2));
        assertEquals(headersObtenidas.get(3), headersEsperadas.get(3));
        assertEquals(headersObtenidas.get(4), headersEsperadas.get(4));
        //Comprobamos el numero que se le asgina a cada fila es correcto
        rowEsperada = new ArrayList<>();
        rowEsperada.add(5.1); rowEsperada.add(3.5);rowEsperada.add(1.4);rowEsperada.add(0.2);
        rowAComparar = new Row(rowEsperada);
        assertEquals(rowAComparar.getData(),tabla.getRowAt(0).getData());
        rowEsperada.clear();
        rowEsperada.add(5.4); rowEsperada.add(3.9);rowEsperada.add(1.3);rowEsperada.add(0.4);
        rowAComparar = new Row(rowEsperada);
        assertEquals(rowAComparar.getData(),tabla.getRowAt(16).getData());
        List<Double> newRow = new ArrayList<>();
        newRow.add(5.2);newRow.add(1.2);newRow.add(5.3);newRow.add(7.2);
        int index = tabla.getLabel("Iris-Setosa");
        System.out.println(index);
        RowWithLabel esperada = new RowWithLabel(newRow, index);
        tabla.addRow(esperada);
        assertEquals(esperada.getData(),tabla.getRowAt(tabla.getRowsSize() -1).getData());
        index = tabla.getLabel("Iris-versicolor");
        System.out.println(index);
        index = tabla.getLabel("Iris-virginica");
        System.out.println(index);
    }
}