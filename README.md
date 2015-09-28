# Android-animated-toolbar

## What's Android-animated-toolbar ?
One day, I saw a question about ["How to implement fade_in on the under the actionBar"](http://stackoverflow.com/a/32800153/5215998).
I find this question really interesting so i started to find out a solution and 1 day later this sample was born.

## Demo
![Demo](http://img11.hostingpics.net/pics/879507animatedToolbar.gif)

## Feature
* Same characteristics as android.support.v7.widget.Toolbar
* Can be use under fragment and activity.
* Two animations : fade_in and fade_out.
* You can choose the animation duration.
* You can choose a delay (time before launching the animation).
* You can choose the color of the animation.

## Usage
First define your toolbar under your xml :

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.kassisdion.animatedtoolbar.lib.toolbar.AnimatedToolbar
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize" />

</LinearLayout>
```

Then initialize the toolbar under your activity :

```java
    private AnimatedToolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final AnimatedToolbar toolbar = (AnimatedToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    
    public AnimatedToolbar getToolbar() {
        return mToolbar;
    }
```

And then, you can start the animation from your activity or from your fragment :

```java
    toolbar.getAnimator()
        .setCallback(mToolbarAnimatorCallback)
        .startAnimation(2 * 1000, ToolbarAnimator.AnimationType.FADE_IN);
```

You can take look at the source code for more information.

## License
See  LICENSE.txt

## Contact
If you have any new idea about this project, feel free to [contact me](mailto:florian.faisant@gmail.com).
