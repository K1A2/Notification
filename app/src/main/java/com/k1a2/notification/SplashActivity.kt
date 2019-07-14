package com.k1a2.notification

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()

        val handeler = Handler()
        handeler.postDelayed(Runnable {
            startActivity(Intent(SplashActivity@this,LoginActivity::class.java))
            finish()
        }, 1500)
    }
}