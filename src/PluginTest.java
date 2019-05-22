import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginTest {
    public static ArrayList<encoder> getPlugins()
    {
        ArrayList<encoder> plugins = new ArrayList<>();
        File folder = new File("E:\\OOP\\plugins");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++)
        {
            if ((listOfFiles[i].isFile()) && (listOfFiles[i].getAbsolutePath().endsWith(".jar")))
            {
                String pathToJar = listOfFiles[i].getAbsolutePath();
                try {
                    JarFile jarFile = new JarFile(pathToJar);
                    Enumeration<JarEntry> e = jarFile.entries();

                    URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
                    URLClassLoader cl = URLClassLoader.newInstance(urls);

                    while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        // -6 because of .class
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace('/', '.');
                        Class c = cl.loadClass(className);
                        if ((encoder.class.isAssignableFrom(c)) && (c != encoder.class))
                        {
                            //System.out.println(className + " implements my interface");
                            Object obj1 = c.newInstance();
                            plugins.add((encoder)obj1);
                        }
                        /*if (className.equals("Base58plugin"))
                        {
                            System.out.println(c.getClass());
                            Object obj1 = c.newInstance();
                            System.out.println(obj1.getClass());
                            Method m1 = obj1.getClass().getMethod("encode", String.class);
                            String result = m1.invoke(obj1, "test string").toString();
                            System.out.println(result);
                            Method m2 = obj1.getClass().getMethod("decode", String.class);
                            result = m2.invoke(obj1, result).toString();
                            System.out.println(result);
                        }*/
                    }
                }
                catch (Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }
        }
        return plugins;
    }
}
