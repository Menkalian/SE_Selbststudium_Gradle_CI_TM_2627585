import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Stack;

@Data
public class MemoryCard {
    @Setter(AccessLevel.PRIVATE)
    Stack<Picture> store = new Stack<>();
}
