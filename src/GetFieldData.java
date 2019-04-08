import java.lang.reflect.Method;
import java.util.ArrayList;

public class GetFieldData {
    public static String getData(int index, String fieldname)
    {
        String data;
        String mehtodName;
        mehtodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        try
        {
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(mehtodName);
            data = m1.invoke(ClassEditor.createdClasses.get(index)).toString();
            if (data != null)
            {
                return data;
            }
            else
            {
                return "";
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return "";
        }
    }

    public static ArrayList<Object> getComplexData(int index, String fieldname)
    {
        ArrayList<Object> data = new ArrayList<>();
        String mehtodName;
        mehtodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        try
        {
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(mehtodName);
            data = (ArrayList<Object>)m1.invoke(ClassEditor.createdClasses.get(index));
            return data;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return data;
        }

    }
}
