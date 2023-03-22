package es.uji.al394752;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TableWithLabelsTest {

    @Test
    void getRowAt() {
        List<Double> esperado = new LinkedList<>();
        esperado.add(5.1);
        esperado.add(3.5);
        esperado.add(1.4);
        esperado.add(0.2);
        String labelEsperado = "Iris-setosa";
        Row rowEsperada = new Row(esperado);
        CSV csv = new CSV();
        String separator = System.getProperty( "file.separator" );
        TableWithLabels table = csv.readTableWithLabels("src" + separator + "iris.csv");
        RowWithLabel resultado = table.getRowAt(0);
        String labelResultado = null;
        //Buscamos la string que coincida con el numberClass que esperamos
        for (String s: table.labelsToIndex.keySet()){
            if (table.labelsToIndex.get(s) == 0) {
                labelResultado = s;
            }
        }
        assertEquals(rowEsperada.getData(),resultado.getData());
        assertEquals(labelEsperado, labelResultado );
    }
    @org.junit.jupiter.api.Test
    void addRow(){
        CSV csv = new CSV();
        String separator = System.getProperty( "file.separator" );
        TableWithLabels tabla = csv.readTableWithLabels( "src" + separator + "iris.csv");
        List<Double> newRow = new ArrayList<>();
        newRow.add(5.2);newRow.add(1.2);newRow.add(5.3);newRow.add(7.2);
        Row esperada = new Row(newRow);
        tabla.addRow(esperada, "Iris-Setosa");
        assertEquals(esperada.getData(),tabla.getRowAt(tabla.rows.size()-1).getData());
    }
}