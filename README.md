# Get Location App

Get Location is an app that tracks your location as well as longitude and latitude.

# Required Features

GPS Location Fetching
- Uses Google’s FusedLocationProviderClient to get the device’s latitude & longitude.  
- Falls back to a fresh GPS request if no cached location is available.  

Runtime Permission Handling
- Implements Android’s modern runtime permission system.  
- Prompts the user to allow fine location access at runtime (not just in the manifest).  

User-Friendly UI (Jetpack Compose)  
- Built entirely with Jetpack Compose (Android’s latest UI toolkit).  
- Simple layout: "Location" label, result text, and a GET LOCATION button.  

Emulator + Real Device Ready  
- Works with Android Emulator by injecting GPS coordinates.  
- Can run on a physical device to show actual GPS coordinates.    

Dynamic Location Display  
- Once permissions are granted, displays latitude and longitude in real-time.  
- Updates instantly after pressing GET LOCATION.  

Error & Permission States  
- Shows “Permission denied” message if the user rejects location access.  
- Guides the user to enable permissions for functionality.
