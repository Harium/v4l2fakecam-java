package examples;

import com.harium.hci.fakecam.FakeCam;

/**
 * sudo modprobe v4l2loopback devices=2 exclusive_caps=1,1
 * ls -ltrh /dev/video*
 *
 * ffplay /dev/video2 xawtv -c /dev/video2
 */
public class Main {

  public static final int WIDTH = 320;
  public static final int HEIGHT = 240;
  public static final String DEVICE = "/dev/video2";

  public static void main(String[] args) {
    /*String path = System.getProperty("user.dir") + "/library/";
    System.load(path + "libfakecam.so");*/

    FakeCam cam = new FakeCam();
    int dev = cam.open(DEVICE, WIDTH, HEIGHT);
    System.out.println("Open device: " + DEVICE);

    if (dev < 0) {
      return;
    }

    byte[] frame = new byte[WIDTH * HEIGHT * 3];

    while (true) {
      drawImage(frame);

      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      boolean success = cam.writeFrame(dev, frame);
      if (!success) {
        System.out.println("Failed to write");
        break;
      }
    }

    cam.close(dev);
  }

  private static void drawImage(byte[] frame) {
    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        int i = (y * WIDTH + x) * 3;

        int r = 0;
        int g = 255;
        int b = 0;
        putPixel(frame, i, r, g, b);
      }
    }
  }

  private static void putPixel(byte[] frame, int i, int r, int g, int b) {
    frame[i] = (byte) r;
    frame[i + 1] = (byte) g;
    frame[i + 2] = (byte) b;
  }

}
