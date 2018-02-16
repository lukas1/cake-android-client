Coding Test (fictitious)


***
NOTE: PLEASE DO NOT USE THIRD PARTY LIBRARIES. However, feel free to state which third party libraries you might have used.
***


Attached you’ll find the code for a simple mobile app to browse cakes. The developer who wrote this code was a summer intern and unfortunately did not manage to finish the project.  The project compiles but the app crashes as soon as it runs.

The app loads a JSON feed containing a repeated list of cakes, with title, image and description from a URL, then displays the contents of the feed in a scrollable list.

We would like you to fix the crash bug, ensure the functionality of the app works as expected (all images display correctly on the table, all text is readable) and performs smoothly.  Use of 3rd party libraries are prohibited for this project due to its sensitive nature.

Additionally, feel free to refactor and optimise the code where appropriate. Ideally, what we’d be looking for is:

* Simple fixes for crash bug(s)
* Application to support rotation
* Safe and efficient loading of images
* Removal of any redundant code
* Refactoring of code to best practices

This is your opportunity to show us how you think and Android app should be architected, please make any changes you feel would benefit the app.

The test should take around 2 hours, certainly no longer than 3 hours. Good luck!

***
Notes by me
***

Testing - there are no unit tests, because adding libraries is forbidden in the exercise and unit tests without JUnit is a torture.

Listview - since no libraries are allowed, project utilizes ListView, although it should really use RecyclerView

Loading images - this should just be handled by Picasso or Glide or other library, again libraries are not allowed

HTTP REST API - this should be done by Retrofit together with Gson or Moshi for JSON parsing, however libraries are not allowed

Asynchrony - Picasso and Retrofit would actually solve asynchronous calls on background thread. Since no libraries are allowed,
instead IO is used, a type, that returns immediately, but the operation is run later, similar to Future-Promise, or RxJava's Observable.
A new thread is created for each async operation. Ideally there should be a thread pool used to limit amount of active threads, but
that's too time consuming for this demo. Anyway, for these types of tasks Kotlin's coroutines are even better choice. Using Kotlin was
however discouraged.
Currently it's a bit difficult to test IO because it relies on Handler, but that could be solved by injecting async runner and for
testing a synchronous runner could be used. Check out my HeroesDemo GitHub project to see it in action there.

ViewModel - View Model is being kept alive by utilizing FragmentActivity's onRetainCustomNonConfigurationInstance()
Activity can subscribe to updates from View Model by subscribing to LiveData of interest.

CakeListLoadingContext - since no libraries are allowed, dependency injection is made more difficult.
In this case I create a single CakeListLoadingContext object for loading cakes, that contains all
dependencies needed down the line. This has the advantage of ease of testing. Theoretically I could write
an integration test for ViewModel only, adding my own context to its constructor, avoiding mocking as much as possible.

Not using AsyncTask or AsyncTaskLoader - these two should be considered an antipattern, they're clumsy, they're promoting keeping
logic in activities or fragments and it's not easy to test them.