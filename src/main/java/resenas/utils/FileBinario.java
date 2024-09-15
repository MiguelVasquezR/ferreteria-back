package resenas.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileBinario {

    public void writeBinaryFile(Map<String, String> map) {
        File file = new File(Utils.NAME_FILE_BINARY_PATH);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream escribir = new ObjectOutputStream(fos);
            escribir.writeObject(map);
            escribir.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> readBinaryFile() {
        File file = new File(Utils.NAME_FILE_BINARY_PATH);
        Map<String, String> map = new HashMap<>();
        if (!file.exists()) {
            System.out.println("Archivo no encontrado, devolviendo mapa vacío.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return map;
        }
        if (file.length() == 0) {
            System.out.println("El archivo está vacío, devolviendo mapa vacío.");
            return map;
        }
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            map = (Map<String, String>) ois.readObject();
        } catch (EOFException eof) {
            System.out.println("Archivo vacío o fin del archivo alcanzado.");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
