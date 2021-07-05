package com.example.colosseum2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.colosseum2.adapters.ReplyAdapter
import com.example.colosseum2.datas.Reply
import com.example.colosseum2.datas.Topic
import com.example.colosseum2.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var mTopic : Topic

    val mReplyList = ArrayList<Reply>()

    lateinit var mReplyAdapter : ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        voteFirstSideBtn.setOnClickListener {

            ServerUtil.postRequestVote(mContext, mTopic.sides[0].id, object : ServerUtil.Companion.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                    getTopicDetailFromServer()
                }
            })
        }

        voteSecondSideBtn.setOnClickListener {

            ServerUtil.postRequestVote(mContext, mTopic.sides[1].id, object : ServerUtil.Companion.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                    getTopicDetailFromServer()
                }
            })
        }
    }

    override fun setValues() {

        mTopic = intent.getSerializableExtra("topic") as Topic

        topicTitleTxt.text = mTopic.title

        Glide.with(mContext).load(mTopic.imageURL).into(topicImg)

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        replyListView.adapter = mReplyAdapter

        getTopicDetailFromServer()
    }

    fun getTopicDetailFromServer() {

        ServerUtil.getRequestTopicDetail(mContext, mTopic.id, object : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

                val topic = Topic.getTopicDataFromJson(topicObj)

                mTopic = topic

                val replyArr = topicObj.getJSONArray("replies")

                for(i in 0 until replyArr.length()){

                    val replyObj = replyArr.getJSONObject(i)
                    val reply = Reply.getReplyFromJson(replyObj)
                    mReplyList.add(reply)
                }

                runOnUiThread {
                    firstSideVoteTxt.text = mTopic.sides[0].title
                    firstSideVoteTxt.text =  "${mTopic.sides[0].voteCount}표"
                    secondSideTxt.text = mTopic.sides[1].title
                    secondSideVoteTxt.text = "${mTopic.sides[1].voteCount}표"

                    mReplyAdapter.notifyDataSetChanged()
                }

            }

        })
    }
}