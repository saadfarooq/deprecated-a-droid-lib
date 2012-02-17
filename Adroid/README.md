A-droid library
=============

This library is intended to have utility classes that help in everyday tasks on the Android 
platform that are harder than they should be

Classes
-------

The following have been added so far. You are welcome to fork or suggest improvements and additions

* `AsyncGetLocationTask` -- get a course location in the background, waits for result if one is not available
* `AsyncJsonGetTask` -- get a Json response from a webservice using GET (webservice details need to be added manually to WebServices.java)
* `AsyncJsonPostTask` -- get a Json response from a webservice using POST (webservice details need to be added manually to WebServices.java)
* `PaginatedGallery` -- a Paginated Gallery similar to iOS with page indicators and click listener (uses code imported from [ViewPagerIndicator][1] lib)
* `PaginatedGalleryAdapter` -- Adapter for the gallery
 
Developed By
------------

* Saad Farooq - <unimpeccable@gmail.com>


Contributing
------------

Want to contribute? Great! Fork and if you have suggestions, contact me via GitHub...

1. Fork it.
2. Create a branch (`git checkout -b a-droid-lib`)
3. Commit your changes (`git commit -am "Added Something"`)
4. Push to the branch (`git push origin a-droid-lib`)
5. Create an [Issue][2] with a link to your branch

License
=======

    Copyright 2012 Saad Farooq
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




[1]: https://github.com/JakeWharton/Android-ViewPagerIndicator/
[2]: http://github.com/github/markup/issues