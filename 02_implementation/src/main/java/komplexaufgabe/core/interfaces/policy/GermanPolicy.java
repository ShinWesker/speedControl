package komplexaufgabe.core.interfaces.policy;

import komplexaufgabe.io.IFileParser;
import komplexaufgabe.io.JsonParser;

import java.util.List;

public class GermanPolicy implements IPolicy {
    private final int[] speedList;
    private final double[] fineList;

    public GermanPolicy(String filePath) {
        IFileParser jsonParser = new JsonParser();
        List<String[]> jsonOut = jsonParser.parse(filePath);
        speedList = new int[jsonOut.size()];
        fineList = new double[jsonOut.size()];

        for (int i = 0; i < jsonOut.size(); i++) {
            speedList[i] = Integer.parseInt(jsonOut.get(i)[0]);
            fineList[i] = Double.parseDouble(jsonOut.get(i)[1]);
        }
    }

    @Override
    public double getFine(int speed) {
        for (int i = 0; i < speedList.length; i++) {
            if (!(speed - 3 > speedList[i])) {
                return fineList[i];
            }
        }
        return 0;
    }
}
