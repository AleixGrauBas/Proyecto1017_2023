package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table{
    Map<String, Integer> labelsToIndex = new HashMap<>();

    public RowWithLabel getRowAt(int rowNumber){
        return (RowWithLabel) rows.get(rowNumber);
    }

    public void addHeader(List<String> header){
        headers.addAll(header);
    }

    public void addRow(Row row, String label){
        int index = 0;
        //Buscamos si ya existe una entrada para dicha label
        //Si no existe la añadimos
        if (!labelsToIndex.containsKey(label)){
            index = labelsToIndex.size();
            labelsToIndex.put(label, index);
        } else { //Si existe la buscamos
            for(String s : labelsToIndex.keySet()){
                if (s.equals(label)){
                    index = labelsToIndex.get(s);
                }
            }
        }
        //Creamos la row con el numberClass nuevo o encontrado
        RowWithLabel rowWithLabel = new RowWithLabel(row.getData(), index );
        rows.add(rowWithLabel);

    }
}
