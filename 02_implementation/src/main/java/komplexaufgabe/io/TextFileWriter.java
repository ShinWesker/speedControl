package komplexaufgabe.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class TextFileWriter implements IFileWriter {

    @Override
    public void writeFile(String path, String content) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WriterConfig.INSTANCE.exportPath + path));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }


}
