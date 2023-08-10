package com.example.diaryproject

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.diaryproject.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            navController = findNavController()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val binding = FragmentLoginBinding.inflate(inflater, container, false)


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
                        //getSharedPreferences 메서드는 Context의 메서드이므로,
                        // 프래그먼트 내에서 직접 사용하는 것이 아니라 requireContext() 메서드를 사용하여 컨텍스트를 가져와야함.
                        val prefID = requireContext().getSharedPreferences(
                            "userID",
                            AppCompatActivity.MODE_PRIVATE
                        )
                        return prefID.getString("name", "") ?: "" //"name" 키에 해당하는 값을 가져오도록 설정
                        //name 이라는 키에 해당하는 값을 가져옵니다. 만약 해당 키에 저장된 값이 없는 경우, 빈 문자열 ""을 반환
                        // ?: -> 엘비스 연산자 뒤 피연산자는 null 인 경우에 가져온다. 즉 기본값.
                    }

                    fun getSavedPw(): String {
                        val prefID = requireContext().getSharedPreferences(
                            "userID",
                            AppCompatActivity.MODE_PRIVATE
                        )
                        //sharedPreference 의 모드지정.
                        // AppCompatActivity.MODE_PRIVATE: 앱 내에서만 접근 가능한 모드를 의미, 다른 앱과 데이터를 공유하지 않는다
                        return prefID.getString("password", "") ?: ""
                    }

                    when (response.code()) {
                        200 -> {
                            val savedName = getSavedName() // 저장된 아이디 가져오기
                            val savedPw = getSavedPw() //저장된 비밀번호 가져오기


                            //사용자에게 입력받은거랑 저장된 값이랑 같은지 비교
                            if (savedName == name && savedPw == pw) {
                                val homeFragment: HomeFragment = HomeFragment()

                                navController.navigate(R.id.fragment_home)


                                Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "이름과 비밀번호를 한 번 더 확인해주세요.",
                                    Toast.LENGTH_LONG
                                ).show()
                                //requireContext(): 프래그먼트의 컨텍스트를 가져오는 메서드. / 컨텍스트: 애플리케이션 전역에서 사용 가능한 정보와 리소스에 접근할 수 있도록 도와주는 역할
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

            navController.navigate(R.id.fragment_create_pass_word)
        }
        return binding.root
        //프래그먼트의 레이아웃을 액티비티에 표시하기 위한 작업x, 프래그먼트 자체의 UI를 설정하고 반환하는 역할.
    }


    //클래스 내부에서 선언 / 클래스의 인스턴스를 생성하지 않고도 해당 클래스의 멤버에 접근할 수 있도록 해주는 기능을 제공.
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Loginragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
