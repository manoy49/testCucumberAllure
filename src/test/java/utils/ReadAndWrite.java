package utils;

import java.io.*;
import java.util.Properties;

public class ReadAndWrite {

    public static String getProperty(String key, String location) {
        String value = "";
        try {
            File file = new File(location);
            FileInputStream fileInputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInputStream);

            if(properties.containsKey(key)) {
                value = (String) properties.get(key);
            }

            properties.clear();
            fileInputStream.close();

        }catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
