package komplexaufgabe.io;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


public class CSVParser implements FileParser{


    @Override
    public List<String[]> parse(String filePath) {
        try {
            Stream<String> stream = Files.lines(Paths.get(filePath));

            return stream.map(line -> {
                String[] token = line.split("\n");
                return token[0];
            }).map(line -> line.split(";")).toList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
