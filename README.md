# BugJump
## [中文](README.CN.md)

* Help BugJump fix bugs

## Features

* Fixed a crash caused by multiple rendering threads calling fastutil at the same time when too many client modules were
  installed
  * for example
    ```
      java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 513
          at it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.rehash(Int2ObjectOpenHashMap.java:1334)
          at it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.insert(Int2ObjectOpenHashMap.java:279)
          at it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.computeIfAbsent(Int2ObjectOpenHashMap.java:500)
          at net.minecraft.class_377.method_2011(class_377.java:137)
          at net.minecraft.class_327.method_27516(class_327.java:57)
          at net.minecraft.class_5225.method_27496(class_5225.java:41)
          at net.minecraft.class_5223.method_27477(class_5223.java:17)
          at net.minecraft.class_5223.method_27473(class_5223.java:120)
          at net.minecraft.class_5223.method_27472(class_5223.java:84)
          at net.minecraft.class_5223.method_27479(class_5223.java:80)
          at net.minecraft.class_5225.method_27482(class_5225.java:40)
          at net.minecraft.class_327.method_1727(class_327.java:364)
          at net.minecraft.class_329.method_1754(class_329.java:651)
          at net.minecraft.class_329.method_1753(class_329.java:238)
          at net.minecraft.class_757.method_3192(class_757.java:862)
          at net.minecraft.class_310.method_1523(class_310.java:1177)
          at net.minecraft.class_310.method_1514(class_310.java:768)
          at net.minecraft.client.main.Main.method_44604(Main.java:244)
          at net.minecraft.client.main.Main.main(Main.java:51)
          at net.fabricmc.loader.impl.game.minecraft.MinecraftGameProvider.launch(MinecraftGameProvider.java:461)
          at net.fabricmc.loader.impl.launch.knot.Knot.launch(Knot.java:74)
          at net.fabricmc.loader.impl.launch.knot.KnotClient.main(KnotClient.java:23)
          at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
          at java.base/java.lang.reflect.Method.invoke(Method.java:577)
          at oolloo.jlw.Wrapper.invokeMain(Wrapper.java:58)
          at oolloo.jlw.Wrapper.main(Wrapper.java:51)
      ```
    
* Raising the packet size limit from 2MB to 2GB
 
## Inspired By

* [Quilt Loading Screen](https://github.com/emmods/quilt_loading_screen)
* [Mod-erate Loading Screen](https://github.com/enjarai/moderate-loading-screen)
