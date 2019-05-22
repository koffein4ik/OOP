import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;

abstract class SerializeFactory {
    abstract void serialize(File fileToSave, ArrayList<Object> objToSerialize);
    abstract ArrayList<Object> deserialize(File fileToRead);
    abstract String getExtension();
}

class BinarySerializer extends SerializeFactory{
    public void serialize(File fileToSave, ArrayList<Object> objToSerialize)
    {
        try
        {

            //fileToSave.createNewFile();
            ArrayList<Object> objectsToSerialize = objToSerialize;
            objectsToSerialize = ClassEditor.getObjectsToSerialize(objToSerialize);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath() + ".bin", false));
            for (int i = 0; i < objectsToSerialize.size(); i++)
            {
                Object obj = objectsToSerialize.get(i);
                out.writeObject(obj);
            }
            out.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public ArrayList<Object> deserialize(File fileToRead)
    {
        try
        {
            FileInputStream fInput = new FileInputStream(fileToRead.getAbsolutePath());
            ObjectInputStream in = new ObjectInputStream(fInput);
            Object obj = in.readObject();
            ArrayList<Object> temp = new ArrayList<>();
            //ClassEditor.createdClasses.clear();
            while (obj != null)
            {
                temp.add(obj);
                if (fInput.available() > 0)
                    obj = in.readObject();
                else
                    break;
            }
            in.close();
            return temp;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return null;
    }

    public String getExtension()
    {
        return "bin";
    }
}

class GsonSerializer extends SerializeFactory {
    public void serialize(File fileToSave, ArrayList<Object> objectsToSerialize)
    {
        try
        {
            //fileToSave.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fileToSave.getAbsolutePath() + ".json", false);
            ArrayList<Object> objToSerialize = ClassEditor.getObjectsToSerialize(objectsToSerialize);
            for (int i = 0; i < objToSerialize.size(); i++)
            {
                String typeName = objToSerialize.get(i).getClass().getTypeName() + "\n";
                fOut.write(typeName.getBytes());
                //Gson gsObject = new Gson();
                Gson gsObject = new GsonBuilder()
                        .registerTypeAdapter(Plane.class, new PlaneAdapterSerializer())
                        .registerTypeAdapter(PassengerPlane.class, new PassengerPlaneAdapterSerializer())
                        .registerTypeAdapter(CargoPlane.class, new CargoPlaneAdapterSerializer())
                        .registerTypeAdapter(Employee.class, new EmployeeAdapterSerializer())
                        .registerTypeAdapter(Pilot.class, new PilotAdapterSerializer())
                        .registerTypeAdapter(SecurityOfficer.class, new SecurityAdapterSerializer())
                        .registerTypeAdapter(AircrewMember.class, new AirCrewAdapterSerializer())
                        .create();
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
    public ArrayList<Object> deserialize (File fileToOpen)
    {
        try
        {
            //ClassEditor.createdClasses.clear();
            ArrayList<Object> temp1 = new ArrayList<>();
            FileInputStream fIn = new FileInputStream(fileToOpen.getAbsolutePath());
            BufferedReader bufIn = new BufferedReader(new InputStreamReader(fIn));
            Gson gsObject = new GsonBuilder()
                    .registerTypeAdapter(Plane.class, new PlaneAdapterDeSerializer())
                    .registerTypeAdapter(Employee.class, new EmployeeAdapterDeSerializer())
                    .create();
            Object obj;
            String line = bufIn.readLine();
            while (line != null)
            {
                Class<?> cl = Class.forName(line);
                line = bufIn.readLine();
                obj = gsObject.fromJson(line, cl);
                Field[] fields = cl.getFields();
                Type arrList = ArrayList.class;
                //ClassEditor.createdClasses.add(obj);
                temp1.add(obj);
                for (Field field : fields)
                {
                    if (field.getType() == arrList)
                    {
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
            return temp1;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        //ClassEditor.restoreConnections();
        return null;
    }

    public String getExtension()
    {
        return "json";
    }
}

class PlaneAdapterSerializer implements JsonSerializer<Plane>
{
    @Override
    public JsonElement serialize (Plane pl1, Type typeOfSrc, JsonSerializationContext JsonContext)
    {
        JsonElement result = new Gson().toJsonTree(pl1);
        result.getAsJsonObject().addProperty("className", typeOfSrc.toString());
        return result;
    }
}

class PassengerPlaneAdapterSerializer implements JsonSerializer<PassengerPlane>
{
    @Override
    public JsonElement serialize (PassengerPlane pl1, Type typeOfSrc, JsonSerializationContext JsonContext)
    {
        JsonElement result = new Gson().toJsonTree(pl1);
        result.getAsJsonObject().addProperty("className", typeOfSrc.toString());
        return result;
    }
}

class EmployeeAdapterSerializer implements JsonSerializer<Employee>
{
    @Override
    public JsonElement serialize (Employee pl1, Type typeOfSrc, JsonSerializationContext JsonContext)
    {
        JsonElement result = new Gson().toJsonTree(pl1);
        result.getAsJsonObject().addProperty("className", typeOfSrc.toString());
        return result;
    }
}

class PilotAdapterSerializer implements JsonSerializer<Pilot>
{
    @Override
    public JsonElement serialize (Pilot pl1, Type typeOfSrc, JsonSerializationContext JsonContext)
    {
        JsonElement result = new Gson().toJsonTree(pl1);
        result.getAsJsonObject().addProperty("className", typeOfSrc.toString());
        return result;
    }
}

class SecurityAdapterSerializer implements JsonSerializer<SecurityOfficer>
{
    @Override
    public JsonElement serialize (SecurityOfficer pl1, Type typeOfSrc, JsonSerializationContext JsonContext)
    {
        JsonElement result = new Gson().toJsonTree(pl1);
        result.getAsJsonObject().addProperty("className", typeOfSrc.toString());
        return result;
    }
}

class AirCrewAdapterSerializer implements JsonSerializer<AircrewMember>
{
    @Override
    public JsonElement serialize (AircrewMember pl1, Type typeOfSrc, JsonSerializationContext JsonContext)
    {
        JsonElement result = new Gson().toJsonTree(pl1);
        result.getAsJsonObject().addProperty("className", typeOfSrc.toString());
        return result;
    }
}

class CargoPlaneAdapterSerializer implements JsonSerializer<CargoPlane>
{
    @Override
    public JsonElement serialize (CargoPlane pl1, Type typeOfSrc, JsonSerializationContext JsonContext)
    {
        JsonElement result = new Gson().toJsonTree(pl1);
        result.getAsJsonObject().addProperty("className", typeOfSrc.toString());
        return result;
    }
}

class PlaneAdapterDeSerializer implements JsonDeserializer<Plane>
{
    @Override
    public Plane deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context)
    {
        String classname = json.getAsJsonObject().get("className").getAsString();
        classname = classname.substring(6);
        Gson gson = new Gson();
        try
        {
            Class<?> cl = Class.forName(classname);
            //Object pl1 = cl.newInstance();
            Object pl1 = gson.fromJson(json, cl);
            return (Plane)pl1;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        Plane pl1 = new Plane();
        /*switch(classname)
        {
            case "PassengerPlane" :
            {
                pl1 = new PassengerPlane();
                pl1 = gson.fromJson(json, PassengerPlane.class);
                break;
            }
            case "CargoPlane" :
            {
                pl1 = new CargoPlane();
                pl1 = gson.fromJson(json, CargoPlane.class);
                break;
            }
            default:
            {
                pl1 = gson.fromJson(json, Plane.class);
            }
        }*/
        return pl1;
    }
}

class EmployeeAdapterDeSerializer implements JsonDeserializer<Employee>
{
    @Override
    public Employee deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context)
    {
        String classname = json.getAsJsonObject().get("className").getAsString();
        classname = classname.substring(6);
        Employee empl1 = new Employee();
        Gson gson = new Gson();
        try
        {
            Class<?> cl = Class.forName(classname);
            //Object empl = cl.newInstance();
            Object empl = gson.fromJson(json, cl);
            return (Employee) empl;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        /*switch(classname)
        {
            case "Pilot" :
            {
                empl1 = new Pilot();
                empl1 = gson.fromJson(json, Pilot.class);
                break;
            }
            case "SecurityOfficer" :
            {
                empl1 = new SecurityOfficer();
                empl1 = gson.fromJson(json, SecurityOfficer.class);
                break;
            }
            case "AircrewMember" :
            {
                empl1 = new AircrewMember();
                empl1 = gson.fromJson(json, AircrewMember.class);
                break;
            }
            default:
            {
                empl1 = gson.fromJson(json, Employee.class);
            }
        }*/
        return empl1;
    }
}

class koffSerializer extends SerializeFactory
{
    public void serialize(File fileToSave, ArrayList<Object> objToSerialize)
    {
        try
        {
            //fileToSave.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fileToSave.getAbsolutePath() + ".kof", false);
            ArrayList<Object> objectsToSerialize = new ArrayList<>();
            System.out.println(objToSerialize.size());
            objectsToSerialize = ClassEditor.getObjectsToSerialize(objToSerialize);
            for (int i = 0; i < objectsToSerialize.size(); i++)
            {
                String serializedObject = serializeObject(objectsToSerialize.get(i), false);
                fOut.write(serializedObject.getBytes());
            }
            fOut.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public ArrayList<Object> deserialize(File fileToOpen)
    {
        try
        {
            ArrayList<Object> result = new ArrayList<>();
            FileInputStream fIn = new FileInputStream(fileToOpen.getAbsolutePath());
            BufferedReader bufIn = new BufferedReader(new InputStreamReader(fIn));
            String line = bufIn.readLine();
            ArrayList<Object> objToAdd = new ArrayList<>();
            while (line != null)
            {
                //ClassEditor.createdClasses.add(deserializeObject(bufIn, line));
                result.add(deserializeObject(bufIn, line));
                line = bufIn.readLine();
            }
            return result;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return null;
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
            return obj;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return null;
    }

    public ArrayList<Object> deserializeArrayList(BufferedReader bufIn)
    {
        ArrayList<Object> result = new ArrayList<>();
        try
        {
            bufIn.readLine();
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

    public String getExtension()
    {
        return "kof";
    }
}
