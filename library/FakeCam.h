/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_harium_hci_fakecam_FakeCam */

#ifndef _Included_com_harium_hci_fakecam_FakeCam
#define _Included_com_harium_hci_fakecam_FakeCam
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_harium_hci_fakecam_FakeCam
 * Method:    open
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_com_harium_hci_fakecam_FakeCam_open
  (JNIEnv *, jobject, jstring, jint, jint);

/*
 * Class:     com_harium_hci_fakecam_FakeCam
 * Method:    writeFrame
 * Signature: (I[C)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harium_hci_fakecam_FakeCam_writeFrame
  (JNIEnv *, jobject, jint, jbyteArray);

/*
 * Class:     com_harium_hci_fakecam_FakeCam
 * Method:    close
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_harium_hci_fakecam_FakeCam_close
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
