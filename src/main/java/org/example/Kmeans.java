package org.example;

import java.util.*;

public class Kmeans {
    int numClusters;
    int numIterations;
    long seed;

    public Kmeans(int numClusters, int numIterations, long seed) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
    }
    List<Row> centros = new ArrayList<>();
    public void train(Table datos){
        Random random = new Random(seed);
        int numeroParametros = datos.rows.size();
        //Creamos los centros aleatorios
        for (int i = 0; i < numClusters; i++){
            List<Double> punto = new ArrayList<>();
            for (int j = 0; j <= numeroParametros; j++){
                punto.add(random.nextDouble());
            }
            Row row = new Row(punto);
            centros.add(row);
        }
        //Lista que almacena las rows con su numero de centro correspondiente
        Map<Row, Integer> listaClusters = new HashMap<>();
        for (int iteraciones = 0; iteraciones < numIterations; iteraciones++) {
            //Miramos a que centro pertenece cada row
            for (int i = 0; i < datos.rows.size(); i++) {
                Double distanciaMenor = null;
                int centro = 0;
                Row rowAComparar = datos.rows.get(i);
                //Calculamos de cual esta mes cerca
                for (int j = 0; j < centros.size(); j++) {
                    Row rowCentro = centros.get(j);
                    Double distancia = calcularDistancia(rowAComparar, rowCentro);
                    if (distanciaMenor == null) {
                        distanciaMenor = distancia;
                        centro = j;
                    } else if (distancia < distanciaMenor) {
                        distanciaMenor = distancia;
                        centro = j;
                    }
                }
                listaClusters.put(rowAComparar, centro);
                //Recaulculamos los centros donde k es el grupo
                for (int k = 0; k < centros.size(); k++){
                    List<Double> punto = new ArrayList<>();
                    for (int j = 0; j <= numeroParametros; j++){
                        double suma = 0;
                        int cantidad = 0;
                        //Buscamos todas las rows de dicho centro y calculamos la suma
                        for (Row row : listaClusters.keySet()) {
                            if (listaClusters.get(row) == k) {
                                suma += row.getData().get(j);
                                cantidad ++;
                            }
                        }
                        if (cantidad == 0){
                            punto.add(centros.get(k).getData().get(j));
                        } else{
                            punto.add(suma/cantidad);
                        }

                    }
                    Row row = new Row(punto);
                    centros.set(k, row);
                }
            }

        }

    }
    public Integer estimate(List<Double> data){
        Integer tipo = null;
        Double distanciaMin = null;
        Row dato = new Row(data);
        for (int i = 0; i < centros.size();i++){
            Double distancia;
            distancia = calcularDistancia(dato, centros.get(i));
            if (tipo == null){
                distanciaMin = distancia;
                tipo = i;
            } else if (distancia < distanciaMin) {
                distanciaMin = distancia;
                tipo = i;
            }
        }
        return tipo;
    }
    private Double calcularDistancia(Row rowAComparar, Row rowCentro){
        Double suma = 0.0;
        List<Double> dataAux = rowAComparar.getData();
        List<Double> data = rowCentro.getData();
        for (int j = 0; j < data.size();j++){
            suma += Math.pow(data.get(j) - dataAux.get(j), 2);
        }
        return Math.sqrt(suma);
    }
}
