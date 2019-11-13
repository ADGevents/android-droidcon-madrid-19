# Android app for Droidcon Madrid 2019

## DI

The project uses dagger/dagger-android as DI framework. In the next sections
you'll see some guides to work with our dagger set up.

### Activity
To create a new activity and make DI work on it you have to only
add a new binding on `ActivityBindingModule` for the correspondent 
activity indicating the modules it needs in order to work
 
```
@ActivityScope
@ContributesAndroidInjector(
    modules = [
        InfoModule::class
    ]
)
abstract fun homeActivity(): HomeActivity
```

Also, your activity must extend `DaggerAppCompatActivity

### Fragment
To create a new fragment and make DI work on it you have to 
follow the next steps:

* Add the next method to the dagger module of the feature using the current sample fragment: 
```
@FragmentScope
@ContributesAndroidInjector
abstract fun contributeInfoFragment(): InfoFragment
```

* Add the mentioned dagger module to the module array the activity
containing the fragment has in its binding 
(e.g. `InfoModule` is the dagger module correspondent to the fragment) 

```
@ActivityScope
@ContributesAndroidInjector(
    modules = [
        InfoModule::class
    ]
)
abstract fun homeActivity(): HomeActivity
```

* Also, your fragment must extend `DaggerFragment

### View Model

To build a view model you have to create the view model class 
by extending `ViewModel` and adding all its dependencies 
in the constructor without annotating it with `@Inject` annotation.

Then you have to create a `ViewModelFactory` for your view model. This 
factory will have all the dependencies of the `ViewModel` on its constructor, 
that will be annotated with `@Inject`. Finally, we have to create a method `get` 
in the factory that receives a `Fragment` or `FragmentActivity` as parameter. Inside that method
you can use the extension function `Fragment.buildViewModel` to build the view model.

```
class InfoFragmentViewModelFactory @Inject constructor() {

    fun get(fragment: Fragment): InfoFragmentViewModel = fragment.buildViewModel {
        InfoFragmentViewModel()
    }
}
```

Then use the view model and the factory in the next way:

```
class InfoFragment private constructor() : DaggerFragment() {

    @Inject
    lateinit var infoFragmentViewModelFactory: InfoFragmentViewModelFactory
    private lateinit var infoFragmentViewModel: InfoFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoFragmentViewModel = infoFragmentViewModelFactory.get(this)
    }

    companion object {
        fun build(): InfoFragment = InfoFragment()
        const val TAG = "fragment:InfoFragment"
    }
}
```

## Run app as instant experience

In Android Studio, go to *Edit Configurations...* menu. 

For any build configuration, in *Installation Settings* section, check the *Deploy as instant app* box.

<img src="https://github.com/ADGevents/android-droidcon-madrid-19/blob/master/doc/instant_app_build_configuration.png" width="800" height="500">

Then run the app with the correspondent build configuration. And that's all!


