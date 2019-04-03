import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class editFactory {
    public static Label createLabel(String Text)
    {
        Label label1 = new Label(Text);
        label1.setMinHeight(30);
        return label1;
    }

    public static Separator createSeparator()
    {
        Separator sep1 = new Separator();
        sep1.setMinWidth(360);
        return sep1;
    }

    public static TextField createTextField(boolean isint, String data)
    {
        TextField tf1 = new TextField();
        if(isint) {
            tf1.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tf1.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }
        tf1.setText(data);
        tf1.setLayoutX(10);
        return tf1;
    }

    public static ComboBox createCB(ArrayList<Object> obj)
    {
        ComboBox<Object> cb1 = new ComboBox<>();
        for (int i = 0; i < obj.size(); i++)
        {
            Class<?> cl = obj.get(i).getClass();
            try
            {
                Method m1 = obj.get(i).getClass().getMethod("getName");
                cb1.getItems().add(m1.invoke(obj.get(i)));
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        cb1.getSelectionModel().select(0);
        return cb1;
    }
}
