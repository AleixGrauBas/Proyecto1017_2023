package es.uji.al394752.clases;

import java.util.ArrayList;
import java.util.List;

public class Row {
    protected List<Double> row;
    public Row(List<Double> row){
        this.row = row;
    }
    public List<Double> getData(){
        return row;
    }
}
