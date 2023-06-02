package es.uji.al394752.lectura;

import es.uji.al394752.clases.Row;
import es.uji.al394752.clases.Table;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class ReaderTemplate {

    protected Table table;
    private String source;
    protected Scanner sc = null;
    public ReaderTemplate(String source){
        this.source = source;
        this.table = new Table();
    }
    protected void openSource(String source) throws FileNotFoundException {
            sc = new Scanner(new FileReader(source));
    }
    protected void processHeaders(String headers) {
        String[] cabeceras = headers.split(",");
        List<String> auxH = new ArrayList<>();
        auxH.addAll(Arrays.asList(cabeceras));
        table.addHeader(auxH);
    }
    abstract void processData(String data);
    protected void closeSource() {
        sc.close();
    }
    protected boolean hasMoreData() {
        return sc.hasNext();
    }
    // comprueba si hay más datos; en nuestro caso, si hay mas línea(s) en el fichero CSV
    String getNextData() {
        return sc.nextLine();
    } // obtener el siguiente dato; una línea del fichero CSV en nuestro caso

    public final Table readTableFromSource()  {
        try{
            openSource(source);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String headers = getNextData();
        processHeaders(headers);
        while (hasMoreData()){
            String data = getNextData();
            processData(data);
        }
        closeSource();
        return table;
    }
}
