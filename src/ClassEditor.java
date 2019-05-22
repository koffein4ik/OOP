import com.google.gson.Gson;
//import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
        ListView<String> createdClassesView = new ListView<>(objectsList);
        createdClassesView.setLayoutX(20);
        createdClassesView.setLayoutY(40);
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
        btn1.setLayoutY(450);
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
        btn2.setLayoutY(450);
        Button btn3 = new Button("Edit");
        btn3.setOnAction(event -> FieldEditor.createWindow(createdClassesView.getSelectionModel().getSelectedIndex()));
        btn3.setPrefWidth(80);
        btn3.setLayoutY(450);
        btn3.setLayoutX(240);
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem openBtn = new MenuItem("Open");
        MenuItem saveBtn = new MenuItem("Save");
        fileMenu.getItems().addAll(openBtn, saveBtn);
        menuBar.getMenus().add(fileMenu);
        menuBar.setPrefWidth(350);
        root.getChildren().addAll(createdClassesView, btn1, btn2, btn3, menuBar);
        FileChooser fChooser = new FileChooser();
        openBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                fChooser.setTitle("Select directory to open file");
                File openedFile = fChooser.showOpenDialog(primaryStage);
                if (openedFile != null)
                {
                    ArrayList<SerializeFactory> serFactory = new ArrayList<>();
                    serFactory.add(new BinarySerializer());
                    serFactory.add(new GsonSerializer());
                    serFactory.add(new koffSerializer());
                    ArrayList<encoder> plugins = PluginTest.getPlugins();
                    String fileExtenstion = openedFile.getAbsolutePath();
                    fileExtenstion = fileExtenstion.substring(fileExtenstion.lastIndexOf(".") + 1);
                    //System.out.println(fileExtenstion);
                    ClassEditor.createdClasses.clear();
                    try
                    {
                        FileInputStream fIn = new FileInputStream(openedFile.getAbsolutePath());
                        BufferedInputStream bufIn = new BufferedInputStream(fIn);
                        String strFromFile = "";
                        int c;
                        while((c = bufIn.read()) != (-1))
                        {
                            strFromFile += (char)c;
                        }
                        for (int i = 0; i < plugins.size(); i++) {
                            if (plugins.get(i).getExtension().equals(fileExtenstion))
                            {
                                String deCodeResult = plugins.get(i).decode(strFromFile);
                                fileExtenstion = openedFile.getAbsolutePath();
                                fileExtenstion = fileExtenstion.substring(0, fileExtenstion.lastIndexOf("."));
                                fileExtenstion = fileExtenstion.substring(fileExtenstion.lastIndexOf(".") + 1);
                                System.out.println(fileExtenstion);
                                System.out.println();
                                for (int j = 0; j < serFactory.size(); j++)
                                {
                                    if (serFactory.get(j).getExtension().equals(fileExtenstion))
                                    {
                                        ClassEditor.createdClasses = serFactory.get(j).deserialize(deCodeResult);
                                    }
                                }
                            }
                        }
                        ClassEditor.restoreConnections(ClassEditor.createdClasses);
                        ClassEditor.update();
                        System.out.println(openedFile.getAbsolutePath());
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                }
            }
        });
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fChooser.setTitle("Select directory to save result");
                File saveFile = fChooser.showSaveDialog(primaryStage);
                if (saveFile != null)
                {
                    ChooseSerializationType.createWindow(saveFile);
                    System.out.println(saveFile.getAbsolutePath());
                }
            }
        });
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.setWidth(350);
        primaryStage.setHeight(550);
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

    public static ArrayList<Object> getObjectsToSerialize(ArrayList<Object> objectsToCheck) {
        ArrayList<Object> result = new ArrayList<>();
        ArrayList<Object> objectsToRemove = new ArrayList<>();
        for (int i = 0; i < objectsToCheck.size(); i++) {
            result.add(objectsToCheck.get(i));
        }
        for (int i = 0; i < result.size(); i++) {
            Field[] fields = result.get(i).getClass().getFields();
            Type arrList = ArrayList.class;
            for (Field field : fields) {
                if (field.getType() == arrList) {
                    ArrayList<Object> temp = new ArrayList<>();
                    String mehtodName;
                    mehtodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    try {
                        Method m1 = result.get(i).getClass().getMethod(mehtodName);
                        temp = (ArrayList<Object>) m1.invoke(result.get(i));
                        for (int k = 0; k < temp.size(); k++) {
                            Object obj = temp.get(k);
                            if (result.contains(obj)) objectsToRemove.add(obj);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
            }
        }
        for (int i = 0; i < objectsToRemove.size(); i++)
        {
            result.remove(objectsToRemove.get(i));
        }
        return result;
    }

    public static void restoreConnections(ArrayList<Object> objectsToCheck)
    {
        for (int i = 0; i < objectsToCheck.size(); i++)
        {
            Object obj = objectsToCheck.get(i);
            Field[] fields = obj.getClass().getFields();
            for (Field field : fields)
            {
                if (field.getType() == ArrayList.class)
                {
                    ArrayList<Object> temp = new ArrayList<>();
                    String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    try
                    {
                        Method m1 = obj.getClass().getMethod(methodName);
                        temp = (ArrayList<Object>)m1.invoke(obj);
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                    deserializeObjects(temp, obj);
                }
            }
        }
    }

    public static void deserializeObjects(ArrayList<Object> list, Object parentObj)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (!createdClasses.contains(list.get(i)))
            {
                createdClasses.add(list.get(i));
                Object obj = list.get(i);
                String methodName = "";
                try {
                    methodName = "set" + parentObj.getClass().getName().substring(0, 1).toUpperCase() + parentObj.getClass().getName().substring(1);
                    Method m1 = obj.getClass().getMethod(methodName, parentObj.getClass());
                    m1.invoke(obj, parentObj);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }
    }

}
