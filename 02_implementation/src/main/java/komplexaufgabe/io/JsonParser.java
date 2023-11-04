package komplexaufgabe.io;

import org.json.JSONArray;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonParser implements IFileParser {
    @Override
    public List<String[]> parse(String filePath) {
        String content = "N/A";

        try {
            content = new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource(filePath).toURI())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<String[]> retList = new ArrayList<>();
        JSONArray arr = new JSONArray(content);
        for (int i = 0; i < arr.length(); i++) {
            String speed = String.valueOf(arr.getJSONObject(i).getInt("speed"));
            String fine = String.valueOf(arr.getJSONObject(i).getDouble("fine"));
            retList.add(new String[]{speed, fine});
        }
        return retList;
    }
}
