package es.uji.al394752;

import es.uji.al394752.Algoritmos.KNN;
import es.uji.al394752.Lectura.CSV;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KNNTest {

    @Test
    void estimate() {
        List<Double> data = new ArrayList<>();
        data.add(4.6);
        data.add(3.1);
        data.add(1.5);
        data.add(0.2);

        CSV csv = new CSV();
        String separator = System.getProperty( "file.separator" );
        TableWithLabels table = csv.readTableWithLabels("src" + separator + "iris.csv");
        KNN knn = new KNN();
        knn.train(table);

        Integer resultado = knn.estimate(data);
        Integer esperado = table.getLabelIndice("Iris-setosa");

        assertEquals(esperado, resultado);
    }
}