import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<String> langs = FXCollections.observableArrayList();

        Parent root = FXMLLoader.load(getClass().getResource("sample/sample.fxml"));
        primaryStage.setTitle("NotePad");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        OrbClass.init(args);
        launch(args);

    }
}
