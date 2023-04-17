package es.uji.al394752.Distancias;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EuclideanDistanceTest {

    @Test
    void calculateDistance() {
        Distance distance = new EuclideanDistance();
        List<Double> p = new ArrayList<>();
        List<Double> q = new ArrayList<>();
        p.add(3.0); p.add(3.5);p.add(4.0);p.add(6.0);
        q.add(-5.1);q.add(-5.2);q.add(2.0);q.add(5.0);
        double esperado = 12.095;
        double obtenido = ((int)(distance.calculateDistance(p,q)*1000.0)/1000.0);
        assertEquals(esperado,obtenido);
    }
}