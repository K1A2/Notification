package com.k1a2.notification

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.k1a2.notification.activity.IntentKey
import com.k1a2.notification.view.SelectModeView

class SelectModeActivity : AppCompatActivity() {

    private lateinit var button_select:Button
    private lateinit var select_card:SelectModeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectmode)

        supportActionBar!!.hide()

        button_select = findViewById(R.id.selectmode_button_select) as Button
        select_card = findViewById(R.id.selectmode_select_card) as SelectModeView

        button_select.setOnClickListener(View.OnClickListener {
            val selected = select_card.getSelectedCard()

            val intent = Intent()

            if (selected == 0) {
                Toast.makeText(SelectModeActivity@this, getString(R.string.selectmodeactivity_toast_select), Toast.LENGTH_SHORT).show()
            } else {
                intent.putExtra(IntentKey.KEY_INTENT_SELECTED, selected)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })
    }
}
