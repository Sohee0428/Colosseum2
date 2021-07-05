package com.example.colosseum2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.colosseum2.R
import com.example.colosseum2.datas.Reply
import com.example.colosseum2.datas.Topic
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



        return row
    }
}