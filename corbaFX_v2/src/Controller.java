import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import javafx.collections.ObservableList;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import NotePadApp.*;
import javafx.scene.layout.HBox;

public class Controller implements Initializable {

    static NotePad notePadImpl;

    @FXML
    private Button addButton;

    @FXML
    private Button showButton;

    @FXML
    private Button deleteButton;

    @FXML
    public ListView listView;

    public void addEvent(ActionEvent event) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);

        TextArea textArea = new TextArea();
        textArea.setMaxSize(250, 50);

        Button addButton = new Button(">");
        HBox.setMargin(addButton, new Insets(0, 10, 0, 10));

        Button deleteButton = new Button("Delete");

        hBox.getChildren().add(textArea);
        hBox.getChildren().add(addButton);
        hBox.getChildren().add(deleteButton);

        ObservableList langs = listView.getItems();
        langs.add(hBox);

        listView.setItems(langs);

        addButton.setOnAction(event1 -> addText(hBox));
        deleteButton.setOnAction(event1 -> deleteEvent(hBox));

        OrbClass.addNullNote();
    }

    private void addText(HBox hBox) {
        ObservableList observableList = listView.getItems();
        int n = observableList.indexOf(hBox);
        System.out.println("addButton:");

        TextArea textArea = (TextArea) hBox.getChildren().get(0);

        if (OrbClass.addNote(textArea.getText(), n)) {
            System.out.println("String added: \n" + textArea.getText() + "\n");
        } else {
            System.out.println("Something went wrong :(");
        }
    }

    public void showEvent() {
        if (OrbClass.showNotes()) {
            String[] strings = OrbClass.getNotes();

            System.out.println("strings:" + Arrays.toString(strings));
        } else {
            System.out.println("Something went wrong :(");
        }
    }

    public void getAllNotes() {
        String[] strings = OrbClass.getNotes();

        if (strings.length > 0) {
            ObservableList observableList = listView.getItems();

            for (String string : strings) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);

                TextArea textArea = new TextArea();
                textArea.setMaxSize(250, 50);
                textArea.setText(string);

                Button addButton = new Button(">");
                HBox.setMargin(addButton, new Insets(0, 10, 0, 10));

                Button deleteButton = new Button("Delete");

                hBox.getChildren().add(textArea);
                hBox.getChildren().add(addButton);
                hBox.getChildren().add(deleteButton);

                observableList.add(hBox);

                listView.setItems(observableList);

                addButton.setOnAction(event1 -> addText(hBox));
                deleteButton.setOnAction(event1 -> deleteEvent(hBox));
            }
        }
    }

    private void deleteEvent(HBox hBox) {
        ObservableList observableList = listView.getItems();
        int n = observableList.indexOf(hBox);
        System.out.println("deleteButton:");

        TextArea textArea = (TextArea) hBox.getChildren().get(0);

        System.out.println("--n--" + n);

        if (OrbClass.deleteNote(n)) {
            System.out.println("String number " + n + " " + textArea.getText() + " deleted\n");

            observableList.remove(n);
            listView.setItems(observableList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//      todo

        getAllNotes();
    }
}
