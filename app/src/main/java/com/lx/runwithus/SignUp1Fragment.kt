package com.lx.runwithus

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import com.google.common.base.Strings.isNullOrEmpty
import com.google.firebase.Timestamp
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.AppData.Companion.birth
import com.lx.runwithus.AppData.Companion.email
import com.lx.runwithus.databinding.FragmentSignUp1Binding
import java.util.Locale
import java.util.regex.Pattern


class SignUp1Fragment : Fragment() {

    var _binding: FragmentSignUp1Binding? = null
    val binding get() = _binding!!
    var listener: OnFragmentSignUpListener? = null
    var birth:String? = null
    var index:Boolean = true

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentSignUpListener) {
            listener = activity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUp1Binding.inflate(inflater, container, false)

        val radioButtonMale = binding.radioButtonMale
        val radioButtonFemale = binding.radioButtonFemale
        val colorAccent = ContextCompat.getColor(requireActivity(), R.color.colorAccent)
        radioButtonMale.buttonTintList = ColorStateList.valueOf(colorAccent)
        radioButtonFemale.buttonTintList = ColorStateList.valueOf(colorAccent)
        val btnDatePicker = binding.btnDatePicker


        confirmPassword()
        gobackStart()
        goNext()

        // DatePicker 버튼 클릭 시 DatePickerDialog를 표시
        btnDatePicker.setOnClickListener {
            showDatePicker()
        }

        return binding.root
    }

    // DatePickerDialog를 표시하는 함수
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // 선택된 날짜를 EditText에 표시
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
                val dateString = dateFormat.format(selectedDate.time)
                birth = (selectedDate.time.year+1900).toString() + (selectedDate.time.month+1).toString() + (selectedDate.time.date).toString()

                binding.signupBirthday.setText(dateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun confirmPassword() {
        val PasswordMatch = binding.textViewPasswordMatch

        // 비밀번호 재입력 EditText에 TextWatcher 설정
        binding.passwordConfirm.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var signupPassword = binding.signupPassword.text.toString().trim()

                // 입력이 시작되면 TextView를 보이게 함
                PasswordMatch.visibility = View.VISIBLE

                // 비밀번호와 비밀번호 재입력이 일치하는지 확인
                if(signupPassword != s.toString().trim()) {
                    PasswordMatch.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorRed))
                    PasswordMatch.text = "비밀번호가 일치하지 않습니다."
                    index = true
                } else if(signupPassword.length < 8) {
                    PasswordMatch.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorRed))
                    PasswordMatch.text = "비밀번호가 너무 짧습니다."
                    index = true
                } else {
                    PasswordMatch.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorGreen))
                    PasswordMatch.text = "비밀번호가 일치합니다."
                    index = false
                }
            }
        })

        binding.signupPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var signupPassword = binding.signupPassword.text.toString().trim()
                var confirmPassword = binding.passwordConfirm.text.toString().trim()


                // 비밀번호와 비밀번호 재입력이 일치하는지 확인
                if(confirmPassword != s.toString().trim()) {
                    PasswordMatch.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorRed))
                    PasswordMatch.text = "비밀번호가 일치하지 않습니다."
                    index = true
                } else if(signupPassword.length < 8) {
                    PasswordMatch.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.colorRed
                        )
                    )
                    PasswordMatch.text = "비밀번호가 너무 짧습니다."
                    index = true
                } else {
                    // 비밀번호가 일치하지 않으면, 메시지를 빨간색으로 표시
                    PasswordMatch.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorGreen))
                    PasswordMatch.text = "비밀번호가 일치합니다."
                    index = false
                }
            }
        })
    }

    fun gobackStart() {
        binding.gobackStartButton.setOnClickListener {
            val intent = Intent(requireActivity(), AgreementActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun goNext() {
        binding.nextSignUpButton.setOnClickListener {
            email = binding.signupId.text.toString().trim()
            AppData.password = binding.signupPassword.text.toString().trim()
            AppData.nickname = binding.signupNickname.text.toString().trim()




            if (email.isNullOrEmpty() || AppData.password.isNullOrEmpty() || AppData.nickname.isNullOrEmpty() || binding.signupBirthday.text.toString().trim().isNullOrEmpty() ) {
                binding.textViewPasswordMatch2.visibility = View.VISIBLE
                binding.textViewPasswordMatch2.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorRed
                    )
                )
                binding.textViewPasswordMatch2.text = "아직 채우지 않은 부분이 있습니다."
            } else if(binding.signupPassword.length() < 8 || index) {
                binding.textViewPasswordMatch2.visibility = View.VISIBLE
                binding.textViewPasswordMatch2.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorRed
                    )
                )
                binding.textViewPasswordMatch2.text = "비밀번호를 다시 적어주세요."
            } else if(!Patterns.EMAIL_ADDRESS.matcher(binding.signupId.text.toString().trim()).matches()) {
                binding.textViewPasswordMatch2.visibility = View.VISIBLE
                binding.textViewPasswordMatch2.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorRed
                    )
                )
                binding.textViewPasswordMatch2.text = "아이디가 이메일 형식에 맞지 않습니다."
            } else {
                AppData.birth = birth?.toInt()
                binding.textViewPasswordMatch2.visibility = View.INVISIBLE
                binding.textViewPasswordMatch2.text = ""
                listener?.onFragmentChanged(FragmentType.SIGNUP2)
            }

        }
    }

}