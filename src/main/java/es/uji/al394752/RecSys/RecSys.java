package es.uji.al394752.RecSys;

import es.uji.al394752.Algoritmos.Algorithm;
import es.uji.al394752.Table;

import java.util.ArrayList;
import java.util.List;

public class RecSys {
    private Algorithm algorithm;
    private List<Integer> listaEstimate = new ArrayList<>();
    Table testData;
    private List<String> testItemNames;
    public RecSys(Algorithm algorithm){
        this.algorithm = algorithm;
    }
    public void train(Table trainData){
        algorithm.train(trainData);
    }
    public void run(Table testData, List<String> testItemNames){
        this.testData = testData;
        this.testItemNames = testItemNames;
        estimate();
    }
    private void estimate(){
        for (int i = 0; i < this.testData.getSize(); i++){
            listaEstimate.add((Integer) algorithm.estimate(testData.getRowAt(i).getData()));
        }
    }
    private int findName(String nameItem){
        int indice = -1;
        for (int i = 0; i < testItemNames.size();i++){
            if (testItemNames.get(i).equals(nameItem))
                indice = i;
        }
        return indice;
    }

    private List<String> selectItems(int indiceLikedItem, int labelLikedItem, int numRec){
        List<String> resultado = new ArrayList<>();
        for (int i = 0; i < numRec; i++){
            for (int j = 0; j < listaEstimate.size(); j++){
                if (listaEstimate.get(j) != null) {
                    if (listaEstimate.get(j) == labelLikedItem && j != indiceLikedItem) {
                        i++;
                        resultado.add(testItemNames.get(j));
                    }
                }
                if (i >= numRec)
                    break;
            }
        }
        return resultado;
    }
    //Buscamos el indice de la cancion que ha gustado y el label que le hemos dado a esta. Luego buscamos canciones con el mismo label
    public List<String> recommend(String nameLikedItem, int numRecommendations){
        int indiceLikedItem = findName(nameLikedItem);
        return selectItems(indiceLikedItem,listaEstimate.get(indiceLikedItem), numRecommendations);
    }
}
