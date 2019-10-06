package examples;

import com.harium.hci.fakecam.FakeCam;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * sudo modprobe v4l2loopback devices=2 exclusive_caps=1,1 ls -ltrh /dev/video*
 * <p>
 * ffplay /dev/video2
 * xawtv -c /dev/video2
 */
public class ImageExample {

  public static final int WIDTH = 640;
  public static final int HEIGHT = 480;
  public static final String DEVICE = "/dev/video2";

  public static void main(String[] args) {
    BufferedImage image = loadImage();

    FakeCam cam = new FakeCam();
    int dev = cam.open(DEVICE, WIDTH, HEIGHT);
    System.out.println("Open device: " + DEVICE);

    if (dev < 0) {
      return;
    }

    byte[] frame = new byte[WIDTH * HEIGHT * 3];

    while (true) {
      drawImage(frame, image);

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

  private static BufferedImage loadImage() {
    String path = System.getProperty("user.dir");
    path += "/src/main/resources/logo.png";
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }

  private static void drawImage(byte[] frame, BufferedImage image) {

    int xOffset = WIDTH / 2 - image.getWidth() / 2;
    int yOffset = HEIGHT / 2 - image.getHeight() / 2;

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = image.getWidth() - 1; x >= 0; x--) {
        int i = ((y + yOffset) * WIDTH + (x + xOffset)) * 3;

        Color color = new Color(image.getRGB(x, y));
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
