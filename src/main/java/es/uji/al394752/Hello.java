package es.uji.al394752;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class Hello extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        StackPane root = new StackPane();

        // Names of items
        List<String> names = readNames(ruta+sep+"songs_test_names.csv");
        Label SongTitles = new Label("Song Titles");
        root.getChildren().add(SongTitles);
        ListView<String> songTitles = new ListView<>();
        //Button btn = new Button("Hola");
        //root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 250, 250));
        primaryStage.show();
    }
}
