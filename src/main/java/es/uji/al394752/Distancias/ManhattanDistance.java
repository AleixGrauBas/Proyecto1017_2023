package es.uji.al394752.Distancias;

import java.util.List;

public class ManhattanDistance implements Distance{
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        Double suma = 0.0;
        for (int j = 0; j < q.size();j++){
            suma += Math.abs(p.get(j) - q.get(j));
        }
        return suma;
    }
}
