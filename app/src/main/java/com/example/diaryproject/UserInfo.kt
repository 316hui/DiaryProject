package com.example.diaryproject

data class UserInfo(
    var username : String = "",
    var password : String = "",
    var success : String = ""
)

data class LoginBackendResponse(
    val code : String,
    val message : String,
    val token : String
)
//로그인 API 호출에 대한 응답으로 받은
// 코드(code), 메시지(message), 그리고 토큰(token)을 저장.
