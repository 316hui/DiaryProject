package com.example.diaryproject

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import com.example.diaryproject.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback // 올바른 Callback import 문으로 수정해야 함
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // 홈 프래그먼트 바인딩

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val name = binding.editText1.text.toString().trim() //trim: 문자열 공백제거
            val pw = binding.editText2.text.toString().trim()


            val api = Api.create()
            val data = UserInfo(name, pw)
            //data 객체는 로그인에 필요한 사용자 정보를 담고 있음.
            //UserInfo 객체를 생성하여 변수 data에 저장.

            //.enqueue(...): 비동기적으로 API 호출을 수행하고 결과를 처리하기 위한 콜백을 등록하는 메서드
            //userLogin(data): 사용자 로그인을 요청하는 API 엔드포인트를 호출하는 메서드
            api.saveUserInfo(data).enqueue(object : Callback<LoginBackendResponse> {
                override fun onResponse(
                    call: Call<LoginBackendResponse>, response: Response<LoginBackendResponse>
                ) //성공적인 응답을 받았을 때 실행 (통신됨)
                {
                    Log.d("로그인 통신 성공", response.toString())
                    Log.d("로그인 통신 성공", response.body().toString())
                    //응답 객체를 문자열로 로그출력
                    // 서버 응답의 본문을 문자열로 로그출력


                    fun getSavedName(): String {
                        val prefID = getSharedPreferences("userID", MODE_PRIVATE)
                        return prefID.getString("name", "") ?: "" //"name" 키에 해당하는 값을 ㄹ 가져오도록 설정
                    }
                    fun getSavedPw(): String {
                        val prefID = getSharedPreferences("userID", MODE_PRIVATE)
                        return prefID.getString("password", "") ?: ""
                    }

                    when (response.code()) {
                         200 -> {
                             val savedName = getSavedName() // 저장된 아이디 가져오기
                             val savedPw = getSavedPw() //저장된 비밀번호 가져오기


                             //사용자에게 입력받은거랑 저장된 값이랑 같은지 비교
                             if (savedName == name && savedPw == pw) {
                                 val homeFragment : HomeFragment = HomeFragment()
                                 val manager: FragmentManager = supportFragmentManager
                                 val transaction: FragmentTransaction = manager.beginTransaction()
                                 transaction.replace(R.id.activity_main, homeFragment)

                                 Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_LONG).show()
                             } else {
                                 Toast.makeText(this@MainActivity, "이름과 비밀번호를 한 번 더 확인해주세요.", Toast.LENGTH_LONG).show()
                             }
                         }

                        405 -> {}

                        500 -> {}
                    }
                }

                override fun onFailure(call: Call<LoginBackendResponse>, t: Throwable) {
                    Log.d("로그인 통신 실패", t.message.toString())
                    Log.d("로그인 통신 실패", "fail")
                } //통신 자체가 안된 경우 실행


            })


        }
        binding.ifNoButton.setOnClickListener {
            val fragment = CreatePassWord()

            // 프래그먼트를 액티비티에 추가
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main, fragment) // fragmentContainer는 프래그먼트를 표시할 레이아웃의 ID.
                .commit()
            Log.d(TAG, "버튼 눌림")
        }
    }
}

