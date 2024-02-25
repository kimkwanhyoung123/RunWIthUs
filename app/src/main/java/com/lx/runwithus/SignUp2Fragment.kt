package com.lx.runwithus

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.databinding.FragmentSignUp2Binding


class SignUp2Fragment : Fragment() {

    var _binding: FragmentSignUp2Binding? = null
    val binding get() = _binding!!
    var listener: OnFragmentSignUpListener? = null


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentSignUpListener) {
            listener = activity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUp2Binding.inflate(inflater, container, false)


        binding.heightInput.setOnClickListener {
            NumberPickerDialog().apply {
                setListener { value -> binding.heightInput.setText(value.toString()) }
            }.show(childFragmentManager, "numberPicker")
        }
        binding.weightInput.setOnClickListener {
            NumberPickerDialog().apply {
                setListener { value -> binding.weightInput.setText(value.toString()) }
                setPickerRange(35, 120)  // 몸무게 범위는 35kg ~ 120kg로 설정
            }.show(childFragmentManager, "numberPicker")
        }
        binding.levelInput.setOnClickListener {
            LevelPickerDialog().apply {
                setListener { value -> binding.levelInput.setText(value) }
            }.show(childFragmentManager, "levelPicker")
        }

        binding.cycleInput.setOnClickListener {
            CyclePickerDialog().apply {
                setListener { value -> binding.cycleInput.setText(value) }
            }.show(childFragmentManager, "cyclePicker")
        }

        binding.cityInput.setOnClickListener {
            CityPickerDialog().apply {
                setListener { value -> binding.cityInput.setText(value)
                    binding.guInput.setText("")
                    println(value)
                }
            }.show(childFragmentManager, "cityPicker")
        }

        binding.guInput.setOnClickListener {
            if(binding.cityInput.text.toString() == "서울특별시") {
                GuPickerDialog1().apply {
                    setListener { value -> binding.guInput.setText(value) }
                }.show(childFragmentManager, "guPicker")
            } else if(binding.cityInput.text.toString() == "부산광역시") {
                GuPickerDialog2().apply {
                    setListener { value -> binding.guInput.setText(value) }
                }.show(childFragmentManager, "guPicker")
            }
        }


        binding.gobackStartButton.setOnClickListener {
            listener?.onFragmentChanged(FragmentType.SIGNUP1)
        }

        goMain()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    fun goMain() {
        binding.signUpCompleteButton.setOnClickListener {
            if (binding.heightInput.text.toString().trim().isNullOrEmpty() || binding.weightInput.text.toString().trim().isNullOrEmpty() || binding.levelInput.text.toString().trim().isNullOrEmpty()
                || binding.cycleInput.text.toString().trim().isNullOrEmpty() || binding.cityInput.text.toString().trim().isNullOrEmpty() || binding.guInput.text.toString().trim().isNullOrEmpty()) {

                binding.textViewPasswordMatch3.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorRed))
                binding.textViewPasswordMatch3.text = "아직 채워지지 않은 부분이 있습니다."
                binding.textViewPasswordMatch3.visibility = View.VISIBLE
            } else {
                addUserToDatabase()
            }

        }
    }

    class NumberPickerDialog : DialogFragment() {
        private var listener: ((Int) -> Unit)? = null
        private var minValue: Int = 130
        private var maxValue: Int = 200

        fun setListener(listener: (Int) -> Unit) {
            this.listener = listener
        }

        fun setPickerRange(min: Int, max: Int) {
            minValue = min
            maxValue = max
        }


        @SuppressLint("MissingInflatedId")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.number_picker_dialog, null)
            val picker = view.findViewById<NumberPicker>(R.id.number_picker).apply {
                minValue = this@NumberPickerDialog.minValue
                maxValue = this@NumberPickerDialog.maxValue
            }
            builder.setView(view)
                .setPositiveButton("확인") { _, _ -> listener?.invoke(picker.value) }
                .setNegativeButton("취소", null)

            return builder.create()
        }
    }
    class LevelPickerDialog : DialogFragment() {
        private var listener: ((String) -> Unit)? = null

        fun setListener(listener: (String) -> Unit) {
            this.listener = listener
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val levels = arrayOf("입문", "초급 (페이스 9 ~ 10분)", "중급 (페이스 7 ~ 8분)", "고급 (페이스 5 ~ 6분)")
            builder.setTitle("나의 러닝 레벨을 선택해주세요")
                .setItems(levels) { _, which ->
                    listener?.invoke(levels[which])
                }

            return builder.create()
        }
    }

    class CyclePickerDialog : DialogFragment() {
        private var listener: ((String) -> Unit)? = null

        fun setListener(listener: (String) -> Unit) {
            this.listener = listener
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val levels = arrayOf("주 1회", "주 2회", "주 3회", "주 4회", "주 5회","주 6회","주 7회")
            builder.setTitle("나의 러닝 주기를 선택해주세요")
                .setItems(levels) { _, which ->
                    listener?.invoke(levels[which])
                }

            return builder.create()
        }
    }

    class CityPickerDialog : DialogFragment() {
        private var listener: ((String) -> Unit)? = null

        fun setListener(listener: (String) -> Unit) {
            this.listener = listener
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val levels = arrayOf("서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시","대전광역시","울산광역시","세종특별자치시","경기도","강원특별자치도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주특별자치도")
            builder.setTitle("희망 운동 시를 선택해주세요")
                .setItems(levels) { _, which ->
                    listener?.invoke(levels[which])
                }

            return builder.create()
        }
    }

    class GuPickerDialog1 : DialogFragment() {
        private var listener: ((String) -> Unit)? = null

        fun setListener(listener: (String) -> Unit) {
            this.listener = listener
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val levels = arrayOf("종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구")
            builder.setTitle("희망 운동 구를 선택해주세요")
                .setItems(levels) { _, which ->
                    listener?.invoke(levels[which])
                }

            return builder.create()
        }
    }

    class GuPickerDialog2 : DialogFragment() {
        private var listener: ((String) -> Unit)? = null

        fun setListener(listener: (String) -> Unit) {
            this.listener = listener
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val levels = arrayOf("중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구", "기장군")
            builder.setTitle("희망 운동 구를 선택해주세요")
                .setItems(levels) { _, which ->
                    listener?.invoke(levels[which])
                }

            return builder.create()
        }
    }

    fun addUserToDatabase() {
        var power = ""
        if (binding.levelInput.text.toString().trim() == "초급 (페이스 9 ~ 10분)") {
            power = "초급"
        } else if (binding.levelInput.text.toString().trim() == "중급 (페이스 7 ~ 8분)") {
            power = "중급"
        } else if (binding.levelInput.text.toString().trim() == "고급 (페이스 5 ~ 6분)") {
            power = "고급"
        } else {
            power = "입문"
        }
        val user = hashMapOf(
            "id" to AppData.email,
            "password" to AppData.password,
            "nickname" to AppData.nickname,
            "birth" to AppData.birth,
            "weight" to binding.weightInput.text.toString().trim(),
            "height" to binding.heightInput.text.toString().trim(),
            "city" to binding.cityInput.text.toString().trim(),
            "gu" to binding.guInput.text.toString().trim(),
            "power" to power,
            "cycle" to binding.cycleInput.text.toString().trim(),
            "photo" to "26"

            )
        val databaseR = Firebase.firestore
        databaseR.collection("users").add(user).addOnSuccessListener {
                documentReference ->
            Log.d(tag, "성공 : ${documentReference.id}")
            val activity = activity
            if (activity != null) {
                val intent = Intent(activity, LoginActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }.addOnFailureListener{
                e ->
            Log.d(tag, "실패", e)
            Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
        }
    }


}