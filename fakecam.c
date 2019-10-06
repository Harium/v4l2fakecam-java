#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <linux/videodev2.h>

#define vidioc(op, arg) \
    if (ioctl(dev_fd, VIDIOC_##op, arg) == -1) \
        sysfail(#op); \
    else

// More Formats at: https://linuxtv.org/downloads/v4l-dvb-apis/uapi/v4l/videodev.html
JNIEXPORT jint JNICALL
openVideo(JNIEnv* env, jobject obj, jstring device, jint width, jint height, jint frameSize)
{
    const char *deviceChar = (*env)->GetStringUTFChars(env, device, 0);
    struct v4l2_format v;

    int dev_fd = open(deviceChar, O_RDWR);
    if (dev_fd == -1) sysfail(deviceChar);
    v.type = V4L2_BUF_TYPE_VIDEO_OUTPUT;
    vidioc(G_FMT, &v);
    v.fmt.pix.width = width;
    v.fmt.pix.height = height;
    v.fmt.pix.pixelformat = V4L2_PIX_FMT_RGB24;
    v.fmt.pix.sizeimage = frameSize;
    vidioc(S_FMT, &v);

    (*env)->ReleaseStringUTFChars(env, device, deviceChar);
    return dev_fd;
}

JNIEXPORT jint JNICALL
writeFrame(JNIEnv* env, jobject obj, jint dev_fd, jint frameSize, jcharArray frame)
{
  char* frameBytes = (*env)->GetCharArrayElements(env, frame, 0);

  int result = write(dev_fd, frame, frameSize);
  if (result != frameSize) {
    sysfail("write");
  }

  (*env)->ReleaseCharArrayElements(env, frame, frameBytes, 0);
  return result;
}

