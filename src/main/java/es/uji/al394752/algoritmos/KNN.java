package es.uji.al394752.algoritmos;

import es.uji.al394752.distancias.Distance;
import es.uji.al394752.distancias.EuclideanDistance;
import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.RowWithLabel;
import es.uji.al394752.clases.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels,  Integer,List<Double>> , DistanceClient{
    TableWithLabels tabla;
    private Distance distance;
    public KNN(TableWithLabels tabla, Distance distance ){
        this.tabla = tabla;
        this.distance = distance;
    }

    public KNN() {
        distance = new EuclideanDistance();
    }

    public void train(TableWithLabels data){
        this.tabla = data;
    }

    public Integer estimate(List<Double> data){
        Integer tipo = null;
        Row row = new Row(data);
        Double distanciaMin = distance.calculateDistance(tabla.getRowAt(0).getData(), data);
        Double distancia;
        for (int i = 1; i < tabla.getSize();i++){
            RowWithLabel rowAux = tabla.getRowAt(i);
            distancia = distance.calculateDistance(rowAux.getData(),data);
            if (distancia < distanciaMin) {
                distanciaMin = distancia;
                tipo = rowAux.getNumberClass();
            }
        }
        return tipo;
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
