package es.uji.al394752.algoritmos;

import es.uji.al394752.distancias.Distance;
import es.uji.al394752.distancias.EuclideanDistance;
import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.Table;

import java.util.*;

public class KMeans implements Algorithm<Table,  Integer,List<Double>> , DistanceClient{
    private int numClusters;
    private int numIterations;
    private long seed;

    private Distance distance;

    public KMeans(int numClusters, int numIterations, long seed, Distance distance) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.distance = distance;
    }
    public KMeans(int numClusters, int numIterations, long seed) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.distance = new EuclideanDistance();
    }
    private List<Row> centros = new ArrayList<>();
    private void crearCentros(Table table){
        Random random = new Random(seed);
        for (int i = 0; i < numClusters; i++){
            Row row =table.getRowAt(Math.abs(random.nextInt()% table.getSize()));
            centros.add(row);
        }
    }

    private void recalcularCentros(Map<Row, Integer> listaClusters, int numeroParametros){
        for (int k = 0; k < centros.size(); k++){
            List<Double> punto = new ArrayList<>();
            for (int j = 0; j < numeroParametros; j++){
                double suma = 0;
                int cantidad = 0;
                //Buscamos todas las rows de dicho centro y calculamos la suma
                for (Map.Entry<Row, Integer> entry : listaClusters.entrySet()) {
                    if (entry.getValue() != null) {
                        if (entry.getValue() == k) {
                            suma += entry.getKey().getData().get(j);
                            cantidad++;
                        }
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
    private void darCentros(Table datos){

        int numeroParametros = datos.getSizeHeaders();

        Map<Row, Integer> listaClusters = new HashMap<>();
        for (int iteraciones = 0; iteraciones < numIterations; iteraciones++) {

            //Miramos a que centro pertenece cada row
            for (int i = 0; i < datos.getSize(); i++) {
                Row rowAComparar = datos.getRowAt(i);
                listaClusters.put(rowAComparar, estimate(rowAComparar.getData()));
            }

            //Recaulculamos los centros donde k es el grupo
            recalcularCentros(listaClusters,numeroParametros);

        }
    }
    public void train(Table datos){

        int numeroParametros = datos.getSizeHeaders();

        crearCentros(datos);
        //Lista que almacena las rows con su numero de centro correspondiente
        darCentros(datos);


    }


    public Integer estimate(List<Double> data){
        Integer tipo = null;
        Double distanciaMin = distance.calculateDistance(data, centros.get(0).getData());
        for (int i = 1; i < centros.size();i++){
            Double distancia;
            distancia = distance.calculateDistance(data, centros.get(i).getData());
            if (distancia < distanciaMin) {
                distanciaMin = distancia;
                tipo = i;
            }
        }
        return tipo;
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
