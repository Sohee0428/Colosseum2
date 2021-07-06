package com.example.colosseum2.datas

import org.json.JSONObject

class Reply {

    var id = 0
    var content = ""

    lateinit var selectedSide : Side

    var writerNickname = ""

    companion object {

        fun getReplyFromJson(jsonObj : JSONObject) : Reply {
            val resultReply = Reply()

            resultReply.id = jsonObj.getInt("id")
            resultReply.content = jsonObj.getString("content")


//            아래는 둘이 타입이 달라서 불가능 Side = JSON
//            resultReply.selectedSide = jsonObj.getJSONObject("selected_side")

            resultReply.selectedSide = Side.getSideFromJson(jsonObj.getJSONObject("selected_side"))

            resultReply.writerNickname = jsonObj.getJSONObject("user").getString("nick_name")


            return resultReply
        }
    }
}