package examples;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.ffmpegcli.FFmpegCliDriver;
import com.harium.hci.fakecam.FakeCam;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Create output device
 * sudo modprobe v4l2loopback devices=1 video_nr=2 exclusive_caps=1
 *
 * Check if video was created
 * ls -ltrh /dev/video*
 *
 * Check if output is correct
 * ffplay -vf hflip /dev/video2
 * xawtv -c /dev/video2
 */
public class CameraExample {

  public static final int WIDTH = 640;
  public static final int HEIGHT = 480;

  public static final String CAPTURE_DEVICE = "/dev/video0";
  public static final String OUTPUT_DEVICE = "/dev/video2";

  public static void main(String[] args) {
    Webcam.setDriver(new FFmpegCliDriver());
    Webcam webcam = Webcam.getWebcamByName(CAPTURE_DEVICE);
    webcam.setViewSize(new Dimension(WIDTH, HEIGHT));
    webcam.open();

    FakeCam cam = new FakeCam();
    int dev = cam.open(OUTPUT_DEVICE, WIDTH, HEIGHT);
    System.out.println("Open device: " + OUTPUT_DEVICE);

    if (dev < 0) {
      return;
    }

    byte[] frame = new byte[WIDTH * HEIGHT * 3];

    while (true) {
      BufferedImage image = webcam.getImage();
      // Apply your effects here
      drawImage(frame, image);

      /*try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }*/

      boolean success = cam.writeFrame(dev, frame);
      if (!success) {
        System.out.println("Failed to write");
        break;
      }
    }

    cam.close(dev);
  }

  private static void drawImage(byte[] frame, BufferedImage image) {
    // Draw at center

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int i = (y * WIDTH + x) * 3;

        // Flip Horizontally
        Color color = new Color(image.getRGB(WIDTH - 1 - x, y));
        //Color color = new Color(image.getRGB(x, y));
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
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
