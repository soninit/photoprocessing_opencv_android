package io.sontieu.snappik;
/**
 * Created by ttson
 * Date: 7/7/2017.
 */

public class PhotoSDK {
    public static String abiInfo() {
        return nativeAbiInfo();
    }

    public static String stringFromJNI() {
        return nativeStringFromJNI();
    }

    public static void salt(long matAddr, int nElem) {
        nativeSalt(matAddr, nElem);
    }

    public static void rotate(long matSrcAddr, long matDstAddr, int centerX, int centerY
            , double angle, double scale) {
        nativeRotate(matSrcAddr, matDstAddr, centerX, centerY, angle, scale);
    }

    public static void crop(long matSrcAddr, long matDstAddr, int x, int y, int width, int height) {
        nativeCrop(matSrcAddr, matDstAddr, x, y, width, height);
    }

    public static void setBrightnessContrast(long matSrcAddr, long matDstAddr, double contrast, int brightness) {
        nativeContrastBrightness(matSrcAddr, matDstAddr, contrast, brightness);
    }

    /**
     *
     * Method from native-opencv
     */
    @SuppressWarnings({"JniMissingFunction"})
    private static native String nativeAbiInfo();
    @SuppressWarnings({"JniMissingFunction"})
    private static native String nativeStringFromJNI();
    @SuppressWarnings({"JniMissingFunction"})
    private static native void nativeSalt(long matAddr, int nElem);
    @SuppressWarnings({"JniMissingFunction"})
    private static native void nativeRotate(long matSrcAddr, long matDstAddr
            , int centerX, int centerY
            , double angle, double scale);
    @SuppressWarnings({"JniMissingFunction"})
    private static native void nativeCrop(long matSrcAddr, long matDstAddr, int x, int y, int width, int height);
    @SuppressWarnings({"JniMissingFunction"})
    private static native void nativeContrastBrightness(long matSrcAddr, long matDstAddr, double contrast, int brightness);
}
