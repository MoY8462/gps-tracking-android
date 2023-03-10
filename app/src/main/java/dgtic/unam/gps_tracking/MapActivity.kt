package dgtic.unam.gps_tracking

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.gps_tracking.databinding.ActivityMapBinding
import java.util.jar.Manifest

class MapActivity : AppCompatActivity(), OnMapReadyCallback, OnMyLocationButtonClickListener, OnMyLocationClickListener {

    /*
    //ViewBinding
    private lateinit var binding: ActivityMapBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    */
    private lateinit var map : GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        createFragment()


        /*/Configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Map"
        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        //HandleLoggout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }*/
    }


    private fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun checkUser() {
        /*/Check user is logges in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //User is logged in
            var email = firebaseUser.email
            //Set on text view
            binding.emailTv.text = email
        }else {
            //User is not loggedin
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }*/
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker(
            19.3329456,
            -99.1851108,
            "Ciudad Univesitaria UNAM",
            "Campus Univesitario")
        createMarker(
            19.484103590,
            -99.2465510187,
            "FES Acatl??n UNAM",
            "Faculta de Estudios Superiores")
        map.setOnMyLocationButtonClickListener (this)
        map.setOnMyLocationClickListener(this)
        enableLocation()

        with(map.uiSettings) {
            isZoomControlsEnabled = true
            //isCompassEnabled = isChecked(R.id.compass_button)
            isMyLocationButtonEnabled = true
            //isIndoorLevelPickerEnabled = isChecked(R.id.level_button)
            //isMapToolbarEnabled = isChecked(R.id.maptoolbar_button)
            isZoomGesturesEnabled = true
            isScrollGesturesEnabled = true
            //isTiltGesturesEnabled = isChecked(R.id.tiltgest_button)
            isRotateGesturesEnabled = true
        }

    }

    private fun createMarker(lat: Number, long: Number, title: String, description: String) {
        val coordinates = LatLng (lat as Double, long as Double)
        val marker = MarkerOptions()
                        .position(coordinates)
                        .title(title)
                        .snippet(description)
                        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_unam)).anchor(0.0f,0.0f)
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled = true
        }else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localizaci??n ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled = true
            Toast.makeText(this, "Para activar la localizaci??n ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Mostrando ubicaci??n", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(pos: Location) {
        Toast.makeText(this, "Te encuentras en ${pos.latitude}, ${pos.longitude}", Toast.LENGTH_SHORT).show()
    }



}

