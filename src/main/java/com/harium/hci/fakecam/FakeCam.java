package com.harium.hci.fakecam;

public class FakeCam {

  static {
    System.loadLibrary("fakecam");
  }

  public native int open(String device, int width, int height);

  public native boolean writeFrame(int dev, byte[] frame);

  public native int close(int dev);

}
