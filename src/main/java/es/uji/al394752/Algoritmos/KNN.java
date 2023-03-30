package es.uji.al394752.Algoritmos;

import es.uji.al394752.Algoritmos.Algorithm;
import es.uji.al394752.Row;
import es.uji.al394752.RowWithLabel;
import es.uji.al394752.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels,  Integer,List<Double>> {
    TableWithLabels tabla;
    public void train(TableWithLabels data){
        this.tabla = data;
    }

    public Integer estimate(List<Double> data){
        Integer tipo = null;
        Row row = new Row(data);
        Double distanciaMin = calcularDistancia(tabla.getRowAt(0), row);
        Double distancia;
        for (int i = 1; i < tabla.getSize();i++){
            RowWithLabel rowAux = tabla.getRowAt(i);
            distancia = calcularDistancia(rowAux,row);
            if (distancia < distanciaMin) {
                distanciaMin = distancia;
                tipo = rowAux.getNumberClass();
            }
        }
        return tipo;
    }
    private Double calcularDistancia(Row rowAComparar, Row rowCentro){
        Double suma = 0.0;
        List<Double> dataAux = rowAComparar.getData();
        List<Double> data = rowCentro.getData();
        for (int j = 0; j < data.size() ;j++){
            suma += Math.pow(data.get(j) - dataAux.get(j), 2);
        }
        return Math.sqrt(suma);
    }
}
