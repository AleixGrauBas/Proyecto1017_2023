package es.uji.al394752.vista;

import java.util.List;

public interface Vista {
    String getRecomendacion();
    String getDistancia();
    String getCanciones();
    int getNumRecomendaciones();
    List<String> hayNuevasRecomendaciones();
}
