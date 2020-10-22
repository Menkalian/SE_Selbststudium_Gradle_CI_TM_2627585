import lombok.Data;

@Data
public class Picture {
    private int faceID;
    private char[][] content;
    private long nanoTimeStamp;
}
