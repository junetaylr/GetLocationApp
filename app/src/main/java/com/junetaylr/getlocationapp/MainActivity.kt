package com.junetaylr.getlocationapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.junetaylr.getlocationapp.ui.theme.GetLocationAppTheme
import androidx.activity.compose.rememberLauncherForActivityResult


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            GetLocationAppTheme {
                LocationScreen(fusedLocationClient)
            }
        }
    }
}

@Composable
fun LocationScreen(fusedLocationClient: FusedLocationProviderClient) {
    var locationText by remember { mutableStateOf("Location") }
    val activity = LocalContext.current as ComponentActivity

    // permission launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLocation(fusedLocationClient) { lat, lng ->
                locationText = "Your Location:\nLatitude: $lat\nLongitude: $lng"
            }
        } else {
            locationText = "Please enable location permission to continue."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = locationText, fontSize = 20.sp, modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                if (ActivityCompat.checkSelfPermission(
                        activity, Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation(fusedLocationClient) { lat, lng ->
                        locationText = "Your Location:\nLatitude: $lat\nLongitude: $lng"
                    }
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        ) {
            Text("GET LOCATION")
        }
    }
}

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
private fun getLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onResult: (Double, Double) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            onResult(location.latitude, location.longitude)
        } else {
            // Fallback if no cached location
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { newLocation ->
                if (newLocation != null) {
                    onResult(newLocation.latitude, newLocation.longitude)
                }
            }
        }
    }
}
