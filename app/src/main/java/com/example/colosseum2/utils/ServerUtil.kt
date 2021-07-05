package com.example.colosseum2.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil() {
//    서버 연결 전담

    companion object {

        interface JsonResponseHandler {
            fun onResponse(jsonObj : JSONObject)
        }

        val BASE_URL = "http://54.180.52.26"

        fun postRequestLogin(email: String, pw: String, handler: JsonResponseHandler?) {

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

//        클라이언트로써의 동작 : Request 요청 => OkHttp  라이브러리 지원
            val client = OkHttpClient()

//        실제로 서버에 요청 날리기
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                서버에 연결 자체를 실패한 경우 (서버 마비, 인터넷 단선 등)
                }

                override fun onResponse(call: Call, response: Response) {
//                로그인 성공 / 실패 (연결 성공 -> 검사 실패) -> 응답이 돌아온 경우

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("응답 본문", jsonObj.toString())

//                    handler 변수가 null이 아니면 내용 띄우기
                    handler?.onResponse(jsonObj)
                }

            })


        }

        fun putRequestSignUp(email:String, pw: String, nickname: String, handler: JsonResponseHandler?) {

//        서버에 입력받은 email, pw, nickname 전달 => 로그인 기능 PUT/user로 전달 => 요청(Request) 실행
//        라이브러리(OkHttp) 활용
//        http://54.180.52.26/user + PUT + 파라미터들 첨부

//        호스트 부소 + 기능 주소 결합
            val urlString = "${BASE_URL}/user"

//        POST 방식 => 파라미터를 폼데이터(폼바디)에 담아주자
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()

//        어디, 어떻게, 무엇을 들고갈지
            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

//        이후에는 동일함.
//        클라이언트로써의 동작 : Request 요청 => OkHttp  라이브러리 지원
            val client = OkHttpClient()

//        실제로 서버에 요청 날리기
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                서버에 연결 자체를 실패한 경우 (서버 마비, 인터넷 단선 등)
                }

                override fun onResponse(call: Call, response: Response) {
//                로그인 성공 / 실패 (연결 성공 -> 검사 실패) -> 응답이 돌아온 경우

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("응답 본문", jsonObj.toString())

//                    handler 변수가 null이 아니면 내용 띄우기
                    handler?.onResponse(jsonObj)
                }

            })


        }

        fun getRequestDuplCheck(type: String, value: String, handler: JsonResponseHandler?){

//            어디로 무엇을 가지고 / url 적으면서 파라미터도 같이 작성 => 보조 도구(Builder)

            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("type", type)
            urlBuilder.addEncodedQueryParameter("value", value)

            val urlString = urlBuilder.build().toString()

            Log.d("완성된 url", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버 응답 본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun getRequestMainInfo(context: Context, handler: JsonResponseHandler?){

//            어디로 무엇을 가지고 / url 적으면서 파라미터도 같이 작성 => 보조 도구(Builder)

            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()

//            urlBuilder.addEncodedQueryParameter("type", type)
//            urlBuilder.addEncodedQueryParameter("value", value)

            val urlString = urlBuilder.build().toString()

            Log.d("완성된 url", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .header("X-Http-Token", ContextUtil.getToken(context))
                    .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버 응답 본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun getRequestTopicDetail(context: Context, topicId: Int, handler: JsonResponseHandler?){

//            어디로 무엇을 가지고 / url 적으면서 파라미터도 같이 작성 => 보조 도구(Builder)

//            val urlBuilder = "${BASE_URL}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()
            val urlBuilder = "${BASE_URL}/topic".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedPathSegment(topicId.toString())

            urlBuilder.addEncodedQueryParameter("order_type", "NEW")

            val urlString = urlBuilder.build().toString()

            Log.d("완성된 url", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .header("X-Http-Token", ContextUtil.getToken(context))
                    .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버 응답 본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun postRequestVote(context: Context, sideId: Int, handler: JsonResponseHandler?) {

//        서버에 입력받은 email, pw 전달 => 로그인 기능 POST/user로 전달 => 요청(Request) 실행
//        라이브러리(OkHttp) 활용
//        http://54.180.52.26/user + POST + 파라미터들 첨부

//        호스트 부소 + 기능 주소 결합
            val urlString = "${BASE_URL}/topic_vote"

//        POST 방식 => 파라미터를 폼데이터(폼바디)에 담아주자
            val formData = FormBody.Builder()
                    .add("side_id", sideId.toString())
                    .build()

//        어디, 어떻게, 무엇을 들고갈지
            val request = Request.Builder()
                    .url(urlString)
                    .post(formData)
                    .header("X-Http-Token", ContextUtil.getToken(context))
                    .build()

//        클라이언트로써의 동작 : Request 요청 => OkHttp  라이브러리 지원
            val client = OkHttpClient()

//        실제로 서버에 요청 날리기
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                서버에 연결 자체를 실패한 경우 (서버 마비, 인터넷 단선 등)
                }

                override fun onResponse(call: Call, response: Response) {
//                로그인 성공 / 실패 (연결 성공 -> 검사 실패) -> 응답이 돌아온 경우

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("응답 본문", jsonObj.toString())

//                    handler 변수가 null이 아니면 내용 띄우기
                    handler?.onResponse(jsonObj)
                }

            })


        }

    }
}

