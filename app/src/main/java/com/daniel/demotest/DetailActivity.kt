package com.daniel.demotest

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.textView
import kotlinx.android.synthetic.main.layout_detail.*

class DetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail)
        val level = intent.getIntExtra(Constant.TAG_LEVEL, 0)
        val name = intent.getStringExtra(Constant.TAG_NAME)

        title = name
        textView.text = "level is ${level}"

        button.setOnClickListener {
            textView.text = "${editText.text} is level ${level}"
        }
    }
}
