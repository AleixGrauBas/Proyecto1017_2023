package es.uji.al394752;

public interface Algorithm <T extends Table, K, J>{

    void train(T table);

    K estimate(J data);
}
