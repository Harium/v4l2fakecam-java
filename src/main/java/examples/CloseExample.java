package examples;

import com.harium.hci.fakecam.FakeCam;

public class CloseExample {

  static FakeCam fakeCam = new FakeCam();

  public static void main(String[] args) {
    int dev = 13;
    fakeCam.close(dev);
  }

}
