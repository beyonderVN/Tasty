# Project 3 - *Twitter Client*

**Name of your app** is an android app that allows a user to view his Twitter timeline and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **36** hours spent in total

## User Stories

The following **required** functionality is completed:

* [ok]	User can **sign in to Twitter** using OAuth login
* [ok]	User can **view tweets from their home timeline**
  * [ok] User is displayed the username, name, and body for each tweet
  * [ok] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
  * [ok] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.
    However there are [Twitter Api Rate Limits](https://dev.twitter.com/rest/public/rate-limiting) in place.
* [ok] User can **compose and post a new tweet**
  * [ok] User can click a “Compose” icon in the Action Bar on the top right
  * [ok] User can then enter a new tweet and post this to twitter
  * [ok] User is taken back to home timeline with **new tweet visible** in timeline

The following **optional** features are implemented:

* [ok] User can **see a counter with total number of characters left for tweet** on compose tweet page
* [ok] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [ok] User can **pull down to refresh tweets timeline**
* [ok] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in offline mode.
* [ok] User can tap a tweet to **open a detailed tweet view**
* [ ] User can **select "reply" from detail view to respond to a tweet**
* [ok] Improve the user interface and theme the app to feel "twitter branded"

The following **bonus** features are implemented:

* [ ] User can see embedded image media within the tweet detail view
* [ ] User can watch embedded video within the tweet
* [ok] Compose tweet functionality is build using modal overlay
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ok] [Leverage RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) as a replacement for the ListView and ArrayAdapter for all lists of tweets.
* [ok] Move the "Compose" action to a [FloatingActionButton](https://github.com/codepath/android_guides/wiki/Floating-Action-Buttons) instead of on the AppBar.
* [ok] On the Twitter timeline, leverage the [CoordinatorLayout](http://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout#responding-to-scroll-events) to apply scrolling behavior that [hides / shows the toolbar](http://guides.codepath.com/android/Using-the-App-ToolBar#reacting-to-scroll).
* [ok] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [ok] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Enable your app to [receive implicit intents](http://guides.codepath.com/android/Using-Intents-to-Create-Flows#receiving-implicit-intents) from other apps.  When a link is shared from a web browser, it should pre-fill the text and title of the web page when composing a tweet.
* [ ] When a user leaves the compose view without publishing and there is existing text, prompt to save or delete the draft.  The draft can be resumed from the compose view.

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/zyBM6UO.mp4' title='Video Walkthrough' width='' alt='Video Walkthrough' />
http://i.imgur.com/zyBM6UO.mp4
GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [ButterKnife] (https://github.com/JakeWharton/butterknife) - Field and method binding for Android views which uses annotation processing to generate boilerplate code for you
- [Rxjava] (https://github.com/ReactiveX/RxJava) -  Reactive Extensions for the JVM
- [Dagger] (https://github.com/square/dagger) - A fast dependency injector for Android and Java.
- [retrofit] (https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
- [Okhttp] (https://github.com/square/okhttp) - An HTTP+HTTP/2 client for Android and Java applications.

## License

    Copyright 2016 beyonderVN

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
