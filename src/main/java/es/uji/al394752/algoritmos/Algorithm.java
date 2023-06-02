package es.uji.al394752.algoritmos;

import es.uji.al394752.clases.Table;

public interface Algorithm <T extends Table, K, J>{

    void train(T table);

    K estimate(J data);
}
