import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class ChooseSerializationType extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public static void createWindow(File saveFile)
    {
        Pane root = new Pane();
        Scene scene = new Scene(root, 200, 100);
        Stage serializationChooser = new Stage();
        ObservableList<String> availableClasses = FXCollections.observableArrayList();
        availableClasses.add("Binary");
        availableClasses.add("JSON");
        availableClasses.add("koffSerializer");
        ComboBox cbSerType = new ComboBox(availableClasses);
        Button okBtn = new Button("Ok");
        cbSerType.setLayoutX(40);
        cbSerType.setPrefWidth(120);
        cbSerType.setLayoutY(10);
        cbSerType.getSelectionModel().selectFirst();
        ArrayList<SerializeFactory> serFactory = new ArrayList<>();
        serFactory.add(new BinarySerializer());
        serFactory.add(new GsonSerializer());
        serFactory.add(new koffSerializer());
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                serFactory.get(cbSerType.getSelectionModel().getSelectedIndex()).serialize(saveFile);
            }
        });
        okBtn.setLayoutX(50);
        okBtn.setPrefWidth(100);
        okBtn.setLayoutY(50);
        root.getChildren().add(cbSerType);
        root.getChildren().add(okBtn);
        serializationChooser.initModality(Modality.APPLICATION_MODAL);
        serializationChooser.setResizable(false);
        serializationChooser.setScene(scene);
        serializationChooser.showAndWait();
    }
}
