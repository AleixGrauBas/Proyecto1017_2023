package org.example;

import java.util.ArrayList;
import java.util.List;

public class Row {
    List<Double> row;
    Row(List<Double> row){
        this.row = row;
    }
    public List<Double> getData(){
        return row;
    }
}
