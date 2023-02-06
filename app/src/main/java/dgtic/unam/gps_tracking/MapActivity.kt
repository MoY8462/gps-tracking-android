package dgtic.unam.gps_tracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.gps_tracking.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    /*
    //ViewBinding
    private lateinit var binding: ActivityMapBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    */
    private lateinit var map : GoogleMap

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
        createMarker()
    }

    private fun createMarker() {
        val coordinates = LatLng ( 19.3329456, -99.1851108)
        val marker = MarkerOptions().position(coordinates).title("CU")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }
}

