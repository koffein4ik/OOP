import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;

abstract class SerializeFactory {
    abstract void serialize(File fileToSave);
    abstract void deserialize(File fileToRead);
}

class BinarySerializer extends SerializeFactory{
    public void serialize(File fileToSave)
    {
        try
        {
            fileToSave.createNewFile();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath(), false));
            for (int i = 0; i < ClassEditor.createdClasses.size(); i++)
            {
                Object obj = ClassEditor.createdClasses.get(i);
                out.writeObject(obj);
            }
            out.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void deserialize(File fileToRead)
    {
        try
        {
            FileInputStream fInput = new FileInputStream(fileToRead.getAbsolutePath());
            ObjectInputStream in = new ObjectInputStream(fInput);
            Object obj = in.readObject();
            ClassEditor.createdClasses.clear();
            while (obj != null)
            {
                ClassEditor.createdClasses.add(obj);
                if (fInput.available() > 0)
                    obj = in.readObject();
                else
                    break;
            }
            in.close();
            ClassEditor.update();
        }
        catch (Exception ex)
        {

        }
    }
}

class GsonSerializer extends SerializeFactory {
    public void serialize(File fileToSave)
    {
        try
        {
            fileToSave.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fileToSave.getAbsolutePath(), false);
            ArrayList<Object> objToSerialize = ClassEditor.getObjectsToSerialize();
            for (int i = 0; i < objToSerialize.size(); i++)
            {
                String typeName = objToSerialize.get(i).getClass().getTypeName() + "\n";
                fOut.write(typeName.getBytes());
                Gson gsObject = new Gson();
                System.out.println(fileToSave.getAbsolutePath());
                Type objType = objToSerialize.get(i).getClass();
                String result = gsObject.toJson(objToSerialize.get(i), objType) + "\n";
                fOut.write(result.getBytes());
            }
            fOut.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    public void deserialize (File fileToOpen)
    {
        try
        {
            FileInputStream fIn = new FileInputStream(fileToOpen.getAbsolutePath());
            BufferedReader bufIn = new BufferedReader(new InputStreamReader(fIn));
            Gson gsObject = new Gson();
            Object obj;
            String line = bufIn.readLine();
            while (line != null)
            {
                Class<?> cl = Class.forName(line);
                line = bufIn.readLine();
                obj = gsObject.fromJson(line, cl);
                //Class<?> cl = ClassEditor.createdClasses.get(index).getClass();
                Field[] fields = cl.getFields();
                Type arrList = ArrayList.class;
                ClassEditor.createdClasses.add(obj);
                for (Field field : fields)
                {
                    if (field.getType() == arrList)
                    {
                        System.out.println("yes");
                        ArrayList<Object> temp = new ArrayList<>();
                        String mehtodName;
                        mehtodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                        try {
                            Method m1 = cl.getMethod(mehtodName);
                            temp = (ArrayList<Object>) m1.invoke(obj);
                            ClassEditor.deserializeObjects(temp, obj);
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.toString());
                        }
                    }
                }
                line = bufIn.readLine();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
}

class koffSerializer extends SerializeFactory
{
    public void serialize(File fileToSave)
    {
        try
        {
            fileToSave.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fileToSave.getAbsolutePath(), false);
            ArrayList<Object> objectsToSerialize = new ArrayList<>();
            objectsToSerialize = ClassEditor.getObjectsToSerialize();
            for (int i = 0; i < objectsToSerialize.size(); i++)
            {
                String serializedObject = serializeObject(objectsToSerialize.get(i), false);
                System.out.println(serializedObject);
                fOut.write(serializedObject.getBytes());
            }
            fOut.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void deserialize(File fileToOpen)
    {
        try
        {
            FileInputStream fIn = new FileInputStream(fileToOpen.getAbsolutePath());
            BufferedReader bufIn = new BufferedReader(new InputStreamReader(fIn));
            String line = bufIn.readLine();
            ArrayList<Object> objToAdd = new ArrayList<>();
            while (line != null)
            {
                //objToAdd.add(deserializeObject(bufIn, line));
                ClassEditor.createdClasses.add(deserializeObject(bufIn, line));
                line = bufIn.readLine();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public String serializeObject(Object obj, Boolean isInArrayList)
    {
        String result = "";
        if (isInArrayList) result += "  ";
        result += obj.getClass().toString().replace("class ", "") + "\n";
        if (isInArrayList) result += "  ";
        result += "{\n";
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields)
        {
            if (Modifier.isTransient(field.getModifiers())) continue;
            String methodName = "";
            try
            {
                methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method m1 = obj.getClass().getMethod(methodName);
                if (field.getType() != ArrayList.class)
                {
                    String value = "";
                    if (!(field.get(obj) == null))
                    {
                        value = m1.invoke(obj).toString();
                    }
                    if (value == null) value = "";
                    if (isInArrayList) result += "  ";
                    result += " " + field.getName() + " : " + value + ";\n";
                    System.out.println(field);
                    continue;
                }
                if (field.getType() == ArrayList.class)
                {
                    ArrayList<Object> arrayListToSerialize = new ArrayList<>();
                    arrayListToSerialize = (ArrayList<Object>)m1.invoke(obj);
                    result += " " + field.getName() + "\n [\n";
                    for (int i = 0; i < arrayListToSerialize.size(); i++)
                    {
                        result += serializeObject(arrayListToSerialize.get(i), true);
                    }
                    result += " ]\n";
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
        if (isInArrayList) result += "  ";
        result += "}\n";
        return result;
    }

    public Object deserializeObject(BufferedReader bufIn, String className)
    {
        String line = "";
        try {
            Class<?> cl = Class.forName(className);
            bufIn.readLine();
            Object obj = cl.newInstance();
            Field[] fields = cl.getFields();
            for (Field field : fields)
            {
                if (Modifier.isTransient(field.getModifiers())) continue;
                line = bufIn.readLine();
                line = line.substring(line.indexOf(":") + 2, line.length() - 1);
                String settername = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method m1 = cl.getMethod(settername, field.getType());
                if (!(field.getType() == ArrayList.class))
                {
                    if (field.getType() == Integer.TYPE)
                    {
                        m1.invoke(obj, Integer.parseInt(line));
                    }
                    if (field.getType() == String.class)
                    {
                        m1.invoke(obj, line);
                    }
                }
                else
                {
                   ArrayList<Object> temp = deserializeArrayList(bufIn);
                   ClassEditor.deserializeObjects(temp, obj);
                   m1.invoke(obj, temp);
                }
            }
            line = bufIn.readLine();
            //System.out.println(line);
            return obj;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        //Object resultObj = new Object();//ИЗМЕНИТЬ
        return null;
    }

    public ArrayList<Object> deserializeArrayList(BufferedReader bufIn)
    {
        ArrayList<Object> result = new ArrayList<>();
        try
        {
            bufIn.readLine();
            //bufIn.readLine();
            String line = bufIn.readLine();
            while (!(line.trim().equals("]")))
            {
                Object obj = deserializeObject(bufIn, line.trim());
                result.add(obj);
                line = bufIn.readLine();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return result;
    }
}
