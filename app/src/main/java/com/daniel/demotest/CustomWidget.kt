package com.daniel.demotest

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.custom_widget.view.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomWidget @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_widget, this, true)
        save.setOnClickListener {
            textView.text = editText.text
        }
    }
}