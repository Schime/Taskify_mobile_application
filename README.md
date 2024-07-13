# Taskify
Taskify is a simple note-taking Android application built using Jetpack Compose and Firebase. It follows the MVVM (Model-View-ViewModel) architecture pattern, ensuring a clear separation of concerns and easier testability.

## Features
  - User Authentication (Login, Register)
  - Admin Panel for user management
  - Note creation, viewing, editing, and deletion
  - Navigation between screens using Jetpack Navigation

## Project Structure
### Model
  - **Note.kt:** Data class representing a note with an auto-generated ID and content
  - **User.kt:** Data class representing a user with an ID, email, and password
### View
  - **AdminScreen.kt:** Admin panel for managing users
  - **FirstScreen.kt:** Screen displaying user's notes with options to add, edit, and delete notes
  - **LoginScreen.kt:** User login screen
  - **MainScreen.kt:** Main screen prompting users to log in or register
  - **NoteEditorScreen.kt:** Screen for editing existing notes
  - **NoteWriterScreen.kt:** Screen for creating new notes
  - **RegisterScreen.kt:** User registration screen
### ViewModel
  - **AdminScreenViewModel.kt:** ViewModel for managing user-related operations in the admin panel
  - **FirstScreenViewModel.kt:** ViewModel for handling note-related operations for the first screen
  - **LoginScreenViewModel.kt:** ViewModel for handling user login operations
  - **NoteEditorScreenViewModel.kt:** ViewModel for managing note editing operations
  - **RegisterScreenViewModel.kt:** ViewModel for handling user registration operations
### Main Activity
  - **MainActivity.kt:** Sets up the navigation graph and initializes Firebase.

## Setup
### Prerequisites
  - [Android Studio](https://developer.android.com/studio)
  - Firebase project (for Firestore)
### Installation
  1. Clone the repository:
    ```
    git clone https://github.com/Schime/Taskify_mobile_application.git
    ```
  2. Open the project in Android Studio
  3. Configure Firebase:
     - Add your 'google-services.json' file to the 'app' directory
  4. Build and run the project on an emulator or physical device.

## Usage
### Main Screen
  - **Login:** Navigates to the login screen
  - **Register:** Navigates to the registration screen
### Login Screen
  - **Email and Password:** Enter your credentials
  - **Log in:** Logs in the user and navigates to the first screen if successful
### Register Screen
  - **Email and Password:** Enter your credentials
  - **Register:** Registers a new user and navigates to the main screen
### Admin Screen
  - **Delete Users:** Allows the admin to delete selected users
  - **Back:** Navigates back to the main screen.
### First Screen
  - **Add Note:** Navigates to the note writer screen
  - **Edit Note:** Navigates to the note editor screen for the selected note
  - **Delete Note:** Deletes the selected notes
  - **Home:** Navigates back to the main screen
### Note Writer Screen
  - **Save Note:** Saves the new note and navigates back to the first screen
### Note Editor Screen
  - **Save Changes:** Updates the note content and navigates back to the first screen

## Dependencies
  - [Jetpack Compose](https://developer.android.com/develop/ui/compose)
  - [Firebase Firestore](https://firebase.google.com/docs/firestore)



