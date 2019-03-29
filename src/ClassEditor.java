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

import java.lang.reflect.Constructor;
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
        Class<PassengerPlane> Airp = PassengerPlane.class;
        Field[] declaredFields = Airp.getFields();
        for (Field field : declaredFields)
        {
            System.out.println(field);
        }
        declaredFields[1].setAccessible(true);
        Passenger Heathrow = new Passenger();
        Class<?> h = Heathrow.getClass();
        System.out.println(h.getTypeName());
        try {
            //Field name = h.getDeclaredField("name");
            Field name =h.getField("name");
            String loc = "London";
            name.set(Heathrow, loc);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        System.out.println(Heathrow.name);
        try {
            Class<?> clazz = Class.forName("Airport");
            Object example = clazz.newInstance();
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }

        ArrayList<Object> classes = new ArrayList<Object>();


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
        //ObservableList<String> classes = FXCollections.observableArrayList(classNames);
        ObservableList<String> createdClasses = FXCollections.observableArrayList();
        ListView<String> createdClassesView = new ListView<String>(createdClasses);
        createdClasses.add("Airbus");
        createdClasses.add("John");

        ArrayList<humanFactory> humanCreator = new ArrayList<humanFactory>();
        humanCreator.add(new employeeFactory());
        humanCreator.add(new humanFactory());
        humanCreator.add(new pilotFactory());
        humanCreator.add(new aircrewMFactory());
        humanCreator.add(new securityFactory());
        humanCreator.add(new passengerFactory());
        Object John = humanCreator.get(2).createPerson();
        Class<?> pil = John.getClass();
        String name1 = "John";
        try {
            Field name2 = pil.getField("name");
            name2.set(John, name1);
        }
        catch (Exception ex)
        {

        }
        System.out.println(((Human) John).name);

        Field[] declaredFields1 = pil.getFields();
        for (Field field : declaredFields1)
        {
            System.out.println(field);
        }
        try {
            Field mfield = John.getClass().getField("name");
            System.out.println("---" + mfield.get(John).toString());
        }
        catch (Exception ex)
        {

        }


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
        root.getChildren().addAll(createdClassesView, /*editor,*/ btn1, btn2);
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.setWidth(850);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
