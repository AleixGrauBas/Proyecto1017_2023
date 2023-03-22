package es.uji.al394752;

import java.util.ArrayList;
import java.util.List;

public class Table {
    List<String> headers = new ArrayList<>();

    List<Row> rows = new ArrayList<>();

    public Row getRowAt(int rowNumber){
        return rows.get(rowNumber);
    }

    public void addHeader(List<String> header){
        headers.addAll(header);
    }

    public void addRow(Row row){
        rows.add(row);
    }
}
