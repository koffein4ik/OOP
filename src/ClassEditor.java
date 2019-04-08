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
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ClassEditor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setPadding(new Insets(10));

        ListView<String> createdClassesView = new ListView<>(list123);

        objFactory.add(new humanFactory());
        objFactory.add(new employeeFactory());
        objFactory.add(new aircrewMFactory());
        objFactory.add(new passengerFactory());
        objFactory.add(new pilotFactory());
        objFactory.add(new securityFactory());
        objFactory.add(new planeFactory());
        objFactory.add(new cargoPlaneFactory());
        objFactory.add(new passengerPlaneFactory());
        objFactory.add(new airportFactory());

        primaryStage.setTitle("Class Editor");
        primaryStage.centerOnScreen();
        Button btn1 = new Button("Add");
        btn1.setOnAction(event->CreateClass.createWindow());
        btn1.setPrefWidth(80);
        btn1.setLayoutX(250);
        btn1.setLayoutY(500);
        Button btn2 = new Button("Delete");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createdClasses.remove(createdClassesView.getSelectionModel().getSelectedIndex());
                list123.remove(createdClassesView.getSelectionModel().getSelectedIndex());
                for(int i = 0; i < createdClasses.size(); i++)
                {
                    Class<?> cl = ClassEditor.createdClasses.get(i).getClass();
                    Field[] fields = cl.getFields();
                    for (Field field: fields)
                    {
                        String fieldtype = field.getType().toString();
                        switch (fieldtype) {
                            case "class java.util.ArrayList":
                            {
                                ArrayList<Object> ItemsToCheck = new ArrayList<>();
                                ItemsToCheck = GetFieldData.getComplexData(i, field.getName());
                                for (int j = 0; j < ItemsToCheck.size(); j++)
                                {
                                    if (isObjectDeleted(ItemsToCheck.get(j)))
                                        ItemsToCheck.remove(j);
                                }
                                break;
                            }
                            default :
                            {
                                Object obj = GetFieldData.getObject(i, field.getName());
                                if (isObjectDeleted(obj))
                                {
                                    try
                                    {
                                        String settername = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                                        Method m1 = createdClasses.get(i).getClass().getMethod(settername,obj.getClass());
                                        obj = null;
                                        m1.invoke(createdClasses.get(i), obj);
                                    }
                                    catch(Exception ex)
                                    {
                                        System.out.println(ex.toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        btn2.setPrefWidth(80);
        btn2.setLayoutX(380);
        btn2.setLayoutY(500);
        Button btn3 = new Button("Edit");
        btn3.setOnAction(event->FieldEditor.createWindow(createdClassesView.getSelectionModel().getSelectedIndex()));
        btn3.setPrefWidth(80);
        btn3.setLayoutY(500);
        btn3.setLayoutX(500);
        root.getChildren().addAll(createdClassesView, btn1, btn2, btn3);
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.setWidth(850);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void update(String name)
    {
        int currIndex = createdClasses.size() - 1;
        try
        {
            Method m = createdClasses.get(currIndex).getClass().getMethod("setName", String.class);
            m.invoke(createdClasses.get(currIndex), name);
            Method m1 = createdClasses.get(currIndex).getClass().getMethod("getName");
            System.out.println(m1.invoke(createdClasses.get(currIndex)));

        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        list123.add(createdClasses.get(currIndex).getClass().getName() + " " + name);
    }
    public static ObservableList<String> list123 = FXCollections.observableArrayList();
    public static ArrayList<factory> objFactory = new ArrayList<factory>();
    public static ArrayList<Object> createdClasses = new ArrayList<Object>();

    public static Boolean isObjectDeleted(Object obj)
    {
        if (!createdClasses.contains(obj))
            return true;
        else
            return false;
    }

}
