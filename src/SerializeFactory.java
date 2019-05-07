import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

class GsonSerializer {
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
    public void deSerialize (File fileToOpen)
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
