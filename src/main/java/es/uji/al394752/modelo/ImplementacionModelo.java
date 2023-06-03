package es.uji.al394752.modelo;

import es.uji.al394752.vista.Vista;
import es.uji.al394752.distancias.Distance;
import es.uji.al394752.distancias.EuclideanDistance;
import es.uji.al394752.distancias.ManhattanDistance;
import es.uji.al394752.lectura.CSV;
import es.uji.al394752.recsys.RecSys;
import es.uji.al394752.algoritmos.KNN;
import es.uji.al394752.algoritmos.KMeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionModelo implements Modelo{
    private Vista vista;
    public  List<String> recomendaciones;
    public void setVista(Vista vista) {
        this.vista = vista;
    }
    private RecSys recSys;
    private String recomendacion;
    private String distancia;
    private List<String> names;
    //Devolvemos las recomendaciones
    public void setRecomendacion(String recomendacion) {
        this.recSys = null;
        this.recomendacion = recomendacion;
    }
    public boolean getRecSys(){return this.recSys == null;}

    @Override
    public List<String> getNombres() {
        String sep = System.getProperty("file.separator");
        String ruta = "src" + sep + "data";
        try {
            names = readNames(ruta+sep+"songs_test_names.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    public void setDistancia(String distancia) {
        recSys = null;
        this.distancia = distancia;
    }
    //actualiza la lista recomendacions cada vez que hay un cambio
    @Override
    public void recomendarCanciones(String cancion, int numRecomendaciones) throws IOException {
        recomendaciones = recSys.recommend(cancion, numRecomendaciones);
    }
    //Inicializa la recomendacion de canciones con la distancia y algoritmo que ya hemos establecido
    @Override
    public void run() throws IOException {
        CSV csv = new CSV();
        String sep = System.getProperty("file.separator");
        String ruta = "src" + sep + "data";

        Distance distance;
        if (distancia.equals("Euclidean"))
            distance = new EuclideanDistance();
        else
            distance = new ManhattanDistance();

        if (recomendacion.equals("KNN")){
            KNN knn = new KNN();
            knn.setDistance(distance);
            recSys = new RecSys(knn);
            recSys.train(csv.readTableWithLabels(ruta + sep + "songs_train.csv"));
            recSys.run(csv.readTableWithLabels(ruta + sep + "songs_test.csv"), names);
        }
        else {
            KMeans kmeans = new KMeans(15, 200, 4321);
            kmeans.setDistance(distance);
            recSys = new RecSys(kmeans);
            recSys.train(csv.readTable(ruta + sep + "songs_train_withoutnames.csv"));
            recSys.run(csv.readTable(ruta + sep + "songs_test_withoutnames.csv"), names);
        }
    }

    @Override
    public List<String> hayNuevasRecomendaciones() {
        return recomendaciones;
    }


    private List<String> readNames(String fileOfItemNames) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileOfItemNames));
        String line;
        List<String> names = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            names.add(line);
        }
        br.close();
        return names;
    }
}
