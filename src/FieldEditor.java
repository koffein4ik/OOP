import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class FieldEditor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public static void createWindow(int index)
    {
        if (index == -1) return;
        Stage editorWindow = new Stage();
        ArrayList<Control> controls = new ArrayList<Control>();
        Class<?> cl = ClassEditor.createdClasses.get(index).getClass();
        Field[] fields = cl.getFields();
        ArrayList<editorFactory> elementsFactory = new ArrayList<>();
        elementsFactory.add(new intFactory());
        elementsFactory.add(new stringFactory());
        elementsFactory.add(new arrayFactory());
        elementsFactory.add(new objectFactory());
        for (Field field: fields)
        {
            controls.add(labelFactory.createLabel(field.getName()));
            String fieldtype = field.getType().toString();
            for (int i = 0; i < elementsFactory.size(); i++)
            {
                if (elementsFactory.get(i).canCreate(fieldtype))
                {
                    controls.add(elementsFactory.get(i).Create(index, field.getName()));
                    if (i == 2)
                    {
                        Button editBtn = new Button("Edit");
                        //editBtn.setOnAction(event->ListEditor.createWindow(index, field.getName()));
                        editBtn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                ListEditor.createWindow(index, field.getName());
                            }
                        });
                        controls.add(editBtn);
                    }
                    break;
                }
            }
            controls.add(sepFactory.createSeparator());
        }
        Button btnOK = new Button("OK");
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < controls.size(); i++)
                {
                    if (controls.get(i).getClass().getTypeName().equals("javafx.scene.control.TextField"))
                    {
                        Label lb1 = (Label) controls.get(i - 1);
                        TextField tf1 = (TextField) controls.get(i);
                        setStringField(index, lb1.getText(), tf1.getText());
                    }
                }
                ClassEditor.update();
            }
        });
        btnOK.setPrefWidth(100);
        FlowPane pane = new FlowPane();
        pane.setHgap(20);
        pane.getChildren().addAll(controls);
        pane.getChildren().addAll(btnOK);
        pane.setMargin(btnOK, new Insets(20,130,20,130));
        for (int i = 0; i < controls.size(); i++)
        {
            if (controls.get(i).getClass().getTypeName().equals("javafx.scene.control.Label"))
            pane.setMargin(controls.get(i), new Insets(0, 0, 0, 10));
        }
        Scene scene = new Scene(pane, 360, btnOK.getLayoutX() - 100);
        editorWindow.initModality(Modality.APPLICATION_MODAL);
        editorWindow.setResizable(false);
        editorWindow.setScene(scene);
        editorWindow.showAndWait();
    }

    public static void setStringField(int index, String fieldname, String strToAdd)
    {
        String settername = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        try
        {
            Field fd = ClassEditor.createdClasses.get(index).getClass().getField(fieldname);
            if (fd.getType().toString().equals("int"))
            {
                Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, Integer.TYPE);
                m1.invoke(ClassEditor.createdClasses.get(index), Integer.parseInt(strToAdd));
            }
            else
            {
                Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, String.class);
                m1.invoke(ClassEditor.createdClasses.get(index), strToAdd);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public static void setObjectField(int index, int parentindex, String fieldname, Object obj)
    {
        String settername = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        try
        {
            Field fd = ClassEditor.createdClasses.get(index).getClass().getField(fieldname);
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, fd.getType());
            m1.invoke(ClassEditor.createdClasses.get(index), obj);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
}
