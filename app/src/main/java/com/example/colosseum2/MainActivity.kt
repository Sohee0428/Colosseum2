package com.example.colosseum2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.colosseum2.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()

//            서버에 실제 회원이 맞는지 확인 요청 (Request)
            ServerUtil.postRequestLogin(inputEmail, inputPw, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

//                    jsonObj : 서버에서 내려준 본문을 JSON 형태로 가공 -> 이 내부를 파싱해서 상황에 따른 대응 (예로 토스트 띄우기)

                    val code = jsonObj.getInt("code")

                    if (code == 200) {
//                        로그인 성공 시
                    } else {
//                        로그인 실패 시
                        val message = jsonObj.getString("message")

                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            })

        }

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)


        }
    }

    override fun setValues() {
    }
}