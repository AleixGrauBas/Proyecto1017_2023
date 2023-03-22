package es.uji.al394752;

import java.util.List;

public class RowWithLabel extends Row{
    int numberClass;
    RowWithLabel(List<Double> row, int numberClass) {
        super(row);
        this.numberClass = numberClass;
    }
    public int getNumberClass(){
        return numberClass;
    }
}
