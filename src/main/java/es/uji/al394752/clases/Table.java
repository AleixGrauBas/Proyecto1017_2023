package es.uji.al394752.clases;

import java.util.ArrayList;
import java.util.List;

public class Table {
    protected List<String> headers = new ArrayList<>();

    protected List<Row> rows = new ArrayList<>();

    public Row getRowAt(int rowNumber){
        return rows.get(rowNumber);
    }

    public void addHeader(List<String> header){
        headers.addAll(header);
    }

    public int getRowsSize() { return rows.size();}
    public void addRow(Row row){
        rows.add(row);
    }
    public int getSize(){return rows.size();}
    public int getSizeHeaders(){return headers.size() ;}

    public List<String> getHeaders(){return  headers;}

    public int getLabel(String dato) {
        return 0;
    }
}
