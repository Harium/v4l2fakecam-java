# fakecam
A thin JNI layer over v4l2loopback module.

## How to build

### Find the location of jni_md.h
```
find / -name jni_md.h 2> /dev/null
```

### Compile the library

Replace <PATH TO JNI_MD.H> by jni_md.h directory found by the previous command.

```
gcc -I/usr/lib/jvm/java-1.8.0/include -I<PATH TO JNI_MD.H> -lrt -lpthread -fPIC -shared -o libfakecam.so fakecam.c
```

Fedora 30 Example:

```
gcc -I/usr/lib/jvm/java-1.8.0/include -I/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.222.b10-0.fc30.x86_64/include/linux/ -lrt -lpthread -fPIC -shared -o libfakecam.so fakecam.c
```

It will produce a libfakecam.so file
