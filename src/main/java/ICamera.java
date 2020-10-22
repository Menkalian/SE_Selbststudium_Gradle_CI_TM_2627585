public interface ICamera {
    void on ();

    char[][] getRawFacePicture (int faceID);

    int[] getFaceArea (char[][] face);

    Picture extractFace (int id, char[][] face, int[] area);

    void off ();
}
