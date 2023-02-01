package dgtic.unam.gps_tracking

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.gps_tracking.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    //Action Bar
    private lateinit var actionBar: ActionBar

    //Progress Dialog
    private lateinit var progressDialog: ProgressDialog

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Actionbar, enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //Configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Create account In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //HandleSignUp
        binding.signUpBtn.setOnClickListener{
            validateData()
        }
    }

    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error = "Please enter password"
        }else if(password.length < 6){
            binding.passwordEt.error = "Passwor invalid, 6 character long"
        }else{
            //data validated, continue signup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //Show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //SignUp succes
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email ${email}", Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this, MapActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //SignUp Failed
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.addCallback(this,OnBackInvokedCallback)
        return super.onSupportNavigateUp()
    }

    private val OnBackInvokedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            Log.d("onBack","Login")
        }
    }
}