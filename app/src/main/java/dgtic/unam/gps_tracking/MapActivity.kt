package dgtic.unam.gps_tracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.gps_tracking.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityMapBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        //Configure actionbar
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
    /*
    private fun checkUser() {
        //Check user is logges in or not
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
        }
    }*/
}