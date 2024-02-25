import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.AppData
import com.lx.runwithus.Event
import com.lx.runwithus.R
import org.w3c.dom.Text
import java.util.Calendar
import java.util.Date

class DayAdapter(
    val context: Context,
    val tempMonth: Int,
    val dayList: MutableList<Date>,
    val onDateClickListener: OnDateClickListener
) : RecyclerView.Adapter<DayAdapter.DayView>() {
    val ROW = 6

    interface OnDateClickListener {
        fun onDateClick(date: Date)
    }

    class DayView(val layout: View) : RecyclerView.ViewHolder(layout)

    private val eventsMap: MutableMap<Date, Event> = mutableMapOf()

    fun getEventForDate(date: Date): Event? {
        return eventsMap[date]
    }

    fun setEventForDate(date: Date, event: Event) {
        eventsMap[date] = event
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {
        val dayText: TextView = holder.layout.findViewById(R.id.item_day_text)
        val dayText2: ImageView = holder.layout.findViewById(R.id.item_day_text2)

        val currentDate = dayList[position]
        dayText.text = currentDate.date.toString()

        val calendar = Calendar.getInstance()
        calendar.time = dayList[position]

        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        when (dayOfWeek) {
            Calendar.SUNDAY -> {
                dayText.setTextColor(Color.RED)
            }
            Calendar.SATURDAY -> {
                dayText.setTextColor(Color.BLUE)
            }
        }

        if (tempMonth != currentDate.month) {
            dayText.alpha = 0.4f
        }

        val layoutParams = holder.layout.layoutParams as RecyclerView.LayoutParams


        holder.layout.setOnClickListener {
            // 날짜 클릭 이벤트 처리
            val currentDate = dayList[position]
            onDateClickListener.onDateClick(currentDate)

            // 해당 날짜의 이벤트 가져오기
            val event = getEventForDate(currentDate)

            // 이벤트가 있으면 이벤트를 보여주고, 없으면 이벤트를 추가하는 다이얼로그 표시
            if (event != null) {
                showEventDialog(event)
            } else {
                showAddEventDialog(currentDate)
            }
        }

        // 해당 날짜의 이벤트가 있는지 확인하고 표시
        val event = getEventForDate(dayList[position])
        if (event != null) {
            dayText.text = "${dayList[position].date}"
        } else {
            dayText.text = currentDate.date.toString()
            dayText2.visibility = View.INVISIBLE
        }
    }


    @SuppressLint("MissingInflatedId")
    private fun showEventDialog(event: Event) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.event_result_dialog, null)
        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val outputEventName = dialogView.findViewById<TextView>(R.id.outputEventName)
        outputEventName.text = event.eventName

        val outputEventTime = dialogView.findViewById<TextView>(R.id.outputEventTime)
        outputEventTime.text = event.eventTime

        val alertDialog = alertDialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        val closeButton = dialogView.findViewById<CardView>(R.id.closeButton)
        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddEventDialog(date: Date) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.event_dialog, null)
        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val editTextEventName = dialogView.findViewById<EditText>(R.id.inputEventName)

        val editTextEventTime = dialogView.findViewById<EditText>(R.id.inputEventTime)

        val addEventButton = dialogView.findViewById<CardView>(R.id.addEventButton)
        val cancelEventButton = dialogView.findViewById<CardView>(R.id.cancelEventButton)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addEventButton.setOnClickListener {
            val eventName =  editTextEventName.text.toString()
            val eventTime =  editTextEventTime.text.toString()
            if (eventName.isNotEmpty()) {
                saveEvent(eventName, date, eventTime)
            }
            alertDialog.dismiss()
        }

        cancelEventButton.setOnClickListener {
            alertDialog.dismiss()
        }


    }

    private fun saveEvent(eventName: String, date: Date, eventTime: String) {
        var databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get().addOnSuccessListener {
                data ->
            var name = data.get("name").toString()
            var city = data.get("city").toString()
            var gu = data.get("gu").toString()

            println(date)
            var alarm = hashMapOf(
                "date" to date.toString(),
                "dateString" to (date.year + 1900).toString() + " 년 " + (date.month+1).toString() + " 월 " +  date.date.toString() + " 일",
                "crewName" to name,
                "city" to city,
                "gu" to gu,
                "eventName" to eventName,
                "eventTime" to eventTime
            )

            addAlarm(alarm)
        }
        val newEvent = Event("일정 : " + eventName, date, "시간 : " + eventTime)
        setEventForDate(date, newEvent)
        notifyDataSetChanged()
    }

    fun addAlarm(alarm: HashMap<String, String>) {
        var databaseR = Firebase.firestore
        databaseR.collection("alarms").add(alarm).addOnSuccessListener {
            println("성공")
        }.addOnFailureListener{
            println("실패")
            println(it)
        }
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }
}