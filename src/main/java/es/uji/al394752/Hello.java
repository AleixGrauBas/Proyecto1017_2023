package es.uji.al394752;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hello extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Song Recommender");
        StackPane root = new StackPane();

        // Names of items
        //Primer apartado Recommendation Type
        //Titulo
        Label RecommendationType = new Label("  Recommendation Type");
        RecommendationType.setStyle("-fx-font-weight: bold;");
        VBox vbox = new VBox(RecommendationType);
        vbox.setAlignment(Pos.BASELINE_LEFT);
        //Botones
        RadioButton opcion1 = new RadioButton("Recommend based on song features");
        RadioButton opcion2 = new RadioButton("Recommend based on guessed genre");
        ToggleGroup grupo = new ToggleGroup();
        opcion1.setToggleGroup(grupo);
        opcion2.setToggleGroup(grupo);

        Label DistanceType = new Label("  Distance Type");
        DistanceType.setStyle("-fx-font-weight: bold;");

        RadioButton Euclidean = new RadioButton("Euclidean");
        RadioButton Manhattan = new RadioButton("Manhattan");
        ToggleGroup grupo2 = new ToggleGroup();
        Euclidean.setToggleGroup(grupo2);
        Manhattan.setToggleGroup(grupo2);

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
        boton.disableProperty().bind(Bindings.isNull(lista.getSelectionModel().selectedItemProperty()));
        StringBinding textoBoton = Bindings.createStringBinding(() ->
                lista.getSelectionModel().getSelectedItem() != null
                        ? "Reccomend " + lista.getSelectionModel().getSelectedItem()
                        : "Recommend", lista.getSelectionModel().selectedItemProperty());

        // Vincular la propiedad text del botón con la StringBinding
        boton.textProperty().bind(textoBoton);
        vbox = new VBox(10, RecommendationType ,opcion1, opcion2,DistanceType, Euclidean, Manhattan,SongTitles, lista, boton);
        root.getChildren().add(vbox);
        ListView<String> songTitles = new ListView<>();
        //Button btn = new Button("Hola");
        //root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 250, 250));
        primaryStage.show();
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
}
