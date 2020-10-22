import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;

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
        System.out.println(camera);
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
}