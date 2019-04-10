import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import javax.naming.event.ObjectChangeListener;
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
            //controls.add(editFactory.createLabel(field.getName()));
            controls.add(labelFactory.createLabel(field.getName()));
            String fieldtype = field.getType().toString();
            /*switch (fieldtype) {
                case "int":
                {
                    controls.add(editFactory.createTextField(true, GetFieldData.getData(index, field.getName())));
                    break;
                }
                case "class java.lang.String":
                {
                    controls.add(editFactory.createTextField(false, GetFieldData.getData(index, field.getName())));
                    break;
                }
                case "class java.util.ArrayList":
                {
                    controls.add(editFactory.createCB(GetFieldData.getComplexData(index, field.getName())));
                    Button editBtn = new Button("Edit");
                    editBtn.setOnAction(event->ListEditor.createWindow(index, field.getName()));
                    controls.add(editBtn);
                    break;
                }
                default :
                {
                    controls.add(editFactory.createLabel(GetFieldData.getObjectName(index, field.getName())));
                }
            }*/
            for (int i = 0; i < elementsFactory.size(); i++)
            {
                if (elementsFactory.get(i).canCreate(fieldtype))
                {
                    controls.add(elementsFactory.get(i).Create(index, field.getName()));
                    if (i == 2)
                    {
                        Button editBtn = new Button("Edit");
                        editBtn.setOnAction(event->ListEditor.createWindow(index, field.getName()));
                        controls.add(editBtn);
                    }
                    break;
                }
            }
            //controls.add(editFactory.createSeparator());
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
            }
        });
        btnOK.setPrefWidth(100);
        FlowPane pane = new FlowPane();
        pane.setHgap(20);
        Scene scene = new Scene(pane, 360, 500);
        Stage editorWindow = new Stage();
        pane.getChildren().addAll(controls);
        pane.getChildren().add(btnOK);
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

    public static void setIntField(int index, String fieldname, int intToAdd)
    {
        String settername = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        try
        {
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, Integer.class);
            m1.invoke(ClassEditor.createdClasses.get(index), intToAdd);
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
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, obj.getClass());
            m1.invoke(ClassEditor.createdClasses.get(index), obj);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
}
