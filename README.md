buffer
======

Buffer Android client. Download v0.1 [here][14].

Why spend time on this:
=======================
- I want to try a new app architecture which is described [here][1].
- Try Buffer API.
- And maybe get a job at Buffer :)

Libraries:
==========
- [Scribe][2] for OAuth.
- [Dagger][3] for dependency injection.
- [android-priority-jobqueue][4] for background job management.
- [Otto][5] for event bus.
- [Retrofit][6] for REST client.
- [Picasso][7] for image download and caching.
   
Feature proposal (aka: things I wish I have time to do and will do if I have time):
================================
- Offline editing for updates, including create new ones, reorder, edit, share, delete, etc…
- Edit and create new updates.
- Cache and prefetch updates.
- Shuffle updates.
- Drag and drop updates to reorder.
- [Swipe list view][8]:
  - It may be easier for user to figure out than floating context menu.
  - I implemented this feature but it doesn’t work well with ViewPager, since ViewPager also depends on swipe gestures.
- Polish UI.

Things I learned:
=================
- Buffer API.
- [New architecture][1].
- android-priority-jobqueue. It’s would be great if the library could:
  - Cancel scheduled jobs.
  - Collapse scheduled jobs into 1, using either group id or another id.
- Retrofit.
- Drop-down navigation with custom views.
- Swipe list view.

Job priorities: 
===============
[`SYNC`][13] is the lowest one, then [`PREFETCH`][13] and [`UI`][13]. [`UI`][13] is the highest priority since most of the time, user is actively waiting for the data.

Cache policies:
===============
- [`AccessTokenController`][9]: data is cached in memory and persistent store. No other fancy things since we can’t get access token automatically.
- [`ConfigurationController`][10]: data is cached in memory and persistent store.
  - In constructor: 
    - Load data from persistent store and save to memory.
  - When data is requested:
    - If it is in memory, it will be returned immediately.
    - If it is not in memory, it will be loaded from network by an UI job and saved. Subscribers will be notified again.
  - Reason for this policy:
    - Configuration is not frequently changed so only make network call if the data is unavailable.
- [`ProfilesController`][11]: data is cached in memory and persistent store, but is always synced with server:
  - In constructor:
    - Load data from persistent store and save to memory.
  - When data is requested:
    - If it is in memory, it will be returned immediately. Then it is loaded from network by a SYNC job and saved. Subscribers will be notified again.
    - If it is not in memory, it will be loaded from network by an UI job and saved. Subscribers will be notified.
  - Reason for this policy:
    - Profiles may be changed from the web interface, but not so frequently.
- [`UpdatesController`][12]: data is cached in memory.
  - When data is requested:
    - If it is in memory, it will be returned immediately.
    - If it is being loaded, an appropriate event will be posted.
    - If it is not in memory and not being loaded, it will be loaded from network by an UI job and saved. Subscribers will be notified.
  - Reason:
    - Updates are changed very frequently, from the app and from web interface. So it’s hard to invalidate cache in persistent store without support from server (or user).

Known bugs:
============
- `JobManager` is not meant to be used in context based objects. It should be stored in application object. Jobs then have to subscribe to lifecycle events of their activity and update their internal flag. `onRun()`, jobs will decide whether they should continue or not, based on the flag.
- To manually show context menu for an list view item, we must call `ListView.showContextMenuForChild(childView)` instead of `Activity.openContextMenu(listView)`.

 [1]: http://birbit.com/a-recipe-for-writing-responsive-rest-clients-on-android
 [2]: https://github.com/fernandezpablo85/scribe-java
 [3]: http://square.github.io/dagger/
 [4]: https://github.com/path/android-priority-jobqueue
 [5]: http://square.github.io/otto/
 [6]: http://square.github.io/retrofit/
 [7]: http://square.github.io/picasso/
 [8]: https://github.com/47deg/android-swipelistview
 [9]: https://github.com/nguyenhuy/buffer/blob/master/buffer/src/main/java/org/nguyenhuy/buffer/controller/AccessTokenControllerImpl.java
 [10]: https://github.com/nguyenhuy/buffer/blob/master/buffer/src/main/java/org/nguyenhuy/buffer/controller/ConfigurationController.java
 [11]: https://github.com/nguyenhuy/buffer/blob/master/buffer/src/main/java/org/nguyenhuy/buffer/controller/ProfilesController.java
 [12]: https://github.com/nguyenhuy/buffer/blob/master/buffer/src/main/java/org/nguyenhuy/buffer/controller/UpdatesController.java
 [13]: https://github.com/nguyenhuy/buffer/blob/master/buffer/src/main/java/org/nguyenhuy/buffer/job/JobPriority.java
 [14]: https://github.com/nguyenhuy/buffer/releases
