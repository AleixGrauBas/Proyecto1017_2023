package es.uji.al394752.modelo;

import es.uji.al394752.recsys.RecSys;

import java.io.IOException;
import java.util.List;

public interface Modelo {
    void setRecomendacion(String recomendacion);
    void setDistancia(String distancia);
    void recomendarCanciones(String cancion, int numRecomendaciones) throws IOException;
    void run() throws IOException;
    List<String> hayNuevasRecomendaciones();

    boolean getRecSys();

    List<String> getNombres();
}
