package es.uji.al394752;

import java.io.IOException;
import java.util.List;

public interface Controlador {
     void anyadeTipoRecomendacion();
     void anyadeDistancia();
     void run() throws IOException;
     void recomendarCanciones() throws IOException;

}
