package dgtic.unam.gps_tracking

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.gps_tracking.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding:ActivityLoginBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //Configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loggin in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //HandleRegister
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        //HandleLogin
        binding.loginBtn.setOnClickListener{
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
        }
        else{
            //data validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //Login succes
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "LoggedIn as $email", Toast.LENGTH_SHORT).show()

                //Open profile
                startActivity(Intent(this, MapActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //loggin failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser(){
        //Get current user if exist
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //User exits
            startActivity(Intent(this,MapActivity::class.java))
            finish()
        }
    }
}