package komplexaufgabe.entities;

public class CameraData {
    private final char[][] cameraPhoto;
    private final long timeStamp;

    public CameraData(char[][] cameraPhoto, long timeStamp) {
        if (cameraPhoto == null || cameraPhoto.length != 7 || cameraPhoto[0].length != 15) {
            throw new IllegalArgumentException("cameraPhoto must be of size 7x15");
        }

        this.cameraPhoto = new char[7][15];
        for (int i = 0; i < 7; i++) {
            System.arraycopy(cameraPhoto[i], 0, this.cameraPhoto[i], 0, 15);
        }

        this.timeStamp = timeStamp;
    }

    public char[][] getCameraPhoto() {
        char[][] copy = new char[7][15];
        for (int i = 0; i < 7; i++) {
            System.arraycopy(this.cameraPhoto[i], 0, copy[i], 0, 15);
        }
        return copy;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CameraData{");
        sb.append("cameraPhoto=\n");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 15; j++) {
                sb.append(cameraPhoto[i][j]);
            }
            sb.append('\n');
        }
        sb.append(", timeStamp=").append(timeStamp).append('}');
        return sb.toString();
    }
}
