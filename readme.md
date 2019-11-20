# MyFancyCar App Assignment

This assignment android app implementation allows user

A. to view list of the following car details:
    1. Name of the car
    2. Picture of the car
    3. Make
    4. Model
    5. Availability {"In Dealership" / "Out of Stock" / "Unavailable"}
    6. Year
B. The app will also handle infinite scrolling for viewing the car listing.
C. There is a dropdown to sort the result list based on name and availability.
D. "Buy" button will be visible for cars whose availability is "In Dealership".
E. Also showing a message across the screen when no network connection detected based on the assumption that the app requires network connection for it to work smoothly.
F. Also mocked the backend services with stubbed API data based on the data details provided in the assignment.


## Architecture:

Implemented the exercise with **MVVM architecture** (Model-View-ViewModel) to handle the data model, it's flow and business logic across the app.

Have used **Data Binding** library for binding the UI components easily with their data source required in app using declarative format.

* Here all the fields have their own model. 
* There are response models to fetch the data from the backend services.
* The ViewModel handles the binding of the UI components and their data source and exposing the UI fields to the View classes in the app's backend. 

This MVVM architecture as well as data-binding combination allows to easily maintain the code when used individually for different modules across a large app.

Utilized the Android's **Material Design** for the UI components look and have standardize feel.

Also used **Leak Canary** library to catch leaks and **Timber** library for better logging and initialized them in the app's application file.


