#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <android/log.h>
#include <android/bitmap.h>

#define  LOG_TAG    "libstphoto"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)


using namespace std;
using namespace cv;

extern "C"
JNIEXPORT jstring JNICALL
Java_io_sontieu_snappik_PhotoSDK_nativeStringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ JNI";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_io_sontieu_snappik_PhotoSDK_nativeAbiInfo(
    JNIEnv* env
    , jobject thiz )
{
    #if defined(__arm__)
        #if defined(__ARM_ARCH_7A__)
        #if defined(__ARM_NEON__)
          #if defined(__ARM_PCS_VFP)
            #define ABI "armeabi-v7a/NEON (hard-float)"
          #else
            #define ABI "armeabi-v7a/NEON"
          #endif
        #else
          #if defined(__ARM_PCS_VFP)
            #define ABI "armeabi-v7a (hard-float)"
          #else
            #define ABI "armeabi-v7a"
          #endif
        #endif
      #else
       #define ABI "armeabi"
      #endif
    #elif defined(__i386__)
    #define ABI "x86"
    #elif defined(__x86_64__)
    #define ABI "x86_64"
    #elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
    #define ABI "mips64"
    #elif defined(__mips__)
    #define ABI "mips"
    #elif defined(__aarch64__)
    #define ABI "arm64-v8a"
    #else
    #define ABI "unknown"
    #endif
        return env->NewStringUTF("ABI " ABI ".");
}

extern "C"
void JNICALL
Java_io_sontieu_snappik_PhotoSDK_nativeSalt(JNIEnv *env, jobject instance,
                                                                           jlong matAddrGray,
                                                                           jint nbrElem) {

    Mat &mGr = *(Mat *) matAddrGray;
    for (int k = 0; k < nbrElem; k++) {
        int i = rand() % mGr.cols;
        int j = rand() % mGr.rows;
        mGr.at<uchar>(j, i) = 255;
    }
}

extern "C"
void JNICALL
Java_io_sontieu_snappik_PhotoSDK_nativeRotate(JNIEnv *env, jobject instance,
                                                                           jlong matSrcAddr,
                                                                           jlong matDstAddr,
                                                                           jint centerX,
                                                                           jint centerY,
                                                                           jdouble angle,
                                                                           jdouble scale) {
    LOGI("Rotate params: centerX: %d, centerY: %d, angle: %lf, scale: %lf", centerX, centerY, angle, scale);

    Mat &matSrc = *(Mat*) matSrcAddr;
    Mat &matDst = *(Mat*) matDstAddr;

    LOGI("Rotate Matsrc: %d-%d, Matdst: %d-%d", matSrc.cols, matSrc.rows, matDst.cols
            , matDst.rows);

    Point center = Point(centerX, centerY);
    Mat rot_mat(2, 3, CV_32FC1);

    rot_mat = getRotationMatrix2D(center, angle, scale);
    warpAffine(matSrc, matDst, rot_mat, matDst.size());
}

extern "C"
void JNICALL
Java_io_sontieu_snappik_PhotoSDK_nativeCrop(JNIEnv *env, jobject instance,
                                                    jlong matSrcAddr, jlong matDstAddr
                                                    , jint x, jint y
                                                    , jint width, jint height) {
    LOGI("Crop params: x: %d, y: %d, width: %d, height: %d", x, y, width, height);
    LOGI("Matsrc: %ld, MatDst: %ld", matSrcAddr, matDstAddr);

    Mat &matSrc = *(Mat*) matSrcAddr;
    Mat &matDst = *(Mat*) matDstAddr;

    cv::Rect roi;
    roi.x = x;
    roi.y = y;
    roi.width = width;
    roi.height = height;


    // Mat matResult = matSrc(roi);
    // matResult.copyTo(matDst);
    matDst = matSrc(roi);
}

extern "C"
void JNICALL
Java_io_sontieu_snappik_PhotoSDK_nativeContrastBrightness(JNIEnv *env, jobject instance,jlong matSrcAddr, jlong matDstAddr,jdouble contrast, jint brightness) {
    LOGI("Contrast Brightness params: contrast: %lf, brightness: %d", contrast, brightness);


    Mat &matSrc = *(Mat*) matSrcAddr;
    Mat &matDst = *(Mat*) matDstAddr;


    LOGI("MatSrc: %d x %d, MatDst: %d x %d\n, matDst: %ld, matSrc: %ld", matSrc.cols, matSrc.rows
                , matDst.cols, matDst.rows, matDstAddr, matSrcAddr);

    for (int y = 0; y < matSrc.rows; y++) {
            for (int x = 0; x < matSrc.cols; x++) {
                for (int c = 0; c < 3; c++) {
                    // matDst.at<Vec3b>(y, x)[c] = saturate_cast<uchar>(contrast * (matSrc.at<Vec3b>(y, x)[c]) + brightness);
                    matDst.at<Vec3b>(y, x)[c] = 255;
                }
            }
    }
}

