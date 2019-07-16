package com.k1a2.notification

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern
import android.text.Spanned
import android.widget.ProgressBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.FirebaseDatabase
import com.k1a2.notification.realtimeDB.UserModel


class SignUpActivity : AppCompatActivity() {

    private lateinit var editText_Name:EditText
    private lateinit var editText_Email:EditText
    private lateinit var editText_Password:EditText
    private lateinit var button_Signup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar!!.hide()

        editText_Email = findViewById(R.id.edit_signup_email) as EditText
        editText_Name = findViewById(R.id.edit_signup_userName) as EditText
        editText_Password = findViewById(R.id.edit_signup_password) as EditText
        button_Signup = findViewById(R.id.loginActivity_button_signUp) as Button

        editText_Email.filters = arrayOf(inputFilter)
        editText_Name.filters = arrayOf(inputFilter)
        editText_Password.filters = arrayOf(inputFilter)

        button_Signup.setOnClickListener {
            val name = editText_Name.text.toString()
            val email = editText_Email.text.toString()
            val password = editText_Password.text.toString()

            if (!name.isEmpty()&&!email.isEmpty()&&!password.isEmpty()) {
                val progressDialog = ProgressDialog(SignUpActivity@this)
                progressDialog.setCancelable(false)
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog.setMessage(getString(R.string.signupactivity_progressdal_title))
                progressDialog.show()

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userModel = UserModel()
                            userModel.userName = name
                            val uid = task.getResult()!!.user.uid

                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel)
                            progressDialog.dismiss()

                            Toast.makeText(SignUpActivity@this, getString(R.string.signupactivity_toast_sucess), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(SignUpActivity@this, "F", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
            } else {
                Toast.makeText(SignUpActivity@this, getString(R.string.signupactivity_toast_blank), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
        val ps = Pattern.compile("^[a-zA-Z0-9!@#$%^&*.]+$")
        if (source == "" || ps.matcher(source).matches()) {
            return@InputFilter source
        }
        Toast.makeText(SignUpActivity@this, "영문, 숫자, !@#\\\$%^&*. 만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
        ""
    }
}
