package com.example.colosseum2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.colosseum2.adapters.ReplyAdapter
import com.example.colosseum2.datas.Reply
import com.example.colosseum2.datas.Side
import com.example.colosseum2.datas.Topic
import com.example.colosseum2.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var mTopic : Topic

    val mReplyList = ArrayList<Reply>()

    lateinit var mReplyAdapter : ReplyAdapter

    var mySelectedSide : Side? = null

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

        writeReplyBtn.setOnClickListener {

            if(mySelectedSide == null) {

                Toast.makeText(mContext, "투표를 한 후에만 의견 등록을 할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
            else {

                val  myIntent = Intent(mContext, EditReplyActivity::class.java)
                myIntent.putExtra("mySide", mySelectedSide)
                startActivity(myIntent)

            }
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

    override fun onResume() {
        super.onResume()

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

                mReplyList.clear()

                for(i in 0 until replyArr.length()){

                    val replyObj = replyArr.getJSONObject(i)
                    val reply = Reply.getReplyFromJson(replyObj)
                    mReplyList.add(reply)
                }

                mySelectedSide = null

                if(!topicObj.isNull("my_side")) {

                    val mySideObj = topicObj.getJSONObject("my_side")
                    val mySide = Side.getSideFromJson(mySideObj)
                    mySelectedSide = mySide
                }

                runOnUiThread {
                    firstSideTxt.text = mTopic.sides[0].title
                    firstSideVoteTxt.text =  "${mTopic.sides[0].voteCount}표"
                    secondSideTxt.text = mTopic.sides[1].title
                    secondSideVoteTxt.text = "${mTopic.sides[1].voteCount}표"

                    mReplyAdapter.notifyDataSetChanged()
                }

            }

        })
    }
}