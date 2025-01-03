# 📱 ImBored App

## 🛠️ Technologies Used

![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Google Maps](https://img.shields.io/badge/Google%20Maps-4285F4?style=for-the-badge&logo=google-maps&logoColor=white)
![Material Design](https://img.shields.io/badge/Material%20Design-757575?style=for-the-badge&logo=material-design&logoColor=white)

## 📝 Project Description

The **ImBored App** The ImBored App is an engaging platform that enables users to explore and manage activities with filtering and location-based mapping. It integrates Firebase Authentication for user security and Google Maps for location management. Users can create, edit, view, and filter activities with various customization options.

## 🔑 Test Account

You can log in using the following test account:

- **Email:** homer@simpson.com
- **Password:** secret

## 📚 Features

### General Features
- **Activity Management**: Users can create, edit, view, and delete activities.
- **Activity Listing**: View a list of activities with sorting and filtering options.
- **Interactive Map**: Activities are displayed on Google Maps with clickable markers for location selection and for viewing markers contents.
- **Toggle button**: To view not only your own but other people's events.

### Authentication
- **Firebase Authentication**: Secure login and registration using email and passwor or Google sign in.
- **Persistent Sessions**: Automatically keeps users logged in until they explicitly log out, Firebase DB and Storage for users and their profile pictures.

### Location & Map Features
- **Google Maps Integration**: View activity locations on the map, with markers and details.
- **Google Maps Activities**: Add your own activities to google maps via form.
- **Current Location**: Displays the user's current location with a dedicated marker.
- **Location Picker**: Users can select activity locations via a clickable marker on Google Maps.


## 📦 Future Work
- Add user roles for admin and regular users with extended functionality.
- Allow activity sharing or invitations to friends via the app.
- Notifications for upcoming activities or location-based suggestions.
- Enhance filtering to include advanced options like date ranges and category-specific searches.
- Fix locationselect screen from clearing text fields in contribute screen

## 📲 How to Run the App Locally
```keys.xml with api keys``` is required to run this app. 
```google-services.json``` with api key is required.

1. **Clone the repository**:
   ```bash
   git clone https://github.com/bakaichi/ImBored-JPC.git
