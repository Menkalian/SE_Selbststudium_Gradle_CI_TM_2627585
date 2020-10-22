import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CameraTest {
    private Camera camera;

    @BeforeEach
    void setUp () {
        Builder b = new Builder();
        camera = b.buildCamera();
    }

    @Test
    @Order(1)
    void testCameraBuilt () {
        assertNotNull(camera);
        Arrays.stream(Camera.class.getFields()).forEach(TestUtils.getCameraFieldAsserter(camera));
    }

    @Test
    @Order(2)
    void testCameraPower () {
        assertFalse(camera.isOn());
        camera.on();
        assertTrue(camera.isOn());
        camera.off();
        assertFalse(camera.isOn());
    }

    @TestFactory
    @Order(3)
    Collection<DynamicTest> checkFaceCoordinates () {
        try {
            final HashMap<Integer, Integer[]> testData = TestUtils.loadTestData();
            return testData.keySet().stream()
                           .map(i -> DynamicTest.dynamicTest("Face Coordinates Test " + i, TestUtils.getTestDataAsserter(i, camera, testData)))
                           .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            Assertions.fail("Could not load testData");
            return Collections.emptyList();
        }
    }

    @TestFactory
    @Order(4)
    Collection<DynamicTest> checkPictureCreation () {
        try {
            final HashMap<Integer, Integer[]> testData = TestUtils.loadTestData();
            return testData.keySet().stream()
                           .map(i -> DynamicTest.dynamicTest("Picture Creation Test " + i, TestUtils.getPictureCreationAsserter(i, camera)))
                           .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            Assertions.fail("Could not load testData");
            return Collections.emptyList();
        }
    }

    @TestFactory
    @Order(5)
    Collection<DynamicTest> checkPictureContent () {
        try {
            final HashMap<Integer, Integer[]> testData = TestUtils.loadTestData();
            return testData.keySet().stream()
                           .map(i -> DynamicTest.dynamicTest("Picture Content Test " + i, TestUtils.getPictureDataAsserter(i, camera)))
                           .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            Assertions.fail("Could not load testData");
            return Collections.emptyList();
        }
    }
}