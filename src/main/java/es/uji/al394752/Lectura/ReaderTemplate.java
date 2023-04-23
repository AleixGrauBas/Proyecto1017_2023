package es.uji.al394752.Lectura;

import es.uji.al394752.Table;

import java.io.BufferedReader;

public abstract class ReaderTemplate {

    protected Table table;
    private String source;

    public ReaderTemplate(String source){
        this.source = source;
        this.table = new Table();
    }
    abstract void openSource(String source);
    abstract void processHeaders(String headers);
    abstract void processData(String data);
    abstract void  closeSource();
    abstract boolean hasMoreData();// comprueba si hay más datos; en nuestro caso, si hay mas línea(s) en el fichero CSV
    abstract String getNextData(); // obtener el siguiente dato; una línea del fichero CSV en nuestro caso

    public final Table readTableFromSource(){
        openSource(source);
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
