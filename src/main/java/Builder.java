import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Builder {
    private static final AtomicLong count = new AtomicLong(0x10000000);

    public Camera buildCamera () {
        String[] uuids = new String[] {getRandomUuid(), getRandomUuid()};
        return new Camera(getNextSerial(), new MemoryCard(), uuids);
    }

    private String getNextSerial () {
        long serial = count.getAndIncrement();
        return StringUtils.leftPad(Long.toHexString(serial), 16, "0");
    }

    private String getRandomUuid () {
        return UUID.randomUUID().toString();
    }
}
