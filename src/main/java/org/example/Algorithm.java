package org.example;

public interface Algorithm <T extends Table> {

    void train(T table);

    T estimate(T data);
}
