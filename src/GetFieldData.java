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
            //System.out.println(ex.toString());
            return "";
        }
    }

    public static String getObjectName(int index, String fieldname)
    {
        Object obj;
        String mehtodName;
        mehtodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        try
        {
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(mehtodName);
            obj = m1.invoke(ClassEditor.createdClasses.get(index));
            if (obj != null) {
                Method m2 = obj.getClass().getMethod("getName");
                String data = m2.invoke(obj).toString();
                if (data != null) {
                    return data;
                } else {
                    return "-";
                }
            }
            else
                return "-";
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return "";
        }
    }

    public static Object getObject(int index, String fieldname)
    {
        Object obj;
        String mehtodName;
        mehtodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
        try {
            Method m1 = ClassEditor.createdClasses.get(index).getClass().getMethod(mehtodName);
            obj = m1.invoke(ClassEditor.createdClasses.get(index));
            return obj;
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
