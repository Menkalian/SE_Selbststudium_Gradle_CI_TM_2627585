import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Picture {
    private int faceID;
    private char[][] content;
    private long nanoTimeStamp;
}
