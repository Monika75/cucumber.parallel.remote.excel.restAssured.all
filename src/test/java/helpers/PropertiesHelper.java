package helpers;

import java.io.*;
import java.util.Properties;

/**
 * Created by Progres on 18.3.2018 г..
 */
public class PropertiesHelper {

    public static void main(String[] args) {
        setVariables();
        loadProperties();
    }

    public static void setVariables() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("src\\test\\resources\\prop2.properties");

            // set the properties value
            prop.setProperty("database", "localhost");
            prop.setProperty("dbuser", "mkyong");
            prop.setProperty("dbpassword", "password");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src\\test\\resources\\prop2.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
