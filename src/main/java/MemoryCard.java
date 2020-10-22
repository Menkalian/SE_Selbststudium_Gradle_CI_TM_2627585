import java.util.Objects;
import java.util.Stack;

@SuppressWarnings("unused")
public class MemoryCard {
    Stack<Picture> store = new Stack<>();

    public MemoryCard () {
    }

    public Stack<Picture> getStore () {
        return this.store;
    }

    private void setStore (Stack<Picture> store) {
        this.store = store;
    }

    public int hashCode () {
        final int PRIME = 59;
        int result = 1;
        final Object $store = this.getStore();
        result = result * PRIME + ($store == null ? 43 : $store.hashCode());
        return result;
    }

    public boolean equals (final Object o) {
        if (o == this) return true;
        if (!(o instanceof MemoryCard)) return false;
        final MemoryCard other = (MemoryCard) o;
        if (!other.canEqual(this)) return false;
        final Object this$store = this.getStore();
        final Object other$store = other.getStore();
        return Objects.equals(this$store, other$store);
    }

    public String toString () {
        return "MemoryCard(store=" + this.getStore() + ")";
    }

    protected boolean canEqual (final Object other) {
        return other instanceof MemoryCard;
    }
}
