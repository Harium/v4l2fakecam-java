# fakecam
A thin JNI layer over v4l2loopback module.

## How to build

### Build JNI Header
```
cd src/main/java
javah com.harium.hci.fakecam.FakeCam
mv *_FakeCam.h ../../../library/FakeCam.h
```

### Compile files
```
cd ../../../library
```
```
gcc -I/usr/lib/jvm/java-1.8.0/include -I/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.222.b10-0.fc30.x86_64/include/linux/ -Wall -lrt -lpthread -fPIC -shared -o libfakecam.so FakeCam.c
```

