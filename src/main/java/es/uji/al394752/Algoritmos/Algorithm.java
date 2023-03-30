package es.uji.al394752.Algoritmos;

import es.uji.al394752.Table;

public interface Algorithm <T extends Table, K, J>{

    void train(T table);

    K estimate(J data);
}
