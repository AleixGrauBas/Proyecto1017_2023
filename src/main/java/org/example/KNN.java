package org.example;

import java.util.List;

public class KNN{
    TableWithLabels tabla;
    public void train(TableWithLabels data){
        this.tabla = data;
    }

    public Integer estimate(List<Double> data){
        Integer tipo = null;
        Double distanciaMin = null;
        for (int i = 0; i < tabla.rows.size();i++){
            Double suma = 0.0;
            Double distancia;
            RowWithLabel rowAux = tabla.getRowAt(i);
            List<Double> dataAux = rowAux.getData();
            for (int j = 0; j < data.size();j++){
                suma += Math.pow(data.get(j) - dataAux.get(j), 2);
            }
            distancia = Math.sqrt(suma);
            if (tipo == null){
                distanciaMin = distancia;
                tipo = rowAux.getNumberClass();
            } else if (distancia < distanciaMin) {
                distanciaMin = distancia;
                tipo = rowAux.getNumberClass();
            }
        }
        return tipo;
    }
}
