# LoadingDotsView
a user-defined view for loading or viewPager's indicator

    自定义的一个加载等待的控件，也可以做为viewPager的指示器，自己一个练手的自定义控件。

    此控件可以自定义加载的小圆点的数量，间距，默认半径，默认颜色，指示颜色，颜色切换频率，半径浮动大小等。
没图说个结巴，看下面的效果图：

![screenshot](dots.gif)

效果图的效果没有真机上看的效果好，见谅。

可以自定义的属性如下：

```java
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="LoadingDotsView">
        <!-- 豆子的数量 -->
        <attr name="dot_count" format="integer"/>
        <!-- 豆子的间距 -->
        <attr name="dot_spacing" format="dimension"/>
        <!-- 豆子的半径 -->
        <attr name="dot_radius" format="dimension"/>
        <!-- 豆子的选中颜色 -->
        <attr name="dot_select_color" format="color"/>
        <!-- 豆子的默认颜色 -->
        <attr name="dot_default_color" format="color"/>
        <!-- 豆子的颜色切换时间 -->
        <attr name="switch_duration" format="integer"/>
        <!-- 豆子浮动的大小 -->
        <attr name="dot_radius_float" format="dimension"/>
    </declare-styleable>

    <declare-styleable name="RoundCornerImageView">
        <attr name="corner_radius" format="dimension"/>
    </declare-styleable>
</resources>
```

在布局文件中使用的方法如下：
```java
<com.***.LoadingDotsView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:orientation="horizontal"
        app:dot_count="9"
        app:dot_radius="4dp"
        app:dot_default_color="#ff0000"
        app:dot_select_color="#00ff00"
        app:switch_duration="500"
        app:dot_spacing="10dp"
        app:dot_radius_float="2dp"/>
```

本屌是混迹在`鸿洋`大神的`玩 Android 2群 423372824`的一个小透明，正在努力学习coding中……
