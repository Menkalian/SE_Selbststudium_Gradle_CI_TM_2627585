import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
