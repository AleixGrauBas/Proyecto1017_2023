package es.uji.al394752;

import es.uji.al394752.Algoritmos.KMeans;
import es.uji.al394752.Distancias.EuclideanDistance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KMeansTest {
    KMeans kmeans = new KMeans(3,10,100, new EuclideanDistance());
    Table datos = new Table();
    @BeforeEach
    void start(){
        List<String> headers = new ArrayList<>();
        headers.add("uno");headers.add("dos");headers.add("tres");headers.add("cuatro");headers.add("cinco");

        List<Double> anyadir = new ArrayList<>();
        Row rowAnyadir;
        anyadir.add(4.6);anyadir.add(3.4);anyadir.add(1.4);anyadir.add(0.3);
        rowAnyadir = new Row(anyadir);
        datos.addRow(rowAnyadir);
        anyadir.add(6.0);anyadir.add(3.0);anyadir.add(4.8);anyadir.add(1.8);
        rowAnyadir = new Row(anyadir);
        datos.addRow(rowAnyadir);
        anyadir.add(5.5);anyadir.add(2.4);anyadir.add(3.7);anyadir.add(1.0);
        rowAnyadir = new Row(anyadir);
        datos.addRow(rowAnyadir);
    }

    @Test
    void train() {
        kmeans.train(datos);
        assertNotNull(kmeans);
    }
    @Test
    void estimate() {
        kmeans.train(datos);
        List<Double> row2 = new ArrayList<>();
        row2.add(4.7);row2.add(3.2);row2.add(1.3);row2.add(0.2);
        Integer esperado = kmeans.estimate(row2);

        List<Double> row1 = new ArrayList<>();
        row1.add(4.6);row1.add(3.4);row1.add(1.4);row1.add(0.3);
        Integer resultado = kmeans.estimate(row1);

        assertEquals(esperado, resultado);
    }

}