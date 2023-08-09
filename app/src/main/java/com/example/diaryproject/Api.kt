package com.example.diaryproject

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
//    @Headers ("Content-Type: application/json")
    @POST("/front") //<-?
    fun saveUserInfo( @Body jsonParams : UserInfo) : Call<LoginBackendResponse>
    //서버로 사용자 로그인 정보를 전송하고, 해당 요청에 대한 응답을 Call<LoginBackendResponse> 형태로 받아옴.
    //@Body 어노테이션은 POST 요청에 들어갈 데이터를 지정. / jsonParams는 UserModel의 객체로 이 객체의 데이터가 Json 형식으로 요청 본문에 포함된다.

    //클래스 내에 선언됨. 클래스 인스턴스를 생성하지 않고도 접근할 수 있는, 멤버 변수 및 메서드 정의하는 객체.
    //하나의 클래스에 하나만 생성가능.
    companion object {
        private const val BASE_URL = "http://222.235.157.77:9310/"
        val gson : Gson = GsonBuilder().setLenient().create()

        fun create() : Api{

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(Api::class.java)
        }
    }
}