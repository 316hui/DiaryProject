package com.example.diaryproject


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //앱 실행했을 때 로그인 프레그먼트
        if (savedInstanceState == null) {
            // 앱을 처음 실행할 때만 해당 프래그먼트를 띄움
            val fragment = LoginFragment() // 띄울 프래그먼트를 생성
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_login, fragment) // fragment_container는 액티비티의 레이아웃에 해당하는 ID
                .commit()
        }
    }
}



