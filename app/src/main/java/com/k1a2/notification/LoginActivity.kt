package com.k1a2.notification

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.k1a2.notification.realtimeDB.UserModel
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var button_signup: Button
    private lateinit var button_signin: Button
    private lateinit var editText_id: EditText
    private lateinit var editText_password: EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        button_signup = findViewById(R.id.loginActivity_button_signUp) as Button
        button_signin = findViewById(R.id.loginActivity_button_signIn) as Button
        editText_id = findViewById(R.id.edit_login_email) as EditText
        editText_password = findViewById(R.id.edit_login_password) as EditText

        editText_id.filters = arrayOf(inputFilter)
        editText_password.filters = arrayOf(inputFilter)

        button_signin.setOnClickListener {
            val email = editText_id.text.toString()
            val password = editText_password.text.toString()

            if (!email.isEmpty() && !password.isEmpty()) {
                if (isValidEmail(email)&&isValidPasswd(password)) {
                    val progressDialog = ProgressDialog(SignUpActivity@this)
                    progressDialog.setCancelable(false)
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    progressDialog.setMessage(getString(R.string.signupactivity_progressdal_title))
                    progressDialog.show()

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(LoginActivity@ this, getString(R.string.signinactivity_toast_success), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(LoginActivity@ this, "F", Toast.LENGTH_SHORT).show()
                            }
                            progressDialog.dismiss()
                        }
                } else {

                }
            } else {
                Toast.makeText(LoginActivity@ this, getString(R.string.signupactivity_toast_blank), Toast.LENGTH_SHORT).show()
            }
        }

        button_signup.setOnClickListener {
            startActivity(Intent(LoginActivity@ this, SignUpActivity::class.java))
        }

        authStateListener = FirebaseAuth.AuthStateListener {firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                //login
                startActivity(Intent(LoginActivity@this, MainActivity::class.java))
                finish()
            } else {
                //logout
            }
        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    private val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
        val ps = Pattern.compile("^[a-zA-Z0-9!@#$%^&*.]+$")
        if (source == "" || ps.matcher(source).matches()) {
            return@InputFilter source
        }
        Toast.makeText(LoginActivity@ this, "영문, 숫자, !@#\\$%^&*. 만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
        ""
    }

    private fun isValidPasswd(target: String): Boolean {
        val p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)")

        val m = p.matcher(target)
        return if (m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*".toRegex())) {
            true
        } else {
            false
        }
    }

    private fun isValidEmail(target:String): Boolean {
        if (target == null || TextUtils.isEmpty(target)) {
                return false
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }
}