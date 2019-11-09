# Android app for Droidcon Madrid 2019

## Adding a new module

As we want to bring an instant app experience mixed with AAB delivery format all new modules created should be dynamic feature modules.

To create a new dynamic feature module in AS, go to File -> New -> New Module... and select "Dynamic Feature Module".

Once the module has been created you have to think if the module should be an instant feature or not

* If the module should be an instant feature delivered instantly and at install time you have to ensure the next tags and values are present on its AndroidManifest.xml

```
dist:instant="true" dist:onDemand="false"
```

* If the module should be a dynamic feature delivered at install time you have you have to ensure the next tags and values are present on its AndroidManifest.xml

```
dist:instant="false" dist:onDemand="false"
```

## Run app as instant experience

In Android Studio, go to *Edit Configurations...* menu. 

Set up a new build configuration matching the one you can see in the next image:

<img src="https://https://github.com/ADGevents/android-droidcon-madrid-19/blob/master/doc/InstantAppBuildConfiguration.jpg" width="500" height="500">

And just run the app from AS as you would do with an usual android app