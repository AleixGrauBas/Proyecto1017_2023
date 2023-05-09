package es.uji.al394752;

import java.io.IOException;
import java.util.List;

public interface Modelo {
    void setRecomendacion(String recomendacion);
    void setDistancia(String distancia);
    void recomendarCanciones(String cancion, int numRecomendaciones) throws IOException;
    void run() throws IOException;
    List<String> hayNuevasRecomendaciones();
}
