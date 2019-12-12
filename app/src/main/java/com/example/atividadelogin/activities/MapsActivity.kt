package com.example.atividadelogin.activities


import ApiMap
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.atividadelogin.R
import com.example.atividadelogin.requests.endpoint.IMapEndPoint
import com.example.atividadelogin.requests.entity_request.MapRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        callMap()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun callMap() {
        if (checkCallingPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true

//            val sydney = LatLng(-8.059616, -34.8730747)
//            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            try {
                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val  locationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        loadPontos()
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {
                    }

                    override fun onProviderDisabled(provider: String?) {

                    }
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            } catch (e: SecurityException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        } else {
            requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), 1010)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setTitle(R.string.map_title)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            1010 -> {
                if (grantResults.isNotEmpty() && checkAllPermissionAreGranted(grantResults)){
                    callMap()
                }
            }
        }
    }

    private fun checkAllPermissionAreGranted(grantResults: IntArray): Boolean {
        var result = true
        grantResults.forEach { grant ->
            if (grant != PackageManager.PERMISSION_GRANTED)
                result = false
        }
        return result
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    fun loadPontos(){
        val result = ApiMap.getInstance().create(IMapEndPoint::class.java).getPontos()
        Log.d("result", result.toString())
        result.enqueue(object : Callback<MapRequest> {

            override fun onFailure(call: Call<MapRequest>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MapRequest>,
                response: Response<MapRequest>) {
                val result: MapRequest = response.body()!!
                Log.d("result", result.toString())

                for (item in result.resultMap.listRecordsMap) {
                    val latitude = item.latitude.toDouble()
                    val longitude = item.longitude.toDouble()
                    val polo = item.polo

                    val latLng = LatLng(latitude, longitude)
                    val marker = mMap.addMarker(MarkerOptions().position(latLng).title(polo))
                    marker.position = latLng
                   // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                }
            }
        })
    }
}

