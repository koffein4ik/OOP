import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ClassEditor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setPadding(new Insets(10));
        Class<Airport> Airp = Airport.class;
        Field[] declaredFields = Airp.getDeclaredFields();
        for (Field field : declaredFields)
        {
            System.out.println(field);
        }
        declaredFields[1].setAccessible(true);
        Airport Heathrow = new Airport();
        Class<?> h = Heathrow.getClass();
        try {
            Field location = h.getDeclaredField("location");
            String loc = "London";
            location.set(Heathrow, loc);
        }
        catch (Exception ex)
        {

        };
        System.out.println(Heathrow.location);
        ArrayList<String> classNames = new ArrayList<String>();
        classNames.add(Airport.class.getName());
        classNames.add(AircrewMember.class.getName());
        classNames.add(Plane.class.getName());
        classNames.add(CargoPlane.class.getName());
        classNames.add(Employee.class.getName());
        classNames.add(Human.class.getName());
        classNames.add(Passenger.class.getName());
        classNames.add(PassengerPlane.class.getName());
        classNames.add(Pilot.class.getName());
        ObservableList<String> classes = FXCollections.observableArrayList(classNames);
        ListView<String> ClassView = new ListView<String>(classes);
        ObservableList<String> createdClasses = FXCollections.observableArrayList();
        ListView<String> createdClassesView = new ListView<String>(createdClasses);
        ClassView.setPrefHeight(400);
        ClassView.setPrefWidth(200);
        ClassView.setLayoutX(10);
        ClassView.setLayoutY(10);
        createdClassesView.setPrefHeight(400);
        createdClassesView.setLayoutX(250);
        createdClassesView.setLayoutY(10);
        createdClasses.add("Airbus");
        createdClasses.add("John");
        primaryStage.setTitle("Class Editor");
        primaryStage.centerOnScreen();
        Button btn1 = new Button("Add");
        btn1.setOnAction(event->ModalWindow.newWindow());
        btn1.setPrefWidth(80);
        btn1.setLayoutX(320);
        btn1.setLayoutY(500);
        Button btn2 = new Button("Delete");
        btn2.setPrefWidth(80);
        btn2.setLayoutX(420);
        btn2.setLayoutY(500);
        root.getChildren().addAll(ClassView, createdClassesView, /*editor,*/ btn1, btn2);
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.setWidth(850);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
