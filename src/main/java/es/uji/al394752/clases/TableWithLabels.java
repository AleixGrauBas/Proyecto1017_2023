package es.uji.al394752.clases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table{
    private Map<String, Integer> labelsToIndex = new HashMap<>();
    public RowWithLabel getRowAt(int rowNumber){
        return (RowWithLabel) rows.get(rowNumber);
    }
    public Integer getLabelIndice(String s){
        return labelsToIndex.get(s);
    }
    public String getLabel(Integer n){
        for (String s: labelsToIndex.keySet()){
            if (labelsToIndex.get(s) == n) {
                return s;
            }
        }
        return null;
    }

    public void addHeader(List<String> header){
        headers.addAll(header);
    }
    public int getLabel(String label){
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
        return index;
    }

}
