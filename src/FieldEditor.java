import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
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
        ArrayList<Control> controls = new ArrayList<Control>();
        Class<?> cl = ClassEditor.createdClasses.get(index).getClass();
        Field[] fields = cl.getFields();
        for (Field field: fields)
        {
            controls.add(editFactory.createLabel(field.getName()));
            String fieldtype = field.getType().toString();
            switch (fieldtype) {
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
            }
            controls.add(editFactory.createSeparator());
        }
        Button btnOK = new Button("OK");
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
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, String.class);
            m1.invoke(ClassEditor.createdClasses.get(index), strToAdd);
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
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(settername, /*ClassEditor.createdClasses.get(parentindex).getClass()*/ obj.getClass());
            m1.invoke(ClassEditor.createdClasses.get(index), obj);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
}
