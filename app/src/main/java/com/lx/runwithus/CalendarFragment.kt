
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lx.runwithus.Event
import com.lx.runwithus.MonthAdapter
import com.lx.runwithus.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment(), DayAdapter.OnDateClickListener {

    private var _binding: FragmentCalendarBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var dayAdapter: DayAdapter // dayAdapter를 멤버 변수로 선언

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view = binding?.root

        initView(binding!!)

        createData()
        return view
    }

    private fun initView(binding: FragmentCalendarBinding) {
        recyclerView = binding.calRecycler
        var position: Int = Int.MAX_VALUE / 2

        binding.calRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // DayAdapter.OnDateClickListener를 구현한 CalendarFragment를 전달
        binding.calRecycler.adapter = MonthAdapter(this)

        // 아이템의 위치 지정
        binding.calRecycler.scrollToPosition(position)

        // 항목씩 스크롤 지정
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calRecycler)
    }


    private fun createData() {
        // 여기에서 데이터 생성 및 관련 작업 수행
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDateClick(date: Date) {
        // 클릭된 날짜에 대한 작업 수행
        val event = dayAdapter.getEventForDate(date)
    }
}
