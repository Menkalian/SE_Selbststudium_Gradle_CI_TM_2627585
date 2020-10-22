import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class Camera implements ICamera {
    private String serialNumber;
    private boolean isOn;

    @Setter(AccessLevel.PUBLIC)
    private MemoryCard memoryCard;

    // IRLeds are physically included
    private IRLed[] irLeds;

    // Chips are physically included
    private Chip[] chips;

    public Camera (String serialNumber, MemoryCard memoryCard, String[] chipsUuids) {
        this.serialNumber = serialNumber;
        this.memoryCard = memoryCard;
        this.isOn = false;
        this.irLeds = new IRLed[24];
        for (int i = 0 ; i < this.irLeds.length ; i++) {
            this.irLeds[i] = new IRLed();
        }
        this.chips = new Chip[2];
        chips[0] = new Chip(chipsUuids[0]);
        chips[1] = new Chip(chipsUuids[1]);

    }

    @Override
    public void on () {
        isOn = true;
    }

    @Override
    public char[][] getRawFacePicture (int faceID) {
        try {
            File faceFile;
            if (faceID < 10) {
                faceFile = new File(this.getClass().getResource("face0" + faceID + ".txt").toURI());
            } else {
                faceFile = new File(this.getClass().getResource("face" + faceID + ".txt").toURI());
            }
            Scanner faceReader = new Scanner(faceFile);
            char[][] faceData = new char[21][14];

            int row = 0;
            int column = 0;

            while (faceReader.hasNext()) {
                String read = (faceReader.nextLine());
                for (char c : read.toCharArray()) {
                    faceData[row][column] = c;
                    column++;
                }
                row++;
                column = 0;
            }
            return faceData;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return new char[21][14];
        }
    }

    @Override
    public int[] getFaceArea (char[][] face) {
        int x1 = -1;
        int y1 = -1;
        int x2 = -1;
        int y2 = -1;
        for (int i = 0 ; i < face.length ; i++) {
            for (int j = 0 ; j < face[i].length ; j++) {
                if (face[i][j] == '+') {
                    if (x1 == -1) {
                        x1 = i;
                        y1 = j + 1;
                    }
                    x2 = i;
                    y2 = j - 1;
                }
            }
        }
        return new int[] {x1, y1, x2, y2};
    }

    @Override
    public Picture extractFace (int id, char[][] face, int[] area) {
        char[][] faceData = new char[10][];
        int rowOffset = area[0];

        for (int i = 0 ; i <= area[2] - rowOffset ; ++i) {
            faceData[i] = Arrays.copyOfRange(face[rowOffset + i], area[1], area[3] + 1);
        }

        Picture picture = new Picture(id, faceData, Instant.now().toEpochMilli());
        getMemoryCard().store.push(picture);
        return picture;
    }

    @Override
    public void off () {
        isOn = false;
    }

    @Getter
    @ToString
    private static class Chip {
        private final String uuid;
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
        private final Core[] cores;

        public Chip (String uuid) {
            this.uuid = uuid;
            this.cores = new Core[2];
            for (int i = 0 ; i < cores.length ; i++) {
                cores[i] = new Core();
            }
        }

        @ToString
        private static class Core {
        }
    }

    @Data
    @NoArgsConstructor
    private static class IRLed {
        private int brightness = 3;
    }
}
