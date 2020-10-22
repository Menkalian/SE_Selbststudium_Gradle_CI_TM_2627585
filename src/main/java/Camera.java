import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Camera implements ICamera {
    private String serialNumber;
    private boolean isOn;

    private MemoryCard memoryCard;

    // IRLeds are physically included
    @Setter(AccessLevel.PRIVATE)
    private IRLed[] irLeds;

    // Chips are physically included
    @Setter(AccessLevel.PRIVATE)
    private Chip[] chips;

    @Override
    public void on () {

    }

    @Override
    public char[][] getRawFacePicture (int faceID) {
        return new char[21][14];
    }

    @Override
    public int[] getFaceArea (char[][] face) {
        return new int[4];
    }

    @Override
    public Picture extractFace (int id, char[][] face, int[] area) {
        return null;
    }

    @Override
    public void off () {

    }

    @Getter
    private static class Chip {
        private String uuid;
        private Core[] cores;

        private static class Core {
        }
    }

    @Data
    @NoArgsConstructor
    private static class IRLed {
        private int brightness = 3;
    }
}
