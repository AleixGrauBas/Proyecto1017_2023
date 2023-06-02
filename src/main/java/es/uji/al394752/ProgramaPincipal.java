package es.uji.al394752;
import es.uji.al394752.controlador.ImplementacionControlador;
import es.uji.al394752.modelo.ImplementacionModelo;
import es.uji.al394752.vista.ImplementacionVista;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramaPincipal extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ImplementacionControlador controlador = new ImplementacionControlador();
        ImplementacionModelo modelo = new ImplementacionModelo();
        ImplementacionVista vista = new ImplementacionVista(primaryStage);
        modelo.setVista(vista);
        controlador.setVista(vista);
        controlador.setModelo(modelo);
        vista.setModelo(modelo);
        vista.setControlador(controlador);
        vista.creaGUI();
    }
}
