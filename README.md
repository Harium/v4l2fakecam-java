# fakecam
A thin JNI layer over v4l2loopback module.

## How to build

### Find the location of jni_md.h
```shell script
find / -name jni_md.h 2> /dev/null
```

### Compile the library

Replace <PATH TO JNI_MD.H> by jni_md.h directory found by the previous command.

```shell script
gcc -I/usr/lib/jvm/java-1.8.0/include -I<PATH TO JNI_MD.H> -lrt -lpthread -fPIC -shared -o libfakecam.so fakecam.c
```

Fedora 30 Example:

```shell script
gcc -I/usr/lib/jvm/java-1.8.0/include -I/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.222.b10-0.fc30.x86_64/include/linux/ -lrt -lpthread -fPIC -shared -o libfakecam.so FakeCam.c
```

It will produce a libfakecam.so file

## Install v4l2loopback
Go to: https://github.com/umlaeute/v4l2loopback/releases
Dowload and extract the desired version
```shell script
sudo make
sudo make install
sudo modprobe v4l2loopback
```

### Check the new video device
```shell script
ls -ltrh /dev/video*
```

### Troubleshoot
If you are having issues (Bad Address) while reinstalling the module, try to remove older versions
```shell script
sudo modprobe -r v4l2loopback
find "/lib/modules/$(uname -r)" | grep v4l2loopback | sudo xargs rm
```
