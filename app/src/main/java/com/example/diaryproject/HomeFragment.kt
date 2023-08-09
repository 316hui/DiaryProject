package com.example.diaryproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.diaryproject.databinding.FragmentHomeBinding
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

        val binding = FragmentHomeBinding.inflate(inflater, container, false) // 바인딩 객체를 사용하여 인플레이션

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1 // 1월부터 시작하므로 +1 처리해야 합니다.

        binding.monthDayTv.text =
            String.format(Locale.getDefault(), "%d년 %d월", currentYear, currentMonth)

        val bnv_main = binding.bnvMain
        //바텀 네비게이션 뷰 바인딩 (홈 프래그먼트 안에 있다.)

        bnv_main.setOnClickListener { item ->
            changeFragment(
                //바텀 네비게이션 아이템이 선택되었을 때, 아이콘과 텍스트 색을 변경 하는 코드
                when (item.id) {
                    R.id.homeFragment -> {
                        bnv_main.itemIconTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.green)
                        //this는 현재 클래스의 인스턴스를 가리키는 참조 -> 현클의 변수, 메서드 접근 /
                        //requireContext()는 현재 컨텍스트를 반환하는 함수로 UI 요소에 접근하거나 리소스에 접근할 때는 정확한 context가 필요로 한다.
                        //리소스에서 정의한 색을 가져오는 메소드. 리소스 ID로 지정된 색을 가져와 ColorStateList 객체로 변환
                        bnv_main.itemTextColor =
                            ContextCompat.getColorStateList(requireContext(), R.color.green)
                        HomeFragment()
                    }

                    R.id.myPageFragment -> {
                        bnv_main.itemIconTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.green)
                        //이떄 this는 액티비티. 액티비티의 컨텍스트를 사용하여 리소스에서 정의한 green 색을 가져오는 작업을 수행.
                        bnv_main.itemTextColor =
                            ContextCompat.getColorStateList(requireContext(), R.color.green)
                        MyPageFragment()
                    }

                    else -> {
                        HomeFragment()
                    }

                }
            )
            true
        }
        bnv_main.selectedItemId = R.id.homeFragment
        //코드는 초기에 화면이 생성될 때 바텀 네비게이션 뷰에서 특정 아이템을 선택한 상태로 설정하는 역할
        return binding.root
    }

    private fun changeFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.homeFragment, fragment).commit()
        //프래그먼트를 추가하거나 교체하는 컨테이너의 ID를 지정, fragment는 프래그먼트 전환을 위해 새로 불러올 프래그먼트의 인스턴스 -> 얘는 추가, 제거, 교체 역할..
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}