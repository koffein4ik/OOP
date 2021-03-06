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
import sun.plugin2.main.server.Plugin;

import java.io.File;
import java.io.FileOutputStream;
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
        ObservableList<String> availableSerTypes = FXCollections.observableArrayList();
        ObservableList<String> availablePlugins = FXCollections.observableArrayList();
//        availableSerTypes.add("Binary");
//        availableSerTypes.add("JSON");
//        availableSerTypes.add("koffSerializer");
        ArrayList<encoder> plugins = PluginTest.getPlugins();
        availablePlugins.add("Don't use plugin");
        for (int i = 0; i < plugins.size(); i++)
        {
            availablePlugins.add(plugins.get(i).getClass().getName());
        }
        ArrayList<SerializeFactory> serFactory = new ArrayList<>();
        serFactory.add(new BinarySerializer());
        serFactory.add(new GsonSerializer());
        serFactory.add(new koffSerializer());
        for (int i = 0; i < serFactory.size(); i++)
        {
            availableSerTypes.add(serFactory.get(i).getExtension());
        }
        ComboBox cbSerType = new ComboBox(availableSerTypes);
        ComboBox cbPluginChooser = new ComboBox(availablePlugins);
        cbPluginChooser.getSelectionModel().selectFirst();
        Button okBtn = new Button("Ok");
        cbSerType.setLayoutX(40);
        cbSerType.setPrefWidth(120);
        cbSerType.setLayoutY(10);
        cbPluginChooser.setLayoutX(40);
        cbPluginChooser.setPrefWidth(120);
        cbPluginChooser.setLayoutY(40);
        cbSerType.getSelectionModel().selectFirst();
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                byte[] serResult = serFactory.get(cbSerType.getSelectionModel().getSelectedIndex()).serialize(ClassEditor.createdClasses);
                String strResult = "";
                try
                {
                    int selecetdIndex = cbPluginChooser.getSelectionModel().getSelectedIndex();
                    FileOutputStream fOut;
                    if (cbPluginChooser.getSelectionModel().getSelectedIndex() > 0)
                    {
                        strResult = plugins.get(cbPluginChooser.getSelectionModel().getSelectedIndex() - 1).encode(serResult);
                        fOut = new FileOutputStream(saveFile.getAbsolutePath() + "." + serFactory.get(cbSerType.getSelectionModel().getSelectedIndex()).getExtension() + "." + plugins.get(cbPluginChooser.getSelectionModel().getSelectedIndex() - 1).getExtension(), false);
                        fOut.write(strResult.getBytes());
                    }
                    else
                    {
                        fOut = new FileOutputStream(saveFile.getAbsolutePath() + "." + serFactory.get(cbSerType.getSelectionModel().getSelectedIndex()).getExtension(), false);
                        fOut.write(serResult);
                    }
                    fOut.close();
                }
                catch (Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });
        okBtn.setLayoutX(50);
        okBtn.setPrefWidth(100);
        okBtn.setLayoutY(80);
        root.getChildren().add(cbSerType);
        root.getChildren().add(cbPluginChooser);
        root.getChildren().add(okBtn);
        serializationChooser.initModality(Modality.APPLICATION_MODAL);
        serializationChooser.setResizable(false);
        serializationChooser.setScene(scene);
        serializationChooser.showAndWait();
    }
}
