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

    private List<RadioButton> crearOpciones(ToggleGroup toggleGroup, String... nombres) {
        List<RadioButton> opciones = new ArrayList<>();

        for (String nombre : nombres) {
            RadioButton radioButton = new RadioButton(nombre);
            radioButton.setToggleGroup(toggleGroup);
            opciones.add(radioButton);
        }

        return opciones;
    }
    private List<RadioButton> crearToggleGroupsAlgoritmo(){
        //Creamos y añadimos botones al togglegroup
        ToggleGroup algoritmo = new ToggleGroup();
        List<RadioButton> opcionesAlgoritmo = crearOpciones(algoritmo, "KNN", "KMeans");

        //Elegimos el primero por defecto y lo actualizamos en el controlador
        algoritmo.selectToggle(opcionesAlgoritmo.get(0));
        recomendacion = opcionesAlgoritmo.get(0).getText();
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
        return opcionesAlgoritmo;
    }
    private List<RadioButton> crearToggleGroupsDistancia() {
        //Creamos y añadimos los botones a un togglegroup
        ToggleGroup distancias = new ToggleGroup();
        List<RadioButton> opcionesDistancias = crearOpciones(distancias, "Euclidean", "Manhattan");

        //Hacemos que por defecto se elija euclidean y actualizamos en el controlador
        distancias.selectToggle(opcionesDistancias.get(0));
        distancia = opcionesDistancias.get(0).getText();
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
        return opcionesDistancias;
    }
    private List<Label> crearLabels(List<String> lista){
        List<Label> labels= new ArrayList<>();

        for (String s : lista){
            Label label = new Label(s);
            label.setStyle("-fx-font-weight: bold;");
            labels.add(label);
        }

        return labels;
    }
    private Button botonRecommend(ListView<String> lista){
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
        return boton;
    }
    private ListView<String> listaSeleccion(){
        //Leemos los nombres de las canciones y los añadimos a una ObservanleList
        ObservableList<String> elementos = FXCollections.observableArrayList();
        elementos.addAll(modelo.getNombres());
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
        return lista;
    }
    public void creaGUI() throws IOException {
        //Ventana y nombre de la ventana
        stage.setTitle("Song Recommender");
        StackPane root = new StackPane();

        ArrayList<String> labels = new ArrayList<>();
        labels.add("  Algorithm"); labels.add("  Distance Type"); labels.add("  Song Titles");
        List<Label> listaLabels = crearLabels(labels);

        List<RadioButton> opcionesAlgoritmo = crearToggleGroupsAlgoritmo();

        List<RadioButton> opcionesDistancias = crearToggleGroupsDistancia();

        ListView<String> lista = listaSeleccion();
        Button boton = botonRecommend(lista);

        VBox vbox = new VBox(10, listaLabels.get(0) ,opcionesAlgoritmo.get(0), opcionesAlgoritmo.get(1),listaLabels.get(1), opcionesDistancias.get(0), opcionesDistancias.get(1),listaLabels.get(2), lista, boton);
        root.getChildren().add(vbox);
        stage.setScene(new Scene(root, 250, 250));
        stage.show();
    }

    private Spinner<Integer> crearSpinner(ObservableList<String> elementos){
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
        return spinner;
    }
    private HBox inicialiar(Stage secundaryStage, String cancion){
        secundaryStage.setTitle("Recommended Titles for " + cancion);
        HBox root = new HBox();
        secundaryStage.setScene(new Scene(root, 250, 250));
        secundaryStage.show();
        return root;
    }
    private void cambiarVentana() throws IOException {
        // Crear una nueva Stage y una nueva escena con los elementos de la segunda ventana
        Stage secundaryStage = new Stage();
        HBox root = inicialiar(secundaryStage,cancion);

        Label numberOfRecommendations = new Label("Number of recommendations: ");

        ObservableList<String> elementos = FXCollections.observableArrayList();
        Spinner<Integer> spinner = crearSpinner(elementos);

        //Creamos un HBOX para que este uno al lado del otro
        HBox hbox = new HBox(10, numberOfRecommendations,spinner);
        hbox.setAlignment(Pos.BASELINE_LEFT);

        //Añadimos los elementos a la vista antes de hacer el calculo costoso run
        Label liked = new Label("if you liked " + cancion + " you might like");
        elementos = inicializarElementos(elementos,spinner);

        //boton de cerrar y añadimos todo a la vista
        Button boton = botonClose(secundaryStage);
        root.getChildren().add(new VBox(10, hbox, liked, new ListView<String>(elementos), boton));
    }
    private ObservableList<String> inicializarElementos(ObservableList<String> elementos, Spinner<Integer> spinner){
        //Llamamos a run para inicializar las recomendaciones y a recomendarCanciones con el valor del spinner por defecto para que nos muestre las 3 primeras
        try {
            numRecomendaciones = spinner.getValue();
            controlador.recomendarCanciones();
            elementos.setAll(hayNuevasRecomendaciones());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return elementos;
    }
    private Button botonClose(Stage secundaryStage){
        Button boton = new Button("close");
        boton.setOnAction(event -> secundaryStage.close());
        boton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white");
        return boton;
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
