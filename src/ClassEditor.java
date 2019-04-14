import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ClassEditor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static ObservableList<String> objectsList = FXCollections.observableArrayList();
    public static ArrayList<factory> objFactory = new ArrayList<factory>();
    public static ArrayList<Object> createdClasses = new ArrayList<Object>();

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setPadding(new Insets(10));
        Button btn4 = new Button();
        ListView<String> createdClassesView = new ListView<>(objectsList);

        createdClassesView.setLayoutX(20);
        createdClassesView.setLayoutY(20);
        createdClassesView.setPrefWidth(300);

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
        btn1.setLayoutX(20);
        btn1.setLayoutY(430);
        ArrayList<String>primitiveTypes = new ArrayList<>();
        primitiveTypes.add("int");
        primitiveTypes.add("class java.lang.String");
        Button btn2 = new Button("Delete");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (createdClassesView.getSelectionModel().getSelectedIndex() == (-1)) return;
                createdClasses.remove(createdClassesView.getSelectionModel().getSelectedIndex());

                objectsList.remove(createdClassesView.getSelectionModel().getSelectedIndex());
                for(int i = 0; i < createdClasses.size(); i++)
                {
                    Class<?> cl = ClassEditor.createdClasses.get(i).getClass();
                    Field[] fields = cl.getFields();
                    for (Field field: fields)
                    {
                        Class<?> type = field.getType();
//                        Class<?> arrayListClass = ArrayList.class;
                        //type.equals(ArrayList.class)
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
                                if(primitiveTypes.contains(fieldtype)) break;
                                Object obj = GetFieldData.getObject(i, field.getName());
                                if (isObjectDeleted(obj))
                                {
                                    try
                                    {
                                        String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                                        Method m1 = createdClasses.get(i).getClass().getMethod(setterName, field.getType());
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
        btn2.setLayoutX(130);
        btn2.setLayoutY(430);
        Button btn3 = new Button("Edit");
        btn3.setOnAction(event -> FieldEditor.createWindow(createdClassesView.getSelectionModel().getSelectedIndex()));
        btn3.setPrefWidth(80);
        btn3.setLayoutY(430);
        btn3.setLayoutX(240);
        root.getChildren().addAll(createdClassesView, btn1, btn2, btn3);
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.setWidth(350);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    static void update()
    {
        objectsList.clear();
        for (Object createdClass : createdClasses) {
            try {
                Method m1 = createdClass.getClass().getMethod("getName");
                String name = m1.invoke(createdClass).toString();
                objectsList.add(createdClass.getClass().getName() + " " + name);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }

    private static Boolean isObjectDeleted(Object obj)
    {
        return !(createdClasses.contains(obj));
    }

}
