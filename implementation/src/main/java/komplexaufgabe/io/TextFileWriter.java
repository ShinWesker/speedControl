package komplexaufgabe.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileWriter implements IFileWriter {

    private BufferedWriter bufferedWriter;

    @Override
    public void writeFile(String path, String content) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }


}
