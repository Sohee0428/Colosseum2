package com.example.colosseum2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colosseum2.datas.Topic
import com.example.colosseum2.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mTopicList = ArrayList<Topic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {

        getTopicListFromServer()
    }

    fun getTopicListFromServer(){
//        서버에서 주제 목록 받아오기

        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.Companion.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONObject("topics")

            }


        })

    }
}