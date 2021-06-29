package com.example.colosseum2.datas

import org.json.JSONObject

class Topic {

    var id = 0
    var title = ""
    var imageURL = ""

    companion object{

        fun getTopicDataFromJson( jsonObj : JSONObject) : Topic {

            val resultTopic = Topic()

            resultTopic.id = jsonObj.getInt("id")
            resultTopic.title = jsonObj.getString("title")
            resultTopic.imageURL = jsonObj.getString("img_url")

            return resultTopic
        }
    }
}