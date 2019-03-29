import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CreateClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public static <names extends Enum<names>> void createWindow(names[] aClasses)
    {
        ComboBox cbnames = new ComboBox<>();
        cbnames.getItems().addAll(aClasses);
        Stage creatorWindow = new Stage();
        creatorWindow.initModality(Modality.APPLICATION_MODAL);
        Pane pane = new Pane();
        pane.getChildren().addAll(cbnames);
        Scene scene = new Scene(pane, 360, 100);
        creatorWindow.setScene(scene);
        creatorWindow.showAndWait();
        ArrayList<humanFactory> humanCreator = new ArrayList<humanFactory>();
        humanCreator.add(new employeeFactory());
        humanCreator.add(new humanFactory());
        humanCreator.add(new pilotFactory());
        humanCreator.add(new aircrewMFactory());
        humanCreator.add(new securityFactory());
        humanCreator.add(new passengerFactory());
        Object John = humanCreator.get(2).createPerson();
        Class<PassengerPlane> pil = PassengerPlane.class;
        Field[] declaredFields = pil.getFields();
        for (Field field : declaredFields)
        {
            System.out.println(field);
        }
    }
}
