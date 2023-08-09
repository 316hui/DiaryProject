package com.example.diaryproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.diaryproject.databinding.FragmentCreatePassWordBinding
import com.example.diaryproject.databinding.FragmentHomeBinding
import okhttp3.internal.userAgent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreatePassWord.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreatePassWord : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //뷰바인딩 설정
        val binding = FragmentCreatePassWordBinding.inflate(inflater, container, false)
        //inflater-> 레이아웃 인플레이터 객체/ container -> 프래그먼트에 표시할 부모 뷰
        //inflate -> 레이아웃 파일을 실제 뷰 객체로 변환.

        binding.createButton.setOnClickListener {
            val name = binding.createName.text.toString().trim()
            val pw = binding.createPw.text.toString().trim()

            saveData(name, pw)
        }

        return binding.root
    }

    private fun saveData(name: String, pw: String) {
        val api = Api.create()
        val data = UserInfo(name, pw)

        api.saveUserInfo(data).enqueue(object : Callback<UserInfo> {
            override fun onResponse(
                call: Call<UserInfo>, response: Response<UserInfo>
            ) {
                if (response.isSuccessful) {
                    // 서버 응답 처리
                    Log.d("서버 응답 성공", response.body().toString())
                } else {
                    // 서버 응답 실패 처리
                    Log.d("서버 응답 실패", response.message())
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("API 요청 실패", t.message ?: "Unknown error")
            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreatePassWord.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreatePassWord().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<UserInfo>) {

}
