# PhotoGallery

Native app that uses the fake API: 
https://jsonplaceholder.typicode.com/

When the network api is called the result is saved in a local database 
in order to use it when there is no network access.

The app lets you:
- Create an image either from the gallery or from the camera.
- Edit an image by editing its title.
- Remove an image.
This API operations are fake so the database is not updated with the return of the API.

This app uses MVVM but trying to follow the [Recommended app architecture](https://developer.android.com/jetpack/guide#recommended-app-arch) 
at the same time.

## Getting Started

The project files can be found at https://github.com/jeprubio/photogallery
	
Easiest and simple way to download code from Github is to download the whole code in a zip file by clicking the "Code" / "Download Zip" button on the right hand side of the page.
	
You can then save the zip file into a convenient location on your computer and start working on it.
	
Another way to get the code is using git:
	
git clone git@github.com:jeprubio/photogallery.git

## Prerequisites

Android studio should be installed in order to run the app.

Follow the instructions at https://developer.android.com/studio/install depending on which SO your computer is running.

## How to run the application

Open the code in android studio. Wait until gradle finishes loading the dependencies.

Press the play button.

## Screenshots

![https://i.ibb.co/pRf8RBr/home.png](https://i.ibb.co/pRf8RBr/home.png)
![https://i.ibb.co/2qjCmxY/details.png](https://i.ibb.co/2qjCmxY/details.png)
![https://i.ibb.co/fnFB9bn/image-picker.png](https://i.ibb.co/fnFB9bn/image-picker.png)

## Many of the libraries used

- [Timber](https://github.com/JakeWharton/timber) - Library to perform logging.

- [Gson](https://github.com/google/gson) - To parse from and to json (configured retrofit to use it).

- [Retrofit](https://github.com/square/retrofit) - To perform API calls and parse the response.

- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - For dependency injection to create dependency objects.

- [Coil](https://github.com/coil-kt/coil) - Loading and caching images (I started using picasso but moved to coil soon after).

- [kpermissions](https://github.com/fondesa/kpermissions) - An Android library totally written in Kotlin that helps to request runtime permissions.

## And for testing:

- [Mockk](https://mockk.io/) - A mocking library similar to mockito that allows you to mock suspend functions.

- [android.arch.core.executor.testing](https://developer.android.com/reference/android/arch/core/executor/testing/package-summary) - to change the background executor for one that executes synchronously.

- [kotlinx-coroutines-test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/) - It has test utilities for working with coroutines.
