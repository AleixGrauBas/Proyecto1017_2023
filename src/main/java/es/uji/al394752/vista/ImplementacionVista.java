package es.uji.al394752.vista;

import es.uji.al394752.controlador.Controlador;
import es.uji.al394752.modelo.Modelo;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        //Ventana y nombre de la ventana
        stage.setTitle("Song Recommender");
        StackPane root = new StackPane();

        //Titulo eleccion de algoritmo
        Label RecommendationType = new Label("  Algorithm");
        RecommendationType.setStyle("-fx-font-weight: bold;");

        //Creamos y añadimos botones al togglegroup
        RadioButton knn = new RadioButton("KNN");
        RadioButton kmeans = new RadioButton("KMeans");
        ToggleGroup algoritmo = new ToggleGroup();
        knn.setToggleGroup(algoritmo);
        kmeans.setToggleGroup(algoritmo);
        //Elegimos el primero por defecto y lo actualizamos en el controlador
        algoritmo.selectToggle(knn);
        recomendacion = knn.getText();
        controlador.anyadeTipoRecomendacion();
        //Gestion de los botones
        algoritmo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Toggle selectedToggle = algoritmo.getSelectedToggle();
                RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                recomendacion = selectedRadioButton.getText();
                controlador.anyadeTipoRecomendacion();
            }
        });

        //Titulo eleccion del tipo de distancia
        Label DistanceType = new Label("  Distance Type");
        DistanceType.setStyle("-fx-font-weight: bold;");
        //Creamos y añadimos los botones a un togglegroup
        RadioButton Euclidean = new RadioButton("Euclidean");
        RadioButton Manhattan = new RadioButton("Manhattan");
        ToggleGroup distancias = new ToggleGroup();
        Euclidean.setToggleGroup(distancias);
        Manhattan.setToggleGroup(distancias);
        //Hacemos que por defecto se elija euclidean y actualizamos en el controlador
        distancias.selectToggle(Euclidean);
        distancia = Euclidean.getText();
        controlador.anyadeDistancia();
        //Gestion de los botones
        distancias.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Toggle selectedToggle = distancias.getSelectedToggle();
                RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                distancia = selectedRadioButton.getText();
                controlador.anyadeDistancia();
            }
        });

        //Añadimos el titulo de la lista de canciones a elegir
        Label SongTitles = new Label("  Song Titles");
        SongTitles.setStyle("-fx-font-weight: bold;");
        //Leemos los nombres de las canciones y los añadimos a una ObservanleList
        String sep = System.getProperty("file.separator");
        String ruta = "src" + sep + "data" + sep + "songs_test_names.csv";
        ObservableList<String> elementos = FXCollections.observableArrayList();
        elementos.addAll(readNames(ruta));

        //Añadimos un tooltip para que nos aparezca informacion sobre las canciones
        Tooltip tooltip = new Tooltip("Double click for recommendations based on this song");

        ListView<String> lista = new ListView<String>();
        lista.setItems(elementos);
        lista.setTooltip(tooltip);
        //Añadimos la funcionalidad doble click
        lista.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                cancion = lista.getSelectionModel().getSelectedItem();
                try {
                    cambiarVentana();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //añadimos el boton recomendar
        Button boton = new Button("Recommend");
        boton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white");
        boton.setOnAction(event -> {
            cancion = lista.getSelectionModel().getSelectedItem();
            try {
                cambiarVentana();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //Hacemos que ponga Recommend o Recommend + cancion si hay alguna seleccionada
        boton.disableProperty().bind(Bindings.isNull(lista.getSelectionModel().selectedItemProperty()));
        StringBinding textoBoton = Bindings.createStringBinding(() ->
                lista.getSelectionModel().getSelectedItem() != null
                        ? "Reccomend " + lista.getSelectionModel().getSelectedItem()
                        : "Recommend", lista.getSelectionModel().selectedItemProperty());

        // Vincular la propiedad text del botón con la StringBinding
        boton.textProperty().bind(textoBoton);
        VBox vbox = new VBox(10, RecommendationType ,knn, kmeans,DistanceType, Euclidean, Manhattan,SongTitles, lista, boton);
        root.getChildren().add(vbox);
        stage.setScene(new Scene(root, 250, 250));
        stage.show();
    }
    private void cambiarVentana() throws IOException {
        // Crear una nueva Stage y una nueva escena con los elementos de la segunda ventana
        Stage secundaryStage = new Stage();
        secundaryStage.setTitle("Recommended Titles");
        HBox root = new HBox();
        secundaryStage.setScene(new Scene(root, 250, 250));
        secundaryStage.show();
        //ObservableList para las canciones que vamos a recomendar
        ObservableList<String> elementos = FXCollections.observableArrayList();
        //Creamos un spinner para seleccionar el numero de recomendaciones y hacemos que su valor por defecto sea 3 para mostrar al principio 3 canciones
        Label numberOfRecommendations = new Label("Number of recommendations: ");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100,3);
        Spinner<Integer> spinner = new Spinner<>(valueFactory);
        //Hacemos que cada vez que se actualice el spinner llame a recomendar canciones y añada los elementos a la observable list
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
        //Añadimos los elementos a la vista antes de hacer el calculo costoso run
        Label liked = new Label("if you liked " + cancion + " you might like");
        VBox vbox = new VBox(10, hbox, liked);
        root.getChildren().add(vbox);
        //Llamamos a run para inicializar las recomendaciones y a recomendarCanciones con el valor del spinner por defecto para que nos muestre las 3 primeras
        numRecomendaciones = spinner.getValue();
        controlador.run();
        controlador.recomendarCanciones();
        elementos.setAll(hayNuevasRecomendaciones());
        ListView<String> lista = new ListView<String>(elementos);

        //boton de cerrar y añadimos todo a la vista
        Button boton = new Button("close");
        boton.setOnAction(event -> secundaryStage.close());
        boton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white");

        vbox = new VBox(10, hbox, liked,lista, boton);


        root.getChildren().add(vbox);

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
