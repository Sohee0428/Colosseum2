package com.example.colosseum2.adapters

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.colosseum2.R
import com.example.colosseum2.datas.Reply
import com.example.colosseum2.datas.Topic
import com.example.colosseum2.utils.ServerUtil
import org.json.JSONObject
import org.w3c.dom.Text

class ReplyAdapter(val mContext: Context, resId: Int, val mList: List<Reply>) : ArrayAdapter<Reply>(mContext, resId, mList){

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

//        val row = tempRow?:mInflater.inflate(resId, null)

        if(tempRow == null) {
            tempRow = mInflater.inflate(R.layout.reply_list_item, null)
        }

        val row =tempRow!!

        val data = mList[position]

        val selectedSideTxt = row.findViewById<TextView>(R.id.selectedSideTxt)
        val userNicknameTxt = row.findViewById<TextView>(R.id.userNicknameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)

        val likeCountBtn = row.findViewById<TextView>(R.id.likeCountBtn)
        val dislikeCountBtn = row.findViewById<TextView>(R.id.dislikeCountBtn)

        contentTxt.text = data.content

        selectedSideTxt.text = "(${data.selectedSide.title})"

        userNicknameTxt.text = data.writerNickname

        if(data.myLike) {
            likeCountBtn.setBackgroundResource(R.drawable.red_border_box)
            likeCountBtn.setTextColor( Color.parseColor("#ff0000"))

            dislikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeCountBtn.setTextColor( Color.parseColor("#a0a0a0"))
        }
        else if(data.myDislike){
            dislikeCountBtn.setBackgroundResource(R.drawable.blue_border_box)
            dislikeCountBtn.setTextColor( Color.parseColor("#0000ff"))

            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeCountBtn.setTextColor( Color.parseColor("#a0a0a0"))
        }
        else{
            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeCountBtn.setTextColor( Color.parseColor("#a0a0a0"))

            dislikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeCountBtn.setTextColor( Color.parseColor("#a0a0a0"))
        }

        likeCountBtn.setOnClickListener {
            ServerUtil.postRequestLikeOrDislike(mContext, data.id, true, object : ServerUtil.Companion.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    if (jsonObj.has("data")) {
                        val likeCount = jsonObj.getJSONObject("data").getJSONObject("reply")
                                .getString("like_count")
                        val disLikeCount = jsonObj.getJSONObject("data").getJSONObject("reply")
                                .getString("dislike_count")
                        val isCheck = jsonObj.getJSONObject("data").getJSONObject("reply")
                                .getBoolean("my_like")

                        Handler(Looper.getMainLooper()).post() {
                            likeCountBtn.text = "좋아요 ${likeCount}개"
                            dislikeCountBtn.text = "싫어요 ${disLikeCount}개"
                        }

                        if (isCheck) {
                            likeCountBtn.setBackgroundResource(R.drawable.red_border_box)
                            likeCountBtn.setTextColor(Color.parseColor("#ff0000"))

                            dislikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
                            dislikeCountBtn.setTextColor(Color.parseColor("#a0a0a0"))
                        } else {
                            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
                            likeCountBtn.setTextColor(Color.parseColor("#a0a0a0"))
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post() {
                            Toast.makeText(mContext, "서버와 통신이 불안정합니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        dislikeCountBtn.setOnClickListener {
            ServerUtil.postRequestLikeOrDislike(mContext, data.id, false, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
                    if (jsonObj.has("data")) {
                        val likeCount = jsonObj.getJSONObject("data").getJSONObject("reply")
                                .getString("like_count")
                        val disLikeCount = jsonObj.getJSONObject("data").getJSONObject("reply")
                                .getString("dislike_count")

                        val isCheck = jsonObj.getJSONObject("data").getJSONObject("reply")
                                .getBoolean("my_dislike")

                        Handler(Looper.getMainLooper()).post() {
                            likeCountBtn.text = "좋아요 ${likeCount}개"
                            dislikeCountBtn.text = "싫어요 ${disLikeCount}개"
                        }

                        if (isCheck) {
                            dislikeCountBtn.setBackgroundResource(R.drawable.blue_border_box)
                            dislikeCountBtn.setTextColor(Color.parseColor("#0000ff"))

                            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
                            likeCountBtn.setTextColor(Color.parseColor("#a0a0a0"))
                        } else {
                            dislikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
                            dislikeCountBtn.setTextColor(Color.parseColor("#a0a0a0"))
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post() {
                            Toast.makeText(mContext, "서버와 통신이 불안정합니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        return row
    }
}