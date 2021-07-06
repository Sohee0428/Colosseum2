package com.example.colosseum2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colosseum2.datas.Side
import kotlinx.android.synthetic.main.activity_edit_reply.*

class EditReplyActivity : BaseActivity() {

    lateinit var mSelectedSide : Side

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {

        mSelectedSide = intent.getSerializableExtra("mySide") as Side

        mySelectedSideTxt.text = mSelectedSide.title
    }

}