package utility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files {
    /**
     * OS-independent file separator.
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String SEMICOLON = ";";
    public static final String COMMA = ",";

    private Files() {
    }

    /**
     * Read a file
     *
     * @param path
     * @return
     */
    public static String readFile(String path) {
        String string = "";
        String read = null;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
            while ((read = in.readLine()) != null) {
                string += read;
                string += "\n";
            }
        } catch (IOException e) {
            System.err.println("There was a problem: " + e);
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return string;
    }

    /**
     * Write a string to a file
     *
     * @param filename
     * @param string
     */
    private static void writeStringToFile(String filename, String string) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeStringToFileUTF8(String filename, String string) {
        Path file = Paths.get(filename);
        BufferedWriter writer = null;
        try {
            writer = java.nio.file.Files.newBufferedWriter(file, StandardCharsets.UTF_8);
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDirectories(String path) {
        try {
            java.nio.file.Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serialize an object.
     * @param o
     * @param filename
     */
    public static void serializeObject(Object o, String filename) {
        if (!filename.endsWith(".ser")) {
            filename = filename + ".ser";
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Deserialize an object specified by a file path.
     * @param filename
     * @return
     */
    public static Object deserializeObject(String filename) {
        if (!filename.endsWith(".ser")) {
            filename = filename + ".ser";
        }
        Object ret = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ret = in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return ret;
    }
}
