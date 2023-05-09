package es.uji.al394752;

import java.io.IOException;
import java.util.List;

public class ImplementacionControlador implements Controlador{
    private Vista vista;
    private Modelo modelo;

    public void setVista(Vista vista) {
        this.vista = vista;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public void anyadeTipoRecomendacion() {
        String recomendacion = vista.getRecomendacion();
        modelo.setRecomendacion(recomendacion);
    }

    @Override
    public void anyadeDistancia() {
        String distancia = vista.getDistancia();
        modelo.setDistancia(distancia);
    }

    @Override
    public void run() throws IOException {
        modelo.run();
    }

    @Override
    public void recomendarCanciones() throws IOException {
        String cancion = vista.getCanciones();
        int numRecomendaciones = vista.getNumRecomendaciones();
        modelo.recomendarCanciones(cancion, numRecomendaciones);
    }

}
