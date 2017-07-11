LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_ROOT:=/Users/admin/Desktop/opencv/OpenCV-android-sdk
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include ${OPENCV_ROOT}/sdk/native/jni/OpenCV.mk

NDK_MODULE_PATH=/Users/admin/Library/Android/sdk/ndk-bundle
LOCAL_ARM_NEON := true
LOCAL_SRC_FILES := photosdk.cpp
LOCAL_CPPFLAGS := -std=gnu++0x
LOCAL_CFLAGS += -O2
LOCAL_LDLIBS += -llog -ldl
LOCAL_MODULE := native-snappik


include $(BUILD_SHARED_LIBRARY)