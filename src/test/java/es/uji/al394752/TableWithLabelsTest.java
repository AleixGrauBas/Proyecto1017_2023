package es.uji.al394752;

import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.RowWithLabel;
import es.uji.al394752.clases.TableWithLabels;
import es.uji.al394752.lectura.CSV;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TableWithLabelsTest {
    private CSV csv = new CSV();
    private String separator = System.getProperty( "file.separator" );
    private TableWithLabels tabla = csv.readTableWithLabels( "src" + separator + "iris.csv");
    @Test
    void getRowAt() {
        List<Double> esperado = new LinkedList<>();
        esperado.add(5.1);
        esperado.add(3.5);
        esperado.add(1.4);
        esperado.add(0.2);
        String labelEsperado = "Iris-setosa";
        Row rowEsperada = new Row(esperado);
        RowWithLabel resultado = tabla.getRowAt(0);
        //Buscamos la string que coincida con el numberClass que esperamos
        String labelResultado = tabla.getLabel(0);
        assertEquals(rowEsperada.getData(),resultado.getData());
        assertEquals(labelEsperado, labelResultado );
    }
    @org.junit.jupiter.api.Test
    void addRow(){
        List<Double> newRow = new ArrayList<>();
        newRow.add(5.2);newRow.add(1.2);newRow.add(5.3);newRow.add(7.2);
        int index = tabla.getLabel("Iris-Setosa");
        RowWithLabel esperada = new RowWithLabel(newRow, index);
        tabla.addRow(esperada);
        assertEquals(esperada.getData(),tabla.getRowAt(tabla.getRowsSize()-1).getData());
    }
}