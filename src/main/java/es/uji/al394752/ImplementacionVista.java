package es.uji.al394752;

import es.uji.al394752.Controlador;
import es.uji.al394752.Modelo;
import es.uji.al394752.Vista;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionVista implements Vista {
    private Modelo modelo;
    private Controlador controlador;
    private Stage stage;
    private String recomendacion;
    private String distancia;
    private String cancion;
    private int numRecomendaciones;

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
    public ImplementacionVista(Stage stage){
        this.stage = stage;
    }
    public void creaGUI() throws IOException {
        stage.setTitle("Song Recommender");
        StackPane root = new StackPane();

        // Names of items
        //Primer apartado Recommendation Type
        //Titulo
        Label RecommendationType = new Label("  Algorithm");
        RecommendationType.setStyle("-fx-font-weight: bold;");
        VBox vbox = new VBox(RecommendationType);
        vbox.setAlignment(Pos.BASELINE_LEFT);
        //Botones
        RadioButton opcion1 = new RadioButton("KNN");
        RadioButton opcion2 = new RadioButton("KMeans");
        ToggleGroup grupo = new ToggleGroup();
        opcion1.setToggleGroup(grupo);
        opcion2.setToggleGroup(grupo);
        grupo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Toggle selectedToggle = grupo.getSelectedToggle();
                RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                recomendacion = selectedRadioButton.getText();
                controlador.anyadeTipoRecomendacion();
            }
        });

        Label DistanceType = new Label("  Distance Type");
        DistanceType.setStyle("-fx-font-weight: bold;");

        RadioButton Euclidean = new RadioButton("Euclidean");
        RadioButton Manhattan = new RadioButton("Manhattan");
        ToggleGroup grupo2 = new ToggleGroup();
        Euclidean.setToggleGroup(grupo2);
        Manhattan.setToggleGroup(grupo2);
        grupo2.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Toggle selectedToggle = grupo2.getSelectedToggle();
                RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                distancia = selectedRadioButton.getText();
                controlador.anyadeDistancia();
            }
        });

        Label SongTitles = new Label("  Song Titles");
        SongTitles.setStyle("-fx-font-weight: bold;");

        String sep = System.getProperty("file.separator");
        String ruta = "src" + sep + "data" + sep + "songs_test_names.csv";
        ObservableList<String> elementos = FXCollections.observableArrayList();
        elementos.addAll(readNames(ruta));

        Tooltip tooltip = new Tooltip("Double click for recommendations based on this song");

        ListView<String> lista = new ListView<String>();
        lista.setItems(elementos);
        lista.setTooltip(tooltip);

        Button boton = new Button("Recommend");
        boton.setOnAction(event -> {
            cancion = lista.getSelectionModel().getSelectedItem();
            try {
                cambiarVentana();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        EventHandler<MouseEvent> doubleClickHandler = new EventHandler<MouseEvent>() {
            private final Duration DOUBLE_CLICK_TIME_THRESHOLD = Duration.millis(300);
            private long lastClickTime = 0;
            @Override
            public void handle(MouseEvent event) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_THRESHOLD.toMillis()) {
                    System.out.println("Doble clic detectado");
                }
                lastClickTime = clickTime;
            }
        };
        root.setOnMouseClicked(doubleClickHandler);

        boton.disableProperty().bind(Bindings.isNull(lista.getSelectionModel().selectedItemProperty()));
        StringBinding textoBoton = Bindings.createStringBinding(() ->
                lista.getSelectionModel().getSelectedItem() != null
                        ? "Reccomend " + lista.getSelectionModel().getSelectedItem()
                        : "Recommend", lista.getSelectionModel().selectedItemProperty());

        // Vincular la propiedad text del botón con la StringBinding
        boton.textProperty().bind(textoBoton);
        vbox = new VBox(10, RecommendationType ,opcion1, opcion2,DistanceType, Euclidean, Manhattan,SongTitles, lista, boton);
        root.getChildren().add(vbox);
        stage.setScene(new Scene(root, 250, 250));
        stage.show();
    }
    private void cambiarVentana() throws IOException {
        // Crear una nueva Stage y una nueva escena con los elementos de la segunda ventana
        controlador.run();
        Stage secundaryStage = new Stage();
        secundaryStage.setTitle("Recommended Titles");
        HBox root = new HBox();
        ObservableList<String> elementos = FXCollections.observableArrayList();

        Label numberOfRecommendations = new Label("Number of recommendations: ");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        Spinner<Integer> spinner = new Spinner<>(valueFactory);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            numRecomendaciones = spinner.getValue();
            try {
                controlador.recomendarCanciones();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            elementos.setAll(hayNuevasRecomendaciones());
        });
        //Creamos un HBOX para que este uno al lado del otro
        HBox hbox = new HBox(10, numberOfRecommendations,spinner);
        hbox.setAlignment(Pos.BASELINE_LEFT);

        Label liked = new Label("if you liked " + cancion + " you might like");

        ListView<String> lista = new ListView<String>(elementos);


        Button boton = new Button("close");
        boton.setOnAction(event -> secundaryStage.close());

        VBox vbox = new VBox(10, hbox, liked,lista, boton);


        root.getChildren().add(vbox);
        secundaryStage.setScene(new Scene(root, 250, 250));
        secundaryStage.show();
    }
    private List<String> readNames(String fileOfItemNames) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileOfItemNames));
        String line;
        List<String> names = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            names.add(line);
        }
        br.close();
        return names;
    }

    @Override
    public String getRecomendacion() {
        return recomendacion;
    }

    @Override
    public String getDistancia() {
        return distancia;
    }

    @Override
    public String getCanciones() {
        return cancion;
    }

    @Override
    public int getNumRecomendaciones() {
        return numRecomendaciones;
    }

    @Override
    public List<String> hayNuevasRecomendaciones() {
        return modelo.hayNuevasRecomendaciones();
    }
}
