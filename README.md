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

## Import
[JitPack](https://jitpack.io/)

Add it in your project's build.gradle at the end of repositories:

```gradle
repositories {
  // ...
  maven { url "https://jitpack.io" }
}
```

Step 2. Add the dependency in the form

```gradle
dependencies {
  compile 'com.github.kassisdion:Android-animated-toolbar:0.0.3'
}
```

## Usage
First define your toolbar under your xml :

```java
<?xml version="1.0" encoding="utf-8"?>
    <com.kassisdion.lib.toolbar.AnimatedToolbar
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize" />
```

Then initialize the toolbar under your activity :

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimatedToolbar toolbar = (AnimatedToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
```

And then, you can start the animation from your activity or from your fragment :

```java
        final int duration = 2 * 1000; //animation duration in ms
        toolbar.getAnimator()
               .startAnimation(duration, ToolbarAnimator.AnimationType.FADE_IN);
```

You can set some extra parameters before startion the animation :
```java
        toolbar.getAnimator()
                .setCallback(new ToolbarAnimatorCallback() {
                    @Override
                    public void hasEnded() {
                        //Do what you want, animation has ended
                    }
                })
                .setDelay(1 * 1000)//time before starting animation (in ms)
                .startAnimation(duration, ToolbarAnimator.AnimationType.FADE_IN
```

You can take look at the source app folder (which contain the sample) more information.

## License
See  LICENSE.txt

## Contact
If you have any new idea about this project, feel free to [contact me](mailto:florian.faisant@gmail.com).
