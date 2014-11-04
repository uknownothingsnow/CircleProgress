inspired from
[https://github.com/daimajia/NumberProgressBar](https://github.com/daimajia/NumberProgressBar)

###Demo

![CircleProgress](https://raw.githubusercontent.com/lzyzsd/CircleProgress/master/circle_progress.gif)

##使用
甜甜圈效果

<me.bruce.circleprogress.DonutProgress
            android:layout_marginLeft="50dp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            custom:progress="20"
            custom:progress_unfinished_color="#ff0000"
            custom:progress_finished_stroke_width="10dp"/>

填充圆圈的效果

<me.bruce.circleprogress.CircleProgress
            android:layout_marginLeft="50dp"
            android:layout_width="100dp"
            android:layout_height="100dp"
            custom:progress="50"
            custom:progress_finished_color="#ff0000"
            custom:progress_unfinished_color="#00ff00"/>

###Build

run `./gradlew assembleDebug` (Mac/Linux)

or

run `gradlew.bat assembleDebug` (Windows)