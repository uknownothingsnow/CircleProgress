[![Android Arsenal](https://img.shields.io/badge/Android+Arsenal-CircleProgress-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1130)

inspired from
[https://github.com/daimajia/NumberProgressBar](https://github.com/daimajia/NumberProgressBar)

and CleanMaster

![CleanMaster](http://i.stack.imgur.com/sZP5t.png)

###Demo

![CircleProgress](https://raw.githubusercontent.com/lzyzsd/CircleProgress/master/demos/circle_progress.gif)

###Demo download [link](https://raw.githubusercontent.com/lzyzsd/CircleProgress/master/demos/example.apk)

###3 kinds of progress view are provided, DonutProgress, CircleProgress, ArcProgress

##Usage

please use jitpack

```
    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

###Gradle

```groovy
dependencies {
    compile 'com.github.lzyzsd:circleprogress:1.2.1'
}
```

###Notice

please always use same width and height for progress views

DonutProgress

```xml
    <com.github.lzyzsd.circleprogress.DonutProgress
        android:layout_marginLeft="50dp"
        android:id="@+id/donut_progress"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        custom:donut_progress="30"/>
```

![DonutProgress](https://raw.githubusercontent.com/lzyzsd/CircleProgress/master/demos/donutprogress_demo.png)

attrs for DonutProgress

```xml
    <declare-styleable name="DonutProgress">
        <attr name="donut_progress" format="integer"/>
        <attr name="donut_max" format="integer"/>
        <attr name="donut_unfinished_color" format="color"/>
        <attr name="donut_finished_color" format="color"/>
        <attr name="donut_finished_stroke_width" format="dimension"/>
        <attr name="donut_unfinished_stroke_width" format="dimension"/>
        <attr name="donut_text_size" format="dimension"/>
        <attr name="donut_text_color" format="color"/>
        <attr name="donut_text" format="string"/>
        <attr name="donut_prefix_text" format="string"/>
        <attr name="donut_suffix_text" format="string"/>
        <attr name="donut_background_color" format="color"/>
    </declare-styleable>
```

CircleProgress

```xml
    <com.github.lzyzsd.circleprogress.CircleProgress
        android:id="@+id/circle_progress"
        android:layout_marginLeft="50dp"
        android:layout_width="100dp"
        android:layout_height="100dp"
        custom:circle_progress="20"/>
```

![CircleProgress](https://raw.githubusercontent.com/lzyzsd/CircleProgress/master/demos/circleprogress_demo.png)

attrs for CircleProgress

```xml
    <declare-styleable name="CircleProgress">
        <attr name="circle_progress" format="integer"/>
        <attr name="circle_max" format="integer"/>
        <attr name="circle_unfinished_color" format="color"/>
        <attr name="circle_finished_color" format="color"/>
        <attr name="circle_text_size" format="dimension"/>
        <attr name="circle_text_color" format="color"/>
        <attr name="circle_prefix_text" format="string"/>
        <attr name="circle_suffix_text" format="string"/>
    </declare-styleable>
```

ArcProgress

```xml
    <com.github.lzyzsd.circleprogress.ArcProgress
        android:id="@+id/arc_progress"
        android:background="#214193"
        android:layout_marginLeft="50dp"
        android:layout_width="100dp"
        android:layout_height="100dp"
        custom:arc_progress="55"
        custom:arc_bottom_text="MEMORY"/>
```

![ArcProgress](https://raw.githubusercontent.com/lzyzsd/CircleProgress/master/demos/arcprogress_demo.png)

attrs for ArchProgress

```xml
    <declare-styleable name="ArcProgress">
        <attr name="arc_progress" format="integer"/>
        <attr name="arc_angle" format="float"/>
        <attr name="arc_stroke_width" format="dimension"/>
        <attr name="arc_max" format="integer"/>
        <attr name="arc_unfinished_color" format="color"/>
        <attr name="arc_finished_color" format="color"/>
        <attr name="arc_text_size" format="dimension"/>
        <attr name="arc_text_color" format="color"/>
        <attr name="arc_suffix_text" format="string"/>
        <attr name="arc_suffix_text_size" format="dimension"/>
        <attr name="arc_suffix_text_padding" format="dimension"/>
        <attr name="arc_bottom_text" format="string"/>
        <attr name="arc_bottom_text_size" format="dimension"/>
    </declare-styleable>
```


donut_inner_drawable

support add a drawable to the center

donut_show_text

show or hide bottom text


###Build

run `./gradlew assembleDebug` (Mac/Linux)

or

run `gradlew.bat assembleDebug` (Windows)


###Changes

version 1.1.0: add bottom text to DonutProgressView

DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2014 Bruce Lee <bruceinpeking#gmail.com>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
