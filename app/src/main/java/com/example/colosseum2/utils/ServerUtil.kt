package com.example.colosseum2.utils

import okhttp3.FormBody
import okhttp3.Request

class ServerUtil() {
//    서버 연결 전담

    val BASE_URL = "http://54.180.52.26"

    fun postRequestLogin(email: String, pw: String) {

//        서버에 입력받은 email, pw 전달 => 로그인 기능 POST/user로 전달 => 요청(Request) 실행
//        라이브러리(OkHttp) 활용
//        http://54.180.52.26/user + POST + 파라미터들 첨부

//        호스트 부소 + 기능 주소 결합
        val urlString = "${BASE_URL}/user"

//        POST 방식 => 파라미터를 폼데이터(폼바디)에 담아주자
        val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//        어디, 어떻게, 무엇을 들고갈지
        val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()
    }

}