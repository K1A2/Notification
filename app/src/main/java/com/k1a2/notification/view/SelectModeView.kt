package com.k1a2.notification.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.k1a2.notification.R
import android.widget.LinearLayout as LinearLayout

class SelectModeView : LinearLayout {

    private lateinit var con: Context
    private lateinit var radio_send:RadioButton
    private lateinit var radio_recieve:RadioButton
    private lateinit var card_send:CardView
    private lateinit var card_recieve:CardView

    constructor(context: Context) : super(context) {
        iniView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        iniView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        iniView(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        iniView(context)
    }

    private fun iniView(context: Context) {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = getContext().getSystemService(infService) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.view_selectmode, this@SelectModeView, false)
        addView(view)

        con = context

        radio_send = view.findViewById(R.id.select_radio_send) as RadioButton
        radio_recieve = view.findViewById(R.id.select_radio_receive) as RadioButton
        card_recieve = view.findViewById(R.id.selectmode_card_receive) as CardView
        card_send = view.findViewById(R.id.selectmode_card_send) as CardView

        card_send.setOnClickListener(onCardClickListener)
        card_recieve.setOnClickListener(onCardClickListener)
    }

    private val onCardClickListener = View.OnClickListener { v ->
        when(v.id) {
            R.id.selectmode_card_receive -> {
                if (radio_send.isChecked) {
                    radio_send.isChecked = false
                }

                if (!radio_recieve.isChecked) {
                    radio_recieve.isChecked = true
                }
            }
            R.id.selectmode_card_send -> {
                if (!radio_send.isChecked) {
                    radio_send.isChecked = true
                }

                if (radio_recieve.isChecked) {
                    radio_recieve.isChecked = false
                }
            }
        }
    }

    public fun getSelectedCard() : Int {
        val isSend = radio_send.isChecked
        val isRecieve = radio_recieve.isChecked

        if ((isRecieve&&isSend)||(!isRecieve&&!isSend)) {
            return 0
        } else if (isRecieve&&!isSend) {
            //recieve
            return 1
        } else if (!isRecieve&&isSend) {
            //send
            return 2
        } else {
            return 0
        }
    }
}