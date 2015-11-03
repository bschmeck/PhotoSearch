# Project 2 - PhotoSearch

PhotoSearch allows a user to search for photos using the Google API.  The user can specify a few advanced search options: image type, image size, image color and site.

Time Spent: 8 hours spent in total

## User Stories

The following user stories must be completed:

[X] User can enter a search query that will display a grid of image results from the Google Image API.
[X] User can click on "settings" which allows selection of advanced search options to filter results
[X] User can configure advanced search filters such as:
    [X] Size (small, medium, large, extra-large)
    [X] Color filter (black, blue, brown, gray, green, etc...)
    [X] Type (faces, photo, clip art, line art)
    [X] Site (espn.com)
[X] Subsequent searches will have any filters applied to the search results
[X] User can tap on any image in results to see the image full-screen
[X] User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

## Video Walkthrough

Here's a walkthrough of implemented user stories:

![Video Walkthrough](demo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright 2015 Ben Schmeckpeper

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
