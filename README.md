# ToDoList
# Pre-work - *MyToDo*

**MyToDo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Trang Luyen**

Time spent: **30** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] List anything else that you can get done to improve the app functionality!
Use Menu to allow add

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

[VIDEO WALKTHROUGH] (https://www.amazon.com/clouddrive/share/SVlNVoGDhnIdEUTqitInEbgchj8zpu8cYn8KuaqR1Af?v=grid&ref_=cd_ph_share_link_copy)


## Notes

Describe any challenges encountered while building the app.
1. I coulnd't figure out how to access ROWID from the SQLite DB. I tried many different ways but still coulnd't find a way to get to it.
For now, deleting a task is absed on the task name itself, which can be bad if there're 2 tasks of the same name. 
2. The emulator is super slow...
3. Sorry I couldn't use LiceCap because the emulator was way too slow, I had to wait 1-2 minutes before anything happens. 
So I used Mobizen to create MP4 instead

## License

    Copyright [2016] [Trang Luyen]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
