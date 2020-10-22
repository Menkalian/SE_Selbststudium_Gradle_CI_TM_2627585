import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {
    public static Consumer<Field> getCameraFieldAsserter (Camera camera) {
        return field -> {
            try {
                assertNotNull(field.get(camera));
            } catch (IllegalAccessException e) {
                Assertions.fail(e);
            }
        };
    }

    public static Executable getPictureCreationAsserter (Integer i, Camera camera) {
        return () -> {
            char[][] face = camera.getRawFacePicture(i);
            int[] coords = camera.getFaceArea(face);
            Picture picture = camera.extractFace(i, face, coords);
            assertNotNull(picture);
            assertNotNull(camera.getMemoryCard().store.peek());
            assertEquals(picture, camera.getMemoryCard().store.peek());
        };
    }

    public static Executable getPictureDataAsserter (Integer key, Camera camera) {
        return () -> {
            char[][] face = camera.getRawFacePicture(key);
            int[] coords = camera.getFaceArea(face);
            Picture picture = camera.extractFace(key, face, coords);
            assertNotNull(picture);
            assertNotNull(picture.getContent());
            char[][] content = picture.getContent();
            assertEquals(10, content.length);
            assertEquals(10, content[0].length);

            List<Character> faceChars = List.of('f', 'a', 'c', 'e');
            // char-type and streams is for some reason not as simple as the other datatypes. But yeah. This is how I got it to work.
            Predicate<char[]> allFacePredicate = arr -> CharBuffer.wrap(arr).chars().mapToObj(i -> (char) i).allMatch(faceChars::contains);
            assertTrue(Arrays.stream(content).allMatch(allFacePredicate));
        };
    }

    public static Executable getTestDataAsserter (Integer key, Camera camera, HashMap<Integer, Integer[]> testData) {
        return () -> {
            final int[] faceArea = camera.getFaceArea(camera.getRawFacePicture(key));
            for (int i = 0 ; i < faceArea.length ; i++) {
                assertEquals(testData.get(key)[i], faceArea[i], "Element " + i + " not as expected");
            }
        };
    }

    public static HashMap<Integer, Integer[]> loadTestData () throws FileNotFoundException {
        File expectedValues = new File(TestUtils.class.getResource("expected.txt").getFile());
        Scanner s = new Scanner(expectedValues);
        HashMap<Integer, Integer[]> toReturn = new HashMap<>();

        while (s.hasNextLine()) {
            String[] data = s.nextLine().split("\\|");
            final Integer[] values = Arrays.stream(data[1].split(",")).map(String::strip).map(Integer::parseInt).toArray(Integer[]::new);
            toReturn.put(Integer.parseInt(data[0].strip()), values);
        }
        return toReturn;
    }
}
