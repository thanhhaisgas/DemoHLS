# DemoHLS
**DemoHLS** is an android app that allows live TV can run ads at real time by using real time database of FireBase.
## Run demo
Cloning the repository and depending on the modules locally is required when using some ExoPlayer extension modules. It's also a suitable approach if you want to make local changes to ExoPlayer, or if you want to use a development branch.

First, clone the repository into a local directory and checkout the desired branch:
```
  git clone https://github.com/google/ExoPlayer.git
  git checkout release-v2
```
Next, add the following to your project's settings.gradle file, replacing path/to/exoplayer with the path to your local copy:
```
  gradle.ext.exoplayerRoot = 'path/to/ExoPlayer'
  gradle.ext.exoplayerModulePrefix = 'exoplayer-'
  apply from: new File(gradle.ext.exoplayerRoot, 'core_settings.gradle')
```
You should now see the ExoPlayer modules appear as part of your project. You can depend on them as you would on any other local module, for example:
```
  compile project(':exoplayer-library-core')
  compile project(':exoplayer-library-dash')
  compile project(':exoplayer-library-ui')
```
## Open-source libraries used
[ExoPlayer](https://github.com/google/ExoPlayer) - ExoPlayer is an application level media player for Android. It provides an alternative to Android’s MediaPlayer API for playing audio and video both locally and over the Internet. ExoPlayer supports features not currently supported by Android’s MediaPlayer API, including DASH and SmoothStreaming adaptive playbacks. Unlike the MediaPlayer API, ExoPlayer is easy to customize and extend, and can be updated through Play Store application updates.
