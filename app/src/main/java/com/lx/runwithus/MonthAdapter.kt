package com.lx.runwithus

import DayAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
import java.util.Date

class MonthAdapter(private val onDateClickListener: DayAdapter.OnDateClickListener) :
    RecyclerView.Adapter<MonthAdapter.Month>() {
    var calendar: Calendar = Calendar.getInstance()

    interface OnDateClickListener {
        fun onDateClick(date: Date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Month {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.month_item,parent,false)
        return Month(view)
    }


    override fun onBindViewHolder(holder: Month, position: Int) {


        //리사이클러뷰 초기화
        var list_layout: RecyclerView = holder.view.findViewById(R.id.month_recycler)



        //달 구하기


        calendar.time = Date() //현재 날짜 초기화
        calendar.set(Calendar.DAY_OF_MONTH,1) //스크롤시 현재 월의 1일로 이동
        calendar.add(Calendar.MONTH , position) //스크롤시 포지션 만큼 달이동

        //title 텍스트 초기화
        var title_text: TextView =  holder.view.findViewById<TextView>(R.id.title)


        //현재 날짜 출력
        title_text.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"

        val tempMonth = calendar.get(Calendar.MONTH)

        //일 구하기


        //6주 7일로 날짜를 표시
        var dayList: MutableList<Date> = MutableList(6 * 7 ) { Date() }

        for (i in 0..5) { // 주
            for (k in 0..6) { // 요일
                // 각 달의 요일만큼 캘린더에 보여진다
                // 요일 표시
                calendar.add(Calendar.DAY_OF_MONTH, (1 - calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time // 배열 인덱스 만큼 요일 데이터 저장
            }
            // 주 표시
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        // 클릭 이벤트에서 사용할 수 있도록 Calendar 객체를 원래 상태로 돌려놓음
        calendar.add(Calendar.MONTH, -position)

        // DayAdapter 생성 시 onDateClickListener 전달
        val dayAdapter = DayAdapter(holder.view.context, tempMonth, dayList, holder.onDateClickListener)
        list_layout.layoutManager = GridLayoutManager(holder.view.context, 7)
        list_layout.adapter = dayAdapter
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE / 2
    }

    class Month(val view: View) : RecyclerView.ViewHolder(view) {
        // DayAdapter에서 사용할 클릭 리스너를 제공하는 방법 중 하나
        val onDateClickListener: DayAdapter.OnDateClickListener = object : DayAdapter.OnDateClickListener {
            override fun onDateClick(date: Date) {
                // 클릭된 날짜에 대한 작업 수행
                // 예를 들어, CalendarFragment의 메서드를 호출하거나 다른 작업 수행
            }
        }
    }
}