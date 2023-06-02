package es.uji.al394752;

import es.uji.al394752.algoritmos.KNN;
import es.uji.al394752.clases.TableWithLabels;
import es.uji.al394752.lectura.CSV;
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

        List<Double> data2 = new ArrayList<>();
        data2.add(6.1);
        data2.add(2.9);
        data2.add(4.7);
        data2.add(1.4);

        List<Double> data3 = new ArrayList<>();
        data3.add(5.9);
        data3.add(3.0);
        data3.add(5.1);
        data3.add(1.8);

        CSV csv = new CSV();
        String separator = System.getProperty( "file.separator" );
        TableWithLabels table = csv.readTableWithLabels("src" + separator + "iris.csv");
        KNN knn = new KNN();
        knn.train(table);

        Integer resultado = knn.estimate(data);
        Integer esperado = table.getLabelIndice("Iris-setosa");

        Integer resultado2 = knn.estimate(data2);
        Integer esperado2 = table.getLabelIndice("Iris-versicolor");

        Integer resultado3 = knn.estimate(data3);
        Integer esperado3 = table.getLabelIndice("Iris-virginica");

        assertEquals(esperado, resultado);
        assertEquals(esperado2, resultado2);
        assertEquals(esperado3, resultado3);
    }
}