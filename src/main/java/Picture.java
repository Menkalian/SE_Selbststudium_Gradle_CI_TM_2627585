@SuppressWarnings("unused")
public class Picture {
    private int faceID;
    private char[][] content;
    private long nanoTimeStamp;

    public Picture (int faceID, char[][] content, long nanoTimeStamp) {
        this.faceID = faceID;
        this.content = content;
        this.nanoTimeStamp = nanoTimeStamp;
    }

    public int getFaceID () {
        return this.faceID;
    }

    public void setFaceID (int faceID) {
        this.faceID = faceID;
    }

    public char[][] getContent () {
        return this.content;
    }

    public void setContent (char[][] content) {
        this.content = content;
    }

    public long getNanoTimeStamp () {
        return this.nanoTimeStamp;
    }

    public void setNanoTimeStamp (long nanoTimeStamp) {
        this.nanoTimeStamp = nanoTimeStamp;
    }

    public int hashCode () {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getFaceID();
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getContent());
        final long $nanoTimeStamp = this.getNanoTimeStamp();
        result = result * PRIME + (int) ($nanoTimeStamp >>> 32 ^ $nanoTimeStamp);
        return result;
    }

    public boolean equals (final Object o) {
        if (o == this) return true;
        if (!(o instanceof Picture)) return false;
        final Picture other = (Picture) o;
        if (!other.canEqual(this)) return false;
        if (this.getFaceID() != other.getFaceID()) return false;
        if (!java.util.Arrays.deepEquals(this.getContent(), other.getContent())) return false;
        return this.getNanoTimeStamp() == other.getNanoTimeStamp();
    }

    public String toString () {
        return "Picture(faceID=" + this.getFaceID() + ", content=" + java.util.Arrays.deepToString(this.getContent()) + ", nanoTimeStamp=" + this.getNanoTimeStamp() + ")";
    }

    protected boolean canEqual (final Object other) {
        return other instanceof Picture;
    }
}
