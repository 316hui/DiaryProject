package com.example.diaryproject


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.diaryproject.databinding.FragmentHomeBinding


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       navController = findNavController(R.id.nav_host_fragment)
        //NavHostFragment의 NavController를 가져옴
    }
}



