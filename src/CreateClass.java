import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.lang.reflect.Method;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CreateClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public static void createWindow()
    {
        int chosenIndex = 0;
        ObservableList<String> availableClasses = FXCollections.observableArrayList();
        for (int i = 0; i < ClassEditor.objFactory.size(); i++)
        {
            try
            {
                Method create = ClassEditor.objFactory.get(i).getClass().getMethod("create");
                availableClasses.add(create.getReturnType().toString().replace("class ", ""));
            }
            catch(Exception ex)
            {

            }
        }
        TextField tf1 = new TextField();
        tf1.setLayoutX(150);
        Button okButton = new Button("OK");
        okButton.setLayoutX(50);
        okButton.setLayoutY(30);
        ComboBox cbClassNames = new ComboBox<String>(availableClasses);
        okButton.setOnAction(event -> {ClassEditor.createdClasses.add(ClassEditor.objFactory.get(cbClassNames.getSelectionModel().getSelectedIndex()).create()); ClassEditor.update(tf1.getText());});
        System.out.println(cbClassNames.getVisibleRowCount());
        Stage creatorWindow = new Stage();
        creatorWindow.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        pane.getChildren().addAll(cbClassNames, okButton, tf1);
        Scene scene = new Scene(pane, 360, 100);
        creatorWindow.setResizable(false);
        creatorWindow.setScene(scene);
        creatorWindow.showAndWait();
    }
}
