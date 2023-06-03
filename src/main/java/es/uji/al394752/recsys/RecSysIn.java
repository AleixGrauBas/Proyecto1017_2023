package es.uji.al394752.recsys;

import es.uji.al394752.clases.Table;

import java.util.List;

public interface RecSysIn {

    void train(Table trainData);
    void run(Table testData, List<String> testItemNames);
    List<String> recommend(String nameLikedItem, int numRecommendations);

}
