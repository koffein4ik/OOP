import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
        //root.setPadding(new Insets(10));
        root.setPadding(new Insets(10));
        //root.setHgap(40);
        //root.setVgap(10);
        Class<Airport> Airp = Airport.class;
        Field[] declaredFields = Airp.getDeclaredFields();
        for (Field field : declaredFields)
        {
            System.out.println(field);
        }
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
        ClassView.setPrefHeight(400);
        ClassView.setLayoutX(10);
        ClassView.setLayoutY(10);
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
        root.getChildren().addAll(ClassView, btn1, btn2);
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.setWidth(850);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
