import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Method;

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
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cbClassNames.getSelectionModel().getSelectedIndex() == (-1)) return;
                ClassEditor.createdClasses.add(ClassEditor.objFactory.get(cbClassNames.getSelectionModel().getSelectedIndex()).create());
                int currIndex = ClassEditor.createdClasses.size() - 1;
                try {
                    Method m = ClassEditor.createdClasses.get(currIndex).getClass().getMethod("setName", String.class);
                    m.invoke(ClassEditor.createdClasses.get(currIndex), tf1.getText());
                    ClassEditor.update();
                }
                catch (Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });
        //okButton.setOnAction(event -> {ClassEditor.createdClasses.add(ClassEditor.objFactory.get(cbClassNames.getSelectionModel().getSelectedIndex()).create()); ClassEditor.update(tf1.getText());});
        //System.out.println(cbClassNames.getVisibleRowCount());
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
