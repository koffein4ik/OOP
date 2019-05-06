import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Scanner;

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
            //ObjectOutputStream out = new ObjectOutputStream(fOut);
            for (int i = 0; i < ClassEditor.createdClasses.size(); i++)
            {
                Gson gsObject = new Gson();
                System.out.println(fileToSave.getAbsolutePath());
                Type objType = ClassEditor.createdClasses.get(i).getClass();
                String result = gsObject.toJson(ClassEditor.createdClasses.get(i), objType);
                fOut.write(result.getBytes());
            }
            //fOut.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    public void deSerialize (File fileToOpen)
    {

    }
}
