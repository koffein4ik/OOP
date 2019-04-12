import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListEditor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public static void createWindow(int index, String fieldname)
    {
        ArrayList<Object> addedObjects = new ArrayList<>();
        ArrayList<Object> availableObjects = new ArrayList<>();
        addedObjects = GetFieldData.getComplexData(index, fieldname);
        ArrayList<Object> tempObjects = new ArrayList<>();
        Pane root = new Pane();
        ListView<String> addedElements = new ListView<>();
        addedElements.setPrefHeight(300);
        for (int i = 0; i < addedObjects.size(); i++)
        {
            tempObjects.add(addedObjects.get(i));
        }
        for (int i = 0; i < addedObjects.size(); i++)
        {
            try
            {
                Method m1 = addedObjects.get(i).getClass().getMethod("getName");
                addedElements.getItems().add(m1.invoke(addedObjects.get(i)).toString());
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        ListView<String> availableElements = new ListView<>();
        availableElements.setPrefHeight(300);
        String className = fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1, fieldname.length() - 1);
        for (int i = 0; i < ClassEditor.createdClasses.size(); i++)
        {
            Class<?> test = ClassEditor.createdClasses.get(i).getClass();
            ArrayList<String> superclasses = new ArrayList<String>();
            superclasses.clear();
            while(test.getSuperclass() != null)
            {
                //System.out.println(test.toString());
                superclasses.add(test.toString());
                test = test.getSuperclass();
            }
            if (superclasses.contains("class " + className)) {
                String result = GetFieldData.getData(i, ClassEditor.createdClasses.get(index).getClass().getName());
                if (result.equals(""))
                {
                    availableObjects.add(ClassEditor.createdClasses.get(i));
                }
            }
        }

        for (int i = 0; i <  availableObjects.size(); i++)
        {
            try
            {
                Method m1 =  availableObjects.get(i).getClass().getMethod("getName");
                availableElements.getItems().add(m1.invoke( availableObjects.get(i)).toString());
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        addedElements.setLayoutX(20);
        addedElements.setLayoutY(50);
        availableElements.setLayoutX(340);
        availableElements.setLayoutY(50);
        Scene scene = new Scene(root, 600, 450);
        Stage editorWindow = new Stage();
        Button addBtn = new Button("Add");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (availableElements.getSelectionModel().getSelectedIndex() == (-1)) return;
                int addIndex = availableElements.getSelectionModel().getSelectedIndex();
                edit(addIndex, tempObjects, availableObjects, addedElements, availableElements);
            }
        });
        addBtn.setLayoutX(60);
        addBtn.setLayoutY(400);
        addBtn.setPrefWidth(80);
        Button delBtn = new Button("Delete");
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (addedElements.getSelectionModel().getSelectedIndex() == (-1)) return;
                int delIndex = addedElements.getSelectionModel().getSelectedIndex();
                edit(delIndex, availableObjects, tempObjects, availableElements, addedElements);
            }
        });
        delBtn.setLayoutX(160);
        delBtn.setPrefWidth(80);
        delBtn.setLayoutY(400);
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                save(index, fieldname, tempObjects, availableObjects);
            }
        });
        saveBtn.setLayoutX(260);
        saveBtn.setLayoutY(400);
        saveBtn.setPrefWidth(80);
        root.getChildren().addAll(addedElements, availableElements, addBtn, delBtn, saveBtn);
        editorWindow.initModality(Modality.APPLICATION_MODAL);
        editorWindow.setResizable(false);
        editorWindow.setScene(scene);
        editorWindow.showAndWait();
    }

    public static void add(int index, ArrayList<Object> destination, ArrayList<Object> source, ListView<String> lv1, ListView<String> lv2)
    {
        destination.add(source.get(index));
        lv1.getItems().add(lv2.getSelectionModel().getSelectedItem());
        source.remove(index);
        lv2.getItems().remove(index);
    }

    public static void delete(int index, ArrayList<Object> destination, ArrayList<Object> source, ListView<String> lv1, ListView<String> lv2)
    {
        destination.add(source.get(index));
        lv1.getItems().add(lv2.getSelectionModel().getSelectedItem());
        source.remove(index);
        lv2.getItems().remove(index);
    }

    public static void edit(int index, ArrayList<Object> destination, ArrayList<Object> source, ListView<String> lv1, ListView<String> lv2)
    {
        destination.add(source.get(index));
        lv1.getItems().add(lv2.getSelectionModel().getSelectedItem());
        source.remove(index);
        lv2.getItems().remove(index);
    }

    public static void save(int index, String fieldname, ArrayList<Object> addedObjects, ArrayList<Object> availableObjects)
    {
        try
        {
            String settername = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, ArrayList.class);
            m1.invoke(ClassEditor.createdClasses.get(index), addedObjects);
            String childFieldName = ClassEditor.createdClasses.get(index).getClass().getName();
            childFieldName = childFieldName.substring(0, 1).toLowerCase() + childFieldName.substring(1);
            for (int i = 0; i < addedObjects.size(); i++)
            {
                int childIndex = ClassEditor.createdClasses.indexOf(addedObjects.get(i));
                FieldEditor.setObjectField(childIndex, index, childFieldName, ClassEditor.createdClasses.get(index));
            }

            childFieldName = ClassEditor.createdClasses.get(index).getClass().getName();
            childFieldName = childFieldName.substring(0, 1).toLowerCase() + childFieldName.substring(1);
            for (int i = 0; i < availableObjects.size(); i++)
            {
                int childIndex = ClassEditor.createdClasses.indexOf(availableObjects.get(i));
                Object obj = null;
                FieldEditor.setObjectField(childIndex, index, childFieldName, null);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

}
