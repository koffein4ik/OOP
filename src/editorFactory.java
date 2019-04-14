import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

abstract class editorFactory {
    public boolean canCreate(String fieldtype)
    {
        return false;
    }
    public Control Create(int index, String fieldname)
    {
        return new TextField();
    }

}

class intFactory extends editorFactory
{
    public boolean canCreate(String fieldtype)
    {
        if (fieldtype.equals("int"))
            return true;
        else
            return false;
    }

    public TextField Create(int index, String fieldname)
    {
        String data = GetFieldData.getData(index, fieldname);
        TextField tf1 = new TextField();
        tf1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        tf1.setText(data);
        tf1.setLayoutX(10);
        return tf1;
    }
}

class stringFactory extends editorFactory
{
    public boolean canCreate(String fieldtype)
    {
        return fieldtype.equals("class java.lang.String");
    }

    public TextField Create(int index, String fieldname)
    {
        TextField tf1 = new TextField();
        String data = GetFieldData.getData(index, fieldname);
        tf1.setText(data);
        tf1.setLayoutX(10);
        return tf1;
    }
}

class arrayFactory extends editorFactory
{
    public boolean canCreate(String fieldtype)
    {
        return fieldtype.equals("class java.util.ArrayList");
    }

    public ComboBox<Object> Create(int index, String fieldname)
    {
        ArrayList<Object> obj = GetFieldData.getComplexData(index, fieldname);
        ComboBox<Object> cb1 = new ComboBox<>();
        for (Object o : obj) {
            try {
                Method m1 = o.getClass().getMethod("getName");
                cb1.getItems().add(m1.invoke(o));
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        cb1.getSelectionModel().select(0);
        return cb1;
    }
}

class objectFactory extends editorFactory
{
    public boolean canCreate(String fieldtype)
    {
        return true;
    }

    public Label Create(int index, String fieldname)
    {
        String data = GetFieldData.getObjectName(index, fieldname);
        return labelFactory.createLabel(data);
    }
}

class labelFactory
{
    static Label createLabel(String Text)
    {
        Label label1 = new Label(Text);
        label1.setMinHeight(30);
        label1.setLayoutX(10);
        return label1;
    }
}

class sepFactory
{
    static Separator createSeparator()
    {
        Separator sep1 = new Separator();
        sep1.setMinWidth(360);
        return sep1;
    }
}
